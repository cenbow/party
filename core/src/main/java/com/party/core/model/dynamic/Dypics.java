package com.party.core.model.dynamic;

import com.party.core.model.BaseModel;

/**
 * 动态图片实体
 * party
 * Created by Wesley
 * on 2016/11/17
 */
public class Dypics extends BaseModel {

    private static final long serialVersionUID = 6362421553022845974L;

    //图片URL
    private String picUrl;

    //关联编号
    private String refId;

    //类型（1：社区动态图片；2：圈子动态图片；）
    private String type;

    //排序
    private Integer sort;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

        Dypics dypics = (Dypics) o;

        if (picUrl != null ? !picUrl.equals(dypics.picUrl) : dypics.picUrl != null) return false;
        if (refId != null ? !refId.equals(dypics.refId) : dypics.refId != null) return false;
        if (type != null ? !type.equals(dypics.type) : dypics.type != null) return false;
        return sort != null ? sort.equals(dypics.sort) : dypics.sort == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (picUrl != null ? picUrl.hashCode() : 0);
        result = 31 * result + (refId != null ? refId.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (sort != null ? sort.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Dypics{" +
                "picUrl='" + picUrl + '\'' +
                ", refId='" + refId + '\'' +
                ", type='" + type + '\'' +
                ", sort=" + sort +
                '}';
    }
}
