package com.party.core.model.city;

import com.party.core.model.BaseModel;

/**
 * 省份实体
 * party
 * Created by wei.li
 * on 2016/9/14 0014.
 */
public class Province extends BaseModel{

    //名称
    private String name;

    //代码
    private String code;

    //是否以开放
    private Integer isOpen;


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

        Province province = (Province) o;

        if (name != null ? !name.equals(province.name) : province.name != null) return false;
        if (code != null ? !code.equals(province.code) : province.code != null) return false;
        return isOpen != null ? isOpen.equals(province.isOpen) : province.isOpen == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (isOpen != null ? isOpen.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Province{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", isOpen=" + isOpen +
                '}';
    }
}

