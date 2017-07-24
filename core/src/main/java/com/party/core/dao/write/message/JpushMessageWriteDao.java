package com.party.core.dao.write.message;

import com.party.core.dao.write.BaseWriteDao;
import com.party.core.model.message.JpushMessage;
import org.springframework.stereotype.Repository;

/**
 * jpush 消息推送数据读取
 * party
 * Created by wei.li
 * on 2016/9/19 0019.
 */
@Repository
public interface JpushMessageWriteDao extends BaseWriteDao<JpushMessage> {
}
