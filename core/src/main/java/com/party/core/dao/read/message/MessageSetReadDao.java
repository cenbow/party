package com.party.core.dao.read.message;

import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.message.MessageSet;
import com.sun.istack.NotNull;
import org.springframework.stereotype.Repository;

/**
 * 消息设置数据读取
 * party
 * Created by wei.li
 * on 2016/8/11 0011.
 */
@Repository
public interface MessageSetReadDao extends BaseReadDao<MessageSet> {
    /**
     * 根据会员id获取消息设置信息
     * @param messageSet
     * @return
     */
    public MessageSet findByMemberId(MessageSet messageSet);
}
