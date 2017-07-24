package com.party.notify.appNotify.service.impl;

import com.party.common.constant.Constant;
import com.party.core.service.member.IIndustryService;
import com.party.core.service.notify.IInstanceService;
import com.party.notify.appNotify.model.AppMessage;
import com.party.notify.appNotify.service.IAppNotifyService;
import com.party.notify.jms.publisher.service.INotifyPublisherService;
import com.party.notify.template.service.ITemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 *
 * app消息推送服务接口
 * User: wei.li
 * Date: 2017/4/5
 * Time: 23:21
 */

@Service
public class AppNotifyService implements IAppNotifyService {

    @Autowired
    private ITemplateService templateService;

    @Autowired
    private INotifyPublisherService notifyPublisherService;

    /**
     * 消息发送
     * @param template
     * @param content
     */
    public void push(String template,String authorId, String type, HashMap<String, Object> content) {
        String message = templateService.replace(template, content);
        AppMessage appMessage = new AppMessage();
        appMessage.setMessageType(type);
        appMessage.setMemberId((String) content.get("createBy"));
        appMessage.setTitle(message);
        appMessage.setChannelType(Constant.MESSAGE_CHANNEL_APP);
        appMessage.setLogo((String) content.get("logo"));
        appMessage.setOrderId((String) content.get("orderId"));
        appMessage.setOrderStatus((Integer) content.get("orderStatus"));
        appMessage.setRelId((String) content.get("relId"));
        appMessage.setTag((String) content.get("tag"));
        appMessage.setPicUrl((String) content.get("url"));
        appMessage.setContent((String) content.get("content"));
        appMessage.setCreateBy(authorId);
        appMessage.setUpdateBy(authorId);
        notifyPublisherService.send(appMessage, Constant.MESSAGE_CHANNEL_APP, true);
    }


}
