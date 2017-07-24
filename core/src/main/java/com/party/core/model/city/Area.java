package com.party.core.model.city;

import com.party.core.model.BaseModel;

import java.io.Serializable;

/**
 * 系统地区实体
 * party
 * Created by wei.li
 * on 2016/8/15 0015.
 */
public class Area extends BaseModel implements Serializable {


    private static final long serialVersionUID = -3837070758098464275L;

    //父节点ID
    private String parentId;

    //父节点字符串
    private String parentIds;

    //地区名
    private String name;

    //排序
    private String sort;

    //地区代码
    private String code;

    //地区类型
    private Integer type;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Area area = (Area) o;

        if (parentId != null ? !parentId.equals(area.parentId) : area.parentId != null) return false;
        if (parentIds != null ? !parentIds.equals(area.parentIds) : area.parentIds != null) return false;
        if (name != null ? !name.equals(area.name) : area.name != null) return false;
        if (sort != null ? !sort.equals(area.sort) : area.sort != null) return false;
        if (code != null ? !code.equals(area.code) : area.code != null) return false;
        return type != null ? type.equals(area.type) : area.type == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (parentId != null ? parentId.hashCode() : 0);
        result = 31 * result + (parentIds != null ? parentIds.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (sort != null ? sort.hashCode() : 0);
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Area{" +
                "parentId='" + parentId + '\'' +
                ", parentIds='" + parentIds + '\'' +
                ", name='" + name + '\'' +
                ", sort='" + sort + '\'' +
                ", code='" + code + '\'' +
                ", type=" + type +
                '}';
    }
}
