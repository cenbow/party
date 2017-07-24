package com.party.mobile.web.dto.login.input;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Created by Wesley on 16/10/31.
 *
 * @Author: Wesley
 * @Description:
 * @Date: Created in 14:38 16/10/31
 * @Modified by:
 */
public enum OpenIdType {
    OPEN_ID_TYPE_WX(0,"app微信授权"),

    OPEN_ID_TYPE_QQ(1,"qq授权"),

    OPEN_ID_TYPE_WB(2,"微博授权"),

    OPEN_ID_TYPE_WXGZH(3,"微信公众号授权");

    //状态码
    private Integer code;

    //状态值
    private String value;

    OpenIdType(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    /**
     * 根据状态码获取状态值
     * @param code 状态码
     * @return 状态值
     */
    public static String getValue(Integer code){
        for (OpenIdType openIdType : OpenIdType.values()){
            if (openIdType.getCode().equals(code)){
                return openIdType.getValue();
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
        for (OpenIdType openIdType : OpenIdType.values()){
            map.put(openIdType.getCode(), openIdType.getValue());
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
