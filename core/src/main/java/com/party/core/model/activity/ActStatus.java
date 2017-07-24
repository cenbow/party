package com.party.core.model.activity;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * 活动报名状态
 *
 * @author Wesley
 * @data 16/9/18 9:22 .
 */
public enum ActStatus {
    ACT_STATUS_AUDITING(0,"审核中"),

    ACT_STATUS_AUDIT_PASS(1,"审核通过，待支付"),

    ACT_STATUS_AUDIT_REJECT(2,"审核拒绝"),

    ACT_STATUS_PAID(3,"已支付，报名成功"),

    ACT_STATUS_CANCEL(4,"已取消"),

    ACT_STATUS_NO_JOIN(5,"未参与");

    //状态码
    private Integer code;

    //状态值
    private String value;

    ActStatus(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    /**
     * 根据状态码获取状态值
     * @param code 状态码
     * @return 状态值
     */
    public static String getValue(Integer code){
        for (ActStatus actStatus : ActStatus.values()){
            if (actStatus.getCode() == code){
                return actStatus.getValue();
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
        for (ActStatus actStatus : ActStatus.values()){
            map.put(actStatus.getCode(),actStatus.getValue());
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
