package com.party.core.model.crowdfund;

import com.party.core.model.BaseModel;

/**
 * 众筹分析
 * Created by wei.li
 *
 * @date 2017/7/7 0007
 * @time 14:49
 */
public class Analyze extends BaseModel {
    private static final long serialVersionUID = -5694890927696060018L;

    //目标编号
    private String targetId;

    //是否加好友
    private Integer isFriend;

    //是否加群
    private Integer isGroup;

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public Integer getIsFriend() {
        return isFriend;
    }

    public void setIsFriend(Integer isFriend) {
        this.isFriend = isFriend;
    }

    public Integer getIsGroup() {
        return isGroup;
    }

    public void setIsGroup(Integer isGroup) {
        this.isGroup = isGroup;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Analyze analyze = (Analyze) o;

        if (targetId != null ? !targetId.equals(analyze.targetId) : analyze.targetId != null) return false;
        if (isFriend != null ? !isFriend.equals(analyze.isFriend) : analyze.isFriend != null) return false;
        return isGroup != null ? isGroup.equals(analyze.isGroup) : analyze.isGroup == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (targetId != null ? targetId.hashCode() : 0);
        result = 31 * result + (isFriend != null ? isFriend.hashCode() : 0);
        result = 31 * result + (isGroup != null ? isGroup.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Analyze{" +
                "targetId='" + targetId + '\'' +
                ", isFriend=" + isFriend +
                ", isGroup=" + isGroup +
                '}';
    }
}
