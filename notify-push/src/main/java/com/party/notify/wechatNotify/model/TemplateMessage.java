package com.party.notify.wechatNotify.model;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 微信模板消息实体
 * Created by wei.li
 *
 * @date 2017/5/17 0017
 * @time 11:49
 */
public class TemplateMessage {

    //接收者
    private String touser;

    //模板ID
    @JSONField(name = "template_id")
    private String templateId;

    //模板跳转链接
    private String url;

    //模板数据
    private Object data;

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
