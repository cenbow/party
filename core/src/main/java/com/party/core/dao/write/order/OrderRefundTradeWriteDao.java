package com.party.core.dao.write.order;

import org.springframework.stereotype.Repository;

import com.party.core.dao.write.BaseWriteDao;
import com.party.core.model.order.OrderRefundTrade;

/**
 * 退款流水
 * 
 * @author Administrator
 *
 */
@Repository
public interface OrderRefundTradeWriteDao extends BaseWriteDao<OrderRefundTrade> {
}
