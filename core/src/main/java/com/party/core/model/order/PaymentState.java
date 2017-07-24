package com.party.core.model.order;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * 订单状态枚举
 * party
 * Created by wei.li
 * on 2016/9/18 0018.
 */
public enum PaymentState {

    NO_PAY("0", "未付款"),

    IS_PAY("1", "已付款");

    //状态码
    private String code;

    //状态值
    private String value;

    PaymentState(String code, String value) {
        this.code = code;
        this.value = value;
    }


    /**
     * 根据状态码获取状态值
     * @param code 状态码
     * @return 状态值
     */
    public static String getValue(String code){
        for (PaymentState paymentState : PaymentState.values()){
            if (paymentState.getCode().equals(code)){
                return paymentState.getValue();
            }
        }
        return null;
    }

    /**
     * 枚举类型转换map
     * @return map形式枚举
     */
    public static Map<String, String> convertMap(){
        Map<String, String> map = Maps.newHashMap();
        for (PaymentState paymentState : PaymentState.values()){
            map.put(paymentState.getCode(), paymentState.getValue());
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
