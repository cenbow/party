package com.party.admin.web.dto.output.notify;

import com.party.core.model.notify.Event;
import org.springframework.beans.BeanUtils;

/**
 * 事件输入视图
 * Created by wei.li
 *
 * @date 2017/4/7 0007
 * @time 16:42
 */
public class EventOutput {

    //编号
    private String id;

    //名称
    private String name;

    //代码
    private String code;

    //类型
    private String type;

    //开关
    private Integer msgSwitch;

    //方式
    private String way;


    public EventOutput() {
        this.msgSwitch = 0;
        this.way = "hand";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getMsgSwitch() {
        return msgSwitch;
    }

    public void setMsgSwitch(Integer msgSwitch) {
        this.msgSwitch = msgSwitch;
    }

    public String getWay() {
        return way;
    }

    public void setWay(String way) {
        this.way = way;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static EventOutput transform(Event event){
        EventOutput eventOutput = new EventOutput();
        BeanUtils.copyProperties(event, eventOutput);
        return eventOutput;
    }
}
