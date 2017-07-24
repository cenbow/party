package com.party.core.dao.read.goods;

import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.goods.GoodsCoupons;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 核销码数据读取
 * party
 * Created by wei.li
 * on 2016/9/19 0019.
 */
@Repository
public interface GoodsCouponsReadDao extends BaseReadDao<GoodsCoupons> {

    /**
     * 根据订单号查询核销码
     * @param orderId 订单号
     * @return 核销码列表
     */
    List<GoodsCoupons> findByOrderId(@Param(value = "orderId") String orderId);
}
