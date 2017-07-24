package com.party.core.dao.write.notify;

import com.party.core.dao.write.BaseWriteDao;
import com.party.core.model.notify.EventChannel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 消息事件通道关系写入
 * Created by wei.li
 *
 * @date 2017/4/5 0005
 * @time 11:42
 */
@Repository
public interface EventChannelWriteDao extends BaseWriteDao<EventChannel> {
    /**
     * 根据事件编号删除通道
     * @param eventId 事件编号
     */
    void deleteByEventId(@Param(value = "eventId") String eventId);
}
