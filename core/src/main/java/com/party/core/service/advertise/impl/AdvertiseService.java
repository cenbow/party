package com.party.core.service.advertise.impl;

import com.google.common.base.Strings;
import com.party.common.paging.Page;
import com.party.core.dao.read.advertise.AdvertiseReadDao;
import com.party.core.dao.write.advertise.AdvertiseWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.advertise.Advertise;
import com.party.core.service.advertise.IAdvertiseService;
import com.sun.istack.NotNull;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 广告服务实现
 * party
 * Created by wei.li
 * on 2016/9/20 0020.
 */
@Service
public class AdvertiseService implements IAdvertiseService{

    @Autowired
    AdvertiseWriteDao advertiseWriteDao;

    @Autowired
    AdvertiseReadDao advertiseReadDao;

    /**
     * 广告插入
     * @param advertise 广告
     * @return 插入结果（true/false）
     */
    @Override
    public String insert(Advertise advertise) {
        BaseModel.preInsert(advertise);
        boolean result = advertiseWriteDao.insert(advertise);
        if (result){
            return advertise.getId();
        }
        return null;
    }


    /**
     * 广告更新
     * @param advertise 广告
     * @return 更新结果（true/false）
     */
    @Override
    public boolean update(Advertise advertise) {
        advertise.setUpdateDate(new Date());
        return advertiseWriteDao.update(advertise);
    }

    /**
     * 逻辑删除广告
     * @param id 实体主键
     * @return 删除结果（true/false）
     */
    @Override
    public boolean deleteLogic(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return advertiseWriteDao.deleteLogic(id);
    }

    /**
     * 物理删除广告
     * @param id 实体主键
     * @return 删除结果（true/false）
     */
    @Override
    public boolean delete(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return advertiseWriteDao.delete(id);
    }

    /**
     * 批量逻辑删除广告
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    @Override
    public boolean batchDeleteLogic(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return advertiseWriteDao.batchDeleteLogic(ids);
    }

    /**
     * 批量物理删除广告
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    @Override
    public boolean batchDelete(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return advertiseWriteDao.batchDelete(ids);
    }

    /**
     * 根据主键获取广告
     * @param id 主键
     * @return 广告
     */
    @Override
    public Advertise get(String id) {
        return advertiseReadDao.get(id);
    }

    /**
     * 分页查询广告
     * @param advertise 广告
     * @param page 分页信息
     * @return 广告列表
     */
    @Override
    public List<Advertise> listPage(Advertise advertise, Page page) {
        return advertiseReadDao.listPage(advertise, page);
    }

    /**
     * 查询所有广告
     * @param advertise 广告
     * @return 广告列表
     */
    @Override
    public List<Advertise> list(Advertise advertise) {
        return advertiseReadDao.listPage(advertise, null);
    }

    /**
     * 批量查询广告
     * @param ids 主键集合
     * @param advertise 广告
     * @param page 分页信息
     * @return 广告列表
     */
    @Override
    public List<Advertise> batchList(@NotNull Set<String> ids, Advertise advertise, Page page) {
        if (CollectionUtils.isEmpty(ids)){
            return Collections.EMPTY_LIST;
        }
        return advertiseReadDao.batchList(ids, new HashedMap(), page);
    }

    @Override
    public List<Advertise> webListPage(Advertise advertise, Map<String, Object> params, Page page) {
        return advertiseReadDao.webListPage(advertise, params, page);
    }
}
