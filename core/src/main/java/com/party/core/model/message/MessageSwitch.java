package com.party.core.model.message;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * 消息类型
 * party
 * Created by Wesley
 * on 2016/11/21
 */
public enum MessageSwitch {

    OPEN(1,"打开"),

    CLOSE(0,"关闭");

    //状态码
    private Integer code;

    //状态值
    private String value;

    MessageSwitch(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    /**
     * 根据代码获取值
     * @param code
     * @return
     */
    public static String getValue(Integer code){
        for (MessageSwitch messageSwitch : MessageSwitch.values()){
            if (messageSwitch.getCode().equals(code)){
                return messageSwitch.getValue();
            }
        }
        return null;
    }


    /**
     * 转换为map
     * @return
     */
    public static Map<Integer, String> convertMap(){
        Map<Integer, String> map = Maps.newHashMap();
        for (MessageSwitch messageSwitch : MessageSwitch.values()){
            map.put(messageSwitch.getCode(), messageSwitch.getValue());
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
