package com.party.notify.jms.listener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.party.common.constant.Constant;
import com.party.core.service.message.IMessageService;
import com.party.core.service.notify.IInstanceService;
import com.party.notify.appNotify.model.AppMessage;
import com.party.notify.appPush.model.AppPushMessage;
import com.party.notify.appPush.service.IPushService;
import com.party.notify.sms.model.SmsMessage;
import com.party.notify.sms.service.ISmsService;
import com.party.notify.wechatNotify.model.TemplateParameter;
import com.party.notify.wechatNotify.service.IWechatNotifyService;
import org.apache.activemq.command.ActiveMQMapMessage;
import org.apache.activemq.command.ActiveMQMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.*;

/**
 * jsm 消息监听器
 * Created by wei.li
 *
 * @date 2017/3/21 0021
 * @time 11:55
 */

@Component(value = "messageJmsListener")
public class MessageListener implements javax.jms.MessageListener {

    @Autowired
    private ISmsService smsService;

    @Autowired
    private IPushService pushService;

    @Autowired
    private IMessageService messageService;

    @Autowired
    private IWechatNotifyService wechatNotifyService;


    protected Logger logger = LoggerFactory.getLogger(getClass());


    public void onMessage(Message message) {
        logger.info("消费线程--{}", Thread.currentThread().getName());
        try {
            if (message instanceof ActiveMQMessage){
                ActiveMQMapMessage mqMessage = (ActiveMQMapMessage) message;
                String value = mqMessage.getString(Constant.OBJECT_KEY);
                String type = mqMessage.getString(Constant.TYPE_KEY);
                boolean isWrite = mqMessage.getBoolean(Constant.IS_WRITE);
                //短信
                if (Constant.MESSAGE_CHANNEL_SMS.equals(type)){
                    SmsMessage smsMessage = JSONObject.toJavaObject(JSON.parseObject(value), SmsMessage.class);
                    logger.info("发送的短信为{}", smsMessage.getContent());
                    smsService.send(isWrite, smsMessage);
                }
                //手机推送
                else if (Constant.MESSAGE_CHANNEL_JPUSH.equals(type)){
                    AppPushMessage appPushMessage = JSONObject.toJavaObject(JSON.parseObject(value), AppPushMessage.class);
                    logger.info("push消息 alias{}, alert{}, type{}", appPushMessage.getAlias(),
                            appPushMessage.getAlert(), appPushMessage.getNoticeType());
                    pushService.send(isWrite, appPushMessage.getAlias(), appPushMessage.getAlert(), appPushMessage.getNoticeType());
                }
                else if (Constant.MESSAGE_CHANNEL_WECHAT.equals(type)){
                    TemplateParameter templateParameter = JSONObject.toJavaObject(JSON.parseObject(value), TemplateParameter.class);
                    logger.info("wechat 模板消息url{}, template{}", templateParameter.getUrl(), templateParameter.getTemplateMessage().toString());
                    wechatNotifyService.send(templateParameter.getUrl(), templateParameter.getTemplateMessage());
                }
                //web
                else if (Constant.MESSAGE_CHANNEL_APP.equals(type)){
                    AppMessage appMessage =  JSONObject.toJavaObject(JSON.parseObject(value), AppMessage.class);
                    messageService.insert(AppMessage.transform(appMessage));
                }
            }
        }catch (JMSException je){
            logger.error("短信队列接受异常", je);
        }
    }
}
