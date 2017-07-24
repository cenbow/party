package com.party.core.service.store;

import com.party.core.model.store.StoreCount;
import com.party.core.service.IBaseService;
import com.sun.istack.Nullable;


/**
 * 商铺统计接口
 * party
 * Created by wei.li
 * on 2016/10/12 0012.
 */
public interface IStoreCountService extends IBaseService<StoreCount>{

    /**
     * 统计店铺统计数据
     * @param memberId 会员编号
     * @param goodId 商品编号
     * @param timeType 时间类型
     * @return 统计数据
     */
    StoreCount count(@Nullable String memberId, @Nullable String goodId, @Nullable Integer timeType);

    /**
     * 统计统计数据
     * @param memberId 会员编号
     * @param timeType 时间类型
     * @return 统计数据
     */
    StoreCount countByMemberId(@Nullable String memberId, @Nullable Integer timeType);


    /**
     * 统计商铺统计数据
     * @param goodId 商品编号
     * @param timeType 时间类型
     * @return 统计数据
     */
    StoreCount countByGoodsId(@Nullable String goodId, @Nullable Integer timeType);
}
