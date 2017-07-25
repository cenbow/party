package com.party.admin.web.controller.order;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.party.admin.utils.RealmUtils;
import com.party.core.model.order.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.party.admin.biz.activity.OrderBizService;
import com.party.admin.utils.excel.ExportExcel;
import com.party.admin.web.dto.input.common.CommonInput;
import com.party.admin.web.dto.output.order.OrderFormOutput;
import com.party.common.constant.Constant;
import com.party.common.paging.Page;
import com.party.common.utils.LangUtils;
import com.party.core.model.BaseModel;
import com.party.core.model.activity.Activity;
import com.party.core.model.crowdfund.Project;
import com.party.core.model.crowdfund.TargetProject;
import com.party.core.model.goods.Goods;
import com.party.core.model.goods.GoodsCoupons;
import com.party.core.model.member.Member;
import com.party.core.model.member.MemberMerchant;
import com.party.core.model.member.MerchantUtil;
import com.party.core.model.system.Dict;
import com.party.core.service.activity.IActivityService;
import com.party.core.service.crowdfund.IProjectService;
import com.party.core.service.crowdfund.ITargetProjectService;
import com.party.core.service.goods.IGoodsCouponsService;
import com.party.core.service.goods.IGoodsService;
import com.party.core.service.member.IMemberMerchantService;
import com.party.core.service.member.impl.MemberService;
import com.party.core.service.order.IOrderFormService;
import com.party.core.service.order.IOrderRefundTradeService;
import com.party.core.service.order.IOrderTradeService;
import com.party.core.service.system.IDictService;
import com.party.core.service.user.IUserService;
import com.party.pay.model.query.TradeStatus;
import com.party.pay.model.refund.WechatPayRefundResponse;

/**
 * 订单
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/order/order")
public class OrderFormController {

	@Autowired
	private IOrderFormService orderFormService;

	@Autowired
	private MemberService memberService;

	@Autowired
	private IUserService userService;

	@Autowired
	private IDictService dictService;

	@Autowired
	private IGoodsCouponsService goodsCouponsService;

	@Autowired
	private IOrderTradeService orderTradeService;

	@Autowired
	private IOrderRefundTradeService orderRefundTradeService;
	
	@Autowired
	private IActivityService activityService;
	
	@Autowired
	private IGoodsService goodsService;
	
	@Autowired
	private IProjectService projectService;
	
    @Autowired
    private ITargetProjectService targetProjectService;
    
    @Autowired
    private OrderBizService orderBizService;
    
    @Autowired
    private IMemberMerchantService memberMerchantService;
	
	@RequestMapping(value = "goodsOrderList")
	public ModelAndView goodsOrderList(OrderForm orderForm, Page page, CommonInput commonInput) {
		ModelAndView mv = new ModelAndView("goods/orderList");
		try {
			page.setLimit(20);
			Map<String, Object> params = commonBiz(commonInput, mv);
			List<OrderForm> orderForms = orderFormService.webListPage(orderForm, params, page);
			List<OrderFormOutput> orderFormOutputs = LangUtils.transform(orderForms, input -> {
				OrderFormOutput orderFormOutput = OrderFormOutput.transform(input);
				return orderFormOutput;
			});
			Goods goods = goodsService.get(orderForm.getGoodsId());
			mv.addObject("goods", goods);
			mv.addObject("orderForm", orderForm);
			mv.addObject("orderForms", orderFormOutputs);
			mv.addObject("page", page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}

	/**
	 * 订单导出
	 * @param commonInput
	 * @param orderForm
	 * @param tradeStatus
	 * @param flag
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "exportOrder", method = RequestMethod.POST)
	public String exportOrder(CommonInput commonInput, OrderForm orderForm, String tradeStatus, boolean flag, HttpServletResponse response) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String fileName = "订单" + sdf.format(new Date()) + ".xlsx";
			orderForm.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
			Map<String, Object> params = CommonInput.appendParams(commonInput);
			params.put("isCrowdfund", "0");
			params.put("payment", 0);
			
			Set<String> tradeStatusSet = orderBizService.searchParams(orderForm, tradeStatus, flag, params);
			List<OrderForm> orderForms = orderFormService.webListPage(orderForm, params, null);
			if (tradeStatusSet.contains(TradeStatus.WX_REFUND.getCode())) {
				orderForm.setStatus(null);
			}
			List<MemberMerchant> merchants = memberMerchantService.list(new MemberMerchant());
			List<OrderFormOutput> orderFormOutputs = LangUtils.transform(orderForms, input -> {
				OrderFormOutput orderFormOutput = OrderFormOutput.transform(input);
				if (StringUtils.isNotEmpty(input.getMerchantId())) {
					if (input.getMerchantId().equals(MerchantUtil.TXZ_WWZ_MERCHANT_ID)
							|| input.getMerchantId().equals(MerchantUtil.TXZ_APP_MERCHANT_ID)) {
						orderFormOutput.setMerchantName(MerchantUtil.TXZ_MERCHANT_NAME);
					} else {
						for (MemberMerchant memberMerchant : merchants) {
							if (memberMerchant.getMerchantId().equals(input.getMerchantId())) {
								orderFormOutput.setMerchantName(merchants.get(0).getMerchantName());
								break;
							}
						}
					}
				}
				orderFormOutput.setPaymentWayName(PaymentWay.getValue(input.getPaymentWay()));
				orderFormOutput.setTypeName(OrderType.getValue(input.getType()));
				orderFormOutput.setStatusName(OrderStatus.getValue(input.getStatus()));
				return orderFormOutput;
			});
			new ExportExcel("", OrderFormOutput.class).setDataList(orderFormOutputs).write(response, fileName).dispose();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 订单列表
	 * @param orderForm
	 * @param tradeStatus
	 * @param page
	 * @param commonInput
	 * @param flag
	 * @return
	 */
	@RequestMapping(value = "orderList")
	public ModelAndView orderList(OrderForm orderForm, String tradeStatus, Page page, CommonInput commonInput, boolean flag) {
		ModelAndView mv = new ModelAndView("order/orderList");
		page.setLimit(20);

		orderForm.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
		Map<String, Object> params = commonBiz(commonInput, mv);
		params.put("isCrowdfund", "0");
		params.put("payment", 0);
		
		Set<String> tradeStatusSet = orderBizService.searchParams(orderForm, tradeStatus, flag, params);
		mv.addObject("tradeStatus", tradeStatus);
		List<OrderForm> orderForms = orderFormService.webListPage(orderForm, params, page);
		List<OrderFormOutput> orderFormOutputs = LangUtils.transform(orderForms, input -> {
			OrderFormOutput orderFormOutput = OrderFormOutput.transform(input);
			return orderFormOutput;
		});
		
		if (tradeStatusSet.contains(TradeStatus.WX_REFUND.getCode())) {
			orderForm.setStatus(null);
		}
		
		mv.addObject("page", page);
		mv.addObject("orderForms", orderFormOutputs);
		/**订单总额**/
		Double orderTotal = orderBizService.getOrderTotal(orderForm, params);
		mv.addObject("orderTotal", orderTotal == null ? 0 : orderTotal);
		/**订单总额**/
		Map<Integer, String> orderStatus = Maps.newHashMap();
		orderStatus.put(OrderStatus.ORDER_STATUS_TO_BE_PAID.getCode(), OrderStatus.ORDER_STATUS_TO_BE_PAID.getValue());
		orderStatus.put(OrderStatus.ORDER_STATUS_HAVE_PAID.getCode(), OrderStatus.ORDER_STATUS_HAVE_PAID.getValue());
		orderStatus.put(OrderStatus.ORDER_STATUS_REFUND.getCode(), OrderStatus.ORDER_STATUS_REFUND.getValue());
		mv.addObject("orderStatus", orderStatus);

		Map<Integer, String> orderTypes = OrderType.convertMap();
		mv.addObject("orderTypes", orderTypes);
		return mv;
	}


	/**
	 * 分销订单
	 * @param orderForm 订单信息
	 * @param page 分页参数
	 * @param distributionId 分销参数
	 * @param commonInput
	 * @return
	 */
	@RequestMapping(value = "distributorList")
	public ModelAndView distributorList(OrderForm orderForm, Page page, @RequestParam(required = false) String distributionId, CommonInput commonInput) {
		ModelAndView mv = new ModelAndView("order/distributorList");
		page.setLimit(20);
		orderForm.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
		Map<String, Object> params = commonBiz(commonInput, mv);
		params.put("distributionId", distributionId);
		List<OrderForm> orderForms = orderFormService.distributorListPage(orderForm, params, page);
		List<OrderFormOutput> orderFormOutputs = LangUtils.transform(orderForms, input -> {
			OrderFormOutput orderFormOutput = OrderFormOutput.transform(input);
			orderFormOutput.setTypeName(OrderType.getValue(input.getType()));
			Member member = memberService.get(input.getMemberId());
			orderFormOutput.setMember(member);
			return orderFormOutput;
		});
		mv.addObject("page", page);
		mv.addObject("orderForms", orderFormOutputs);

		/**订单总额**/
		Map<Integer, String> orderStatus = Maps.newHashMap();
		orderStatus.put(OrderStatus.ORDER_STATUS_TO_BE_PAID.getCode(), OrderStatus.ORDER_STATUS_TO_BE_PAID.getValue());
		orderStatus.put(OrderStatus.ORDER_STATUS_HAVE_PAID.getCode(), OrderStatus.ORDER_STATUS_HAVE_PAID.getValue());
		orderStatus.put(OrderStatus.ORDER_STATUS_REFUND.getCode(), OrderStatus.ORDER_STATUS_REFUND.getValue());
		mv.addObject("orderStatus", orderStatus);

		Dict dict = new Dict();
		dict.setType("OrderType");
		dict.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
		mv.addObject("orderTypes", dictService.list(dict));
		mv.addObject("distributionId", distributionId);
		return mv;
	}


	public Map<String, Object> commonBiz(CommonInput commonInput, ModelAndView mv) {
		Map<String, Object> params = CommonInput.appendParams(commonInput);
		mv.addObject("input", commonInput);
		Map<Integer, String> orderStatus = Maps.newHashMap();
		orderStatus.put(OrderStatus.ORDER_STATUS_TO_BE_PAID.getCode(), OrderStatus.ORDER_STATUS_TO_BE_PAID.getValue());
		orderStatus.put(OrderStatus.ORDER_STATUS_HAVE_PAID.getCode(), OrderStatus.ORDER_STATUS_HAVE_PAID.getValue());
		orderStatus.put(OrderStatus.ORDER_STATUS_REFUND.getCode(), OrderStatus.ORDER_STATUS_REFUND.getValue());
		orderStatus.put(OrderStatus.ORDER_STATUS_OTHER.getCode(), OrderStatus.ORDER_STATUS_OTHER.getValue());
		mv.addObject("orderStatus", orderStatus);
		return params;
	}

	/**
	 * 订单详情
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "orderInfo")
	public ModelAndView orderInfo(String id) {
		ModelAndView mv = new ModelAndView("order/orderInfo");
		OrderForm orderForm = orderFormService.get(id);
		OrderFormOutput orderFormOutput = OrderFormOutput.transform(orderForm);
		// 核销码
		GoodsCoupons t = new GoodsCoupons();
		t.setOrderId(orderForm.getId());
		List<GoodsCoupons> goodsCoupons = goodsCouponsService.list(t);
		orderFormOutput.setGoodsCoupons(goodsCoupons);

		if (orderForm.getType() == OrderType.ORDER_ACTIVITY.getCode()) {
			Activity activity = activityService.get(orderForm.getGoodsId());
			if (activity != null) {
				orderFormOutput.setPicture(activity.getPic());
			}
		} else if (orderForm.getType() == OrderType.ORDER_CROWD_FUND.getCode()) {
			Project project = projectService.get(orderForm.getGoodsId());
			if (project != null) {
				orderFormOutput.setPicture(project.getPic());
			}
		} else if (orderForm.getType() == OrderType.ORDER_NOMAL.getCode() || orderForm.getType() == OrderType.ORDER_CUSTOMIZED.getCode()) {
			Goods goods = goodsService.get(orderForm.getGoodsId());
			if (goods != null) {
				orderFormOutput.setPicture(goods.getPicsURL());
			}
		}
		
		if (StringUtils.isNotEmpty(orderForm.getMerchantId())) {
			if (orderForm.getMerchantId().equals(MerchantUtil.TXZ_WWZ_MERCHANT_ID)
					|| orderForm.getMerchantId().equals(MerchantUtil.TXZ_APP_MERCHANT_ID)) {
				orderFormOutput.setMerchantName(MerchantUtil.TXZ_MERCHANT_NAME);
			} else {
				List<MemberMerchant> memberMerchantList = memberMerchantService.findByMerchantId(orderForm.getMerchantId());
				if (!CollectionUtils.isEmpty(memberMerchantList) && null != memberMerchantList.get(0)) {
					orderFormOutput.setMerchantName(memberMerchantList.get(0).getMerchantName());
				}
			}
		}

		//交易流水
		OrderRefundTrade orderRefundTrade = orderRefundTradeService.recentlyByOrderId(id);
		WechatPayRefundResponse response = new WechatPayRefundResponse();
		if (null != orderRefundTrade && !Strings.isNullOrEmpty(orderRefundTrade.getData())){
			response = JSONObject.toJavaObject(JSONObject.parseObject(orderRefundTrade.getData()), WechatPayRefundResponse.class);
		}

		mv.addObject("response", response);
		mv.addObject("orderForm", orderFormOutput);
		return mv;
	}
	
	/**
	 * 更新业务发起者编号
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateOrderFormInitiatorId")
	public String updateOrderFormInitiatorId(){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("initiatorId", "1");
		List<OrderForm> orderForms = orderFormService.webListPage(new OrderForm(), params, null);
		for (OrderForm orderForm : orderForms) {
			if (StringUtils.isEmpty(orderForm.getInitiatorId())) {
				if (orderForm.getType() == OrderType.ORDER_ACTIVITY.getCode()) {
					Activity activity = activityService.get(orderForm.getGoodsId());
					if (activity != null) {
						orderForm.setInitiatorId(activity.getMember());
						orderFormService.update(orderForm);
					}
				} else if (orderForm.getType() == OrderType.ORDER_CROWD_FUND.getCode()) {
					System.err.println(orderForm.getGoodsId());
					TargetProject targetProject = targetProjectService.findByProjectId(orderForm.getGoodsId());
					if (targetProject != null && targetProject.getType().equals(Constant.CROWD_FUND_TYPE_ACTIVITY)) {
						Activity activity = activityService.get(targetProject.getTargetId());
						orderForm.setInitiatorId(activity.getMember()); // 众筹项目发起者
						orderFormService.update(orderForm);
					}
				} else if (orderForm.getType() == OrderType.ORDER_NOMAL.getCode() || orderForm.getType() == OrderType.ORDER_CUSTOMIZED.getCode()) {
					Goods goods = goodsService.get(orderForm.getGoodsId());
					if (goods != null) {
						orderForm.setInitiatorId(goods.getMemberId());
						orderFormService.update(orderForm);
					}
				}
			}
		}
		return null;
	}


	/**
	 * 退款交易信息
	 * @param orderId 订单号
	 * @return 交互数据
	 */
	@RequestMapping(value = "refundInfo/{orderId}")
	public ModelAndView refundInfo(@PathVariable String orderId){
		ModelAndView modelAndView = new ModelAndView("order/refundInfo");
		OrderRefundTrade orderRefundTrade = orderRefundTradeService.recentlyByOrderId(orderId);
		WechatPayRefundResponse response = new WechatPayRefundResponse();
		if (null != orderRefundTrade && !Strings.isNullOrEmpty(orderRefundTrade.getData())){
			response = JSONObject.toJavaObject(JSONObject.parseObject(orderRefundTrade.getData()), WechatPayRefundResponse.class);
		}
		modelAndView.addObject("response", response);
		return modelAndView;
	}

	/**
	 * 合作商订单列表
	 * @param mmId
	 * @param page
	 * @return
	 */
	@RequestMapping("memberOrderList")
	public ModelAndView memberOrderList(String mmId, Page page, OrderForm orderForm) {
		page.setLimit(20);
		ModelAndView mv = new ModelAndView("system/member/orderList");
		orderForm.setInitiatorId(mmId);
		orderForm.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
		orderForm.setStatus(OrderStatus.ORDER_STATUS_HAVE_PAID.getCode()); // 已支付
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("isCrowdfund", "0");
		params.put("payment", 0);
		List<OrderForm> orderForms = orderFormService.webListPage(orderForm, params, page);
		List<OrderFormOutput> orderFormOutputs = LangUtils.transform(orderForms, input -> {
			OrderFormOutput orderFormOutput = OrderFormOutput.transform(input);
			String label = OrderType.getValue(input.getType());
			orderFormOutput.setTypeName(label);
			return orderFormOutput;
		});
		mv.addObject("orderForms", orderFormOutputs);
		mv.addObject("page", page);
		mv.addObject("orderForm", orderForm);

		Member member = memberService.get(mmId);
		mv.addObject("member", member);

		// 余额
		double totalAccount = orderBizService.getTotalAccount(mmId);
		mv.addObject("totalPayment", totalAccount);

		Map<Integer, String> orderTypes = Maps.newHashMap();
		orderTypes.put(OrderType.ORDER_ACTIVITY.getCode(), OrderType.ORDER_ACTIVITY.getValue());
		orderTypes.put(OrderType.ORDER_CROWD_FUND.getCode(), OrderType.ORDER_CROWD_FUND.getValue());
		mv.addObject("orderTypes", orderTypes);
		return mv;
	}

	/**
	 * 订单导出
	 *
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "exportMemberOrder", method = RequestMethod.POST)
	public String exportMemberOrder(HttpServletResponse response) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String fileName = "订单" + sdf.format(new Date()) + ".xlsx";
			OrderForm orderForm = new OrderForm();
			orderForm.setInitiatorId(RealmUtils.getCurrentUser().getId());
			orderForm.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
			orderForm.setStatus(OrderStatus.ORDER_STATUS_HAVE_PAID.getCode()); // 已支付
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("isCrowdfund", "0");
			params.put("payment", 0);
			// 订单类型（只查活动，众筹支持订单）
			Set<Integer> orderTypes = new HashSet<Integer>();
			// orderTypes.add(OrderType.ORDER_ACTIVITY.getCode());
			// orderTypes.add(OrderType.ORDER_CROWD_FUND.getCode());
			params.put("types", orderTypes);
			params.put("txzMerchantId", MerchantUtil.TXZ_WWZ_MERCHANT_ID);
			List<OrderForm> orderForms = orderFormService.webListPage(orderForm, params, null);
			List<MemberMerchant> merchants = memberMerchantService.list(new MemberMerchant());
			MemberMerchant merchant = memberMerchantService.findByMemberId(RealmUtils.getCurrentUser().getId());
			List<OrderFormOutput> orderFormOutputs = LangUtils.transform(orderForms, input -> {
				OrderFormOutput orderFormOutput = OrderFormOutput.transform(input);
				if (com.party.common.utils.StringUtils.isNotEmpty(input.getMerchantId())) {
					if (input.getMerchantId().equals(MerchantUtil.TXZ_WWZ_MERCHANT_ID)
							|| input.getMerchantId().equals(MerchantUtil.TXZ_APP_MERCHANT_ID)) {
						orderFormOutput.setMerchantName(MerchantUtil.TXZ_MERCHANT_NAME);
					} else if (merchant != null && merchant.getMerchantId().equals(input.getMerchantId())) {
						orderFormOutput.setMerchantName(merchant.getMerchantName());
					} else {
						for (MemberMerchant memberMerchant : merchants) {
							if (memberMerchant.getMerchantId().equals(input.getMerchantId())) {
								orderFormOutput.setMerchantName(merchants.get(0).getMerchantName());
								break;
							}
						}
					}
				}
				orderFormOutput.setPaymentWayName(PaymentWay.getValue(input.getPaymentWay()));
				orderFormOutput.setTypeName(OrderType.getValue(input.getType()));
				orderFormOutput.setStatusName(OrderStatus.getValue(input.getStatus()));
				return orderFormOutput;
			});
			new ExportExcel("", OrderFormOutput.class).setDataList(orderFormOutputs).write(response, fileName).dispose();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
