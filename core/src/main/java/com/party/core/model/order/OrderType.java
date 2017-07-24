package com.party.core.model.order;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * OrderType
 * 订单类型
 * @author Wesley
 * @data 16/9/18 9:22 .
 */
public enum OrderType {
    ORDER_NOMAL(0,"标准商品订单"),

    ORDER_CUSTOMIZED(1,"定制商品订单"),

    ORDER_ACTIVITY(2,"活动订单"),

    ORDER_CROWD_FUND(3, "众筹订单"),

    ORDER_LEVEL(4, "等级套餐订单");

    //状态码
    private Integer code;

    //状态值
    private String value;

    OrderType(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    /**
     * 根据状态码获取状态值
     * @param code 状态码
     * @return 状态值
     */
    public static String getValue(Integer code){
        for (OrderType orderType : OrderType.values()){
            if (orderType.getCode().equals(code)){
                return orderType.getValue();
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
        for (OrderType orderType : OrderType.values()){
            map.put(orderType.getCode(),orderType.getValue());
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
