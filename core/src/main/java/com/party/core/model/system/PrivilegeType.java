package com.party.core.model.system;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * 权限类型枚举
 * Created by wei.li
 *
 * @date 2017/6/26 0026
 * @time 16:29
 */
public enum  PrivilegeType {

    MENU_PRIVILEGE(0, "菜单权限"),

    OTHER_PRIVILEGE(1, "其他权限");

    //状态码
    private Integer code;

    //状态值
    private String value;

    PrivilegeType(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    public static String getValue(Integer code){
        for (PrivilegeType privilegeType : PrivilegeType.values()){
            if (privilegeType.getCode().equals(code)){
                return privilegeType.getValue();
            }
        }
        return null;
    }

    public static Map<Integer, String> convertMap(){
        Map<Integer, String> map = Maps.newHashMap();
        for (PrivilegeType privilegeType : PrivilegeType.values()){
            map.put(privilegeType.getCode(), privilegeType.getValue());
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
