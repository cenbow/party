package com.party.core.service.notify;

import com.party.core.model.notify.Event;
import com.party.core.service.IBaseService;

import java.util.List;

/**
 * 消息事件服务接口
 * Created by wei.li
 *
 * @date 2017/4/5 0005
 * @time 19:10
 */
public interface IEventService extends IBaseService<Event>{

    /**
     * 根据编号查找事件
     * @param code 编号
     * @return 事件
     */
    Event findByCode(String code);

    /**
     * 定时任务列表
     * @return 事件列表
     */
    List<Event> jobList();
}
