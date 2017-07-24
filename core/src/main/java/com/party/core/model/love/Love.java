package com.party.core.model.love;

import com.party.core.model.BaseModel;

/**
 * 点赞实体
 * party
 * Created by wei.li
 * on 2016/9/18 0018.
 */
public class Love extends BaseModel {

    private static final long serialVersionUID = -662020994999324554L;

    //是否点赞(0：否 1：是 )
    private Integer isLove;

    //关联id
    private String refId;

    //点赞类型（1：社区动态；2：圈子动态；）
    private String loveType;

    //排序
    private Integer sort;

    public Integer getIsLove() {
        return isLove;
    }

    public void setIsLove(Integer isLove) {
        this.isLove = isLove;
    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public String getLoveType() {
        return loveType;
    }

    public void setLoveType(String loveType) {
        this.loveType = loveType;
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

        Love love = (Love) o;

        if (isLove != null ? !isLove.equals(love.isLove) : love.isLove != null) return false;
        if (refId != null ? !refId.equals(love.refId) : love.refId != null) return false;
        if (loveType != null ? !loveType.equals(love.loveType) : love.loveType != null) return false;
        return sort != null ? sort.equals(love.sort) : love.sort == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (isLove != null ? isLove.hashCode() : 0);
        result = 31 * result + (refId != null ? refId.hashCode() : 0);
        result = 31 * result + (loveType != null ? loveType.hashCode() : 0);
        result = 31 * result + (sort != null ? sort.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Love{" +
                "isLove=" + isLove +
                ", refId='" + refId + '\'' +
                ", loveType='" + loveType + '\'' +
                ", sort=" + sort +
                '}';
    }
}
