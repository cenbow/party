package com.party.core.model.sign;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * 签到成绩状态枚举
 * Created by wei.li
 *
 * @date 2017/6/21 0021
 * @time 15:12
 */
public enum  SignGradeStatus {

    INVALID(0, "无效"),

    EFFECTIVE(1, "有效");


    //状态码
    private Integer code;

    //状态值
    private String value;

    SignGradeStatus(Integer code, String value) {
        this.code = code;
        this.value = value;
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

    public static String getValue(Integer code){
        for (SignGradeStatus signGradeStatus : SignGradeStatus.values()){
            if (signGradeStatus.getCode().equals(code)){
                return signGradeStatus.getValue();
            }
        }
        return null;
    }

    public static Map<Integer,String> convertMap(){
        Map<Integer,String> map = Maps.newHashMap();
        for (SignGradeStatus signGradeStatus : SignGradeStatus.values()){
            map.put(signGradeStatus.getCode(), signGradeStatus.getValue());
        }
        return map;
    }
}
