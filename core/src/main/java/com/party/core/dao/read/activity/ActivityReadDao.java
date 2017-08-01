package com.party.core.dao.read.activity;

import com.party.common.paging.Page;
import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.activity.Activity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * ActivityReadDao活动数据读取
 *
 * @author Wesley
 * @data 16/9/5 17:21 .
 */
@Repository
public interface ActivityReadDao extends BaseReadDao<Activity> {

	List<Activity> joinedList(Activity activity);

	List<Activity> webListPage(@Param("activity") Activity activity, @Param("params") Map<String, Object> params, Page page);

	/**
	 * 根据事件编号统计
	 * @param eventId 事件编号
	 * @return 统计结果
	 */
	Activity countForEventId(@Param(value = "eventId") String eventId);

    Integer getCount(Activity activity);
}
