package com.party.core.service.store;

import com.party.common.paging.Page;
import com.party.core.model.store.StoreGoods;
import com.party.core.service.IBaseService;
import com.sun.istack.NotNull;
import com.sun.istack.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 商铺商品服务接口
 * party
 * Created by wei.li
 * on 2016/10/11 0011.
 */
public interface IStoreGoodsService extends IBaseService<StoreGoods> {


    /**
     * 选择性查询商户商品
     * @param goodsType 商品类型
     * @param sort 排序方式
     * @param memberId 用户编号
     * @param inSales 是否销售中
     * @param page 分页参数
     * @return 商户商品列表
     */
    List<StoreGoods> listSelect(Integer goodsType, Integer sort, String memberId,
                                Boolean inSales, Page page);

    /**
     * 查询所有的商户商品
     * @return 商户商品列表
     */
    List<StoreGoods> listAll();

    /**
     * 统计商铺商品数据
     * @return 商铺商品数据
     */
    StoreGoods count(@Nullable String memberId,@Nullable String goodId);

    /**
     * 根据会员统计数据
     * @param memberId 会员主键
     * @return 商铺统计数据
     */
    StoreGoods countByMemberId(@Nullable String memberId);


    /**
     * 根据会员商品统计数据
     * @param goodId 商品主键
     * @return 商品统计数据
     */
    StoreGoods countByGoodId(@Nullable String goodId);


    /**
     * 根据会员和商品查询商铺商品
     * @param memberId 会员编号
     * @param goodsId 商品编号
     * @return 商铺商品
     */
    StoreGoods findByMemberGoods(@NotNull String memberId,@NotNull String goodsId);

    /**
     * 根据用户查询商铺商品
     * @param userId 用户编号
     * @return 商铺商品
     */
    StoreGoods findByUserId(@NotNull String userId);

    /**
     * 根据商品查询商品商品
     * @param goodsId 商品编号
     * @return 商品商品列表
     */
    List<StoreGoods> findByGoodsId(@NotNull String goodsId);

    /**
     * 根据用户批量查询商铺商品
     * @param ids 用户编号集合
     * @param storeGoods 商品信息
     * @param page 分页参数
     * @return 商铺商品列表
     */
    List<StoreGoods> batchListByUserId(@NotNull Set<String> ids, StoreGoods storeGoods, Page page);
}
