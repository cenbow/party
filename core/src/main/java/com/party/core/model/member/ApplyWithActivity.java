package com.party.core.model.member;

/**
 * 报名包含活动信息
 * Created by wei.li
 *
 * @date 2017/3/9 0009
 * @time 18:44
 */
public class ApplyWithActivity extends MemberAct {
    private static final long serialVersionUID = -122260356402363027L;

    //活动题目
    private String activityTitle;

    public String getActivityTitle() {
        return activityTitle;
    }

    public void setActivityTitle(String activityTitle) {
        this.activityTitle = activityTitle;
    }
}
