package com.party.core.dao.read.order;

import org.springframework.stereotype.Repository;

import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.order.OrderFormInfo;

/**
 * 订单附属信息
 * 
 * @author Administrator
 *
 */
@Repository
public interface OrderFormInfoReadDao extends BaseReadDao<OrderFormInfo> {

	OrderFormInfo findByOrderId(String orderId);
}
