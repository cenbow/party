package com.party.core.dao.read.message;

import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.message.SysMessage;
import org.springframework.stereotype.Repository;

/**
 * 系统消息数据读取
 * party
 * Created by wei.li
 * on 2016/8/23 0023.
 */
@Repository
public interface SysMessageReadDao extends BaseReadDao<SysMessage> {
}
