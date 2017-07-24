package com.party.core.model.fans;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * 会员关注状态
 *
 * @author Wesley
 * @data 16/11/8 9:22 .
 */
public enum FocusStatus {
    FOCUS_STATUS_SAME_MEMBER(-1,"同一用户"),

    FOCUS_STATUS_NOT_FOCUS_ON(0,"未关注"),

    FOCUS_STATUS_HAS_FOCUS_ON(1,"已关注"),

    FOCUS_STATUS_MUTUAL_FOCUS_ON(2,"互相关注");


    //状态码
    private Integer code;

    //状态值
    private String value;

    FocusStatus(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    /**
     * 根据状态码获取状态值
     * @param code 状态码
     * @return 状态值
     */
    public static String getValue(String code){
        for (FocusStatus focusStatus : FocusStatus.values()){
            if (focusStatus.getCode().equals(code)){
                return focusStatus.getValue();
            }
        }
        return null;
    }


    /**
     * 枚举类型转换为map
     * @return 转换后的map
     */
    public static Map<Integer,String> convertMap(){
        Map<Integer,String> map = Maps.newHashMap();
        for (FocusStatus focusStatus : FocusStatus.values()){
            map.put(focusStatus.getCode(),focusStatus.getValue());
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
