package com.party.web.biz.order;

import com.google.common.collect.Lists;
import com.party.common.paging.Page;
import com.party.common.utils.BigDecimalUtils;
import com.party.common.utils.LangUtils;
import com.party.core.model.BaseModel;
import com.party.core.model.member.MerchantUtil;
import com.party.core.model.order.OrderForm;
import com.party.core.model.order.OrderStatus;
import com.party.core.model.order.OrderType;
import com.party.core.model.wallet.Withdrawals;
import com.party.core.service.order.IOrderFormService;
import com.party.core.service.wallet.IWithdrawalService;
import com.party.web.utils.RealmUtils;
import com.party.web.utils.WithdrawalStatus;
import com.party.web.web.dto.output.order.OrderFormOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.*;

@Service
public class OrderBizService {

	@Autowired
	private IOrderFormService orderFormService;

	@Autowired
	private IWithdrawalService withdrawalService;

	final DecimalFormat COMMA_FORMAT = new DecimalFormat("#,###.##");

	/**
	 * 活动，众筹已支付的订单总额
	 * @return
	 */
	public Double getOrderTotal(boolean isTxz) {
		OrderForm orderForm = new OrderForm();
		orderForm.setInitiatorId(RealmUtils.getCurrentUser().getId());
		orderForm.setStatus(OrderStatus.ORDER_STATUS_HAVE_PAID.getCode()); // 已支付
		orderForm.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
		Map<String, Object> params = new HashMap<String, Object>();
		Set<Integer> orderTypes = new HashSet<Integer>();
//		orderTypes.add(OrderType.ORDER_ACTIVITY.getCode()); // 活动
//		orderTypes.add(OrderType.ORDER_CROWD_FUND.getCode()); // 众筹

		if (isTxz) {
			Set<String> merchants = new HashSet<String>();
			merchants.add(MerchantUtil.TXZ_WWZ_MERCHANT_ID);
			merchants.add(MerchantUtil.TXZ_APP_MERCHANT_ID);
			params.put("txzMerchantId", merchants);
		}

		params.put("types", orderTypes);
		params.put("isCrowdfund", 0);
		params.put("payment", 0);
		return orderFormService.getTotalPayment(orderForm, params);
	}

	/**
	 * 提现总额
	 * @return
	 */
	public Double getWithdrawalTotal() {
		Withdrawals withdrawal = new Withdrawals();
		withdrawal.setCreateBy(RealmUtils.getCurrentUser().getId());
		withdrawal.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
		Map<String, Object> params = new HashMap<String, Object>();
		Set<Integer> status = new HashSet<Integer>();
		status.add(WithdrawalStatus.STATUS_HAVE_PAID.getCode()); // 已付款
		status.add(WithdrawalStatus.STATUS_IN_REVIEW.getCode()); // 处理中
		params.put("status", status);
		return withdrawalService.getTotalPayment(withdrawal, params);
	}

	public String getFormatText(Double value) {
		value = BigDecimalUtils.round(value, 2);
		return COMMA_FORMAT.format(value);
	}

	public String getFormatText(Float value) {
		value = BigDecimalUtils.round(value, 2);
		return COMMA_FORMAT.format(value);
	}

	/**
	 * 获取余额
	 * 
	 * @return
	 */
	public double getTotalAccount() {
		Double orderTotal = getOrderTotal(true);
		Double withdrawalTotal = getWithdrawalTotal();

		double blance = 0.0;
		
		if (orderTotal == null) {
			orderTotal = 0.0;
			blance = 0;
		}
		if(withdrawalTotal == null) {
			withdrawalTotal = 0.0;
			blance = orderTotal;
		}
//		
//		if (orderTotal != null) {
//			// 手续费
//			double serviceFee = BigDecimalUtils.mul(orderTotal, 0.006);
//			// 净额
//			blance = BigDecimalUtils.sub(orderTotal, serviceFee);
//			blance = BigDecimalUtils.round(blance, 2);
//		}
		
		if (orderTotal != null && withdrawalTotal != null) {
			blance = BigDecimalUtils.sub(orderTotal, withdrawalTotal);
		}
		return blance;
	}

	/**
	 * 用户订单列表
	 * @param page
	 * @return
	 */
	public List<OrderFormOutput> memberOrderList(Page page, OrderForm orderForm) {
		if (orderForm == null) {
			orderForm = new OrderForm();
		}
		orderForm.setInitiatorId(RealmUtils.getCurrentUser().getId());
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
		return orderFormOutputs;
	}
}
