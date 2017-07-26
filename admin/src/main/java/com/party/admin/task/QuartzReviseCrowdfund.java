package com.party.admin.task;

import com.party.common.constant.Constant;
import com.party.core.model.BaseModel;
import com.party.core.model.activity.Activity;
import com.party.core.model.crowdfund.Project;
import com.party.core.model.crowdfund.ProjectWithAuthor;
import com.party.core.model.crowdfund.Support;
import com.party.core.model.crowdfund.SupportWithMember;
import com.party.core.model.order.OrderForm;
import com.party.core.service.activity.IActivityService;
import com.party.core.service.crowdfund.IProjectService;
import com.party.core.service.crowdfund.ISupportService;
import com.party.core.service.order.IOrderFormService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 校正众筹数据
 * Created by wei.li
 *
 * @date 2017/4/28 0028
 * @time 17:53
 */

@Component(value = "quartzReviseCrowdfund")
public class QuartzReviseCrowdfund {

    @Autowired
    private IProjectService projectService;

    @Autowired
    private ISupportService supportService;

    @Autowired
    private IOrderFormService orderFormService;

    @Autowired
    private IActivityService activityService;

    protected static Logger logger = LoggerFactory.getLogger(QuartzReviseCrowdfund.class);
    public void revise(){
        logger.info("校正众筹数据开始");
        List<ProjectWithAuthor> projectList = projectService.list(new ProjectWithAuthor());
        for (Project project : projectList){
            //过滤掉已经筹满的
            if (project.getIsSuccess().equals(Constant.CROWDFUND_PROJECT_SUCCESS)){
                continue;
            }
            Float sum = supportService.sumByProjectId(project.getId());
            List<SupportWithMember> list = supportService.findByProjectId(project.getId());
            List<Support> supportList = supportService.findSupportByProjectId(project.getId());

            //过滤数据正确的
            if (project.getActualAmount().equals(sum) && project.getFavorerNum().equals(list.size())){
                continue;
            }

            //过滤没有人支持的
            if (project.getFavorerNum() == 0){
                continue;
            }

            //更新所有支持
            for (Support support : supportList){
                OrderForm orderForm = orderFormService.get(support.getOrderId());
                if (support.getPayStatus() + 1 != orderForm.getStatus()){
                    support.setPayStatus(orderForm.getStatus() - 1);
                    supportService.update(support);
                }
            }

            //已经支付的人数
            project.setFavorerNum(list.size());
            project.setActualAmount(sum);
            if (sum >= project.getTargetAmount()){
                project.setIsSuccess(Constant.CROWDFUND_PROJECT_SUCCESS);//众筹成功
            }
            else if (0 < sum && sum< project.getActualAmount()){
                project.setIsSuccess(Constant.IS_CROWFUND_ING);//众筹中
            }
            else if (0 == sum){
                project.setIsSuccess(Constant.CROWDFUND_PROJECT_REFUNDED);
            }

            projectService.update(project);
        }
        logger.info("校正众筹数据结束");
    }

    public void targetRevise(){
        logger.info("校正众筹数项目数据开始");
        Activity activity = new Activity();
        activity.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
        activity.setIsCrowdfunded(Constant.IS_CROWDFUNDED);

        List<Activity> list = activityService.list(activity);
        for (Activity a : list){
            ProjectWithAuthor projectWithAuthor = new ProjectWithAuthor();
            projectWithAuthor.setTargetId(a.getId());
            projectWithAuthor.setIsSuccess(Constant.IS_CROWFUND_SUCCESS);
            List<ProjectWithAuthor> crowfunded = projectService.list(projectWithAuthor);

            ProjectWithAuthor projectCrowfunded = new ProjectWithAuthor();
            projectCrowfunded.setTargetId(a.getId());
            projectWithAuthor.setIsSuccess(Constant.IS_CROWFUND_ING);
            List<ProjectWithAuthor> beCrowfund = projectService.list(projectWithAuthor);

            if (a.getBeCrowdfundNum().equals(beCrowfund.size()) && a.getCrowdfundedNum().equals(crowfunded.size())){
                continue;
            }
            a.setBeCrowdfundNum(beCrowfund.size());
            a.setCrowdfundedNum(crowfunded.size());
            activityService.update(a);
        }
        logger.info("校正众筹数项目数据结束");
    }

}
