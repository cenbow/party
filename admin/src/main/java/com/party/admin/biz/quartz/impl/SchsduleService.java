package com.party.admin.biz.quartz.impl;

import com.party.common.constant.Constant;
import com.party.core.exception.ScheduleException;
import com.party.core.model.notify.Event;
import com.party.admin.biz.quartz.ISchsduleService;
import com.party.admin.biz.quartz.factory.AsyncJobFactory;
import com.party.admin.biz.quartz.factory.SyncJobFactory;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 定时任务实现
 * Created by wei.li
 *
 * @date 2017/4/12 0012
 * @time 10:57
 */
@Service
public class SchsduleService implements ISchsduleService {

    private static String JOB_GROUP = "jobGroup";

    private static final Logger logger = LoggerFactory.getLogger(SchsduleService.class);


    /**
     * 获取触发器key
     * @param jobName
     * @return
     */
    @Override
    public TriggerKey getTriggerKey(String jobName) {
        return TriggerKey.triggerKey(jobName, JOB_GROUP);
    }

    /**
     * 获取表达式触发器
     * @param scheduler
     * @param jobName
     * @return
     */
    @Override
    public CronTrigger getCronTrigger(Scheduler scheduler, String jobName) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(jobName, JOB_GROUP);
            return (CronTrigger) scheduler.getTrigger(triggerKey);
        } catch (SchedulerException e) {
            logger.error("获取定时任务CronTrigger出现异常", e);
            throw new ScheduleException("获取定时任务CronTrigger出现异常");
        }
    }

    /**
     * 创建任务
     * @param scheduler
     * @param event
     */
    @Override
    public void createScheduleJob(Scheduler scheduler, Event event) {
        boolean isSync = event.getIsSync().equals(0);
        createScheduleJob(scheduler, event.getName(), event.getCronExpression(), isSync, event);
    }

    /**
     * 创建任务
     * @param scheduler
     * @param jobName
     * @param cronExpression
     * @param isSync
     * @param param
     */
    @Override
    public void createScheduleJob(Scheduler scheduler, String jobName, String cronExpression, boolean isSync, Object param) {

        //同步或异步
        Class<? extends Job> jobClass = isSync ? AsyncJobFactory.class : SyncJobFactory.class;

        //构建job信息
        JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, JOB_GROUP).build();

        //表达式调度构建器
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);

        //按新的cronExpression表达式构建一个新的trigger
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(jobName, JOB_GROUP).withSchedule(scheduleBuilder).build();
        Event event = (Event) param;

        //放入参数，运行时的方法可以获取
        jobDetail.getJobDataMap().put(Constant.JOB_PARAM_KEY, event);
        try {
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            logger.error("创建定时任务失败", e);
            throw new ScheduleException("创建定时任务失败");
        }
    }

    /**
     * 运行一次任务
     * @param scheduler
     * @param jobName
     */
    @Override
    public void runOnce(Scheduler scheduler, String jobName) {
        JobKey jobKey = JobKey.jobKey(jobName, JOB_GROUP);
        try {
            scheduler.triggerJob(jobKey);
        } catch (SchedulerException e) {
            logger.error("运行一次定时任务失败", e);
            throw new ScheduleException("运行一次定时任务失败");
        }
    }

    /**
     * 暂停任务
     * @param scheduler
     * @param jobName
     */
    @Override
    public void pauseJob(Scheduler scheduler, String jobName) {
        JobKey jobKey = JobKey.jobKey(jobName, JOB_GROUP);
        try {
            scheduler.pauseJob(jobKey);
        } catch (SchedulerException e) {
            logger.error("暂停定时任务失败", e);
            throw new ScheduleException("暂停定时任务失败");
        }
    }

    /**
     * 恢复任务
     * @param scheduler
     * @param jobName
     */
    @Override
    public void resumeJob(Scheduler scheduler, String jobName) {
        JobKey jobKey = JobKey.jobKey(jobName, JOB_GROUP);
        try {
            scheduler.resumeJob(jobKey);
        } catch (SchedulerException e) {
            logger.error("暂停定时任务失败", e);
            throw new ScheduleException("暂停定时任务失败");
        }
    }

    /**
     * 获取jobKey
     * @param jobName
     * @return
     */
    @Override
    public JobKey getJobKey(String jobName) {
        return JobKey.jobKey(jobName, JOB_GROUP);
    }

    /**
     * 更新定时任务
     * @param scheduler
     * @param event
     */
    @Override
    public void updateScheduleJob(Scheduler scheduler, Event event) {
        updateScheduleJob(scheduler, event.getName(), event.getCronExpression());
    }

    /**
     * 更新定时任务
     * @param scheduler
     * @param jobName
     * @param cronExpression
     */
    @Override
    public void updateScheduleJob(Scheduler scheduler, String jobName, String cronExpression) {
        try {



            TriggerKey triggerKey = this.getTriggerKey(jobName);

            //表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

            //按新的cronExpression表达式重新构建trigger
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
            Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
            // 忽略状态为PAUSED的任务，解决集群环境中在其他机器设置定时任务为PAUSED状态后，集群环境启动另一台主机时定时任务全被唤醒的bug
            if(!triggerState.name().equalsIgnoreCase("PAUSED")){
                //按新的trigger重新设置job执行
                scheduler.rescheduleJob(triggerKey, trigger);
            }
        } catch (SchedulerException e) {
            logger.error("更新定时任务失败", e);
            throw new ScheduleException("更新定时任务失败");
        }
    }

    /**
     * 删除定时任务
     * @param scheduler
     * @param jobName
     */
    @Override
    public void deleteScheduleJob(Scheduler scheduler, String jobName) {
        try {
            scheduler.deleteJob(getJobKey(jobName));
        } catch (SchedulerException e) {
            logger.error("删除定时任务失败", e);
            throw new ScheduleException("删除定时任务失败");
        }
    }
}
