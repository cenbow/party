package com.party.admin.biz.notify;

import com.google.common.base.Strings;
import com.party.common.constant.Constant;
import com.party.core.model.YesNoStatus;
import com.party.core.model.notify.Event;
import com.party.core.service.notify.IEventChannelService;
import com.party.core.service.notify.IEventService;
import com.party.admin.biz.quartz.IScheduleJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 消息事件业务接口
 * Created by wei.li
 *
 * @date 2017/4/10 0010
 * @time 9:29
 */

@Service
public class EventBizService {

    @Autowired
    private IEventService eventService;

    @Autowired
    private IEventChannelService eventChannelService;

    @Autowired
    private IScheduleJobService scheduleJobService;

    /**
     * 保存事件
     * @param event
     */
    @Transactional
    public void save(Event event){
        //新增
        if (Strings.isNullOrEmpty(event.getId())){
            if (Constant.EVENT_AUTO.equals(event.getWay())){
                scheduleJobService.insert(event);
            }
            else {
                eventService.insert(event);
            }
        }
        //修改
        else {
            Event dbEvent = eventService.get(event.getId());
            if (Constant.EVENT_AUTO.equals(event.getWay())){
                scheduleJobService.update(event);
            }
            else {
                if (Constant.EVENT_AUTO.equals(dbEvent.getWay())){
                    scheduleJobService.delete(event.getId());
                }
                eventService.update(event);
            }
        }
    }


    /**
     * 开启关闭
     * @param id 时间编号
     * @param msgSwitch 开关
     */
    public void eventSwitch(String id, Integer msgSwitch){
        Event event = eventService.get(id);
        if (Constant.EVENT_AUTO.equals(event.getWay())){
            //开启
            if (YesNoStatus.YES.getCode().equals(msgSwitch)){
                scheduleJobService.resumeJob(id);
            }
            else if (YesNoStatus.NO.getCode().equals(msgSwitch)){
                scheduleJobService.pauseJob(id);
            }
        }
        else if (Constant.EVENT_HAND.equals(event.getWay())){
            event.setMsgSwitch(msgSwitch);
            eventService.update(event);
        }
    }

}
