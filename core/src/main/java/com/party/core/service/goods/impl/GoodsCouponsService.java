package com.party.core.service.goods.impl;

import com.google.common.base.Strings;
import com.party.common.paging.Page;
import com.party.core.dao.read.goods.GoodsCouponsReadDao;
import com.party.core.dao.write.goods.GoodsCouponsWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.goods.GoodsCoupons;
import com.party.core.service.goods.IGoodsCouponsService;
import com.sun.istack.NotNull;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 核销码服务实现
 * party
 * Created by wei.li
 * on 2016/9/19 0019.
 */

@Service
public class GoodsCouponsService implements IGoodsCouponsService {

    @Autowired
    GoodsCouponsReadDao goodsCouponsReadDao;

    @Autowired
    GoodsCouponsWriteDao goodsCouponsWriteDao;


    /**
     * 核销码插入
     * @param goodsCoupons 核销码
     * @return 插入结果（true/false）
     */
    @Override
    public String insert(GoodsCoupons goodsCoupons) {
        BaseModel.preInsert(goodsCoupons);
        boolean result = goodsCouponsWriteDao.insert(goodsCoupons);
        if (result){
            return goodsCoupons.getId();
        }
        return null;
    }

    /**
     * 核销码更新
     * @param goodsCoupons 核销码
     * @return 更新结果（true/false）
     */
    @Override
    public boolean update(GoodsCoupons goodsCoupons) {
        goodsCoupons.setUpdateDate(new Date());
        return goodsCouponsWriteDao.update(goodsCoupons);
    }


    /**
     * 逻辑删除核销码
     * @param id 实体主键
     * @return 删除结果（true/false）
     */
    @Override
    public boolean deleteLogic(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return goodsCouponsWriteDao.deleteLogic(id);
    }


    /**
     * 物理删除核销码
     * @param id 实体主键
     * @return 删除结果（true/false）
     */
    @Override
    public boolean delete(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return goodsCouponsWriteDao.delete(id);
    }

    /**
     * 批量逻辑删除核销码
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    @Override
    public boolean batchDeleteLogic(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return goodsCouponsWriteDao.batchDeleteLogic(ids);
    }


    /**
     * 批量物理删除核销码
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    @Override
    public boolean batchDelete(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return goodsCouponsWriteDao.batchDelete(ids);
    }

    /**
     * 根据主键获取核销码
     * @param id 主键
     * @return 核销码
     */
    @Override
    public GoodsCoupons get(String id) {
        return goodsCouponsReadDao.get(id);
    }


    /**
     * 根据订单号查询核销码
     * @param orderId 订单号
     * @return 核销码列表
     */
    @Override
    public List<GoodsCoupons> findByOrderId(String orderId) {
        return goodsCouponsReadDao.findByOrderId(orderId);
    }

    /**
     * 分页查询核销码
     * @param goodsCoupons 核销码
     * @param page 分页信息
     * @return 核销码列表
     */
    @Override
    public List<GoodsCoupons> listPage(GoodsCoupons goodsCoupons, Page page) {
        return goodsCouponsReadDao.listPage(goodsCoupons, page);
    }

    /**
     * 查询所有核销码
     * @param goodsCoupons 核销码
     * @return 核销码列表
     */
    @Override
    public List<GoodsCoupons> list(GoodsCoupons goodsCoupons) {
        return goodsCouponsReadDao.listPage(goodsCoupons, null);
    }

    /**
     * 批量查询核销码
     * @param ids 主键集合
     * @param goodsCoupons 核销码
     * @param page 分页信息
     * @return 核销码列表
     */
    @Override
    public List<GoodsCoupons> batchList(@NotNull Set<String> ids, GoodsCoupons goodsCoupons, Page page) {
        if (CollectionUtils.isEmpty(ids)){
            return Collections.EMPTY_LIST;
        }
        return goodsCouponsReadDao.batchList(ids, new HashedMap(), page);
    }
}
