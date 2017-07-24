package com.party.core.model.message;

/**
 * 消息状态枚举
 * party
 * Created by wei.li
 * on 2016/10/8 0008.
 */
public enum MessageStatus {

    MESSAGE_STATUS_NEW(1, "新消息"),

    MESSAGE_STATUS_READ(0, "已读消息");

    //状态码
    private Integer code;

    //状态值
    private String value;

    MessageStatus(Integer code, String value) {
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
