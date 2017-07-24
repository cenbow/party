package com.party.core.model.store;

/**
 * 时间类型枚举
 * party
 * Created by wei.li
 * on 2016/10/12 0012.
 */
public enum TimeType {

    BY_YESTERDAY(0, "昨天"),

    BY_LAST_WEEK(1, "上周"),

    BY_LAST_MONTH(2, "上个月");

    //状态码
    private Integer code;

    //状态值
    private String value;

    TimeType(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
