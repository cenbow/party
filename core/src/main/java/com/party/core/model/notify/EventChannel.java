package com.party.core.model.notify;

import com.party.core.model.BaseModel;

/**
 * 消息事件通道关联
 * Created by wei.li
 *
 * @date 2017/4/5 0005
 * @time 11:41
 */
public class EventChannel extends BaseModel {

    //消息通道编号
    private String channelId;

    //事件编号
    private String eventId;

    //通道开关
    private Integer channelSwitch;

    //消息模板
    private String template;

    //是否写入开关
    private Integer writeSwitch;

    //微信消息头部
    private String first;

    //微信消息备注
    private String remark;

    //消息连接
    private String url;

    //微信模板编号
    private String templateId;

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public Integer getChannelSwitch() {
        return channelSwitch;
    }

    public void setChannelSwitch(Integer channelSwitch) {
        this.channelSwitch = channelSwitch;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public Integer getWriteSwitch() {
        return writeSwitch;
    }

    public void setWriteSwitch(Integer writeSwitch) {
        this.writeSwitch = writeSwitch;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }
}
