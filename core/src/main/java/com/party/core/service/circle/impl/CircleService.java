package com.party.core.service.circle.impl;

import java.util.*;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.party.common.paging.Page;
import com.party.common.utils.StringUtils;
import com.party.core.dao.read.circle.CircleReadDao;
import com.party.core.dao.write.circle.CircleWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.circle.Circle;
import com.party.core.service.circle.ICircleService;
import com.sun.istack.NotNull;

/**
 * CircleService圈子服务实现
 *
 * @author Juliana
 * @data 2016-12-14
 */

@Service
public class CircleService implements ICircleService{

    @Autowired
    private CircleReadDao circleReadDao;
    @Autowired
    private CircleWriteDao circleWriteDao;

    /**
     * 插入圈子数据
     * @param circle 圈子实体
     * @return 插入结果（true/false）
     */
    public String insert(Circle circle) {
        BaseModel.preInsert(circle);
        boolean result = circleWriteDao.insert(circle);
        if (result){
            return circle.getId();
        }
        return null;
    }

    /**
     * 更新圈子信息
     * @param circle 圈子实体
     * @return 更新结果（true/false）
     */
    public boolean update(Circle circle) {
        if (null == circle)
            return false;
        circle.setUpdateDate(new Date());
        return circleWriteDao.update(circle);
    }

    /**
     * 逻辑删除圈子数据
     * @param id 圈子实体主键
     * @return 删除结果（true/false）
     */
    public boolean deleteLogic(@NotNull String id) {
        if (StringUtils.isBlank(id))
            return false;

        return circleWriteDao.deleteLogic(id);
    }

    /**
     * 物理删除圈子数据
     * @param id 圈子实体主键
     * @return 删除结果（true/false）
     */
    public boolean delete(@NotNull String id) {
        if (StringUtils.isBlank(id))
            return false;

        return circleWriteDao.delete(id);
    }

    /**
     * 批量逻辑删除圈子数据
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    public boolean batchDeleteLogic(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids))
            return false;

        return circleWriteDao.batchDeleteLogic(ids);
    }

    /**
     * 批量物理删除圈子数据
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    public boolean batchDelete(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids))
            return false;

        return circleWriteDao.batchDelete(ids);
    }

    /**
     * 根据主键获取圈子实体数据
     * @param id 主键
     * @return 圈子实体
     */
    public Circle get(String id) {
        if (StringUtils.isBlank(id))
            return null;

        return circleReadDao.get(id);
    }
    /**
     * 查询单个圈子
     * @param circleMember 圈子实体
     * @return 圈子
     */
    public Circle getUnique(Circle circle) {
        return circleReadDao.getUnique(circle);
    }
    /**
     * 分页查询圈子列表
     * @param circle 圈子实体
     * @param page 分页信息
     * @return 圈子列表
     */
    public List<Circle> listPage(Circle circle, Page page) {
        return circleReadDao.listPage(circle, page);
    }

    /**
     * 查询所有圈子数据
     * @param circle 圈子实体
     * @return 圈子列表
     */
    public List<Circle> list(Circle circle) {
        return circleReadDao.listPage(circle, null);
    }

    /**
     * 查询所有圈子
     * @return 圈子列表
     */
    @Override
    public List<Circle> listAll() {
        return circleReadDao.listPage(null, null);
    }

    /**
     * web分页
     * @param circle
     * @param page
     * @return
     */
    @Override
    public List<Map<String, Object>> webListPage(Circle circle, Map<String, Object> params, Page page) {
        return circleReadDao.webListPage(circle, params, page);
    }

    /**
     * 根据圈子主键集合查询圈子数据
     * @param ids 主键集合
     * @param circle 圈子实体
     * @param page 分页信息
     * @return 圈子列表
     */
    public List<Circle> batchList(@NotNull Set<String> ids, Circle circle, Page page) {
        if (CollectionUtils.isEmpty(ids))
            return Collections.EMPTY_LIST;

        return circleReadDao.batchList(ids, new HashedMap(), page);
    }
}
