package com.party.notify.wechatNotify.model;

/**
 * Created by wei.li
 *
 * @date 2017/5/18 0018
 * @time 10:03
 */
public class TemplateParameter {

    private TemplateMessage templateMessage;

    private String url;


    public TemplateMessage getTemplateMessage() {
        return templateMessage;
    }

    public void setTemplateMessage(TemplateMessage templateMessage) {
        this.templateMessage = templateMessage;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
