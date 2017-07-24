package com.party.core.service.circle.impl;

import com.party.common.paging.Page;
import com.party.common.utils.StringUtils;
import com.party.core.dao.read.circle.CircleInviteReadDao;
import com.party.core.dao.write.circle.CircleInviteWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.circle.CircleInvite;
import com.party.core.service.circle.ICircleInviteService;
import com.sun.istack.NotNull;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * CircleInviteService
 *
 * @author Juliana
 * @data 2016-12-14
 */

@Service
public class CircleInviteService implements ICircleInviteService {

    @Autowired
    private CircleInviteReadDao circleInviteReadDao;
    @Autowired
    private CircleInviteWriteDao circleInviteWriteDao;

    /**
     * 插入圈子邀请数据
     * @param circleInvite 圈子邀请实体
     * @return 插入结果（true/false）
     */
    public String insert(CircleInvite circleInvite) {
        BaseModel.preInsert(circleInvite);
        boolean result = circleInviteWriteDao.insert(circleInvite);
        if (result){
            return circleInvite.getId();
        }
        return null;
    }

    /**
     * 更新圈子邀请信息
     * @param circleInvite 圈子邀请实体
     * @return 更新结果（true/false）
     */
    public boolean update(CircleInvite circleInvite) {
        if (null == circleInvite)
            return false;
        circleInvite.setUpdateDate(new Date());
        return circleInviteWriteDao.update(circleInvite);
    }

    /**
     * 逻辑删除圈子邀请数据
     * @param id 圈子邀请实体主键
     * @return 删除结果（true/false）
     */
    public boolean deleteLogic(@NotNull String id) {
        if (StringUtils.isBlank(id))
            return false;

        return circleInviteWriteDao.deleteLogic(id);
    }

    /**
     * 物理删除圈子邀请数据
     * @param id 圈子邀请实体主键
     * @return 删除结果（true/false）
     */
    public boolean delete(@NotNull String id) {
        if (StringUtils.isBlank(id))
            return false;

        return circleInviteWriteDao.delete(id);
    }

    /**
     * 批量逻辑删除圈子邀请数据
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    public boolean batchDeleteLogic(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids))
            return false;

        return circleInviteWriteDao.batchDeleteLogic(ids);
    }

    /**
     * 批量物理删除圈子邀请数据
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    public boolean batchDelete(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids))
            return false;

        return circleInviteWriteDao.batchDelete(ids);
    }

    /**
     * 根据主键获取圈子邀请实体数据
     * @param id 主键
     * @return 圈子邀请实体
     */
    public CircleInvite get(String id) {
        if (StringUtils.isBlank(id))
            return null;

        return circleInviteReadDao.get(id);
    }
    /**
     * 查询单个圈子邀请
     * @param circleInvite 圈子邀请实体
     * @return 圈子邀请
     */
    public CircleInvite getUnique(CircleInvite circleInvite) {
        return circleInviteReadDao.getUnique(circleInvite);
    }
    /**
     * 分页查询圈子邀请列表
     * @param circleInvite 圈子邀请实体
     * @param page 分页信息
     * @return 圈子邀请列表
     */
    public List<CircleInvite> listPage(CircleInvite circleInvite, Page page) {
        return circleInviteReadDao.listPage(circleInvite, page);
    }

    /**
     * 查询所有圈子邀请数据
     * @param circleInvite 圈子邀请实体
     * @return 圈子邀请列表
     */
    public List<CircleInvite> list(CircleInvite circleInvite) {
        return circleInviteReadDao.listPage(circleInvite, null);
    }



    /**
     * 根据圈子邀请主键集合查询圈子邀请数据
     * @param ids 主键集合
     * @param circleInvite 圈子邀请实体
     * @param page 分页信息
     * @return 圈子邀请列表
     */
    public List<CircleInvite> batchList(@NotNull Set<String> ids, CircleInvite circleInvite, Page page) {
        if (CollectionUtils.isEmpty(ids))
            return Collections.EMPTY_LIST;

        return circleInviteReadDao.batchList(ids, new HashedMap(), page);
    }

    @Override
    public CircleInvite getTheFirstEmptyInvite(CircleInvite search) {
        return circleInviteReadDao.getTheFirstEmptyInvite(search);
    }
}
