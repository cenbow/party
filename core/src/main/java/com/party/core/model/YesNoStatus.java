package com.party.core.model;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * 是，否 状态
 * YesNoStatus
 * @author Wesley
 * @data 16/11/21
 */
public enum YesNoStatus {
    YES(1, "是"),
    NO(0, "否");

    //状态码
    private Integer code;

    //状态值
    private String value;

    YesNoStatus(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    /**
     * 根据状态码获取状态值
     * @param code 状态码
     * @return 状态值
     */
    public static String getValue(Integer code){
        for (YesNoStatus yesNoStatus : YesNoStatus.values()){
            if (yesNoStatus.getCode().equals(code)){
                return yesNoStatus.getValue();
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
        for (YesNoStatus yesNoStatus : YesNoStatus.values()){
            map.put(yesNoStatus.getCode(),yesNoStatus.getValue());
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
