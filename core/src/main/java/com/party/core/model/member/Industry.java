package com.party.core.model.member;

import com.party.core.model.BaseModel;

import java.io.Serializable;

/**
 * 行业实体
 * party
 * Created by wei.li
 * on 2016/8/13 0013.
 */
public class Industry extends BaseModel implements Serializable {

    private static final long serialVersionUID = -1913922340097024188L;

    //父级ID
    private String parentId;

    //父级ID字符串
    private String parentIds;

    //行业名
    private String name;

    //排序
    private String sort;

    //拼音
    private String pinyin;

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Industry industry = (Industry) o;

        if (parentId != null ? !parentId.equals(industry.parentId) : industry.parentId != null) return false;
        if (parentIds != null ? !parentIds.equals(industry.parentIds) : industry.parentIds != null) return false;
        if (name != null ? !name.equals(industry.name) : industry.name != null) return false;
        return sort != null ? sort.equals(industry.sort) : industry.sort == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (parentId != null ? parentId.hashCode() : 0);
        result = 31 * result + (parentIds != null ? parentIds.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (sort != null ? sort.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "industry{" +
                "parentId='" + parentId + '\'' +
                ", parentIds='" + parentIds + '\'' +
                ", name='" + name + '\'' +
                ", sort='" + sort + '\'' +
                '}';
    }
}
