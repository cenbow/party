package com.party.core.dao.write.order;

import com.party.core.dao.write.BaseWriteDao;
import com.party.core.model.order.OrderTrade;
import org.springframework.stereotype.Repository;

/**
 * 订单交易数据写入
 * party
 * Created by wei.li
 * on 2016/9/18 0018.
 */

@Repository
public interface OrderTradeWriteDao extends BaseWriteDao<OrderTrade> {
}
