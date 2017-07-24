package com.party.core.model.dynamic;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * 动态类型枚举
 * party
 * Created by wei.li
 * on 2016/9/19 0019.
 */
public enum DynamicType {

    COMMUNITY("1", "社区动态"),

    CIRCLE("2", "圈子动态");

    //状态码
    private String code;

    //状态值
    private String value;

    DynamicType(String code, String value) {
        this.code = code;
        this.value = value;
    }

    /**
     * 根据状态码获取状态值
     * @param code 状态码
     * @return 状态值
     */
    public static String getValue(String code){
        for (DynamicType dynamicType : DynamicType.values()){
            if (dynamicType.getCode().equals(code)){
                return dynamicType.getValue();
            }
        }
        return null;
    }

    /**
     * 获取map 形式动态类型
     * @return 动态类型map
     */
    public static Map<String, String> convertMap(){
        Map<String, String> map = Maps.newHashMap();
        for (DynamicType dynamicType : DynamicType.values()){
            map.put(dynamicType.getCode(), dynamicType.getValue());
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
