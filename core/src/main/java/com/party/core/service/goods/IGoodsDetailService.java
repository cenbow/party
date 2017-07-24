package com.party.core.service.goods;

import com.party.core.model.goods.GoodsDetail;
import com.party.core.service.IBaseService;

/**
 * IGoodsService 商品详情接口
 *
 * @author Wesley
 * @data 16/9/7 16:57 .
 */
public interface IGoodsDetailService extends IBaseService<GoodsDetail>{
    /**
     * 根据refId查找商品详情数据
     * @param refId
     * @return 商品详情实体
     */
    public GoodsDetail getByRefId(String refId);
}
