package com.party.admin.biz.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.party.common.constant.Constant;
import com.party.core.exception.OrderException;
import com.party.core.model.YesNoStatus;
import com.party.core.model.activity.ActStatus;
import com.party.core.model.activity.Activity;
import com.party.core.model.crowdfund.Project;
import com.party.core.model.crowdfund.Support;
import com.party.core.model.crowdfund.TargetProject;
import com.party.core.model.goods.GoodsCoupons;
import com.party.core.model.member.MemberAct;
import com.party.core.model.order.OrderForm;
import com.party.core.model.order.OrderRefundTrade;
import com.party.core.model.order.OrderStatus;
import com.party.core.model.order.OrderType;
import com.party.core.model.order.PaymentState;
import com.party.core.model.order.PaymentWay;
import com.party.core.service.activity.IActivityService;
import com.party.core.service.count.IDataCountService;
import com.party.core.service.crowdfund.IProjectService;
import com.party.core.service.crowdfund.ISupportService;
import com.party.core.service.crowdfund.ITargetProjectService;
import com.party.core.service.goods.IGoodsCouponsService;
import com.party.core.service.member.IMemberActService;
import com.party.core.service.order.IOrderFormService;
import com.party.core.service.order.IOrderRefundTradeService;
import com.party.pay.model.query.TradeStatus;
import com.party.pay.model.refund.AliPayRefundResponse;
import com.party.pay.model.refund.WechatPayRefundResponse;
import com.party.pay.service.pay.PayOrderBizService;

@Service
public class OrderFormBizService {

	@Autowired
	private IDataCountService dataCountService;

	@Autowired
	private OrderTradeBizService orderTradeBizService;

	@Autowired
	private PayOrderBizService payOrderBizService;

	@Autowired
	IOrderFormService orderFormService;

	@Autowired
	IGoodsCouponsService goodsCouponsService;

	@Autowired
	IMemberActService memberActService;

	@Autowired
	IActivityService activityService;

	@Autowired
	IProjectService projectService;

	@Autowired
	ISupportService supportService;

	@Autowired
	ITargetProjectService targetProjectService;

	@Autowired
	IOrderRefundTradeService orderRefundTradeService;

	private static final int SUPPORT_IS_PAY = 1;

	/**
	 * 处理支付业务
	 * 
	 * @param orderId
	 * @param from
	 * @throws Exception
	 */
	@Transactional(readOnly = false)
	public void updatePayBusiness(OrderForm orderForm, Object object, Integer paymentWay) throws Exception {
		payOrder(orderForm, object, paymentWay);
//		payOrderBizService.updateJoinNum(orderForm.getId());
	}

	/**
	 * 处理退款业务
	 * 
	 * @param orderId
	 * @param object
	 * @param paymentWay
	 * @throws Exception
	 */
	@Transactional(readOnly = false)
	public void updateRefundBusiness(String orderId, Object object, Integer paymentWay) throws Exception {
		refund(orderId, object, paymentWay);
//		payOrderBizService.updateJoinNum(orderId);
	}

	/**
	 * 退款更改状态
	 * 
	 * @param orderId
	 * @param refundResponse
	 */
	@Transactional(readOnly = false)
	public void refund(String orderId, Object object, Integer paymentWay) throws Exception {
		/************* 更改业务状态 *****************/
		OrderForm orderForm = orderFormService.get(orderId);
		orderForm.setIsPay(PaymentState.NO_PAY.getCode()); // 支付状态更改为未付款
		orderForm.setStatus(OrderStatus.ORDER_STATUS_REFUND.getCode()); // 订单状态更改为已退款
		
		if (orderForm.getPaymentWay().equals(PaymentWay.WECHAT_PAY.getCode())) {
			orderForm.setTradeState(TradeStatus.WX_REFUND.getCode()); // 交易状态已退款
		} else if (orderForm.getPaymentWay().equals(PaymentWay.ALI_PAY.getCode())) {
			orderForm.setTradeState(TradeStatus.ALI_TRADE_CLOSED.getCode()); // 交易状态已关闭
		}
		
		orderFormService.update(orderForm);

		if (orderForm.getType() == OrderType.ORDER_ACTIVITY.getCode()) {
			// 活动——更改活动报名状态
			MemberAct dbMemberAct = memberActService.findByOrderId(orderId);
			dbMemberAct.setCheckStatus(ActStatus.ACT_STATUS_AUDIT_REJECT.getCode()); // 审核拒绝
			dbMemberAct.setJoinin(YesNoStatus.NO.getCode()); // 取消
			memberActService.update(dbMemberAct);
		}

		/************* 更改核销码状态为无效 *****************/
		GoodsCoupons goodsCoupon = new GoodsCoupons();
		goodsCoupon.setOrderId(orderId);
		List<GoodsCoupons> goodsCoupons = goodsCouponsService.list(goodsCoupon);
		for (GoodsCoupons coupons : goodsCoupons) {
			coupons.setStatus(0);
			goodsCouponsService.update(coupons);
		}

		/************* 持久化退款交易信息 *****************/
		OrderRefundTrade orderTrade = new OrderRefundTrade();
		orderTrade.setOrderFormId(orderForm.getId());

		if (orderForm.getType() == OrderType.ORDER_ACTIVITY.getCode()) {
			orderTrade.setOrigin(1);// 来源于活动
		} else if (OrderType.ORDER_NOMAL.getCode().equals(orderForm.getType())
				|| OrderType.ORDER_CUSTOMIZED.getCode().equals(orderForm.getType())) {
			orderTrade.setOrigin(0);// 来源于商品
		}

		String transactionId = null;
		String responseData = null;

		if (paymentWay == PaymentWay.WECHAT_PAY.getCode()) {
			WechatPayRefundResponse response = (WechatPayRefundResponse) object;
			transactionId = response.getTransactionId();
			responseData = JSONObject.toJSONString(response);
		} else if (paymentWay == PaymentWay.ALI_PAY.getCode()) {
			AliPayRefundResponse response = (AliPayRefundResponse) object;
			transactionId = response.getTradeNo();
			responseData = JSONObject.toJSONString(response);
		}

		orderTrade.setTransactionId(transactionId);
		orderTrade.setType(orderForm.getPaymentWay());
		orderTrade.setData(responseData);
		orderRefundTradeService.insert(orderTrade);
	}

	@Transactional
	public void payOrder(OrderForm orderForm, Object object, Integer paymentWay) throws OrderException {
		OrderForm t = orderFormService.get(orderForm.getId());
		
		// 修改订单状态
		t.setIsPay(PaymentState.IS_PAY.getCode());
		t.setPaymentWay(paymentWay);
		t.setStatus(OrderStatus.ORDER_STATUS_HAVE_PAID.getCode());
		orderFormService.update(t);

		// 如果是活动支付成功，设置活动报名状态
		if (orderForm.getType().equals(OrderType.ORDER_ACTIVITY.getCode())) {
			MemberAct dbMemberAct = memberActService.findByOrderId(t.getId());
			if (dbMemberAct != null) {
				dbMemberAct.setCheckStatus(ActStatus.ACT_STATUS_PAID.getCode()); // 已支付，报名成功
				memberActService.update(dbMemberAct);
			}
		}

		// 持久化交易信息
		orderTradeBizService.save(orderForm, object, paymentWay);

		// 如果是众筹订单
		if (orderForm.getType().equals(OrderType.ORDER_CROWD_FUND.getCode())) {
			this.paySupport(orderForm);
			return;
		}

		// 发码
		sendCode(orderForm);

		// 统计商品销售额
		dataCountService.countSales(orderForm.getGoodsId(), orderForm.getPayment());
	}

	/**
	 * 订单发码
	 * 
	 * @param orderForm
	 *            订单信息
	 */
	public void sendCode(OrderForm orderForm) {
		
		List<GoodsCoupons> goodsCouponss = goodsCouponsService.findByOrderId(orderForm.getId());
		if (goodsCouponss.size() == 0) {
			List<String> verifyCodeList = payOrderBizService.getVerifyCode(orderForm.getId(), orderForm.getUnit());
			for (String verifyCode : verifyCodeList) {
				
				// 生成核销码
				GoodsCoupons goodsCoupons = new GoodsCoupons();
				goodsCoupons.setOrderId(orderForm.getId());
				goodsCoupons.setMemberId(orderForm.getMemberId());
				goodsCoupons.setGoodsId(orderForm.getGoodsId());
				goodsCoupons.setVerifyCode(verifyCode);
				goodsCoupons.setType(orderForm.getType());
				goodsCouponsService.insert(goodsCoupons);
			}
		}

	}

	/**
	 * 支持订单支付成功回调
	 * 
	 * @param orderForm
	 *            订单信息
	 */
	@Transactional
	public void paySupport(OrderForm orderForm) {

		// 修改支持状态
		Support support = supportService.findByOrderId(orderForm.getId());
		support.setPayStatus(SUPPORT_IS_PAY);
		supportService.update(support);

		// 更改众筹
		Project project = projectService.get(support.getProjectId());
		TargetProject targetProject = targetProjectService.findByProjectId(project.getId());
		int favorerNum = project.getFavorerNum() + 1;
		float actualAmount = supportService.sumByProjectId(project.getId());
		//实时已筹集金额
		project.setRealTimeAmount(actualAmount);
		//已筹金额是否大于目标金额
		actualAmount = actualAmount > project.getTargetAmount() ? project.getTargetAmount() : actualAmount;
		project.setFavorerNum(favorerNum);
		project.setActualAmount(actualAmount);

		// 更改项目
		Activity activity = activityService.get(targetProject.getTargetId());

		// 如果众筹成功
		boolean isSueccess = projectService.isSuccess(project.getTargetAmount(), project.getActualAmount());
		if ((!project.getIsSuccess().equals(Constant.IS_SUCCESS)) && isSueccess) {
			// 修改众筹主订单
			OrderForm mainOrder = orderFormService.get(targetProject.getOrderId());
			this.payOrder(mainOrder, null, PaymentWay.CROWD_FUND_PAY.getCode());
			project.setIsSuccess(Constant.IS_SUCCESS);

			// 商品正在众筹人数减
			activity.setCrowdfundedNum(activity.getCrowdfundedNum() + 1);
			Integer beCrowdfundNum = activity.getBeCrowdfundNum() - 1 <= 0 ? 0 : activity.getBeCrowdfundNum() - 1;
			activity.setBeCrowdfundNum(beCrowdfundNum);
		}
		projectService.update(project);
		int actFavorerNum = activity.getFavorerNum() + 1;
		activity.setFavorerNum(actFavorerNum);
		activityService.update(activity);
	}
}
