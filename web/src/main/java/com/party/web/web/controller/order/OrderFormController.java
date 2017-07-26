package com.party.web.web.controller.order;

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
import com.party.core.model.member.MemberMerchant;
import com.party.core.model.member.MerchantUtil;
import com.party.core.model.order.OrderForm;
import com.party.core.model.order.OrderStatus;
import com.party.core.model.order.OrderType;
import com.party.core.model.order.PaymentWay;
import com.party.core.model.system.SysRole;
import com.party.core.service.activity.IActivityService;
import com.party.core.service.charge.IPackageMemberService;
import com.party.core.service.charge.IPackageService;
import com.party.core.service.crowdfund.IProjectService;
import com.party.core.service.goods.IGoodsCouponsService;
import com.party.core.service.goods.IGoodsService;
import com.party.core.service.member.IMemberMerchantService;
import com.party.core.service.member.impl.MemberService;
import com.party.core.service.order.IOrderFormService;
import com.party.core.service.system.ISysRoleService;
import com.party.web.biz.order.OrderBizService;
import com.party.web.biz.wallet.WithdrawalsBizService;
import com.party.web.utils.RealmUtils;
import com.party.web.utils.excel.ExportExcel;
import com.party.web.web.dto.AjaxResult;
import com.party.web.web.dto.output.order.OrderFormOutput;
import com.party.web.web.dto.output.wallet.WithdrawalOutput;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

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
	private IPackageMemberService packageMemberService;
	@Autowired
	private IPackageService packageService;
	@Autowired
	private ISysRoleService sysRoleService;

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

		memberIndexCommon(mv);
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
		Double orderTotal = orderBizService.getOrderTotal(false);
		if (orderTotal != null) {
			mv.addObject("orderTotal", orderTotal);
		} else {
			mv.addObject("orderTotal", 0);
		}

		Map<Integer, String> orderTypes = Maps.newHashMap();
		orderTypes.put(OrderType.ORDER_ACTIVITY.getCode(), OrderType.ORDER_ACTIVITY.getValue());
		orderTypes.put(OrderType.ORDER_CROWD_FUND.getCode(), OrderType.ORDER_CROWD_FUND.getValue());
		mv.addObject("orderTypes", orderTypes);

		memberIndexCommon(mv);
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

		memberIndexCommon(mv);
		return mv;
	}

	private void memberIndexCommon(ModelAndView mv) {
		String memberId = RealmUtils.getCurrentUser().getId();
		Member member = memberService.get(memberId);
		mv.addObject("member", member);
		// 余额
		double totalAccount = orderBizService.getTotalAccount();
		mv.addObject("totalPayment", totalAccount);

		Map<String, Object> params = Maps.newHashMap();
		params.put("type", "1");
		params.put("memberId", memberId);
		List<SysRole> sysRoles = sysRoleService.getRoleByMemberId(params);
		mv.addObject("sysRoles", sysRoles);

		PackageMember packageMember = packageMemberService.findByMemberId(new PackageMember("", memberId));
		if (packageMember == null) {
			ProductPackage t = new ProductPackage();
			t.setType(1);
			List<ProductPackage> packages = packageService.list(t);
			List<ProductPackage> newPackges = new ArrayList<ProductPackage>();
			for (ProductPackage p : packages) {
				boolean flag = false;
				for (SysRole r : sysRoles) {
					if (p.getSysRoleId().equals(r.getId())) {
						flag = true;
						break;
					}
				}
				if (flag) {
					newPackges.add(p);
				}
			}
			if (newPackges.size() > 0) {
				Collections.sort(newPackges, new Comparator<ProductPackage>() {
					@Override
					public int compare(ProductPackage o1, ProductPackage o2) {
						return o1.getLevel() > o2.getLevel() ? -1 : 1;
					}
				});

				packageMember = new PackageMember();
				packageMember.setLevelId(newPackges.get(0).getId());
				packageMember.setSysRoleId(newPackges.get(0).getSysRoleId());
			}
		}

		if (packageMember != null) {
			ProductPackage productPackage = packageService.get(packageMember.getLevelId());
			mv.addObject("productPackage", productPackage);
		}
		mv.addObject("packageMember", packageMember);
	}

}
