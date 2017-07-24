package com.party.notify.sms.service.impl;

import com.google.common.collect.Maps;
import com.party.common.constant.Constant;
import com.party.common.redis.RedisLock;
import com.party.common.utils.HttpUtils;

import com.party.core.model.notify.Instance;
import com.party.core.service.notify.IInstanceService;
import com.party.notify.jms.publisher.service.INotifyPublisherService;
import com.party.notify.sms.model.SmsMessage;
import com.party.notify.sms.service.ISmsService;
import com.party.notify.template.service.ITemplateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 短信发送接口
 * User: wei.li
 * Date: 2017/4/5
 * Time: 22:52
 */

@Service
public class SmsService implements ISmsService {

    @Autowired
    private ITemplateService templateService;

    @Autowired
    private INotifyPublisherService notifyPublisherService;

    @Autowired
    private IInstanceService instanceService;


    // 短信发送网关地址
    @Value("#{sms['sms.url']}")
    private String url;

    // 短信发送账户
    @Value("#{sms['sms.uid']}")
    private String uid;

    // 短信发送密码
    @Value("#{sms['sms.pwd']}")
    private String pwd;

    //公众号编号
    @Value("${sms.timeout}")
    private Integer timeout;

    protected static Logger logger = LoggerFactory.getLogger(SmsService.class);


    /**
     * 短信推送
     * @param template 模板
     * @param isWrite 是否写入
     * @param authorId 发送者
     * @param telephone 手机号
     * @param content 内容
     */
    public void push(boolean isWrite,String authorId, String template, String telephone,  HashMap<String, Object> content) {
        // 根据事项找到模板
        String massage = templateService.replace(template, content);
        SmsMessage smsMessage = new SmsMessage();
        smsMessage.setContent(massage);
        smsMessage.setTelephone(telephone);
        smsMessage.setMemberId((String) content.get("senderId"));
        smsMessage.setTargetId((String) content.get("targetId"));
        notifyPublisherService.send(smsMessage, Constant.MESSAGE_CHANNEL_SMS, isWrite);
    }

    /**
     * 短信推送
     * @param telephone 手机号
     * @param content 内容
     */
    public void push(String telephone, String content){
        SmsMessage smsMessage = new SmsMessage();
        smsMessage.setContent(content);
        smsMessage.setTelephone(telephone);
        notifyPublisherService.send(smsMessage, Constant.MESSAGE_CHANNEL_SMS, true);
    }

    /**
     * 写入消息
     * @param template 模板
     * @param content 内容
     */
    public void write(String template,String targetId, String memberId, HashMap<String, Object> content) {
        // 根据事项找到模板
        String massage = templateService.replace(template, content);
        instanceService.insert(massage, Constant.MESSAGE_CHANNEL_SMS, targetId, memberId);
    }

    /**
     * 发送短信
     * @param isWrite 是否写入
     * @param smsMessage 内容
     * @return 发送结果
     */
    @Transactional
    public boolean send(boolean isWrite, SmsMessage smsMessage) {
        String result = send(smsMessage.getTelephone(), smsMessage.getContent());
        Integer isSuccess =  Constant.IS_FAIL;
        if (result.startsWith("0")){
            isSuccess = Constant.IS_SUCCESS;
            result = "0";
        }
        if (isWrite){
            Instance instance = new Instance();
            instance.setTitle(smsMessage.getContent());
            instance.setChannelType(Constant.MESSAGE_CHANNEL_SMS);
            instance.setReceiver(smsMessage.getTelephone());
            instance.setResult(result);
            instance.setIsSuccess(isSuccess);
            instance.setMemberId(smsMessage.getMemberId());
            instance.setTargetId(smsMessage.getTargetId());
            instance.setTime(1);
            instanceService.insert(instance);
        }
        return true;
    }


    /**
     * 短信发送
     * @param tel 手机号
     * @param content 内容
     */
    public String send(String tel, String content){
        return send(tel, content, uid, pwd, url);
    }

    /**
     * 短信发送
     * @param tel 手机号
     * @param content 内容
     * @param uid 账号
     * @param pwd 密码
     * @param url 连接
     * @return
     */
    public String send(String tel, String content, String uid, String pwd, String url) {
        try {
            Map<String, String> parm = Maps.newHashMap();
            parm.put("account", uid);
            parm.put("pswd", pwd);
            parm.put("mobile", tel);
            parm.put("msg",URLEncoder.encode(content, "utf-8"));
            parm.put("needstatus", "true");
            String msg = HttpUtils.httpPost(url, parm);

            String str=msg.split(",")[1];
            logger.info("短信发送返回报文{}", str);

            return str;
        } catch (IOException e) {
            logger.error("短信发送异常",e);
        }
        return "";
    }
}
