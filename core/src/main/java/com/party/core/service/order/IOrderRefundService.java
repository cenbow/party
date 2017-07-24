package com.party.core.service.order;

import com.party.pay.model.refund.WechatPayRefundResponse;

/**
 * Created by wei.li
 *
 * @date 2017/4/28 0028
 * @time 11:40
 */
public interface IOrderRefundService {

    /**
     * 订单退款
     * @param orderId 订单编号
     * @param response 退款报文
     */
    void refund(String orderId, WechatPayRefundResponse response);

    /**
     * 持久化流水信息
     * @param orderId 订单号
     * @param response 响应参数
     */
    void orderTrade(String orderId, WechatPayRefundResponse response);
}
