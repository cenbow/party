package com.party.core.dao.read.wechat;

import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.wechat.WechatMessage;
import org.springframework.stereotype.Repository;

/**
 * 微信消息数据读取
 * party
 * Created by wei.li
 * on 2016/9/12 0012.
 */
@Repository
public interface WechatMessageReadDao extends BaseReadDao<WechatMessage>{
}
