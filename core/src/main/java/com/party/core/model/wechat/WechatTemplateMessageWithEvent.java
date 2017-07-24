package com.party.core.model.wechat;

/**
 * Created by wei.li
 *
 * @date 2017/5/19 0019
 * @time 16:37
 */
public class WechatTemplateMessageWithEvent extends WechatTemplateMassage{

    //消息事件名称
    private String eventName;

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

        WechatTemplateMessageWithEvent that = (WechatTemplateMessageWithEvent) o;

        return eventName != null ? eventName.equals(that.eventName) : that.eventName == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (eventName != null ? eventName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "WechatTemplateMessageWithEvent{" +
                "eventName='" + eventName + '\'' +
                '}';
    }
}
