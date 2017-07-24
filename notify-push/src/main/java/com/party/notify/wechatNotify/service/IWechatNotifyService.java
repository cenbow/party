package com.party.notify.wechatNotify.service;

import com.party.notify.wechatNotify.model.TemplateMessage;

import java.util.HashMap;

/**
 * Created by wei.li
 *
 * @date 2017/5/17 0017
 * @time 13:52
 */
public interface IWechatNotifyService {

    /**
     * 发送微信模板消息
     * @param url 连接
     * @param templateMessage 模板消息实体
     * @return 是否发送成功(true/false)
     */
    boolean send(String url, TemplateMessage templateMessage);



    /**
     * 微信消息推送接口
     * @param isWrite 是否写入
     * @param content 消息内容
     */
    void push(boolean isWrite, HashMap<String, Object> content);
}
