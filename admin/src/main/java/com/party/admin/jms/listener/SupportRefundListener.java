package com.party.admin.jms.listener;

import com.party.admin.biz.crowdfund.SupportBizService;
import com.party.common.constant.Constant;
import org.apache.activemq.command.ActiveMQMapMessage;
import org.apache.activemq.command.ActiveMQMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;

/**
 * Created by wei.li
 *
 * @date 2017/5/3 0003
 * @time 11:29
 */

@Component(value = "supportRefundListener")
public class SupportRefundListener implements javax.jms.MessageListener{

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SupportBizService supportBizService;

    public void onMessage(Message message) {
        try {
            if (message instanceof ActiveMQMessage){
                ActiveMQMapMessage mqMessage = (ActiveMQMapMessage) message;
                String id = mqMessage.getString(Constant.TYPE_KEY);
                supportBizService.refund(id);
            }
        } catch (JMSException e) {
            logger.error("退款消息队列异常", e);
        }
    }
}
