package com.party.core.model.system;

import com.party.core.model.BaseModel;

/**
 * 系统字典实体
 * party
 * Created by wei.li
 * on 2016/9/14 0014.
 */
public class Dict extends BaseModel {

    private static final long serialVersionUID = -750515309773226643L;

    //数据值
    private String value;

    //标签名
    private String label;

    //类型
    private String type;

    //描叙
    private String description;

    //排序
    private Integer sort;

    //父级编号
    private String parentId;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Dict dict = (Dict) o;

        if (value != null ? !value.equals(dict.value) : dict.value != null) return false;
        if (label != null ? !label.equals(dict.label) : dict.label != null) return false;
        if (type != null ? !type.equals(dict.type) : dict.type != null) return false;
        if (description != null ? !description.equals(dict.description) : dict.description != null) return false;
        if (sort != null ? !sort.equals(dict.sort) : dict.sort != null) return false;
        return parentId != null ? parentId.equals(dict.parentId) : dict.parentId == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (label != null ? label.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (sort != null ? sort.hashCode() : 0);
        result = 31 * result + (parentId != null ? parentId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Dict{" +
                "value='" + value + '\'' +
                ", label='" + label + '\'' +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", sort=" + sort +
                ", parentId='" + parentId + '\'' +
                '}';
    }
}
