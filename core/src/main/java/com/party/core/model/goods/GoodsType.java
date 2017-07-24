package com.party.core.model.goods;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * GoodsType
 * 商品类型
 * @author Wesley
 * @data 16/10/12 9:22 .
 */
public enum GoodsType {
    GOODS_NOMAL(0,"标准商品"),

    GOODS_CUSTOMIZED(1,"定制商品"),

    GOODS_ACTIVITY(2,"活动"),

    GOODS_ARTICLE(3, "文章"),

    GOODS_CROWD_FUND(4, "众筹");

    //状态码
    private Integer code;

    //状态值
    private String value;

    GoodsType(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    /**
     * 根据状态码获取状态值
     * @param code 状态码
     * @return 状态值
     */
    public static String getValue(String code){
        for (GoodsType orderType : GoodsType.values()){
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
        for (GoodsType orderType : GoodsType.values()){
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
