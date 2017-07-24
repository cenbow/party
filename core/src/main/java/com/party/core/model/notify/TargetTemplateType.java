package com.party.core.model.notify;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * 目标模板类型枚举
 * Created by wei.li
 *
 * @date 2017/7/4 0004
 * @time 11:11
 */
public enum  TargetTemplateType {

    CROWDFUND_SUCCESS(0, "众筹成功"),

    CROWDFUND_UNDERWAY(1, "进行中"),

    CROWDFUNF_ALL(3, "所有众筹");

    //状态码
    private Integer code;

    //状态值
    private String value;

    TargetTemplateType(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    public static String getValue(Integer code){
        for (TargetTemplateType targetTemplateType : TargetTemplateType.values()){
            if (code.equals(targetTemplateType.getCode())){
                return targetTemplateType.getValue();
            }
        }
        return null;
    }

    public static Map<Integer, String> convertMap(){
        Map<Integer, String> map = Maps.newHashMap();
        for (TargetTemplateType targetTemplateType : TargetTemplateType.values()){
            map.put(targetTemplateType.getCode(), targetTemplateType.getValue());
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
