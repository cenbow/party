package com.party.core.dao.read.notify;

import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.notify.Event;
import org.springframework.stereotype.Repository;

/**
 * 消息事件数据写入
 * Created by wei.li
 *
 * @date 2017/4/5 0005
 * @time 11:40
 */
@Repository
public interface EventReadDao extends BaseReadDao<Event> {

    Event findByCode(String code);
}
