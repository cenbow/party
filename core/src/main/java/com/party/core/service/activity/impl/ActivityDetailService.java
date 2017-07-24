package com.party.core.service.activity.impl;

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
import com.party.core.dao.read.activity.ActivityDetailReadDao;
import com.party.core.dao.write.activity.ActivityDetailWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.activity.ActivityDetail;
import com.party.core.service.activity.IActivityDetailService;
import com.sun.istack.NotNull;

/**
 * ActivityDetailService
 *
 * @author Wesley
 * @data 16/9/6 14:48 .
 */
@Service
public class ActivityDetailService implements IActivityDetailService{
    @Autowired
    private ActivityDetailReadDao readDao;
    @Autowired
    private ActivityDetailWriteDao writeDao;


    /**
     * 插入活动详情数据
     * @param activityDetail 活动详情实体
     * @return 插入结果(true/false)
     */
    public String insert(ActivityDetail activityDetail) {
        BaseModel.preInsert(activityDetail);
        boolean result = writeDao.insert(activityDetail);
        if (result){
            return activityDetail.getId();
        }
        return null;
    }

    /**
     * 更新活动详情数据
     * @param activityDetail 活动详情实体
     * @return 更新结果(true/false)
     */
    public boolean update(ActivityDetail activityDetail) {
        if (null == activityDetail)
            return false;
        activityDetail.setUpdateDate(new Date());
        return writeDao.update(activityDetail);
    }

    /**
     * 逻辑删除活动详情数据
     * @param id 实体主键
     * @return 逻辑删除结果(true/false)
     */
    public boolean deleteLogic(@NotNull String id) {
        if (StringUtils.isBlank(id))
            return false;

        return writeDao.deleteLogic(id);
    }


    /**
     * 物理删除活动详情数据
     * @param id 实体主键
     * @return 物理删除结果(true/false)
     */
    public boolean delete(@NotNull String id) {
        if (StringUtils.isBlank(id))
            return false;

        return writeDao.delete(id);
    }

    /**
     * 批量逻辑删除活动详情数据
     * @param ids 主键集合
     * @return 批量逻辑删除结果(true/false)
     */
    public boolean batchDeleteLogic(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids))
            return false;

        return writeDao.batchDeleteLogic(ids);
    }

    /**
     * 批量物理删除活动详情数据
     * @param ids 主键集合
     * @return 批量物理删除结果(true/false)
     */
    public boolean batchDelete(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids))
            return false;

        return writeDao.batchDelete(ids);
    }

    /**
     * 根据主键获取活动详情数据
     * @param id 主键
     * @return 活动详情实体
     */
    public ActivityDetail get(String id) {
        if (StringUtils.isBlank(id))
            return null;

        return readDao.get(id);
    }

    /**
     * 分页查询活动详情列表
     * @param activityDetail
     * @param page 分页信息
     * @return 活动详情列表
     */
    public List<ActivityDetail> listPage(ActivityDetail activityDetail, Page page) {
        return readDao.listPage(activityDetail, page);
    }

    /**
     * 查询所有活动详情数据
     * @param activityDetail
     * @return 活动详情列表
     */
    public List<ActivityDetail> list(ActivityDetail activityDetail) {
        return readDao.listPage(activityDetail, null);
    }

    /**
     * 根据活动详情主键集合查询活动详情数据
     * @param ids 主键集合
     * @param activityDetail
     * @param page 分页信息
     * @return 活动详情列表
     */
    public List<ActivityDetail> batchList(@NotNull Set<String> ids, ActivityDetail activityDetail, Page page) {
        if (CollectionUtils.isEmpty(ids))
            return Collections.EMPTY_LIST;

        return readDao.batchList(ids, new HashedMap(), page);
    }

    /**
     * 根据refId查找活动详情数据
     * @param refId
     * @return 活动详情实体
     */
    public ActivityDetail getByRefId(String refId) {
        if (StringUtils.isBlank(refId))
            return null;

        return readDao.getByRefId(refId);
    }

    /**
     * 根据refId删除活动详情数据
     */
	@Override
	public boolean deleteByRefId(String refId) {
		if (StringUtils.isBlank(refId)) {
			return false;
		}
		ActivityDetail detail = readDao.getByRefId(refId);
		if (detail != null) {
			return writeDao.delete(detail.getId());
		} else {
			return false;
		}
	}
}
