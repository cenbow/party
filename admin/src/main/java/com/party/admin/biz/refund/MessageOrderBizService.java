package com.party.admin.biz.refund;

import com.party.core.model.goods.GoodsCoupons;
import com.party.core.service.goods.IGoodsCouponsService;
import com.party.notify.notifyPush.servce.INotifySendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.party.core.model.order.OrderForm;
import com.party.core.model.order.OrderType;
import com.party.core.service.order.IOrderFormService;

import java.util.List;

/**
 * 订单消息推送 Created by wei.li
 *
 * @date 2017/3/31 0031
 * @time 19:22
 */
@Service
public class MessageOrderBizService {

	@Autowired
	private IOrderFormService orderFormService;

	@Autowired
	private INotifySendService notifySendService;

	@Autowired
	private IGoodsCouponsService goodsCouponsService;

	/**
	 * 活动审核拒绝推送
	 * @param orderId
	 */
	public void activityRefundSend(String orderId) {
		OrderForm orderForm = orderFormService.get(orderId);
		if (orderForm.getPayment() > Float.valueOf("0.0")) {
			notifySendService.sendPayCheack(orderForm);
		} else {
			notifySendService.sendFreeCheack(orderForm);
		}
	}

	/**
	 * 退款推送
	 * 
	 * @param orderId
	 */
	public void refundSend(String orderId) {
		OrderForm orderForm = orderFormService.get(orderId);
		if (orderForm.getType().equals(OrderType.ORDER_ACTIVITY.getCode())) {
			notifySendService.sendActivityRefund(orderForm);
		} else if (orderForm.getType().equals(OrderType.ORDER_NOMAL.getCode())
				|| orderForm.getType().equals(OrderType.ORDER_CUSTOMIZED.getCode())) {
			notifySendService.sendGoodsRefund(orderForm);
		}
	}

	/**
	 * 下单推送推送
	 * 
	 * @param orderId 订单号
	 */
	public void paySend(String orderId) {
		OrderForm orderForm = orderFormService.get(orderId);
		GoodsCoupons goodsCoupon = new GoodsCoupons();
		goodsCoupon.setOrderId(orderForm.getId());
		List<GoodsCoupons> goodsCoupons = goodsCouponsService.list(goodsCoupon);
		for (GoodsCoupons coupons : goodsCoupons) {
			if (orderForm.getType().equals(OrderType.ORDER_ACTIVITY.getCode())) {
				notifySendService.sendApply(orderForm, coupons.getVerifyCode());
			} else if (orderForm.getType().equals(OrderType.ORDER_NOMAL.getCode()) || orderForm.getType().equals(OrderType.ORDER_CUSTOMIZED.getCode())) {
				notifySendService.senGoodsBuy(orderForm, coupons.getVerifyCode());
			}
		}
	}
}
