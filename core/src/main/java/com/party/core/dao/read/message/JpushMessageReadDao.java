package com.party.core.dao.read.message;

import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.message.JpushMessage;
import org.springframework.stereotype.Repository;

/**
 * jpush消息推送数据读取
 * party
 * Created by wei.li
 * on 2016/9/19 0019.
 */
@Repository
public interface JpushMessageReadDao extends BaseReadDao<JpushMessage> {
}
