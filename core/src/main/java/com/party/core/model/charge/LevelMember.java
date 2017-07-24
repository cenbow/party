package com.party.core.model.charge;

import com.party.core.model.BaseModel;

import java.util.Date;

/**
 * 等级与会员中间表
 */
public class LevelMember extends BaseModel {
    private String memberId; // 用户
    private String levelId; // 等级
    private String sysRoleId; // 角色
    private Date startTime; // 开始时间
    private Date endTime; // 结束时间
    private Integer status; // 状态 1 有效 2 失效

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getLevelId() {
        return levelId;
    }

    public void setLevelId(String levelId) {
        this.levelId = levelId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSysRoleId() {
        return sysRoleId;
    }

    public void setSysRoleId(String sysRoleId) {
        this.sysRoleId = sysRoleId;
    }
}
