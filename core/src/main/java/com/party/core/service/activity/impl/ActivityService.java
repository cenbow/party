package com.party.core.service.activity.impl;

import com.party.common.paging.Page;
import com.party.common.utils.StringUtils;
import com.party.common.utils.UUIDUtils;
import com.party.core.dao.read.activity.ActivityReadDao;
import com.party.core.dao.write.activity.ActivityWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.activity.Activity;
import com.party.core.service.activity.IActivityService;
import com.sun.istack.NotNull;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * ActivityService活动服务实现
 *
 * @author Wesley
 * @data 16/9/5 17:28 .
 */

@Service
public class ActivityService implements IActivityService{

    @Autowired
    private ActivityReadDao activityReadDao;

    @Autowired
    private ActivityWriteDao activityWriteDao;

    /**
     * 插入活动数据
     * @param activity 活动实体
     * @return 插入结果（true/false）
     */
    public String insert(Activity activity) {
        BaseModel.preInsert(activity);
        boolean result = activityWriteDao.insert(activity);
        if (result){
            return activity.getId();
        }
        return null;
    }

    /**
     * 更新活动信息
     * @param activity 活动实体
     * @return 更新结果（true/false）
     */
    public boolean update(Activity activity) {
        if (null == activity)
            return false;
        activity.setUpdateDate(new Date());
        return activityWriteDao.update(activity);
    }

    /**
     * 逻辑删除活动数据
     * @param id 活动实体主键
     * @return 删除结果（true/false）
     */
    public boolean deleteLogic(@NotNull String id) {
        if (StringUtils.isBlank(id))
            return false;

        return activityWriteDao.deleteLogic(id);
    }

    /**
     * 物理删除活动数据
     * @param id 活动实体主键
     * @return 删除结果（true/false）
     */
    public boolean delete(@NotNull String id) {
        if (StringUtils.isBlank(id))
            return false;

        return activityWriteDao.delete(id);
    }

    /**
     * 批量逻辑删除活动数据
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    public boolean batchDeleteLogic(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids))
            return false;

        return activityWriteDao.batchDeleteLogic(ids);
    }

    /**
     * 批量物理删除活动数据
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    public boolean batchDelete(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids))
            return false;

        return activityWriteDao.batchDelete(ids);
    }

    /**
     * 根据主键获取活动实体数据
     * @param id 主键
     * @return 活动实体
     */
    public Activity get(String id) {
        if (StringUtils.isBlank(id))
            return null;

        return activityReadDao.get(id);
    }

    /**
     * 分页查询活动列表
     * @param activity 活动实体
     * @param page 分页信息
     * @return 活动列表
     */
    public List<Activity> listPage(Activity activity, Page page) {
        return activityReadDao.listPage(activity, page);
    }

    /**
     * 查询所有活动数据
     * @param activity 活动实体
     * @return 活动列表
     */
    public List<Activity> list(Activity activity) {
        return activityReadDao.listPage(activity, null);
    }

    /**
     * 查询所有活动
     * @return 活动列表
     */
    @Override
    public List<Activity> listAll() {
        return activityReadDao.listPage(null, null);
    }

    /**
     * 根据活动主键集合查询活动数据
     * @param ids 主键集合
     * @param activity 活动实体
     * @param page 分页信息
     * @return 活动列表
     */
    public List<Activity> batchList(@NotNull Set<String> ids, Activity activity, Page page) {
        if (CollectionUtils.isEmpty(ids))
            return Collections.EMPTY_LIST;

        return activityReadDao.batchList(ids, new HashedMap(), page);
    }

	public List<Activity> joinedList(Activity activity) {
		return activityReadDao.joinedList(activity);
	}

	@Override
	public List<Activity> listPage(Activity activity, Map<String, Object> params, Page page) {
		return activityReadDao.webListPage(activity, params, page);
	}

    /**
     * 根据事件编号统计
     * @param eventId 事件编号
     * @return 统计结果
     */
    @Override
    public Activity countForEventId(String eventId) {
        return activityReadDao.countForEventId(eventId);
    }
}
