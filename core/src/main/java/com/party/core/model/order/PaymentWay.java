package com.party.core.model.order;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * 支付方式枚举
 * party
 * Created by wei.li
 * on 2016/9/18 0018.
 */
public enum PaymentWay {

    ALI_PAY(0,"支付宝支付"),

    WECHAT_PAY(1,"微信支付"),

    CROWD_FUND_PAY(2, "众筹支付");

    //状态码
    private Integer code;

    //状态值
    private String value;

    PaymentWay(Integer code, String value) {
        this.code = code;
        this.value = value;
    }


    /**
     * 根据代码获取枚举值
     * @param code 代码
     * @return 状态值
     */
    public static String getValue(Integer code){
        for (PaymentWay paymentWay : PaymentWay.values()){
            if (paymentWay.getCode().equals(code)){
                return paymentWay.getValue();
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
        for (PaymentWay paymentWay : PaymentWay.values()){
            map.put(paymentWay.getCode(),paymentWay.getValue());
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
