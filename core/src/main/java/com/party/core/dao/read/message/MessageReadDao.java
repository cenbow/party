package com.party.core.dao.read.message;

import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.message.Message;

import org.springframework.stereotype.Repository;

/**
 * 会员消息数据读取
 * party
 * Created by wei.li
 * on 2016/9/18 0018.
 */
@Repository
public interface MessageReadDao extends BaseReadDao<Message> {

    /**
     * 根据条件查询消息
     * @param message 会员消息
     * @return 会员消息
     */
    Message find(Message message);
    
    /**
     * 根据条件查询消息数
     * @param message
     * @return
     */
	Integer getCount(Message message);
}
