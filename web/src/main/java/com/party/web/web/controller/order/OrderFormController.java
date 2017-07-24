package com.party.web.web.controller.order;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.party.web.web.dto.AjaxResult;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Maps;
import com.party.common.paging.Page;
import com.party.common.utils.LangUtils;
import com.party.core.model.BaseModel;
import com.party.core.model.activity.Activity;
import com.party.core.model.crowdfund.Project;
import com.party.core.model.goods.Goods;
import com.party.core.model.goods.GoodsCoupons;
import com.party.core.model.member.MemberMerchant;
import com.party.core.model.member.MerchantUtil;
import com.party.core.model.order.OrderForm;
import com.party.core.model.order.OrderStatus;
import com.party.core.model.order.OrderType;
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
import com.party.web.biz.order.OrderBizService;
import com.party.web.utils.RealmUtils;
import com.party.web.utils.excel.ExportExcel;
import com.party.web.web.dto.input.common.CommonInput;
import com.party.web.web.dto.output.order.OrderFormOutput;

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

	/**
	 * 订单列表
	 * 
	 * @param page
	 * @param distributionId
	 * @param timeType
	 * @param c_start
	 * @param c_end
	 * @return
	 */
	@RequestMapping(value = "orderList")
	public ModelAndView orderList(OrderForm orderForm, Page page, @RequestParam(required = false) String distributionId, CommonInput commonInput) {
		ModelAndView mv = new ModelAndView("order/orderList");
		page.setLimit(20);
		orderForm.setInitiatorId(RealmUtils.getCurrentUser().getId());
		orderForm.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
		Map<String, Object> params = commonBiz(commonInput, mv);
		params.put("distributionId", distributionId);
		params.put("isCrowdfund", "0");
		// 订单类型（只查活动，众筹支持订单）
		Set<Integer> orderTypes = new HashSet<Integer>();
		orderTypes.add(OrderType.ORDER_ACTIVITY.getCode());
		orderTypes.add(OrderType.ORDER_CROWD_FUND.getCode());
		params.put("types", orderTypes);
		List<OrderForm> orderForms = orderFormService.webListPage(orderForm, params, page);
		List<OrderFormOutput> orderFormOutputs = LangUtils.transform(orderForms, input -> {
			String label = OrderType.getValue(input.getType());
			OrderFormOutput orderFormOutput = OrderFormOutput.transform(input);
			orderFormOutput.setTypeName(label);
			return orderFormOutput;
		});
		mv.addObject("page", page);
		mv.addObject("orderForms", orderFormOutputs);
		mv.addObject("totalAccount", "");
		Map<Integer, String> orderStatus = Maps.newHashMap();
		orderStatus.put(OrderStatus.ORDER_STATUS_TO_BE_PAID.getCode(), OrderStatus.ORDER_STATUS_TO_BE_PAID.getValue());
		orderStatus.put(OrderStatus.ORDER_STATUS_HAVE_PAID.getCode(), OrderStatus.ORDER_STATUS_HAVE_PAID.getValue());
		orderStatus.put(OrderStatus.ORDER_STATUS_REFUND.getCode(), OrderStatus.ORDER_STATUS_REFUND.getValue());
		mv.addObject("orderStatus", orderStatus);
		mv.addObject("distributionId", distributionId);
		return mv;
	}

	public Map<String, Object> commonBiz(CommonInput commonInput, ModelAndView mv) {
		Map<String, Object> params = CommonInput.appendParams(commonInput);
		mv.addObject("input", commonInput);

		Set<Integer> status = new HashSet<Integer>();
		status.add(OrderStatus.ORDER_STATUS_TO_BE_PAID.getCode());
		status.add(OrderStatus.ORDER_STATUS_HAVE_PAID.getCode());
		status.add(OrderStatus.ORDER_STATUS_REFUND.getCode());
		params.put("status", status);

		Map<Integer, String> orderStatus = Maps.newHashMap();
		orderStatus.put(OrderStatus.ORDER_STATUS_TO_BE_PAID.getCode(), OrderStatus.ORDER_STATUS_TO_BE_PAID.getValue());
		orderStatus.put(OrderStatus.ORDER_STATUS_HAVE_PAID.getCode(), OrderStatus.ORDER_STATUS_HAVE_PAID.getValue());
		orderStatus.put(OrderStatus.ORDER_STATUS_REFUND.getCode(), OrderStatus.ORDER_STATUS_REFUND.getValue());
		orderStatus.put(OrderStatus.ORDER_STATUS_OTHER.getCode(), OrderStatus.ORDER_STATUS_OTHER.getValue());
		mv.addObject("orderStatus", orderStatus);
		return params;
	}

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
		mv.addObject("orderForm", orderFormOutput);
		return mv;
	}

	/**
	 * 订单导出
	 * 
	 * @param orderForm
	 * @param page
	 * @param distributionId
	 * @param commonInput
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "export", method = RequestMethod.POST)
	public String exportFile(OrderForm orderForm, Page page, @RequestParam(required = false) String distributionId, CommonInput commonInput,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String fileName = "订单" + sdf.format(new Date()) + ".xlsx";
			orderForm.setInitiatorId(RealmUtils.getCurrentUser().getId());
			orderForm.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
			Map<String, Object> params = commonBiz(commonInput, new ModelAndView());
			params.put("distributionId", distributionId);
			params.put("isCrowdfund", "0");
			List<OrderForm> orderForms = orderFormService.webListPage(orderForm, params, null);
			List<OrderFormOutput> orderFormOutputs = LangUtils.transform(orderForms, input -> {
				String label = OrderType.getValue(input.getType());
				OrderFormOutput orderFormOutput = OrderFormOutput.transform(input);
				orderFormOutput.setTypeName(label);
				return orderFormOutput;
			});
			new ExportExcel("", OrderFormOutput.class).setDataList(orderFormOutputs).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
