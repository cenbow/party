package com.party.core.dao.read.goods;

import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.goods.GoodsDetail;
import org.springframework.stereotype.Repository;

/**
 * GoodsReadDao
 *
 * @author Wesley
 * @data 16/9/7 16:02 .
 */
@Repository
public interface GoodsDetailReadDao extends BaseReadDao<GoodsDetail> {
    public GoodsDetail getByRefId(String refId);
}
