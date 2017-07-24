package com.party.core.model.message;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * 消息类型
 * party
 * Created by wei.li
 * on 2016/8/23 0023.
 */
public enum SysMessageType {

    MEMBER(0,"会员消息"),

    ACTIVITY(1,"活动消息"),

    SIGN_UP(2,"报名消息"),

    FEEDBACK(3,"反馈消息"),

    REPORT(4,"举报消息");


    //状态码
    private Integer code;

    //状态值
    private String value;

    SysMessageType(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    /**
     * 根据代码获取值
     * @param code
     * @return
     */
    public static String getValue(Integer code){
        for (SysMessageType sysMessageType : SysMessageType.values()){
            if (sysMessageType.getCode().equals(code)){
                return sysMessageType.getValue();
            }
        }
        return null;
    }


    /**
     * 转换为map
     * @return
     */
    public static Map<Integer, String> convertMap(){
        Map<Integer, String> map = Maps.newHashMap();
        for (SysMessageType sysMessageType : SysMessageType.values()){
            map.put(sysMessageType.getCode(), sysMessageType.getValue());
        }
        return map;
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
