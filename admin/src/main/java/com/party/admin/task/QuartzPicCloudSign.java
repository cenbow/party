package com.party.admin.task;

import com.party.core.service.picCloud.PicCloudBizService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 万象优图定时任务
 * Created by wei.li
 *
 * @date 2017/3/30 0030
 * @time 15:25
 */

@Component(value = "quartzPicCloudSign")
public class QuartzPicCloudSign {

    @Autowired
    private PicCloudBizService picCloudBizService;

    protected static Logger logger = LoggerFactory.getLogger(QuartzPicCloudSign.class);

    /**
     * 刷新签名
     */
    public void refresh(){
        logger.info("万象优图定时任务启动");
        try {
            picCloudBizService.refreshPicCloudSign();
        } catch (Exception e) {
            logger.debug("万象优图定时任务启动异常", e);
        }
    }
}
