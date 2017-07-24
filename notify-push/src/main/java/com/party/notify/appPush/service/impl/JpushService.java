package com.party.notify.appPush.service.impl;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.audience.AudienceTarget;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import com.google.common.base.Strings;
import com.party.common.constant.Constant;

import com.party.core.model.notify.Instance;
import com.party.core.service.notify.IInstanceService;
import com.party.notify.appPush.model.AppPushMessage;
import com.party.notify.appPush.service.IPushService;
import com.party.notify.jms.publisher.service.INotifyPublisherService;
import com.party.notify.template.service.ITemplateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * jupsh消息推送服务
 * User: wei.li
 * Date: 2017/4/5
 * Time: 23:19
 */

@Service
public class JpushService implements IPushService {

    @Autowired
    private ITemplateService templateService;

    @Autowired
    private INotifyPublisherService notifyPublisherService;

    @Autowired
    private IInstanceService instanceService;


    @Value("#{jpush['jpush.appKey']}")
    private String appKey;

    @Value("#{jpush['jpush.masterSecret']}")
    private String masterSecret;

    //公众号编号
    @Value("${sms.timeout}")
    private Integer timeout;

    protected static Logger logger = LoggerFactory.getLogger(JpushService.class);

    private static JPushClient jPushClient = null;



    /**
     * 推送jpush
     * @param isWrite 是否写入
     * @param template 消息模板
     * @param content 内容
     * @param authorId 发送者
     * @param type 类型
     */
    public void push(boolean isWrite, String template, String authorId, String type, HashMap<String, Object> content) {
        String message = templateService.replace(template, content);
        AppPushMessage appPushMessage = new AppPushMessage();
        appPushMessage.setAlert(message);
        appPushMessage.setAlias(authorId);
        appPushMessage.setNoticeType(type);
        notifyPublisherService.send(appPushMessage, Constant.MESSAGE_CHANNEL_JPUSH, false);
    }

    /**
     * 信息写入
     * @param template 模板
     * @param content 内容
     */
    public void write(String template, HashMap<String, Object> content) {
        String message = templateService.replace(template, content);
        instanceService.insert(message, Constant.MESSAGE_CHANNEL_JPUSH, null, null);
    }

    /**
     * 发送消息
     * @param alias 别名
     * @param alert 通知信息
     * @param notice_type 通知类型
     */
    public void send(String alias, String alert, String notice_type) {
        sendPushMsg(null, alias, null, alert, null, null, notice_type);
    }


    /**
     * 发送消息
     * @param isWrite 是否写入
     * @param alias 别名
     * @param alert 通知信息
     * @param notice_type 通知类型
     */
    public void send(boolean isWrite, String alias, String alert, String notice_type){
        send(alias, alert, notice_type);
        String result = "success";
        Integer isSueccess = Constant.IS_SUCCESS;
        if (isWrite){
            instanceService.insert(alert, Constant.MESSAGE_CHANNEL_JPUSH, alias, result, isSueccess);
        }
    }

    /**
     * 发送消息
     * @param platform 平台名称:(Platform.android(),Platform.ios(),,Platform.winphone(),Platform.android_ios(),Platform.android_winphone(),Platform.ios_winphone())
     * @param alias 别名
     * @param tag 标签名
     * @param alert 通知信息
     * @param msgContent 消息内容
     * @param extra 额外信息
     * @param notice_type 通知类型
     */
    public void sendPushMsg(Platform platform, String alias, String tag, String alert, String msgContent, String extra, String notice_type) {
        PushPayload payload = buildPushObject_alert(platform, alias, tag, alert, msgContent, extra, notice_type);
        sendPush(payload);
    }

    /**
     * 构建推送对象
     * @param platform 平台名称:(Platform.android(),Platform.ios(),,Platform.winphone(),Platform.android_ios(),Platform.android_winphone(),Platform.ios_winphone())
     * @param alias 别名
     * @param tag 标签名
     * @param alert 通知信息
     * @param msgContent 消息内容
     * @param extra 额外信息
     * @param notice_type 通知类型
     * @return
     */
    public PushPayload buildPushObject_alert(Platform platform, String alias, String tag, String alert, String msgContent, String extra, String notice_type) {
        PushPayload.Builder builder = PushPayload.newBuilder();
        //目标平台
        if(platform != null){
            builder.setPlatform(platform);
        }else{
            builder.setPlatform(Platform.all());
        }

        //目标别名
        if(!Strings.isNullOrEmpty(alias)){
            builder.setAudience(Audience.newBuilder().addAudienceTarget(AudienceTarget.alias(alias)).build());
        }

        //目标tag
        if(!Strings.isNullOrEmpty(tag)){
            builder.setAudience(Audience.newBuilder().addAudienceTarget(AudienceTarget.tag(tag)).build());
        }

        //推送内容
        if (!Strings.isNullOrEmpty(msgContent))
        {
            //push
            if (Strings.isNullOrEmpty(extra))
            {
                extra = "no extra";
            }
            builder.setMessage(Message.newBuilder()
                    .setMsgContent(msgContent)
                    .addExtra("extra", extra)
                    .build());
        }

        builder.setNotification(Notification.newBuilder()
                .setAlert(alert)
                .addPlatformNotification(AndroidNotification.newBuilder()
                        .addExtra("notice_type", notice_type)
                        .build())
                .addPlatformNotification(IosNotification.newBuilder()
                        .addExtra("notice_type", notice_type)
                        .build())
                .build());

        builder.setOptions(Options.newBuilder()
                .setApnsProduction(true)
                .build());

        return builder.build();
    }

    /**
     * 发送消息
     * @param payload 平台名称
     */
    public void sendPush(PushPayload payload) {
        JPushClient jpushClient = getJpushClient();
        try {
            PushResult result = jpushClient.sendPush(payload);
            logger.info("Got result - " + result);

        } catch (APIConnectionException e) {
            logger.error("Connection error. Should retry later. ", e);

        } catch (APIRequestException e) {
            logger.error("Error response from JPush server. Should review and fix it. ", e);
            logger.info("HTTP Status: " + e.getStatus());
            logger.info("Error Code: " + e.getErrorCode());
            logger.info("Error Message: " + e.getErrorMessage());
            logger.info("Msg ID: " + e.getMsgId());
        }
    }


    /**
     * 获取联系客户端
     * @return 客户端
     */
    public  JPushClient getJpushClient()
    {
        if (null == jPushClient) {//非多线程使用
            jPushClient = new JPushClient(masterSecret, appKey, 3);
        }
        return jPushClient;
    }



    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getMasterSecret() {
        return masterSecret;
    }

    public void setMasterSecret(String masterSecret) {
        this.masterSecret = masterSecret;
    }
}
