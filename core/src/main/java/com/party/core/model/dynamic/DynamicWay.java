package com.party.core.model.dynamic;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * 动态方式
 * party
 * Created by wei.li
 * on 2016/9/19 0019.
 */
public enum DynamicWay {

    YOUR_SEND(0, "自发"),

    OTHERS_SEND(1, "代发");

    //状态码
    private Integer code;

    //状态值
    private String value;

    DynamicWay(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    /**
     * 根据状态码获取状态值
     * @param code 状态码
     * @return 状态值
     */
    public static String getValue(Integer code){
        for (DynamicWay dynamicWay : DynamicWay.values()){
            if (dynamicWay.getCode().equals(code)){
                return dynamicWay.getValue();
            }
        }
        return null;
    }


    /**
     * 获取map类型动态方式
     * @return 动态方式
     */
    public static Map<Integer, String> convertMap(){
        Map<Integer, String> map = Maps.newHashMap();
        for (DynamicWay dynamicWay : DynamicWay.values()){
            map.put(dynamicWay.getCode(), dynamicWay.getValue());
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
