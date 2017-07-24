package com.party.core.model.circle;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * 圈子申请状态
 *
 * @author Wesley
 * @data 16/9/18 9:22 .
 */
public enum CircleCode {
    Apply_STATUS_AUDITING(0,"审核中"),

    Apply_STATUS_AUDIT_REJECT(1,"拒绝"),

    Apply_STATUS_AUDIT_PASS(2,"审核通过"),

    CIRCLE_AUTH_OPEN(1,"内部公开"),

    CIRCLE_AUTH_CLOSE(0,"不公开"),

    CIRCLE_AUTH_TYPE_AUTH(2,"每个会员类型单独设置隐私"),

    TYPE_AUTH_OPEN(1,"对内部人员公开"),

    TYPE_AUTH_CLOSE(0,"对内部人员不公开"),

    TYPE_AUTH_INNER_OPEN(2,"对本类型成员公开");


    //状态码
    private Integer code;

    //状态值
    private String value;

    CircleCode(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    /**
     * 根据状态码获取状态值
     * @param code 状态码
     * @return 状态值
     */
    public static String getValue(Integer code){
        for (CircleCode actStatus : CircleCode.values()){
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
        for (CircleCode actStatus : CircleCode.values()){
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
