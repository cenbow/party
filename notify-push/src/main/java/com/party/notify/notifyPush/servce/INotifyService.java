package com.party.notify.notifyPush.servce;

import java.util.HashMap;

/**
 * User: wei.li
 * Date: 2017/4/6
 * Time: 23:19
 */
public interface INotifyService {

    /**
     * 消息推送
     * @param eventCode 事件编号
     * @param telephone 电话号
     * @param authorId 作者编号
     * @param content 内容
     */
    void push(String eventCode, String telephone, String authorId,  HashMap<String, Object> content);

    /**
     * 消息推送
     * @param eventCode 时间编码
     * @param telephone 电话号
     * @param authorId 作者编号
     * @param content 内容
     * @param template 模板编号
     */
    void push(String eventCode, String telephone, String authorId,  HashMap<String, Object> content, String template);
    /**
     * 消息推送
     * @param eventCode 时间编号
     * @param authorId 作者编号
     * @param content 内容
     */
    void push(String eventCode, String authorId,  HashMap<String, Object> content);
}
