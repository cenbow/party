package com.party.pay.service.refund;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.party.core.model.YesNoStatus;
import com.party.core.model.activity.ActStatus;
import com.party.core.model.activity.Activity;
import com.party.core.model.goods.Goods;
import com.party.core.model.goods.GoodsCoupons;
import com.party.core.model.member.MemberAct;
import com.party.core.model.order.OrderForm;
import com.party.core.model.order.OrderRefundTrade;
import com.party.core.model.order.OrderStatus;
import com.party.core.model.order.OrderType;
import com.party.core.model.order.PaymentState;
import com.party.core.model.order.PaymentWay;
import com.party.core.service.activity.IActivityService;
import com.party.core.service.goods.IGoodsCouponsService;
import com.party.core.service.goods.IGoodsService;
import com.party.core.service.member.IMemberActService;
import com.party.core.service.order.IOrderFormService;
import com.party.core.service.order.IOrderRefundTradeService;
import com.party.pay.model.query.TradeStatus;
import com.party.pay.model.refund.AliPayRefundResponse;
import com.party.pay.model.refund.WechatPayRefundResponse;

/**
 * 退款业务
 * 
 * @author yy-pc
 *
 */
@Service
public class RefundOrderBizService {

	@Autowired
	IOrderFormService orderFormService;

	@Autowired
	IMemberActService memberActService;

	@Autowired
	IActivityService activityService;

	@Autowired
	IGoodsService goodsService;

	@Autowired
	IGoodsCouponsService goodsCouponsService;

	@Autowired
	IOrderRefundTradeService orderRefundTradeService;

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
		if (orderForm.getPayment() > Float.valueOf("0.0")) {
			orderForm.setStatus(OrderStatus.ORDER_STATUS_REFUND.getCode()); // 订单状态更改为已退款
		} else {
			orderForm.setStatus(OrderStatus.ORDER_STATUS_OTHER.getCode()); // 订单状态更改为其他
		}
		
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

		if (orderForm.getPayment() > Float.valueOf("0.0")) {
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

			OrderForm t = orderFormService.get(orderId);
			
			if (paymentWay == PaymentWay.WECHAT_PAY.getCode()) {
				WechatPayRefundResponse response = (WechatPayRefundResponse) object;
				transactionId = response.getTransactionId();
				responseData = JSONObject.toJSONString(response);
				t.setTradeState(TradeStatus.WX_REFUND.getCode());
				orderFormService.update(t);
			} else if (paymentWay == PaymentWay.ALI_PAY.getCode()) {
				AliPayRefundResponse response = (AliPayRefundResponse) object;
				transactionId = response.getTradeNo();
				responseData = JSONObject.toJSONString(response);
				t.setTradeState(TradeStatus.ALI_TRADE_CLOSED.getCode());
				orderFormService.update(t);
			}

			orderTrade.setTransactionId(transactionId);
			orderTrade.setType(orderForm.getPaymentWay());
			orderTrade.setData(responseData);
			orderRefundTradeService.insert(orderTrade);
		}
	}

	/**
	 * 更新预定成功人数
	 * 
	 * @param goodsId
	 * @param orderType
	 */
	@Transactional(readOnly = false)
	public void updateJoinNum(String orderId) {
		OrderForm orderForm = orderFormService.get(orderId);
		// 成功的订单
		List<OrderForm> orderForms = orderFormService.getOrder(orderForm.getGoodsId(), null, false);

		if (orderForm.getType() == OrderType.ORDER_ACTIVITY.getCode()) {
			// 更新活动报名成功人数
			Activity activity = activityService.get(orderForm.getGoodsId());
			activity.setJoinNum(orderForms.size());
			activityService.update(activity);
		} else if (orderForm.getType() == OrderType.ORDER_NOMAL.getCode() || orderForm.getType() == OrderType.ORDER_CUSTOMIZED.getCode()) {
			// 更新商品预定成功人数
			Goods goods = goodsService.get(orderForm.getGoodsId());
			goods.setJoinNum(orderForms.size());
			goodsService.update(goods);
		}
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
		updateJoinNum(orderId);
	}
}
