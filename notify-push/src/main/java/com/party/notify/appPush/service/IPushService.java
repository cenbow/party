package com.party.notify.appPush.service;

import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import com.party.notify.appPush.model.AppPushMessage;

import java.util.HashMap;

/**
 * 手机消息推送接口
 * User: wei.li
 * Date: 2017/4/6
 * Time: 22:48
 */
public interface IPushService {

    /**
     * 消息推送
     * @param isWrite 是否写入
     * @param template 模板
     * @param content 内容
     * @param authorId 发送者
     * @param type 类型
     */
    void push(boolean isWrite, String template, String authorId, String type, HashMap<String, Object> content);

    /**
     * 根据别名向所有平台推送通知信息
     * @param alias 别名
     * @param isWrite 是否写入
     * @param alert 通知信息
     * @param notice_type 通知类型
     */
    void send(boolean isWrite, String alias, String alert, String notice_type);

    /**
     * 消息写入
     * @param template 模板
     * @param content 内容
     */
    void write(String template, HashMap<String, Object> content);

    /**
     * 向平台plaform，别名alias，标签tag，推送alert通知信息，消息内容msgContent,额外信息extra，类型为notice_type
     * @param platform 平台名称:(Platform.android(),Platform.ios(),,Platform.winphone(),Platform.android_ios(),Platform.android_winphone(),Platform.ios_winphone())
     * @param alias 别名
     * @param tag 标签名
     * @param alert 通知信息
     * @param msgContent 消息内容
     * @param extra 额外信息
     * @param notice_type 通知类型
     */
    void sendPushMsg(Platform platform, String alias, String tag, String alert, String msgContent, String extra, String notice_type);


    /**
     * 构建推送对象：平台为platform，目标为注册推送id列表registrationIds，别名为alias，内容为 messageContent ,类型为notice_type的通知
     * @param platform 平台名称:(Platform.android(),Platform.ios(),,Platform.winphone(),Platform.android_ios(),Platform.android_winphone(),Platform.ios_winphone())
     * @param alias 别名
     * @param tag 标签名
     * @param alert 通知信息
     * @param msgContent 消息内容
     * @param extra 额外信息
     * @param notice_type 通知类型
     * @return
     */
    PushPayload buildPushObject_alert(Platform platform, String alias, String tag, String alert, String msgContent, String extra, String notice_type);

    /**
     * 发送消息
     * @param payload 平台名称
     */
    void sendPush(PushPayload payload);
}
