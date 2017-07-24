package com.party.core.service.crowdfund.impl;

import com.party.common.constant.Constant;
import com.party.core.model.activity.Activity;
import com.party.core.model.crowdfund.Project;
import com.party.core.model.crowdfund.TargetProject;
import com.party.core.service.activity.IActivityService;
import com.party.core.service.crowdfund.IProjectRefundService;
import com.party.core.service.crowdfund.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 众筹退款实现
 * Created by wei.li
 *
 * @date 2017/4/28 0028
 * @time 11:47
 */

@Service
public class ProjectRefundService implements IProjectRefundService {

    @Autowired
    private IProjectService projectService;


    /**
     * 众筹项目退款
     * @param project 众筹信息
     */
    @Override
    @Transactional
    public void refund(Project project) {

        project.setIsSuccess(Constant.CROWDFUND_PROJECT_REFUNDED);//退款成功
        projectService.update(project);
    }
}
