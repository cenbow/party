package com.party.core.dao.write.wechat;

import com.party.core.dao.write.BaseWriteDao;
import com.party.core.model.wechat.WechatMessage;
import org.springframework.stereotype.Repository;

/**
 * 微信消息数据写入
 * party
 * Created by wei.li
 * on 2016/9/12 0012.
 */

@Repository
public interface WechatMessageWriteDao extends BaseWriteDao<WechatMessage> {
}
