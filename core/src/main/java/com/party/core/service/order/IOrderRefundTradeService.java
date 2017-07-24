package com.party.core.service.order;

import com.party.core.model.order.OrderRefundTrade;
import com.party.core.service.IBaseService;

/**
 * 订单退款流水
 * @author Administrator
 *
 */
public interface IOrderRefundTradeService extends IBaseService<OrderRefundTrade> {

	OrderRefundTrade findByOrderId(String id);

	/**
	 * 根据订单获取最近的交易信息
	 * @param orderId 订单号
	 * @return 交易信息
	 */
	OrderRefundTrade recentlyByOrderId(String orderId);
}
