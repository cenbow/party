package com.party.mobile.web.dto.activity;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * ActProgressStatus
 * 活动或者商品是否已经截止
 * @author Wesley
 * @data 16/9/18 9:22 .
 */
public enum ActProgressStatus {
    PROGRESS_STATUS_OVERDUE(0,"已截至"),

    PROGRESS_STATUS_AGOING(1,"进行中");

    //状态码
    private Integer code;

    //状态值
    private String value;

    ActProgressStatus(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    /**
     * 根据状态码获取状态值
     * @param code 状态码
     * @return 状态值
     */
    public static String getValue(String code){
        for (ActProgressStatus status : ActProgressStatus.values()){
            if (status.getCode().equals(code)){
                return status.getValue();
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
        for (ActProgressStatus status : ActProgressStatus.values()){
            map.put(status.getCode(),status.getValue());
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
