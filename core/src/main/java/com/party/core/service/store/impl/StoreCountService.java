package com.party.core.service.store.impl;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.party.common.paging.Page;
import com.party.common.utils.UUIDUtils;
import com.party.core.dao.read.store.StoreCountReadDao;
import com.party.core.dao.write.store.StoreCountWriteDao;
import com.party.core.model.store.StoreCount;
import com.party.core.service.store.IStoreCountService;
import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 商铺服务实现
 * party
 * Created by wei.li
 * on 2016/10/12 0012.
 */

@Service
public class StoreCountService implements IStoreCountService {

    @Autowired
    StoreCountReadDao storeCountReadDao;

    @Autowired
    StoreCountWriteDao storeCountWriteDao;

    /**
     * 商铺统计插入
     * @param storeCount 商铺统计
     * @return 插入结果（true/false）
     */
    @Override
    public String insert(StoreCount storeCount) {
        storeCount.setId(UUIDUtils.generateRandomUUID());
        boolean result = storeCountWriteDao.insert(storeCount);
        if (result){
            return storeCount.getId();
        }
        return null;
    }

    /**
     * 更新商铺统计数据
     * @param storeCount 商铺统计数据
     * @return 更新结果（true/false）
     */
    @Override
    public boolean update(StoreCount storeCount) {
        return storeCountWriteDao.update(storeCount);
    }


    /**
     * 逻辑删除商铺统计
     * @param id 实体主键
     * @return 删除结果（true/false）
     */
    @Override
    public boolean deleteLogic(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return storeCountWriteDao.deleteLogic(id);
    }

    /**
     * 物理删除商铺统计
     * @param id 实体主键
     * @return 删除结果（true/false）
     */
    @Override
    public boolean delete(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return storeCountWriteDao.delete(id);
    }

    /**
     * 批量逻辑删除商铺统计
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    @Override
    public boolean batchDeleteLogic(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return storeCountWriteDao.batchDeleteLogic(ids);
    }

    /**
     * 批量物理删除商铺统计
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    @Override
    public boolean batchDelete(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return storeCountWriteDao.batchDelete(ids);
    }

    /**
     * 根据主键获取商铺统计
     * @param id 主键
     * @return 商铺统计
     */
    @Override
    public StoreCount get(String id) {
        return storeCountReadDao.get(id);
    }


    /**
     * 分页查询商铺统计信息
     * @param storeCount 商铺统计信息
     * @param page 分页信息
     * @return 商铺统计信息列表
     */
    @Override
    public List<StoreCount> listPage(StoreCount storeCount, Page page) {
        return storeCountReadDao.listPage(storeCount, page);
    }

    /**
     * 查询商铺统计信息
     * @param storeCount 商铺统计数据
     * @return
     */
    @Override
    public List<StoreCount> list(StoreCount storeCount) {
        return storeCountReadDao.listPage(storeCount, null);
    }


    /**
     * 统计店铺统计数据
     * @param memberId 会员编号
     * @param goodId 商品编号
     * @param timeType 时间类型
     * @return 统计数据
     */
    @Override
    public StoreCount count(@Nullable String memberId, @Nullable String goodId, @Nullable Integer timeType) {
        Map<String, Object> query = Maps.newHashMap();
        query.put("goodsId", goodId);
        query.put("memberId", memberId);
        query.put("timeType", timeType);
        return storeCountReadDao.count(query);
    }

    /**
     * 统计统计数据
     * @param memberId 会员编号
     * @param timeType 时间类型
     * @return 统计数据
     */
    @Override
    public StoreCount countByMemberId(@Nullable String memberId, @Nullable Integer timeType) {
        return this.count(memberId, null, timeType);
    }

    /**
     * 统计统计数据
     * @param goodId 商品编号
     * @param timeType 时间类型
     * @return 统计数据
     */
    @Override
    public StoreCount countByGoodsId(@Nullable String goodId, @Nullable Integer timeType) {
        return this.count(null, goodId, timeType);
    }

    /**
     * 批量查询商铺统计数据
     * @param ids 主键集合
     * @param storeCount 商铺统计数据
     * @param page 分页信息
     * @return 商铺统计数据列表
     */
    @Override
    public List<StoreCount> batchList(@NotNull Set<String> ids, StoreCount storeCount, Page page) {
        return storeCountReadDao.batchList(ids, new HashedMap(), page);
    }
}
