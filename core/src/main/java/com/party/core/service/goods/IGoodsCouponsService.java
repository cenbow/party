package com.party.core.service.goods;

import com.party.core.model.goods.GoodsCoupons;
import com.party.core.service.IBaseService;

import java.util.List;

/**
 * 核销码服务接口
 * party
 * Created by wei.li
 * on 2016/9/19 0019.
 */
public interface IGoodsCouponsService extends IBaseService<GoodsCoupons> {

    /**
     * 根据订单号查询和小马
     * @param orderId 订单号
     * @return 核销码列表
     */
    List<GoodsCoupons> findByOrderId(String orderId);
}
