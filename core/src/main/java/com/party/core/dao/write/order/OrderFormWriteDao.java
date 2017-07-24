package com.party.core.dao.write.order;

import com.party.core.dao.write.BaseWriteDao;
import com.party.core.model.order.OrderForm;
import org.springframework.stereotype.Repository;

/**
 * 商品订单数据写入
 * party
 * Created by wei.li
 * on 2016/9/18 0018.
 */

@Repository
public interface OrderFormWriteDao extends BaseWriteDao<OrderForm> {
}
