package com.party.core.model.order;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * OrderOrigin
 * 订单来源记录
 * @author Wesley
 * @data 16/9/18 9:22 .
 */
public enum OrderOrigin {
    ORDER_ORIGIN_GOODS(0,"审核中"),

    ORDER_ORIGIN_ACT(1,"待支付"),

    ORDER_ORIGIN_LEVEL(2, "会员等级产品");

    //状态码
    private Integer code;

    //状态值
    private String value;

    OrderOrigin(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    /**
     * 根据状态码获取状态值
     * @param code 状态码
     * @return 状态值
     */
    public static String getValue(String code){
        for (OrderOrigin orderType : OrderOrigin.values()){
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
        for (OrderOrigin orderType : OrderOrigin.values()){
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
