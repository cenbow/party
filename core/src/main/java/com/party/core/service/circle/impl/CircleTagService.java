package com.party.core.service.circle.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.party.common.paging.Page;
import com.party.common.utils.StringUtils;
import com.party.core.dao.read.circle.CircleTagReadDao;
import com.party.core.dao.write.circle.CircleTagWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.circle.CircleTag;
import com.party.core.service.circle.ICircleTagService;
import com.sun.istack.NotNull;

/**
 * CircleTagService圈子标签服务实现
 *
 * @author Juliana
 * @data 2016-12-14
 */

@Service
public class CircleTagService implements ICircleTagService{

    @Autowired
    private CircleTagReadDao circleTagReadDao;
    @Autowired
    private CircleTagWriteDao circleTagWriteDao;

    /**
     * 插入圈子标签数据
     * @param circleTag 圈子标签实体
     * @return 插入结果（true/false）
     */
    public String insert(CircleTag circleTag) {
        BaseModel.preInsert(circleTag);
        boolean result = circleTagWriteDao.insert(circleTag);
        if (result){
            return circleTag.getId();
        }
        return null;
    }

    /**
     * 更新圈子标签信息
     * @param circleTag 圈子标签实体
     * @return 更新结果（true/false）
     */
    public boolean update(CircleTag circleTag) {
        if (null == circleTag)
            return false;
        circleTag.setUpdateDate(new Date());
        return circleTagWriteDao.update(circleTag);
    }

    /**
     * 逻辑删除圈子标签数据
     * @param id 圈子标签实体主键
     * @return 删除结果（true/false）
     */
    public boolean deleteLogic(@NotNull String id) {
        if (StringUtils.isBlank(id))
            return false;

        return circleTagWriteDao.deleteLogic(id);
    }

    /**
     * 物理删除圈子标签数据
     * @param id 圈子标签实体主键
     * @return 删除结果（true/false）
     */
    public boolean delete(@NotNull String id) {
        if (StringUtils.isBlank(id))
            return false;

        return circleTagWriteDao.delete(id);
    }

    /**
     * 批量逻辑删除圈子标签数据
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    public boolean batchDeleteLogic(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids))
            return false;

        return circleTagWriteDao.batchDeleteLogic(ids);
    }

    /**
     * 批量物理删除圈子标签数据
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    public boolean batchDelete(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids))
            return false;

        return circleTagWriteDao.batchDelete(ids);
    }

    /**
     * 根据主键获取圈子标签实体数据
     * @param id 主键
     * @return 圈子标签实体
     */
    public CircleTag get(String id) {
        if (StringUtils.isBlank(id))
            return null;

        return circleTagReadDao.get(id);
    }

    /**
     * 分页查询圈子标签列表
     * @param circleTag 圈子标签实体
     * @param page 分页信息
     * @return 圈子标签列表
     */
    public List<CircleTag> listPage(CircleTag circleTag, Page page) {
        return circleTagReadDao.listPage(circleTag, page);
    }

    /**
     * 查询所有圈子标签数据
     * @param circleTag 圈子标签实体
     * @return 圈子标签列表
     */
    public List<CircleTag> list(CircleTag circleTag) {
        return circleTagReadDao.listPage(circleTag, null);
    }

    /**
     * 根据圈子标签主键集合查询圈子标签数据
     * @param ids 主键集合
     * @param circleTag 圈子标签实体
     * @param page 分页信息
     * @return 圈子标签列表
     */
    public List<CircleTag> batchList(@NotNull Set<String> ids, CircleTag circleTag, Page page) {
        if (CollectionUtils.isEmpty(ids))
            return Collections.EMPTY_LIST;

        return circleTagReadDao.batchList(ids, new HashedMap(), page);
    }
}
