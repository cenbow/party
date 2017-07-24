package com.party.notify.sms.service;



import com.party.notify.sms.model.SmsMessage;

import java.util.HashMap;

/**
 * 短信发送接口
 * User: wei.li
 * Date: 2017/4/6
 * Time: 22:36
 */
public interface ISmsService {

    /**
     * 消息推送
     * @param isWrite 是否是需要写入
     * @param authorId 发送者
     * @param template 模板
     * @param content 内容
     */
    void push(boolean isWrite,String authorId, String template, String telephone, HashMap<String, Object> content);

    /**
     * 消息推送
     * @param telephone 手机
     * @param content 内容
     */
    void push(String telephone, String content);


    /**
     * 短信发送
     * @param telephone 手机号
     * @param constant 内容
     * @throws Exception 业务异常
     */
    String send(String telephone, String constant);

    /**
     * 短信发送
     * @param tel 手机号
     * @param content 内容
     * @param uid 账号
     * @param pwd 密码
     * @param url 连接
     * @return
     */
     String send(String tel, String content, String uid, String pwd, String url);

    /**
     * 发送手机短信
     * @param isWrite 是否写入
     * @param smsMessage 内容
     * @return 发送结果
     */
    boolean send(boolean isWrite, SmsMessage smsMessage);

    /**
     * 写入
     * @param template 模板
     * @param targetId 目标编号
     * @param memberId 会员编号
     * @param content 内容
     */
    void write(String template, String targetId, String memberId, HashMap<String, Object> content);
}
