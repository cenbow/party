package com.party.core.model.notify;

/**
 * Created by wei.li
 *
 * @date 2017/4/11 0011
 * @time 11:56
 */
public class RelationWithChannel extends  EventChannel {

    //名称
    private String name;

    //编码
    private String code;

    //事件名称
    private String eventName;

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

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        RelationWithChannel that = (RelationWithChannel) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (code != null ? !code.equals(that.code) : that.code != null) return false;
        return eventName != null ? eventName.equals(that.eventName) : that.eventName == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (eventName != null ? eventName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "RelationWithChannel{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", eventName='" + eventName + '\'' +
                '}';
    }
}
