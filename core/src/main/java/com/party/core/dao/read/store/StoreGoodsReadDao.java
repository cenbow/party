package com.party.core.dao.read.store;

import com.party.common.paging.Page;
import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.store.StoreGoods;
import com.sun.istack.NotNull;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 商铺商品数据读取
 * party
 * Created by wei.li
 * on 2016/10/10 0010.
 */

@Repository
public interface StoreGoodsReadDao extends BaseReadDao<StoreGoods> {

    /**
     * 条件查询商铺商品信息
     * @param query 自定义查询条件
     * @param page 分页条件
     * @return 商铺商户列表
     */
    List<StoreGoods> listSelect(@Param("query") Map<String, Object> query, Page page);


    /**
     * 统计商铺商品数据
     * @param query 查询数据
     * @return 商铺商品数据
     */
    StoreGoods count(@Param("query") Map<String, Object> query);

    /**
     * 根据用户批量查询商铺商品
     * @param ids 用户编号集合
     * @param param 查询参数
     * @param page 分页参数
     * @return 商铺商品列表
     */
    List<StoreGoods> batchListByUserId(@Param("ids") Set<String> ids, @Param("param")Map<String , Object> param, Page page);
}
