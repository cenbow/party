package com.party.core.model.advertise;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * 广告位置
 *
 * @author Wesley
 * @data 16/9/18 9:22 .
 */
public enum AdPos {
    AD_POS_ACT(1,"活动广告"),

    AD_POS_STANDARD_GOODS(2,"标准预订广告"),

    AD_POS_COMMUNITY(3,"社区广告"),

    AD_POS_CUSTOMIZE_GOODS(4,"定制活动预定广告");

    //状态码
    private Integer code;

    //状态值
    private String value;

    AdPos(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    /**
     * 根据状态码获取状态值
     * @param code 状态码
     * @return 状态值
     */
    public static String getValue(Integer code){
        for (AdPos pos : AdPos.values()){
            if (pos.getCode()==code){
                return pos.getValue();
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
        for (AdPos pos : AdPos.values()){
            map.put(pos.getCode(),pos.getValue());
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
