package com.party.core.model.order;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * OrderType
 * 活动订单状态
 * @author Wesley
 * @data 16/9/18 9:22 .
 */
public enum OrderStatus {
    ORDER_STATUS_IN_REVIEW(0,"审核中"),

    ORDER_STATUS_TO_BE_PAID(1,"待支付"),

    ORDER_STATUS_HAVE_PAID(2,"已支付"),
    
    ORDER_STATUS_REFUND(4,"已退款"),

    ORDER_STATUS_OTHER(3,"其它");

    //状态码
    private Integer code;

    //状态值
    private String value;

    OrderStatus(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    /**
     * 根据状态码获取状态值
     * @param code 状态码
     * @return 状态值
     */
    public static String getValue(Integer code){
        for (OrderStatus orderType : OrderStatus.values()){
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
        for (OrderStatus orderType : OrderStatus.values()){
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
