package com.party.core.model.sign;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * 签到报名状态
 * Created by wei.li
 *
 * @date 2017/6/7 0007
 * @time 16:17
 */
public enum  SignApplyStatus {

    NOT_AUDIT_STATUS(0, "待审核"),

    PASS_STATUS(1, "审核通过"),

    REFUSE_STATUS(2, "审核拒绝");


    //状态码
    private Integer code;

    //状态值
    private String value;

    SignApplyStatus(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    /**
     * 根据状态码获取状态值
     * @param code 状态码
     * @return 状态值
     */
    public static String getValue(Integer code){
        for (SignApplyStatus signApplyStatus : SignApplyStatus.values()){
            if (signApplyStatus.getCode() == code){
                return signApplyStatus.getValue();
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
        for (SignApplyStatus signApplyStatus : SignApplyStatus.values()){
            map.put(signApplyStatus.getCode(),signApplyStatus.getValue());
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
