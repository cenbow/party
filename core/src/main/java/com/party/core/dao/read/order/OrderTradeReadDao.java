package com.party.core.dao.read.order;

import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.order.OrderTrade;

import org.springframework.stereotype.Repository;

/**
 * 订单交易数据读取
 * party
 * Created by wei.li
 * on 2016/9/18 0018.
 */
@Repository
public interface OrderTradeReadDao extends BaseReadDao<OrderTrade> {

	OrderTrade findByOrderId(String orderId);
}
