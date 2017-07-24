package com.party.core.model;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * 审核状态
 *
 * @author Wesley
 * @data 16/11/21 9:22 .
 */
public enum AuditStatus {
    AUDITING("0","审核中"),

    PASS("1","通过"),

    REJECT("2","拒绝");

    //状态码
    private String code;

    //状态值
    private String value;

    AuditStatus(String code, String value) {
        this.code = code;
        this.value = value;
    }

    /**
     * 根据状态码获取状态值
     * @param code 状态码
     * @return 状态值
     */
    public static String getValue(String code){
        for (AuditStatus actStatus : AuditStatus.values()){
            if (actStatus.getCode().equals(code)){
                return actStatus.getValue();
            }
        }
        return null;
    }


    /**
     * 枚举类型转换为map
     * @return 转换后的map
     */
    public static Map<String,String> convertMap(){
        Map<String,String> map = Maps.newHashMap();
        for (AuditStatus checkStatus : AuditStatus.values()){
            map.put(checkStatus.getCode(),checkStatus.getValue());
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
