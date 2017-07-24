package com.party.admin.biz.quartz.factory;

import com.party.common.constant.Constant;
import com.party.common.reflect.ReflectUtils;
import com.party.core.model.notify.Event;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * 同步任务工厂类
 * Created by wei.li
 *
 * @date 2017/4/12 0012
 * @time 10:19
 */

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class SyncJobFactory extends QuartzJobBean {

    private static final Logger logger = LoggerFactory.getLogger(SyncJobFactory.class);

    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        logger.info("SyncJobFactory execute");
        Event event = (Event) context.getMergedJobDataMap().get(Constant.JOB_PARAM_KEY);
        logger.info("运行的定时任务是{}, 详情{}", event.getName(), event.toString());

        ReflectUtils.invokMethod(event.getClassName(), event.getMethod());
    }
}
