package com.party.notify.appNotify.service;

import com.party.notify.appNotify.model.AppMessage;

import java.util.HashMap;

/**
 * Created by wei.li
 *
 * @date 2017/4/7 0007
 * @time 10:12
 */
public interface IAppNotifyService {

    /**
     * 发送系统消息
     * @param template 模板
     * @param authorId 作者
     * @param type 类型
     * @param content 内容
     */
    void push(String template, String authorId, String type, HashMap<String, Object> content);
}
