package com.party.core.dao.write.notify;

import com.party.core.dao.write.BaseWriteDao;
import com.party.core.model.notify.Event;
import org.springframework.stereotype.Repository;

/**
 * 消息事件写入
 * Created by wei.li
 *
 * @date 2017/4/5 0005
 * @time 11:39
 */
@Repository
public interface EventWriteDao extends BaseWriteDao<Event>{
}
