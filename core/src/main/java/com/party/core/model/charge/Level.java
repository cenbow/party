package com.party.core.model.charge;

import com.party.core.model.BaseModel;

/**
 * 等级产品
 */
public class Level extends BaseModel {
    private String name; // 名称
    private Float price; // 费用
    private String unit; // 单位
    private Integer sort; // 排序
    private String picture; // 封面图
    private String sysRoleId; // 角色id
    private String sysRoleName; // 角色名称

    public static final String UNIT_MONTH = "月";
    public static final String UNIT_QUARTER = "季度";
    public static final String UNIT_HALF_YEAR = "半年";
    public static final String UNIT_YEAR = "年";

    public String getSysRoleName() {
        return sysRoleName;
    }

    public void setSysRoleName(String sysRoleName) {
        this.sysRoleName = sysRoleName;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getSysRoleId() {
        return sysRoleId;
    }

    public void setSysRoleId(String sysRoleId) {
        this.sysRoleId = sysRoleId;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
