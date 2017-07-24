package com.party.core.service.crowdfund.impl;

import com.party.common.constant.Constant;
import com.party.core.model.activity.Activity;
import com.party.core.model.crowdfund.Project;
import com.party.core.model.crowdfund.ProjectWithAuthor;
import com.party.core.model.crowdfund.Support;
import com.party.core.model.crowdfund.SupportWithMember;
import com.party.core.model.order.OrderForm;
import com.party.core.service.activity.IActivityService;
import com.party.core.service.crowdfund.IProjectReviseService;
import com.party.core.service.crowdfund.IProjectService;
import com.party.core.service.crowdfund.ISupportService;
import com.party.core.service.order.IOrderFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 众筹校正数据
 * Created by wei.li
 *
 * @date 2017/7/3 0003
 * @time 18:47
 */

@Service
public class ProjectReviseService implements IProjectReviseService{

    @Autowired
    private IProjectService projectService;

    @Autowired
    private ISupportService supportService;

    @Autowired
    private IOrderFormService orderFormService;

    @Autowired
    private IActivityService activityService;


    /**
     * 校正数据
     * @param id 编号
     */
    @Override
    public void reviseFavorer(String id) {
        Project project = projectService.get(id);
        List<SupportWithMember> list = supportService.findByProjectId(project.getId());
        Float sum = supportService.sumByProjectId(project.getId());

        project.setActualAmount(sum);
        project.setFavorerNum(list.size());

        if (sum >= project.getTargetAmount()){
            project.setIsSuccess(Constant.CROWDFUND_PROJECT_SUCCESS);//众筹成功
        }
        else if (0 < sum && sum < project.getTargetAmount()){
            project.setIsSuccess(Constant.IS_CROWFUND_ING);
        }
        else if (0 == sum){
            if (project.getIsSuccess().equals(Constant.CROWDFUND_PROJECT_REFUNDING)){
                project.setIsSuccess(Constant.CROWDFUND_PROJECT_REFUNDED);
            }
            else{
                project.setIsSuccess(Constant.IS_CROWFUND_ING);
            }
        }
        projectService.update(project);

        List<Support> supportList = supportService.findSupportByProjectId(id);

        //更新所有支持
        for (Support support : supportList){
            OrderForm orderForm = orderFormService.get(support.getOrderId());
            if (null == orderForm){
                continue;
            }
            if (support.getPayStatus() + 1 != orderForm.getStatus()){
                support.setPayStatus(orderForm.getStatus() - 1);
                supportService.update(support);
            }
        }
    }

    /**
     * 校正众筹目标
     * @param activityId 众筹目标
     */
    @Override
    public void reviseTarget(String activityId) {

        Activity activity = activityService.get(activityId);
        //众筹成功
        ProjectWithAuthor projectWithAuthor = new ProjectWithAuthor();
        projectWithAuthor.setTargetId(activity.getId());
        projectWithAuthor.setIsSuccess(Constant.IS_CROWFUND_SUCCESS);
        List<ProjectWithAuthor> crowfunded = projectService.list(projectWithAuthor);

        //众筹中
        ProjectWithAuthor projectCrowfunded = new ProjectWithAuthor();
        projectCrowfunded.setTargetId(activity.getId());
        projectWithAuthor.setIsSuccess(Constant.IS_CROWFUND_ING);
        List<ProjectWithAuthor> beCrowfund = projectService.list(projectWithAuthor);

        //校正支持者
        Integer sum = projectService.sumfavorerNum(activity.getId());

        activity.setBeCrowdfundNum(beCrowfund.size());
        activity.setCrowdfundedNum(crowfunded.size());
        activity.setFavorerNum(sum);
        activityService.update(activity);
    }
}
