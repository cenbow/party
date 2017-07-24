package com.party.core.dao.write.message;

import com.party.core.dao.write.BaseWriteDao;
import com.party.core.model.message.Message;
import org.springframework.stereotype.Repository;

/**
 * 会员消息数据写入
 * party
 * Created by wei.li
 * on 2016/9/18 0018.
 */
@Repository
public interface MessageWriteDao extends BaseWriteDao<Message> {
    /**
     * 更新用户消息状态为已经读
     * @param message
     * @return
     */
    boolean updateRead(Message message);
}
