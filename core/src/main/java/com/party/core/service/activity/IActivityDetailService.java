package com.party.core.service.activity;

import com.party.core.model.activity.ActivityDetail;
import com.party.core.service.IBaseService;

/**
 * IActivityDetailService 活动详情接口
 *
 * @author Wesley
 * @data 16/9/6 14:47 .
 */
public interface IActivityDetailService extends IBaseService<ActivityDetail> {
    /**
     * 根据refId查找活动详情数据
     * @param refId
     * @return 活动详情实体
     */
    public ActivityDetail getByRefId(String refId);

    /**
     * 根据refId删除活动详情数据
     * @param id refId
     * @return 
     */
	public boolean deleteByRefId(String id);
}
