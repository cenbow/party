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
import com.party.core.dao.read.circle.CircleMemberTagReadDao;
import com.party.core.dao.write.circle.CircleMemberTagWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.circle.CircleMember;
import com.party.core.model.circle.CircleMemberTag;
import com.party.core.service.circle.ICircleMemberTagService;
import com.sun.istack.NotNull;

/**
 * CircleMemberTagService圈子用户标签服务实现
 *
 * @author Juliana
 * @data 2016-12-14
 */

@Service
public class CircleMemberTagService implements ICircleMemberTagService{

    @Autowired
    private CircleMemberTagReadDao circleMemberTagReadDao;
    @Autowired
    private CircleMemberTagWriteDao circleMemberTagWriteDao;

    /**
     * 插入圈子用户标签数据
     * @param circleMemberTag 圈子用户标签实体
     * @return 插入结果（true/false）
     */
    public String insert(CircleMemberTag circleMemberTag) {
        BaseModel.preInsert(circleMemberTag);
        boolean result = circleMemberTagWriteDao.insert(circleMemberTag);
        if (result){
            return circleMemberTag.getId();
        }
        return null;
    }

    /**
     * 更新圈子用户标签信息
     * @param circleMemberTag 圈子用户标签实体
     * @return 更新结果（true/false）
     */
    public boolean update(CircleMemberTag circleMemberTag) {
        if (null == circleMemberTag)
            return false;
        circleMemberTag.setUpdateDate(new Date());
        return circleMemberTagWriteDao.update(circleMemberTag);
    }

    /**
     * 逻辑删除圈子用户标签数据
     * @param id 圈子用户标签实体主键
     * @return 删除结果（true/false）
     */
    public boolean deleteLogic(@NotNull String id) {
        if (StringUtils.isBlank(id))
            return false;

        return circleMemberTagWriteDao.deleteLogic(id);
    }

    /**
     * 物理删除圈子用户标签数据
     * @param id 圈子用户标签实体主键
     * @return 删除结果（true/false）
     */
    public boolean delete(@NotNull String id) {
        if (StringUtils.isBlank(id))
            return false;

        return circleMemberTagWriteDao.delete(id);
    }

    /**
     * 批量逻辑删除圈子用户标签数据
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    public boolean batchDeleteLogic(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids))
            return false;

        return circleMemberTagWriteDao.batchDeleteLogic(ids);
    }

    /**
     * 批量物理删除圈子用户标签数据
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    public boolean batchDelete(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids))
            return false;

        return circleMemberTagWriteDao.batchDelete(ids);
    }
    /**
     * 查询单个圈子用户关系
     * @param circleMember 圈子实体
     * @return 圈子列表
     */
    public CircleMemberTag getUnique(CircleMemberTag CircleMemberTag) {
        return circleMemberTagReadDao.getUnique(CircleMemberTag);
    }
    /**
     * 根据主键获取圈子用户标签实体数据
     * @param id 主键
     * @return 圈子用户标签实体
     */
    public CircleMemberTag get(String id) {
        if (StringUtils.isBlank(id))
            return null;

        return circleMemberTagReadDao.get(id);
    }

    /**
     * 分页查询圈子用户标签列表
     * @param circleMemberTag 圈子用户标签实体
     * @param page 分页信息
     * @return 圈子用户标签列表
     */
    public List<CircleMemberTag> listPage(CircleMemberTag circleMemberTag, Page page) {
        return circleMemberTagReadDao.listPage(circleMemberTag, page);
    }

    /**
     * 查询所有圈子用户标签数据
     * @param circleMemberTag 圈子用户标签实体
     * @return 圈子用户标签列表
     */
    public List<CircleMemberTag> list(CircleMemberTag circleMemberTag) {
        return circleMemberTagReadDao.listPage(circleMemberTag, null);
    }

    /**
     * 根据圈子用户标签主键集合查询圈子用户标签数据
     * @param ids 主键集合
     * @param circleMemberTag 圈子用户标签实体
     * @param page 分页信息
     * @return 圈子用户标签列表
     */
    public List<CircleMemberTag> batchList(@NotNull Set<String> ids, CircleMemberTag circleMemberTag, Page page) {
        if (CollectionUtils.isEmpty(ids))
            return Collections.EMPTY_LIST;

        return circleMemberTagReadDao.batchList(ids, new HashedMap(), page);
    }
    /**
     *根据tagids获取关系 
     */
	@Override
	public List<String> getByTagsId(String tags) {
		return circleMemberTagReadDao.getByTagsId(tags);		
	}
}
