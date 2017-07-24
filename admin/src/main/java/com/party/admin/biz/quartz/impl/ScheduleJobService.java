package com.party.admin.biz.quartz.impl;

import com.party.core.model.notify.Event;
import com.party.core.service.notify.IEventService;
import com.party.admin.biz.quartz.IScheduleJobService;
import com.party.admin.biz.quartz.ISchsduleService;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * 定时任务服务实现
 * Created by wei.li
 *
 * @date 2017/4/12 0012
 * @time 11:55
 */
@Service
public class ScheduleJobService implements IScheduleJobService {

    @Autowired
    private IEventService eventService;

    @Autowired
    private Scheduler scheduler;

    @Autowired
    private ISchsduleService schsduleService;

    /**
     * 初始化定时任务列表
     */
    public void init(){
        List<Event> eventList = eventService.jobList();
        if (CollectionUtils.isEmpty(eventList)){
            return;
        }

        for (Event event : eventList){
            CronTrigger cronTrigger = schsduleService.getCronTrigger(scheduler, event.getName());
            //不存在，创建一个
            if (cronTrigger == null) {
                schsduleService.createScheduleJob(scheduler, event);
            } else {
                //已存在，那么更新相应的定时设置
                schsduleService.updateScheduleJob(scheduler, event);
            }
        }
    }

    /**
     * 插入定时任务
     * @param event 事件
     * @return 编号
     */
    @Override
    public String insert(Event event) {
        schsduleService.createScheduleJob(scheduler, event);
        event.setStatus(1);
        event.setMsgSwitch(1);
        return eventService.insert(event);
    }

    /**
     * 更新定时任务
     * @param event 事件
     */
    @Override
    public void update(Event event) {
        CronTrigger cronTrigger = schsduleService.getCronTrigger(scheduler, event.getName());
        if (null == cronTrigger){
            schsduleService.createScheduleJob(scheduler, event);
        }
        else {
            schsduleService.updateScheduleJob(scheduler, event);
        }
        eventService.update(event);
    }

    /**
     * 删除并更新
     * @param event
     */
    @Override
    public void delUpdate(Event event) {
        //先删除
        schsduleService.deleteScheduleJob(scheduler, event.getName());
        //再创建
        schsduleService.createScheduleJob(scheduler, event);
        //数据库直接更新即可
        eventService.update(event);
    }

    /**
     * 删除定时任务
     * @param eventId
     */
    @Override
    public void delete(String eventId) {
        //先删除
        Event event = eventService.get(eventId);
        schsduleService.deleteScheduleJob(scheduler, event.getName());
        eventService.delete(eventId);
    }

    /**
     * 运行一次
     * @param eventId 定时任务编号
     */
    @Override
    public void runOnce(String eventId) {
        Event event = eventService.get(eventId);
        schsduleService.runOnce(scheduler, event.getName());
    }

    /**
     * 暂停
     * @param eventId 定时任务编号
     */
    @Override
    public void pauseJob(String eventId) {
        Event event = eventService.get(eventId);
        schsduleService.pauseJob(scheduler, event.getName());
        event.setStatus(0);
        event.setMsgSwitch(0);
        eventService.update(event);
    }

    /**
     * 恢复任务
     * @param eventId 定时任务编号
     */
    @Override
    public void resumeJob(String eventId) {
        Event event = eventService.get(eventId);
        schsduleService.resumeJob(scheduler, event.getName());
        event.setStatus(1);
        event.setMsgSwitch(1);
        eventService.update(event);
    }

    /**
     * 获取任务对象
     * @param eventId 定时任务编号
     * @return
     */
    @Override
    public Event get(String eventId) {
        return eventService.get(eventId);
    }


    /**
     * 定时任务列表
     * @param event 事件
     * @return
     */
    @Override
    public List<Event> queryList(Event event) {
        return eventService.jobList();
    }

    @Override
    public List<Event> queryExecutingJobList() {
        return null;
    }
}
