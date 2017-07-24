package com.party.core.service.order.impl;
import com.alibaba.fastjson.JSONObject;
import com.party.core.model.order.OrderForm;
import com.party.core.model.order.OrderRefundTrade;
import com.party.core.model.order.OrderStatus;
import com.party.core.model.order.PaymentState;
import com.party.core.model.order.PaymentWay;
import com.party.core.service.order.IOrderFormService;
import com.party.core.service.order.IOrderRefundService;
import com.party.core.service.order.IOrderRefundTradeService;
import com.party.pay.model.query.TradeStatus;
import com.party.pay.model.refund.WechatPayRefundResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 订单退款接口实现
 * Created by wei.li
 *
 * @date 2017/4/28 0028
 * @time 11:42
 */

@Service
public class OrderRefundService implements IOrderRefundService {

    @Autowired
    private IOrderFormService orderFormService;

    @Autowired
    private IOrderRefundTradeService orderRefundTradeService;


    /**
     * 订单退款
     * @param orderId 订单编号
     * @param response 退款报文
     */
    @Override
    @Transactional
    public void refund(String orderId, WechatPayRefundResponse response) {
        //修改订单状态
        OrderForm orderForm = orderFormService.get(orderId);

        orderForm.setIsPay(PaymentState.NO_PAY.getCode()); // 支付状态更改为未付款
        orderForm.setStatus(OrderStatus.ORDER_STATUS_REFUND.getCode()); // 订单状态更改为已退款
        
		if (orderForm.getPaymentWay().equals(PaymentWay.WECHAT_PAY.getCode())) {
			orderForm.setTradeState(TradeStatus.WX_REFUND.getCode()); // 交易状态已退款
		} else if (orderForm.getPaymentWay().equals(PaymentWay.ALI_PAY.getCode())) {
			orderForm.setTradeState(TradeStatus.ALI_TRADE_CLOSED.getCode()); // 交易状态已关闭
		}
        orderFormService.update(orderForm);

        //持久化流水信息
        OrderRefundTrade orderTrade = new OrderRefundTrade();
        orderTrade.setOrderFormId(orderForm.getId());
        orderTrade.setOrigin(3);
        orderTrade.setTransactionId(response.getTransactionId());
        orderTrade.setType(orderForm.getPaymentWay());
        orderTrade.setData(JSONObject.toJSONString(response));
        orderRefundTradeService.insert(orderTrade);
    }

    /**
     * 持久化流水信息
     * @param orderId 订单号
     * @param response 响应参数
     */
    public void orderTrade(String orderId, WechatPayRefundResponse response){
        OrderForm orderForm = orderFormService.get(orderId);
        //持久化流水信息
        OrderRefundTrade orderTrade = new OrderRefundTrade();
        orderTrade.setOrderFormId(orderForm.getId());
        orderTrade.setOrigin(3);
        orderTrade.setTransactionId(response.getTransactionId());
        orderTrade.setType(orderForm.getPaymentWay());
        orderTrade.setData(JSONObject.toJSONString(response));
        orderRefundTradeService.insert(orderTrade);
    }
}
