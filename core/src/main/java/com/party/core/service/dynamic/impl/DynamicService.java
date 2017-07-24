package com.party.core.service.dynamic.impl;

import com.google.common.base.Strings;
import com.party.common.paging.Page;
import com.party.core.dao.read.dynamic.DynamicReadDao;
import com.party.core.dao.write.dynamic.DynamicWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.circle.CircleTopic;
import com.party.core.model.circle.TopicMap;
import com.party.core.model.dynamic.Dynamic;
import com.party.core.service.dynamic.IDynamicService;
import com.sun.istack.NotNull;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 动态服务实现
 * party
 * Created by wei.li
 * on 2016/8/19 0019.
 */

@Service
public class DynamicService implements IDynamicService {

    @Autowired
    private DynamicReadDao dynamicReadDao;

    @Autowired
    private DynamicWriteDao dynamicWriteDao;


    /**
     * 动态插入
     *
     * @param dynamic 动态信息
     * @return 插入结果（true/false）
     */
    public String insert(Dynamic dynamic) {
        BaseModel.preInsert(dynamic);
        boolean result = dynamicWriteDao.insert(dynamic);
        if (result) {
            return dynamic.getId();
        }
        return null;
    }


    /**
     * 动态更新
     *
     * @param dynamic 动态信息
     * @return 更新结果（true/false）
     */
    public boolean update(Dynamic dynamic) {
        dynamic.setUpdateDate(new Date());
        return dynamicWriteDao.update(dynamic);
    }


    /**
     * 动态逻辑删除
     *
     * @param id 动态主键
     * @return 删除结果（true/false）
     */
    public boolean deleteLogic(String id) {
        return dynamicWriteDao.deleteLogic(id);
    }

    /**
     * 动态物理删除
     *
     * @param id 动态主键
     * @return 删除结果（true/false）
     */
    public boolean delete(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)) {
            return false;
        }
        return dynamicWriteDao.delete(id);
    }


    /**
     * 动态批量逻辑删除
     *
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    public boolean batchDeleteLogic(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return false;
        }
        return dynamicWriteDao.batchDeleteLogic(ids);
    }

    /**
     * 动态批量物理删除
     *
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    public boolean batchDelete(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return false;
        }
        return dynamicWriteDao.batchDelete(ids);
    }

    /**
     * 根据主键获取动态信息
     *
     * @param id 主键
     * @return 动态信息
     */
    public Dynamic get(String id) {
        return dynamicReadDao.get(id);
    }

    /**
     * 分页查询动态信息
     *
     * @param dynamic 动态信息
     * @param page    分页信息
     * @return 动态列表
     */
    public List<Dynamic> listPage(Dynamic dynamic, Page page) {
        return dynamicReadDao.listPage(dynamic, page);
    }

    /**
     * 查询所有动态信息
     *
     * @param dynamic 动态信息
     * @return 动态列表
     */
    public List<Dynamic> list(Dynamic dynamic) {
        return dynamicReadDao.listPage(null, null);
    }


    /**
     * 批量查询动态信息
     *
     * @param ids     主键集合
     * @param dynamic 动态信息
     * @param page    分页信息
     * @return 动态列表
     */
    public List<Dynamic> batchList(@NotNull Set<String> ids, Dynamic dynamic, Page page) {
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.EMPTY_LIST;
        }
        return dynamicReadDao.batchList(ids, new HashedMap(), page);
    }

    /**
     * 根据会员ID查询动态
     *
     * @param memberIds 会员ID
     * @param dynamic   会员信息
     * @param page      分页信息
     * @return 会员列表
     */
    @Override
    public List<Dynamic> batchListByMemberId(@NotNull Set<String> memberIds, Dynamic dynamic, Page page) {
        if (CollectionUtils.isEmpty(memberIds)) {
            return Collections.EMPTY_LIST;
        }
        return dynamicReadDao.batchListByMemberId(memberIds, dynamic, page);
    }

    @Override
    public List<Dynamic> listDynamicMapPage(Dynamic dynamic,Map<String, Object> params) {
        return dynamicReadDao.listDynamicMapPage(dynamic,params);
    }

    @Override
    public List<TopicMap> listTopicMapPage( Dynamic dynamic, Map<String, Object> params) {
        return dynamicReadDao.listTopicMapPage( dynamic, params);
    }

    @Override
    public List<Dynamic> webListPage(Dynamic dynamic, Map<String, Object> params, Page page) {
        return dynamicReadDao.webListPage(dynamic, params, page);
    }

    @Override
    public List<TopicMap> listCircleTopicPage(CircleTopic circleTopic, Dynamic dynamic, Map<String, Object> params, Page page) {
        return dynamicReadDao.listCircleTopicPage(circleTopic, dynamic, params, page);
    }
}
