package com.party.core.model.city;

import com.party.core.model.BaseModel;

import java.io.Serializable;

/**
 * 城市实体
 * party
 * Created by wei.li
 * on 2016/8/13 0013.
 */
public class City extends BaseModel implements Serializable {

    private static final long serialVersionUID = 4059926619890127517L;

    //省份
    private String province;

    //名称
    private String name;

    //代码
    private String code;

    //是否开启
    private Integer isOpen;

    //排序
    private Integer sort;

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Integer isOpen) {
        this.isOpen = isOpen;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        City city = (City) o;

        if (province != null ? !province.equals(city.province) : city.province != null) return false;
        if (name != null ? !name.equals(city.name) : city.name != null) return false;
        if (code != null ? !code.equals(city.code) : city.code != null) return false;
        return isOpen != null ? isOpen.equals(city.isOpen) : city.isOpen == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (province != null ? province.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (isOpen != null ? isOpen.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "city{" +
                "province='" + province + '\'' +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", isOpen=" + isOpen +
                '}';
    }
}
