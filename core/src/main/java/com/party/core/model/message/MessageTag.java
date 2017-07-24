package com.party.core.model.message;

/**
 * 消息分类枚举
 * party
 * Created by wei.li
 * on 2016/10/8 0008.
 */
public enum MessageTag {

    UNDEFIND("-1", "未定义"),

    DISCOVERY("1", "社区"),

    CIRCLE("2", "圈子"),

    MEMBER("3","会员");

    //状态码
    private String code;

    //状态值
    private String value;

    MessageTag(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
