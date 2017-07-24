package com.party.core.service.store.impl;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.party.common.paging.Page;
import com.party.core.dao.read.store.StoreGoodsReadDao;
import com.party.core.dao.write.store.StoreGoodsWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.store.StoreGoods;
import com.party.core.service.store.IStoreGoodsService;
import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 商铺商品服务实现
 * party
 * Created by wei.li
 * on 2016/10/11 0011.
 */
@Service
public class StoreGoodsService implements IStoreGoodsService {

    @Autowired
    StoreGoodsReadDao storeGoodsReadDao;

    @Autowired
    StoreGoodsWriteDao storeGoodsWriteDao;


    /**
     * 商铺商品插入
     * @param storeGoods 商铺商品
     * @return 插入结果（true/false）
     */
    @Override
    public String insert(StoreGoods storeGoods) {
        BaseModel.preInsert(storeGoods);
        boolean result = storeGoodsWriteDao.insert(storeGoods);
        if (result){
            return storeGoods.getId();
        }
        return null;
    }

    /**
     * 更新商铺商品
     * @param storeGoods 商铺商品
     * @return 更新结果（true/false）
     */
    @Override
    public boolean update(StoreGoods storeGoods) {
        return storeGoodsWriteDao.update(storeGoods);
    }

    /**
     * 逻辑删除商铺商品
     * @param id 实体主键
     * @return 删除结果（true/false）
     */
    @Override
    public boolean deleteLogic(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return storeGoodsWriteDao.deleteLogic(id);
    }

    /**
     * 物理删除商铺商品
     * @param id 实体主键
     * @return 删除结果（true/false）
     */
    @Override
    public boolean delete(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return storeGoodsWriteDao.delete(id);
    }

    /**
     * 批量逻辑删除商铺商品
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    @Override
    public boolean batchDeleteLogic(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return storeGoodsWriteDao.batchDeleteLogic(ids);
    }

    /**
     * 批量物理删除商铺商品
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    @Override
    public boolean batchDelete(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return storeGoodsWriteDao.batchDelete(ids);
    }

    /**
     * 根据逐渐获取商铺商品信息
     * @param id 主键
     * @return 商铺商品信息
     */
    @Override
    public StoreGoods get(String id) {
        return storeGoodsReadDao.get(id);
    }

    /**
     * 分页查询商铺商品信息
     * @param storeGoods 商铺商品信息
     * @param page 分页信息
     * @return 商铺商品信息列表
     */
    @Override
    public List<StoreGoods> listPage(StoreGoods storeGoods, Page page) {
        return storeGoodsReadDao.listPage(storeGoods, page);
    }

    /**
     * 插叙所有商铺商品信息
     * @param storeGoods 商铺商品信息
     * @return 商铺商品信息列表
     */
    @Override
    public List<StoreGoods> list(StoreGoods storeGoods) {
        return storeGoodsReadDao.listPage(storeGoods, null);
    }


    /**
     * 查询所有商户商品
     * @return 商户商品列表
     */
    @Override
    public List<StoreGoods> listAll() {
        return storeGoodsReadDao.listPage(null, null);
    }


    /**
     * 根据会员活动查询唯一商铺商品
     * @param memberId 会员编号
     * @param goodsId 商品编号
     * @return 商铺商品
     */
    @Override
    public StoreGoods findByMemberGoods(@NotNull String memberId, @NotNull String goodsId) {
        StoreGoods storeGoods = new StoreGoods();
        storeGoods.setMemberId(memberId);
        storeGoods.setGoodsId(goodsId);
        List<StoreGoods> storeGoodsList = this.list(storeGoods);
        if (!CollectionUtils.isEmpty(storeGoodsList)){
            return storeGoodsList.get(0);
        }
        return null;
    }

    /**
     * 统计商铺商户数据
     * @param memberId 会员编号
     * @param goodId 商品编号
     * @return 统计数据
     */
    @Override
    public StoreGoods count(@Nullable String memberId, @Nullable String goodId) {
        Map<String, Object> query = Maps.newHashMap();
        query.put("goodsId", goodId);
        query.put("memberId",memberId);
        return storeGoodsReadDao.count(query);
    }

    /**
     * 根据会员统计商户数据
     * @param memberId 会员主键
     * @return 统计数据
     */
    @Override
    public StoreGoods countByMemberId(@Nullable String memberId) {
        return this.count(memberId, null);
    }

    /**
     * 根据商品统计数据
     * @param goodId 商品主键
     * @return 统计数据
     */
    @Override
    public StoreGoods countByGoodId(@Nullable String goodId) {
        return this.count(null, goodId);
    }


    /**
     * 根据用户查询商铺商品
     * @param userId 用户编号
     * @return 商铺商品
     */
    @Override
    public StoreGoods findByUserId(@NotNull String userId) {
        if (!Strings.isNullOrEmpty(userId)){
            StoreGoods storeGoods = new StoreGoods();
            storeGoods.setUserId(userId);
            List<StoreGoods> storeGoodsList = this.list(storeGoods);
            return CollectionUtils.isEmpty(storeGoodsList) ? null : storeGoodsList.get(0);
        }
        return null;
    }

    /**
     * 根据商品查询商品商品
     * @param goodsId 商品编号
     * @return 商品商品列表
     */
    @Override
    public List<StoreGoods> findByGoodsId(@NotNull String goodsId) {
        if (Strings.isNullOrEmpty(goodsId)){
            return Collections.EMPTY_LIST;
        }
        StoreGoods storeGoods = new StoreGoods();
        storeGoods.setGoodsId(goodsId);
        return this.list(storeGoods);
    }

    /**
     * 选择性查询商户商品
     * @param goodsType 商品类型
     * @param sort 排序方式
     * @param memberId 会员编号
     * @param inSales 是否销售中
     * @param page 分页参数
     * @return 商户商品列表
     */
    @Override
    public List<StoreGoods> listSelect(Integer goodsType, Integer sort, String memberId,
                                       Boolean inSales, Page page) {
        Map<String, Object> query = Maps.newHashMap();
        query.put("goodsType", goodsType);
        query.put("sort", sort);
        query.put("inSales", inSales);
        query.put("memberId",memberId);
        List<StoreGoods> list = storeGoodsReadDao.listSelect(query, page);
        return list;
    }

    /**
     * 批量查询商铺商品信息
     * @param ids 主键集合
     * @param storeGoods 商铺商品信息
     * @param page 分页信息
     * @return 商铺商品信息列表
     */
    @Override
    public List<StoreGoods> batchList(@NotNull Set<String> ids, StoreGoods storeGoods, Page page) {
        if (CollectionUtils.isEmpty(ids)){
            return Collections.EMPTY_LIST;
        }
        Map<String, Object> query = Maps.newHashMap();
        query.put("goodsId", storeGoods.getGoodsId());
        return storeGoodsReadDao.batchList(ids, query, page);
    }

    /**
     * 根据用户批量查询商铺商品
     * @param ids 用户编号集合
     * @param storeGoods 商品信息
     * @param page 分页参数
     * @return 商铺商品列表
     */
    @Override
    public List<StoreGoods> batchListByUserId(@NotNull Set<String> ids, StoreGoods storeGoods, Page page) {
        if (CollectionUtils.isEmpty(ids)){
            return Collections.EMPTY_LIST;
        }
        Map<String, Object> query = Maps.newHashMap();
        query.put("goodsId", storeGoods.getGoodsId());
        return storeGoodsReadDao.batchListByUserId(ids, query, page);
    }
}
