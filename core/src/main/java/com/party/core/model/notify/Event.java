package com.party.core.model.notify;

import com.party.core.model.BaseModel;

import java.io.Serializable;

/**
 * 消息事件
 * Created by wei.li
 *
 * @date 2017/4/5 0005
 * @time 11:35
 */
public class Event extends BaseModel implements Serializable {


    private static final long serialVersionUID = -3263423177094832908L;
    //名称
    private String name;

    //类型
    private String type;

    //代码
    private String code;

    //开关
    private Integer msgSwitch;

    //方式（auto,hand）
    private String way;

    //任务运行时间表达式
    private String cronExpression;

    //状态(0:停止 1：启动)
    private Integer status;

    //是否异步（0：否， 1：是）
    private Integer isSync;

    //方法名
    private String className;

    //任务执行方法
    private String method;

    public Event() {
    }

    public Event(Integer msgSwitch, String way, Integer isSync) {
        this.msgSwitch = msgSwitch;
        this.way = way;
        this.isSync = isSync;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsSync() {
        return isSync;
    }

    public void setIsSync(Integer isSync) {
        this.isSync = isSync;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Event event = (Event) o;

        if (name != null ? !name.equals(event.name) : event.name != null) return false;
        if (type != null ? !type.equals(event.type) : event.type != null) return false;
        if (code != null ? !code.equals(event.code) : event.code != null) return false;
        if (msgSwitch != null ? !msgSwitch.equals(event.msgSwitch) : event.msgSwitch != null) return false;
        if (way != null ? !way.equals(event.way) : event.way != null) return false;
        if (cronExpression != null ? !cronExpression.equals(event.cronExpression) : event.cronExpression != null)
            return false;
        if (status != null ? !status.equals(event.status) : event.status != null) return false;
        if (isSync != null ? !isSync.equals(event.isSync) : event.isSync != null) return false;
        if (className != null ? !className.equals(event.className) : event.className != null) return false;
        return method != null ? method.equals(event.method) : event.method == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (msgSwitch != null ? msgSwitch.hashCode() : 0);
        result = 31 * result + (way != null ? way.hashCode() : 0);
        result = 31 * result + (cronExpression != null ? cronExpression.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (isSync != null ? isSync.hashCode() : 0);
        result = 31 * result + (className != null ? className.hashCode() : 0);
        result = 31 * result + (method != null ? method.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Event{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", code='" + code + '\'' +
                ", msgSwitch=" + msgSwitch +
                ", way='" + way + '\'' +
                ", cronExpression='" + cronExpression + '\'' +
                ", status=" + status +
                ", isSync=" + isSync +
                ", className='" + className + '\'' +
                ", method='" + method + '\'' +
                '}';
    }
}
