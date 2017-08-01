package com.party.web.web.controller.order;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.party.common.paging.Page;
import com.party.common.utils.LangUtils;
import com.party.core.model.BaseModel;
import com.party.core.model.activity.Activity;
import com.party.core.model.charge.PackageMember;
import com.party.core.model.charge.ProductPackage;
import com.party.core.model.crowdfund.Project;
import com.party.core.model.goods.Goods;
import com.party.core.model.goods.GoodsCoupons;
import com.party.core.model.member.Member;
import com.party.core.model.order.OrderForm;
import com.party.core.model.order.OrderStatus;
import com.party.core.model.order.OrderType;
import com.party.core.model.order.PaymentWay;
import com.party.core.model.system.SysRole;
import com.party.core.service.activity.IActivityService;
import com.party.core.service.activity.OrderActivityBizService;
import com.party.core.service.charge.IPackageService;
import com.party.core.service.crowdfund.IProjectService;
import com.party.core.service.goods.IGoodsCouponsService;
import com.party.core.service.goods.IGoodsService;
import com.party.core.service.member.IMemberMerchantService;
import com.party.core.service.member.impl.MemberService;
import com.party.core.service.order.IOrderFormService;
import com.party.web.biz.charge.PackageBizService;
import com.party.web.biz.order.OrderBizService;
import com.party.web.biz.wallet.WithdrawalsBizService;
import com.party.web.utils.RealmUtils;
import com.party.web.utils.excel.ExportExcel;
import com.party.web.web.dto.AjaxResult;
import com.party.web.web.dto.output.order.OrderFormOutput;
import com.party.web.web.dto.output.wallet.WithdrawalOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
	private IGoodsCouponsService goodsCouponsService;

	@Autowired
	private IActivityService activityService;

	@Autowired
	private IGoodsService goodsService;

	@Autowired
	private IProjectService projectService;

	@Autowired
	private OrderBizService orderBizService;

	@Autowired
	private IMemberMerchantService memberMerchantService;
	@Autowired
	private WithdrawalsBizService withdrawalsBizService;
	@Autowired
	private IPackageService packageService;
	@Autowired
	private PackageBizService packageBizService;
	@Autowired
    private OrderActivityBizService orderActivityBizService;

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

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
		// 获取商户名称
		String merchantName = orderActivityBizService.
				getMerchantName(orderForm.getMerchantId(), orderForm.getPaymentWay(), orderForm.getInitiatorId());
		orderFormOutput.setMerchantName(merchantName);
		mv.addObject("orderForm", orderFormOutput);
		return mv;
	}

	/**
	 * 个人中心 交易明细
	 * @param page
	 * @return
	 */
	@RequestMapping("tradeList")
	public ModelAndView tradeList(Page page) {
		ModelAndView mv = new ModelAndView("system/member/tradeList");
		page.setLimit(10);
		OrderForm orderForm = new OrderForm();
		orderForm.setMemberId(RealmUtils.getCurrentUser().getId());
		orderForm.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
		orderForm.setType(OrderType.ORDER_LEVEL.getCode());
		Map<String, Object> params = Maps.newHashMap();
		params.put("isCrowdfund", "0");
		List<OrderForm> orderForms = orderFormService.webListPage(orderForm, params, page);
		List<OrderFormOutput> orderFormOutputs = LangUtils.transform(orderForms, input -> {
			OrderFormOutput orderFormOutput = OrderFormOutput.transform(input);
			String label = OrderType.getValue(input.getType());
			orderFormOutput.setTypeName(label);
			return orderFormOutput;
		});
		mv.addObject("orderForms", orderFormOutputs);
		mv.addObject("page", page);

		memberIndexCommon(mv, null);
		return mv;
	}

	/**
	 * 个人中心 交易明细 导出
	 *
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("exportTradeOrder")
	public AjaxResult exportTradeOrder(HttpServletResponse response) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String fileName = "交易明细" + sdf.format(new Date()) + ".xlsx";
			OrderForm orderForm = new OrderForm();
			orderForm.setMemberId(RealmUtils.getCurrentUser().getId());
			orderForm.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
			orderForm.setType(OrderType.ORDER_LEVEL.getCode());
			Map<String, Object> params = Maps.newHashMap();
			params.put("isCrowdfund", "0");
			List<OrderForm> orderForms = orderFormService.webListPage(orderForm, params, null);
			List<OrderFormOutput> orderFormOutputs = LangUtils.transform(orderForms, input -> {
				OrderFormOutput orderFormOutput = OrderFormOutput.transform(input);
				// 获取商户名称
				String merchantName = orderActivityBizService.
						getMerchantName(input.getMerchantId(), input.getPaymentWay(), input.getInitiatorId());
				orderFormOutput.setMerchantName(merchantName);
				orderFormOutput.setPaymentWayName(PaymentWay.getValue(input.getPaymentWay()));
				orderFormOutput.setTypeName(OrderType.getValue(input.getType()));
				orderFormOutput.setStatusName(OrderStatus.getValue(input.getStatus()));
				return orderFormOutput;
			});
			new ExportExcel("", OrderFormOutput.class).setDataList(orderFormOutputs).write(response, fileName).dispose();
		} catch (Exception e) {
			logger.error("导出交易明细异常{}", e);
		}
		return null;
	}

	/**
	 * 个人中心 收益明细
	 *
	 * @return
	 */
	@RequestMapping("orderList")
	public ModelAndView orderList(Page page, OrderForm orderForm) {
		ModelAndView mv = new ModelAndView("system/member/orderList");
		page.setLimit(10);
		List<OrderFormOutput> orderFormOutputs = orderBizService.memberOrderList(page, orderForm);
		mv.addObject("orderForms", orderFormOutputs);
		mv.addObject("page", page);

		// 订单总额
		Double orderTotal = orderBizService.getOrderTotal(false, orderForm.getType());
		if (orderTotal != null) {
			mv.addObject("orderTotal", orderTotal);
		} else {
			mv.addObject("orderTotal", 0);
		}

		Map<Integer, String> orderTypes = Maps.newHashMap();
		orderTypes.put(OrderType.ORDER_ACTIVITY.getCode(), OrderType.ORDER_ACTIVITY.getValue());
		orderTypes.put(OrderType.ORDER_CROWD_FUND.getCode(), OrderType.ORDER_CROWD_FUND.getValue());
		mv.addObject("orderTypes", orderTypes);

		memberIndexCommon(mv, orderForm.getType());
		return mv;
	}

	/**
	 * 个人中心 提现明细
	 *
	 * @param page
	 * @return
	 */
	@RequestMapping("withdrawList")
	public ModelAndView withdrawList(Page page) {
		ModelAndView mv = new ModelAndView("system/member/withdrawList");
		page.setLimit(10);
		List<WithdrawalOutput> withdrawalOutputs = withdrawalsBizService.withdrawList(page);
		mv.addObject("withdrawals", withdrawalOutputs);
		mv.addObject("page", page);

		// 提现总额
		Double withdrawalTotal = orderBizService.getWithdrawalTotal();
		if (withdrawalTotal != null) {
			mv.addObject("withdrawalTotal", withdrawalTotal);
		} else {
			mv.addObject("withdrawalTotal", 0);
		}

		memberIndexCommon(mv, null);
		return mv;
	}

	private void memberIndexCommon(ModelAndView mv, Integer type) {
		String memberId = RealmUtils.getCurrentUser().getId();
		Member member = memberService.get(memberId);
		mv.addObject("member", member);
		// 余额
		double totalAccount = orderBizService.getTotalAccount(type);
		mv.addObject("totalPayment", totalAccount);

		List<SysRole> sysRoles = Lists.newArrayList();
		PackageMember packageMember = packageBizService.getPackageMember(memberId, sysRoles);
		mv.addObject("sysRoles", sysRoles);
		if (packageMember != null) {
			ProductPackage productPackage = packageService.get(packageMember.getLevelId());
			mv.addObject("productPackage", productPackage);
		}
		mv.addObject("packageMember", packageMember);
	}

}
