package com.party.core.dao.write.notify;

import com.party.core.dao.write.BaseWriteDao;
import com.party.core.model.notify.MsgChannel;
import org.springframework.stereotype.Repository;

/** 消息通道数据写入
 * Created by wei.li
 *
 * @date 2017/4/5 0005
 * @time 11:34
 */

@Repository
public interface MsgChannelWriteDao extends BaseWriteDao<MsgChannel>{
}
