package com.party.core.service.crowdfund;


import com.party.core.model.activity.Activity;

/**
 * 校正数据服务接口
 * Created by wei.li
 *
 * @date 2017/7/3 0003
 * @time 18:45
 */

public interface IProjectReviseService {


    /**
     * 校正支持数据
     * @param id 编号
     */
    void reviseFavorer(String id);


    /**
     * 校正众筹目标
     * @param activityId 众筹目标
     */
    void reviseTarget(String activityId);
}
