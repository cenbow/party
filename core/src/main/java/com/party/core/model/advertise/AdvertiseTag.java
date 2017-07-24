package com.party.core.model.advertise;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * 内部广告类型
 *
 * @author Wesley
 * @data 16/11/22 9:22 .
 */
public enum AdvertiseTag {
    ADVERTISE_TAG_ACT("0","活动"),

    ADVERTISE_TAG_STANDARD_GOODS("1","标准商品"),

    ADVERTISE_TAG_CUSTOMIZE_GOODS("2","定制商品"),

    ADVERTISE_TAG_ARTICLE_STRATEGY("3","攻略文章"),

    ADVERTISE_TAG_ARTICLE_EXPERT("4","达人文章"),

    ADVERTISE_TAG_ARTICLE_DYNAMIC("5","动态文章");


    //类型
    private String code;

    //类型名
    private String value;

    AdvertiseTag(String code, String value) {
        this.code = code;
        this.value = value;
    }

    /**
     * 根据状态码获取状态值
     * @param code 状态码
     * @return 状态值
     */
    public static String getValue(String code){
        for (AdvertiseTag articleType : AdvertiseTag.values()){
            if (articleType.getCode().equals(code)){
                return articleType.getValue();
            }
        }
        return null;
    }


    /**
     * 枚举类型转换为map
     * @return 转换后的map
     */
    public static Map<String,String> convertMap(){
        Map<String,String> map = Maps.newHashMap();
        for (AdvertiseTag actStatus : AdvertiseTag.values()){
            map.put(actStatus.getCode(),actStatus.getValue());
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
