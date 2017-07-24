package com.party.core.model.message;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * 前端消息内容
 * party
 * Created by wei.li
 * on 2016/10/8 0008.
 */
public enum MessageType {

    LOVE("1", "赞"),

    COMMENT("2", "评论"),

    FOCUS("3", "关注"),

    SYS("4", "系统通知"),

    ACT("5", "活动"),

    GOODS("6", "商品"),

    ACTIVE_PUSH("7", "主动推送");


    //状态码
    private String code;

    //状态值
    private String value;

    MessageType(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public static Map<String, String> convertMap(){
        Map<String, String> map = Maps.newHashMap();
        for (MessageType messageType : MessageType.values()){
            map.put(messageType.getCode(), messageType.getValue());
        }
        return map;
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
