package com.party.mobile.task;

import com.party.common.paging.PagingInterceptor;
import com.party.core.model.distributor.DistributorCount;
import com.party.core.service.distributor.IDistributorCountService;
import com.party.mobile.biz.distributor.DistributorBizService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 统计分销参数
 * Created by wei.li
 *
 * @date 2017/3/20 0020
 * @time 14:18
 */

@Component(value = "quartzForDistribution")
public class QuartzForDistribution {


    @Autowired
    private IDistributorCountService distributorCountService;

    @Autowired
    private DistributorBizService distributorBizService;

    private static final Logger logger = LoggerFactory.getLogger(QuartzForDistribution.class);

    @Scheduled( fixedRate = 1000*60*90)
    public void refreshCount(){
        List<DistributorCount> list = distributorCountService.list(new DistributorCount());
        for (DistributorCount distributorCount : list){
            try {
                distributorBizService.refreshCount(distributorCount);
            } catch (Exception e) {
                logger.error("更新分销统计异常", e);
                continue;
            }
        }
    }
}
