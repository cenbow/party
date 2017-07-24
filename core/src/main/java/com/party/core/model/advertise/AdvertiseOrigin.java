package com.party.core.model.advertise;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * 广告来源
 *
 * @author Wesley
 * @data 16/11/22 9:22 .
 */
public enum AdvertiseOrigin {
    ADVERTISE_ORIGIN_OUTER("0","外部广告"),

    ADVERTISE_ORIGIN_INDOOR("1","内部广告");

    //类型
    private String code;

    //类型名
    private String value;

    AdvertiseOrigin(String code, String value) {
        this.code = code;
        this.value = value;
    }

    /**
     * 根据状态码获取状态值
     * @param code 状态码
     * @return 状态值
     */
    public static String getValue(String code){
        for (AdvertiseOrigin articleType : AdvertiseOrigin.values()){
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
        for (AdvertiseOrigin actStatus : AdvertiseOrigin.values()){
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
