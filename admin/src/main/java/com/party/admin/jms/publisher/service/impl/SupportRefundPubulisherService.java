package com.party.admin.jms.publisher.service.impl;

import com.party.admin.jms.publisher.service.ISupportRefundPubulisherService;
import com.party.common.constant.Constant;
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
 * @date 2017/5/3 0003
 * @time 11:23
 */

@Service
public class SupportRefundPubulisherService implements ISupportRefundPubulisherService {

    private JmsTemplate jmsRefundTemplate;

    private ExecutorService threadPool = Executors.newFixedThreadPool(5);


    public void setJmsRefundTemplate(JmsTemplate jmsRefundTemplate) {
        this.jmsRefundTemplate = jmsRefundTemplate;
    }

    public void send(final String supportId) {
        sendMessage(supportId);
    }


    public void sendMessage(final String supportId){
        jmsRefundTemplate.send(new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                MapMessage mapMessage = session.createMapMessage();
                mapMessage.setString(Constant.TYPE_KEY, supportId);
                return mapMessage;
            }
        });
    }
}
