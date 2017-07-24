package com.party.notify.jms.publisher.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.party.common.constant.Constant;
import com.party.notify.jms.publisher.service.INotifyPublisherService;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by wei.li
 *
 * @date 2017/4/7 0007
 * @time 10:38
 */

@Service
public class NotifyPublisherService implements INotifyPublisherService {


    private JmsTemplate jmsTemplate;

    private ExecutorService threadPool = Executors.newFixedThreadPool(50);

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    /**
     * 发送jms消息
     * @param object 发送实体
     * @param type 类型
     */
    public void send(final Object object, final String type, final boolean isWrite){
        threadPool.execute(new Runnable() {
            public void run() {
              sendMessage(object, type, isWrite);
            }
        });
    }


    /**
     * 真实推送消息
     * @param object 发送实体
     * @param type 类型
     */
    private void sendMessage(final Object object, final String type, final boolean isWrite){
        jmsTemplate.send(new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                MapMessage mapMessage = session.createMapMessage();
                mapMessage.setString(Constant.TYPE_KEY, type);
                mapMessage.setString(Constant.OBJECT_KEY, JSONObject.toJSONString(object));
                mapMessage.setBoolean(Constant.IS_WRITE, isWrite);
                return mapMessage;
            }
        });
    }
}
