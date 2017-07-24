package com.party.core.dao.read.order;

import org.springframework.stereotype.Repository;

import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.order.OrderRefundTrade;

/**
 * 订单退款流水读取
 * 
 * @author Administrator
 *
 */
@Repository
public interface OrderRefundTradeReadDao extends BaseReadDao<OrderRefundTrade> {

	OrderRefundTrade findByOrderId(String id);

	/**
	 * 根据订单获取最近的交易信息
	 * @param orderId 获取最近的交易信息
	 * @return 交易信息
	 */
	OrderRefundTrade recentlyByOrderId(String orderId);
}
