package com.party.core.service.circle.impl;

import java.util.*;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.party.common.paging.Page;
import com.party.common.utils.StringUtils;
import com.party.core.dao.read.circle.CircleApplyReadDao;
import com.party.core.dao.write.circle.CircleApplyWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.circle.CircleApply;
import com.party.core.model.circle.CircleMember;
import com.party.core.service.circle.ICircleApplyService;
import com.sun.istack.NotNull;

/**
 * CircleApplyService圈子申请服务实现
 *
 * @author Juliana
 * @data 2016-12-14
 */

@Service
public class CircleApplyService implements ICircleApplyService{

    @Autowired
    private CircleApplyReadDao circleApplyReadDao;
    @Autowired
    private CircleApplyWriteDao circleApplyWriteDao;
    /**
     * 通过搜索删除
     * @param circleApply
     */
    @Transactional
    public void delBySearch(CircleApply circleApply){
        List<CircleApply> list = list(circleApply);
        for(CircleApply item:list){
            delete(item.getId());
        }
    }
    /**
     * 插入圈子申请数据
     * @param circleApply 圈子申请实体
     * @return 插入结果（true/false）
     */
    public String insert(CircleApply circleApply) {
        BaseModel.preInsert(circleApply);
        boolean result = circleApplyWriteDao.insert(circleApply);
        if (result){
            return circleApply.getId();
        }
        return null;
    }

    /**
     * 更新圈子申请信息
     * @param circleApply 圈子申请实体
     * @return 更新结果（true/false）
     */
    public boolean update(CircleApply circleApply) {
        if (null == circleApply)
            return false;
        circleApply.setUpdateDate(new Date());
        return circleApplyWriteDao.update(circleApply);
    }

    /**
     * 逻辑删除圈子申请数据
     * @param id 圈子申请实体主键
     * @return 删除结果（true/false）
     */
    public boolean deleteLogic(@NotNull String id) {
        if (StringUtils.isBlank(id))
            return false;

        return circleApplyWriteDao.deleteLogic(id);
    }

    /**
     * 物理删除圈子申请数据
     * @param id 圈子申请实体主键
     * @return 删除结果（true/false）
     */
    public boolean delete(@NotNull String id) {
        if (StringUtils.isBlank(id))
            return false;

        return circleApplyWriteDao.delete(id);
    }

    /**
     * 批量逻辑删除圈子申请数据
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    public boolean batchDeleteLogic(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids))
            return false;

        return circleApplyWriteDao.batchDeleteLogic(ids);
    }

    /**
     * 批量物理删除圈子申请数据
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    public boolean batchDelete(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids))
            return false;

        return circleApplyWriteDao.batchDelete(ids);
    }

    /**
     * 根据主键获取圈子申请实体数据
     * @param id 主键
     * @return 圈子申请实体
     */
    public CircleApply get(String id) {
        if (StringUtils.isBlank(id))
            return null;

        return circleApplyReadDao.get(id);
    }

    /**
     * 分页查询圈子申请列表
     * @param circleApply 圈子申请实体
     * @param page 分页信息
     * @return 圈子申请列表
     */
    public List<CircleApply> listPage(CircleApply circleApply, Page page) {
        return circleApplyReadDao.listPage(circleApply, page);
    }

    /**
     * 查询所有圈子申请数据
     * @param circleApply 圈子申请实体
     * @return 圈子申请列表
     */
    public List<CircleApply> list(CircleApply circleApply) {
        return circleApplyReadDao.listPage(circleApply, null);
    }
    /**
     * 查询所有圈子申请数量
     * @param circleApply 圈子申请实体
     * @return 圈子申请列表
     */
    public Long count(CircleApply circleApply) {
        return circleApplyReadDao.count(circleApply);
    }
    /**
     * 查询单个圈子用户关系
     * @param circleApply 圈子申请实体
     * @return 圈子列表
     */
    public CircleApply getUnique(CircleApply circleApply) {
        return circleApplyReadDao.getUnique(circleApply);
    }

    @Override
    public List<Map<String, Object>> webListPage(CircleApply apply,Map<String,Object> params, Page page) {
        return circleApplyReadDao.webListPage(apply, params, page);
    }

    /**
     * 根据圈子申请主键集合查询圈子申请数据
     * @param ids 主键集合
     * @param circleApply 圈子申请实体
     * @param page 分页信息
     * @return 圈子申请列表
     */
    public List<CircleApply> batchList(@NotNull Set<String> ids, CircleApply circleApply, Page page) {
        if (CollectionUtils.isEmpty(ids))
            return Collections.EMPTY_LIST;

        return circleApplyReadDao.batchList(ids, new HashedMap(), page);
    }
}
