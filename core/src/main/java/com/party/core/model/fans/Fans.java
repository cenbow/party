package com.party.core.model.fans;

import com.party.core.model.BaseModel;

/**
 * 粉丝实体
 * party
 * Created by wei.li
 * on 2016/9/19 0019.
 */
public class Fans extends BaseModel {

    private static final long serialVersionUID = 7885200857131203539L;

    //会员编号
    private String memberId;

    //粉丝编号
    private String followerId;

    //排序
    private Integer sort;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getFollowerId() {
        return followerId;
    }

    public void setFollowerId(String followerId) {
        this.followerId = followerId;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Fans fans = (Fans) o;

        if (memberId != null ? !memberId.equals(fans.memberId) : fans.memberId != null) return false;
        if (followerId != null ? !followerId.equals(fans.followerId) : fans.followerId != null) return false;
        return sort != null ? sort.equals(fans.sort) : fans.sort == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (memberId != null ? memberId.hashCode() : 0);
        result = 31 * result + (followerId != null ? followerId.hashCode() : 0);
        result = 31 * result + (sort != null ? sort.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Fans{" +
                "memberId='" + memberId + '\'' +
                ", followerId='" + followerId + '\'' +
                ", sort=" + sort +
                '}';
    }
}
