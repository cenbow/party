package com.party.core.model.system;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * 角色类型枚举
 * Created by wei.li
 *
 * @date 2017/6/26 0026
 * @time 16:23
 */
public enum  RoleType {

    SYSTEM_ROLE(0, "系统角色"),

    PARTNER_ROLE(1, "合作商角色");

    //状态码
    private Integer code;

    //状态值
    private String value;

    RoleType(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    public static String getValue(Integer code){
        for (RoleType roleType : RoleType.values()){
            if (roleType.getCode().equals(code)){
                return roleType.getValue();
            }
        }
        return null;
    }

    public static Map<Integer, String> convertMap(){
        Map<Integer, String> map = Maps.newHashMap();
        for (RoleType roleType : RoleType.values()){
            map.put(roleType.getCode(), roleType.getValue());
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
