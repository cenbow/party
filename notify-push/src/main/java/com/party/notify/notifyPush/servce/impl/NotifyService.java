package com.party.notify.notifyPush.servce.impl;

import com.party.common.constant.Constant;
import com.party.core.exception.BusinessException;
import com.party.core.model.YesNoStatus;
import com.party.core.model.notify.Event;
import com.party.core.model.notify.EventChannel;
import com.party.core.service.notify.IEventChannelService;
import com.party.core.service.notify.IEventService;
import com.party.notify.appNotify.model.AppMessage;
import com.party.notify.appNotify.service.IAppNotifyService;
import com.party.notify.appPush.service.IPushService;
import com.party.notify.email.service.IEmailService;
import com.party.notify.notifyPush.servce.INotifyService;
import com.party.notify.sms.service.ISmsService;
import com.party.notify.wechatNotify.service.IWechatNotifyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * 系统消息服务接口
 * User: wei.li
 * Date: 2017/4/5
 * Time: 22:41
 */

@Service
public class NotifyService implements INotifyService {

    @Autowired
    private IEventService eventService;


    @Autowired
    private IEventChannelService eventChannelService;

    @Autowired
    private ISmsService smsService;

    @Autowired
    private IAppNotifyService appNotifyService;

    @Autowired
    private IPushService pushService;

    @Autowired
    private IEmailService emailService;

    @Autowired
    private IWechatNotifyService wechatNotifyService;

    protected static Logger logger = LoggerFactory.getLogger(NotifyService.class);

    /**
     * 消息推送
     * @param eventCode 时间编码
     * @param telephone 电话号
     * @param authorId 作者编号
     * @param content 内容
     * @param template 模板编号
     */
    public void push(String eventCode, String telephone, String authorId, HashMap<String, Object> content, String template) {
        Event event = eventService.findByCode(eventCode);
        if (null == event){
            throw new BusinessException("消息发送异常，消息事件不存在");
        }

        //事件开关
        if (YesNoStatus.NO.getCode().equals(event.getMsgSwitch())){
            return;
        }

        // 短信发送
        EventChannel sEventChannel = eventChannelService.findByChannelCodeAndEventId(Constant.MESSAGE_CHANNEL_SMS,event.getId());
        if (null != sEventChannel){
            if (YesNoStatus.YES.getCode().equals(sEventChannel.getChannelSwitch())){
                if (null != template){
                    smsService.push(true, authorId, template, telephone, content);
                }
                else {
                    boolean isWrite = sEventChannel.getWriteSwitch().equals(1);
                    smsService.push(isWrite, authorId, sEventChannel.getTemplate(), telephone, content);
                }
            }
        }


        // jpush发送
        EventChannel jEventChannel = eventChannelService.findByChannelCodeAndEventId(Constant.MESSAGE_CHANNEL_JPUSH, event.getId());
        if (null != jEventChannel){
            if (YesNoStatus.YES.getCode().equals(jEventChannel.getChannelSwitch())){
                boolean isWrite = jEventChannel.getWriteSwitch().equals(1);
                pushService.push(isWrite, jEventChannel.getTemplate(), authorId, event.getType(), content);
            }
        }

        //微信消息
        EventChannel wEventChannel = eventChannelService.findByChannelCodeAndEventId(Constant.MESSAGE_CHANNEL_WECHAT, event.getId());
        if (null != wEventChannel){
            if (YesNoStatus.YES.getCode().equals(wEventChannel.getChannelSwitch())){
                boolean isWrite = wEventChannel.getWriteSwitch().equals(1);
                try {
                    wechatNotifyService.push(isWrite, content);
                } catch (Exception e) {
                    logger.debug("微信消息推送异常", e);
                }
            }
        }

        // 系统消息
        EventChannel aEventChannel = eventChannelService.findByChannelCodeAndEventId(Constant.MESSAGE_CHANNEL_APP, event.getId());
        if (null != aEventChannel){
            appNotifyService.push(aEventChannel.getTemplate(), authorId, event.getType(), content);
        }



        // 邮件发送
        EventChannel eEventChannel = eventChannelService.findByChannelCodeAndEventId(Constant.MESSAGE_CHANNEL_EMAIL, event.getId());
        if (null != eEventChannel){
            if (YesNoStatus.YES.getCode().equals(eEventChannel.getChannelSwitch())){
                emailService.push();
            }
        }
    }

    /**
     * 消息推送
     * @param eventCode 事件编号
     * @param telephone 电话号
     * @param authorId 作者编号
     * @param content 内容
     */
    public void push(String eventCode, String telephone, String authorId, HashMap<String, Object> content) {
        this.push(eventCode, telephone, authorId, content, null);
    }

    /**
     * 消息推送
     * @param eventCode 时间编号
     * @param content 内容
     */
    public void push(String eventCode, String authorId, HashMap<String, Object> content) {
        this.push(eventCode, null, authorId, content);
    }
}
