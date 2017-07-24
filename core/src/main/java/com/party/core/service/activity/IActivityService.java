package com.party.core.service.activity;

import com.party.common.paging.Page;
import com.party.core.model.activity.Activity;
import com.party.core.service.IBaseService;

import java.util.List;
import java.util.Map;

/**
 * IActivityService 活动接口
 *
 * @author Wesley
 * @data 16/9/5 17:26 .
 */
public interface IActivityService extends IBaseService<Activity> {

	/**
	 * 查询所有活动
	 * 
	 * @return 活动列表
	 */
	List<Activity> listAll();

	List<Activity> listPage(Activity activity, Map<String, Object> params, Page page);

	/**
	 * 根据事件编号统计
	 * @param eventId 事件编号
	 * @return 统计结果
	 */
	Activity countForEventId(String eventId);
}
