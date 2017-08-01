package com.party.web.web.controller.wallet;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.party.common.paging.Page;
import com.party.common.utils.BigDecimalUtils;
import com.party.common.utils.LangUtils;
import com.party.core.model.BaseModel;
import com.party.core.model.member.MemberMerchant;
import com.party.core.model.member.MerchantUtil;
import com.party.core.model.order.OrderForm;
import com.party.core.model.order.OrderStatus;
import com.party.core.model.order.OrderType;
import com.party.core.model.order.PaymentWay;
import com.party.core.model.wallet.Withdrawals;
import com.party.core.service.activity.OrderActivityBizService;
import com.party.core.service.member.IMemberMerchantService;
import com.party.core.service.order.IOrderFormService;
import com.party.core.service.wallet.IWithdrawalService;
import com.party.web.biz.order.OrderBizService;
import com.party.web.biz.wallet.WithdrawalsBizService;
import com.party.web.utils.RealmUtils;
import com.party.web.utils.WithdrawalStatus;
import com.party.web.utils.excel.ExportExcel;
import com.party.web.web.dto.output.order.OrderFormOutput;
import com.party.web.web.dto.output.wallet.WithdrawalOutput;

/**
 * 钱包
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/wallet")
public class WalletController {

	@Autowired
	private IOrderFormService orderFormService;
	@Autowired
	private OrderBizService orderBizService;
	@Autowired
	private IWithdrawalService withdrawalService;
	@Autowired
	private WithdrawalsBizService withdrawalsBizService;
	@Autowired
	private IMemberMerchantService memberMerchantService;
	@Autowired
    private OrderActivityBizService orderActivityBizService;

	/**
	 * 明细记录
	 * 
	 * @param page
	 * @return
	 */
	@RequestMapping("orderList")
	public ModelAndView orderList(Page page) {
		ModelAndView mv = new ModelAndView("wallet/orderList");
		page.setLimit(15);
		OrderForm orderForm = new OrderForm();
		orderForm.setInitiatorId(RealmUtils.getCurrentUser().getId());
		orderForm.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
		orderForm.setStatus(OrderStatus.ORDER_STATUS_HAVE_PAID.getCode()); // 已支付
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("isCrowdfund", "0");
		params.put("payment", 0);
		// 订单类型（只查活动，众筹支持订单）
		// Set<Integer> orderTypes = new HashSet<Integer>();
		// orderTypes.add(OrderType.ORDER_ACTIVITY.getCode());
		// orderTypes.add(OrderType.ORDER_CROWD_FUND.getCode());
		// params.put("types", orderTypes);

		// 余额
		double totalAccount = orderBizService.getTotalAccount(null);
		mv.addObject("totalPayment", totalAccount);

		// 订单总额
		Double orderTotal = orderBizService.getOrderTotal(false, null);
		if (orderTotal != null) {
			mv.addObject("orderTotal", orderTotal);
		} else {
			mv.addObject("orderTotal", 0);
		}

		List<OrderForm> orderForms = orderFormService.webListPage(orderForm, params, page);
		List<OrderFormOutput> orderFormOutputs = LangUtils.transform(orderForms, input -> {
			String label = OrderType.getValue(input.getType());
			OrderFormOutput orderFormOutput = OrderFormOutput.transform(input);
			orderFormOutput.setTypeName(label);
			return orderFormOutput;
		});
		mv.addObject("page", page);
		mv.addObject("orderForms", orderFormOutputs);
		return mv;
	}

	/**
	 * 提现记录
	 * 
	 * @param page
	 * @return
	 */
	@RequestMapping("withdrawalList")
	public ModelAndView withdrawalList(Page page) {
		ModelAndView mv = new ModelAndView("wallet/withdrawalList");
		page.setLimit(15);
		Withdrawals withdrawal = new Withdrawals();
		withdrawal.setCreateBy(RealmUtils.getCurrentUser().getId());
		withdrawal.setDelFlag(BaseModel.DEL_FLAG_NORMAL);

		// 余额
		double totalAccount = orderBizService.getTotalAccount(null);
		mv.addObject("totalPayment", totalAccount);

		// 提现总额
		Double withdrawalTotal = orderBizService.getWithdrawalTotal();
		if (withdrawalTotal != null) {
			mv.addObject("withdrawalTotal", withdrawalTotal);
		} else {
			mv.addObject("withdrawalTotal", 0);
		}

		List<Withdrawals> withdrawals = withdrawalService.listPage(withdrawal, page);
		List<WithdrawalOutput> withdrawalOutputs = LangUtils.transform(withdrawals, input -> {
			WithdrawalOutput output = WithdrawalOutput.transform(input);
			String account = input.getAccountNumber();
			account = withdrawalsBizService.formatAccountNumber(account);
			output.setAccountNumber(account);

			// 手续费
			double serviceFee = BigDecimalUtils.mul(input.getPayment(), 0.006);
			serviceFee = BigDecimalUtils.round(serviceFee, 2);
			// 净额
			double netAmount = BigDecimalUtils.sub(input.getPayment(), serviceFee);

			output.setServiceFee(serviceFee);
			output.setNetAmount(netAmount);
			return output;
		});
		mv.addObject("withdrawals", withdrawalOutputs);
		mv.addObject("page", page);
		return mv;
	}

	/**
	 * 提现记录导出
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "exportWithdrawal", method = RequestMethod.POST)
	public String exportWithdrawal(HttpServletRequest request, HttpServletResponse response) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String fileName = "提现明细" + sdf.format(new Date()) + ".xlsx";
			Withdrawals withdrawal = new Withdrawals();
			withdrawal.setCreateBy(RealmUtils.getCurrentUser().getId());
			withdrawal.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
			List<Withdrawals> withdrawals = withdrawalService.listPage(withdrawal, null);
			List<WithdrawalOutput> withdrawalOutputs = LangUtils.transform(withdrawals, input -> {
				WithdrawalOutput output = WithdrawalOutput.transform(input);
				String account = input.getAccountNumber();
				account = withdrawalsBizService.formatAccountNumber(account);
				output.setAccountNumber(account);

				// 手续费
				double serviceFee = BigDecimalUtils.mul(input.getPayment(), 0.006);
				serviceFee = BigDecimalUtils.round(serviceFee, 2);
				// 净额
				double netAmount = BigDecimalUtils.sub(input.getPayment(), serviceFee);

				output.setServiceFee(serviceFee);
				output.setNetAmount(netAmount);

				String value = WithdrawalStatus.getValue(input.getStatus());
				output.setStatus2(value);

				return output;
			});
			new ExportExcel("", WithdrawalOutput.class).setDataList(withdrawalOutputs).write(response, fileName).dispose();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 订单导出
	 * @param orderForm
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "exportOrder", method = RequestMethod.POST)
	public String exportOrder(OrderForm orderForm, HttpServletResponse response) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String fileName = "收益明细" + sdf.format(new Date()) + ".xlsx";
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
			e.printStackTrace();
		}
		return null;
	}
}
