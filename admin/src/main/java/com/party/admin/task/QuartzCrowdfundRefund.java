package com.party.admin.task;

import java.util.List;

import com.party.core.model.crowdfund.ProjectWithAuthor;
import com.party.core.service.crowdfund.IProjectRefundService;
import com.party.core.service.crowdfund.ISupportRefundService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.party.admin.biz.crowdfund.ProjectBizService;
import com.party.admin.biz.crowdfund.SupportBizService;
import com.party.admin.biz.refund.RefundBizService;
import com.party.common.constant.Constant;
import com.party.core.model.crowdfund.Project;
import com.party.core.model.crowdfund.SupportWithMember;
import com.party.core.service.crowdfund.IProjectService;
import com.party.core.service.crowdfund.ISupportService;
import com.party.pay.model.refund.WechatPayRefundResponse;
import org.springframework.util.CollectionUtils;

/**
 * 众筹到时间退款问题
 * Created by wei.li
 *
 * @date 2017/3/30 0030
 * @time 15:39
 */

@Component(value = "quartzCrowdfundRefund")
public class QuartzCrowdfundRefund {

    @Autowired
    private IProjectService projectService;

    @Autowired
    private ProjectBizService projectBizService;

    protected static Logger logger = LoggerFactory.getLogger(QuartzCrowdfundRefund.class);


    public void doQuertz(){
        List<ProjectWithAuthor> projectList = projectService.endList();
        for (Project project : projectList){
            projectBizService.refund(project);
        }
    }
}
