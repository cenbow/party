package com.party.core.service.order;

import com.party.core.model.order.OrderTrade;
import com.party.core.service.IBaseService;

/**
 * 订单交易服务接口
 * party
 * Created by wei.li
 * on 2016/9/18 0018.
 */
public interface IOrderTradeService extends IBaseService<OrderTrade> {

	OrderTrade findByOrderId(String orderId);
}
