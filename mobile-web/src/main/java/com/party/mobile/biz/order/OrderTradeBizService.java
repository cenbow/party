package com.party.mobile.biz.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.party.common.utils.BigDecimalUtils;
import com.party.core.model.order.OrderForm;
import com.party.core.model.order.OrderOrigin;
import com.party.core.model.order.OrderTrade;
import com.party.core.model.order.OrderType;
import com.party.core.model.order.PaymentWay;
import com.party.core.service.order.IOrderFormService;
import com.party.core.service.order.IOrderTradeService;
import com.party.pay.model.pay.ali.PayResponse;
import com.party.pay.model.pay.wechat.NotifyRequest;

/**
 * 订单交易信息业务接口
 * Created by wei.li
 *
 * @date 2017/3/31 0031
 * @time 19:57
 */

@Service
public class OrderTradeBizService {

    @Autowired
    private IOrderTradeService orderTradeService;
    
    @Autowired
    private IOrderFormService orderFormService;

    /**
     * 持久化交易信息
     * @param orderForm 订单信息
     * @param object 支付信息
     * @param paymentWay 支付方式
     */

    @Transactional
    public void save(OrderForm orderForm, Object object, Integer paymentWay){
    	OrderForm t = orderFormService.get(orderForm.getId());
    	
        // 持久化交易信息
        OrderTrade orderTrade = new OrderTrade();
        orderTrade.setOrderFormId(orderForm.getId());

        // 如果是活动订单
        if (orderForm.getType().equals(OrderType.ORDER_ACTIVITY.getCode())) {
            orderTrade.setOrigin(OrderOrigin.ORDER_ORIGIN_ACT.getCode());// 来源于活动
        }
        //如果是商品订单
        else if (orderForm.getType().equals(OrderType.ORDER_NOMAL.getCode()) ){
            orderTrade.setOrigin(OrderOrigin.ORDER_ORIGIN_GOODS.getCode());// 来源于商品
        }

        // 判断支付方式
        if (PaymentWay.ALI_PAY.getCode().equals(paymentWay)) {
            PayResponse payResponse = (PayResponse) object;
            orderTrade.setType(PaymentWay.ALI_PAY.getCode());
            orderTrade.setTransactionId(payResponse.getTradeNo());
            orderTrade.setData(JSONObject.toJSONString(payResponse));
            t.setPayment(payResponse.getTotalFee());
            t.setTradeState(payResponse.getTradeStatus());
            t.setTransactionId(payResponse.getTradeNo());
            orderFormService.update(t);
        } else if (PaymentWay.WECHAT_PAY.getCode().equals(paymentWay)) {
            NotifyRequest notifyRequest = (NotifyRequest) object;
            orderTrade.setTransactionId(notifyRequest.getTransactionId());
            orderTrade.setType(PaymentWay.WECHAT_PAY.getCode());
            orderTrade.setData(JSONObject.toJSONString(notifyRequest));
            double price = BigDecimalUtils.div(Double.valueOf(notifyRequest.getTotalFee()), 100);
            t.setPayment((float) price);
            t.setMerchantId(notifyRequest.getMchId());
            t.setTradeState(notifyRequest.getResultCode());
            t.setTransactionId(notifyRequest.getTransactionId());
            orderFormService.update(t);
        }

        if (!PaymentWay.CROWD_FUND_PAY.getCode().equals(paymentWay)){
            orderTradeService.insert(orderTrade);
        }
    }
}
