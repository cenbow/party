package com.party.admin.biz.quartz;

import com.party.core.model.notify.Event;
import org.quartz.CronTrigger;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.TriggerKey;

/**
 *
 * 定时任务接口
 * Created by wei.li
 *
 * @date 2017/4/12 0012
 * @time 10:24
 */
public interface ISchsduleService {

    /**
     * 获取触发器key
     *
     * @param jobName
     * @return
     */
    TriggerKey getTriggerKey(String jobName);

    /**
     * 获取表达式触发器
     *
     * @param scheduler
     * @param jobName
     * @return
     */
    CronTrigger getCronTrigger(Scheduler scheduler, String jobName);

    /**
     * 创建任务
     *
     * @param scheduler
     * @param event
     */
    void createScheduleJob(Scheduler scheduler, Event event);

    /**
     * 创建定时任务
     * @param scheduler
     * @param jobName
     * @param cronExpression
     * @param isSync
     * @param param
     */
    void createScheduleJob(Scheduler scheduler, String jobName,String cronExpression, boolean isSync, Object param);


    /**
     * 运行一次任务
     * @param scheduler
     * @param jobName
     */
    void runOnce(Scheduler scheduler, String jobName);


    /**
     * 暂停任务
     * @param scheduler
     * @param jobName
     */
    void pauseJob(Scheduler scheduler, String jobName);


    /**
     * 恢复任务
     * @param scheduler
     * @param jobName
     */
    void resumeJob(Scheduler scheduler, String jobName);

    /**
     * 获取jobKey
     * @param jobName
     * @return
     */
    JobKey getJobKey(String jobName);

    /**
     * 更新定时任务
     * @param scheduler
     * @param event
     */
    void updateScheduleJob(Scheduler scheduler, Event event);

    /**
     * 更新定时任务
     * @param scheduler
     * @param jobName
     * @param cronExpression
     */
    void updateScheduleJob(Scheduler scheduler, String jobName,String cronExpression);

    /**
     * 删除定时任务
     * @param scheduler
     * @param jobName
     */
    void deleteScheduleJob(Scheduler scheduler, String jobName);
}
