package com.party.web.biz.crowdfund;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.party.common.paging.Page;
import com.party.core.exception.BusinessException;
import com.party.core.model.activity.Activity;
import com.party.core.model.activity.ActivityDetail;
import com.party.core.model.crowdfund.Project;
import com.party.core.model.crowdfund.ProjectContent;
import com.party.core.model.crowdfund.TargetProject;
import com.party.core.model.member.Member;
import com.party.core.model.member.MemberAct;
import com.party.core.service.activity.IActivityDetailService;
import com.party.core.service.activity.IActivityService;
import com.party.core.service.crowdfund.IProjectContentService;
import com.party.core.service.crowdfund.IProjectService;
import com.party.core.service.crowdfund.ITargetProjectService;
import com.party.core.service.member.IMemberActService;
import com.party.core.service.member.IMemberService;
import com.party.web.utils.Constant;
import com.party.web.utils.RealmUtils;
import com.party.web.web.dto.input.crowdfund.LaunchInput;
import com.party.web.web.dto.output.crowdfund.DetailOutput;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

/**
 * 众筹业务接口
 * Created by wei.li
 *
 * @date 2017/2/17 0017
 * @time 16:10
 */

@Service
public class CrowdFundBizService {

    @Autowired
    private IProjectService projectService;

    @Autowired
    private IProjectContentService projectContentService;

    @Autowired
    private IMemberActService memberActService;

    @Autowired
    private IActivityService activityService;

    @Autowired
    private IActivityDetailService activityDetailService;

    @Autowired
    private ITargetProjectService targetProjectService;

    /**
     * 我发布的列表
     * @return
     */
    public List<Project> launchList(Page page){
        Member member = RealmUtils.getCurrentUser();
        HashMap<String, Object> param = Maps.newHashMap();
        param.put("authorId", member.getId());
        List<Project> projectList = projectService.listPage(param, page);
        return projectList;
    }

    /**
     * 我支持的列表
     * @return
     */
    public List<Project> supportList(Page page){
        Member member = RealmUtils.getCurrentUser();
        HashMap<String, Object> param = Maps.newHashMap();
        param.put("favorerId", member.getId());
        List<Project> projectList = projectService.listPage(param, page);
        return projectList;
    }

    /**
     * 保存众筹项目
     * @param launchInput
     */
    @Transactional
    public void save(LaunchInput launchInput){
        Project project = LaunchInput.transform(launchInput);
        String content = launchInput.getContent();
        if (Strings.isNullOrEmpty(launchInput.getId())){
            this.insert(project, content);
        }
       else {
            this.update(project, content);
        }
    }


    @Transactional
    public void insert(MemberAct memberAct){

        //持久化众筹项目
        Activity activity = activityService.get(memberAct.getActId());

        //是否是众筹活动
        if (!activity.getIsCrowdfunded().equals(com.party.common.constant.Constant.IS_CROWDFUNDED)){
            throw new BusinessException("该活动不是众筹活动");
        }
        Project project = new Project();
        project.setTitle(activity.getTitle());
        project.setTargetAmount(activity.getPrice());
        project.setPic(activity.getPic());

        String content = null;
        //活动详情
        ActivityDetail activityDetail = activityDetailService.getByRefId(activity.getId());
        if (null != activityDetail && null != activityDetail.getContent()){
            content = activityDetail.getContent();
        }
        String id = this.insert(project, content);

        //持久化众筹关联
        TargetProject targetProject = new TargetProject();
        targetProject.setTargetId(activity.getId());
        targetProject.setOrderId(memberAct.getOrderId());
        targetProject.setProjectId(id);
        targetProject.setType(Constant.CROWD_FUND_TYPE_ACTIVITY);
        targetProjectService.insert(targetProject);
    }

    /**
     * 插入众筹项目
     * @param project 众筹信息
     * @param content 详细信息
     * @return 编号
     */
    @Transactional
    public String insert(Project project, String content){
        String id = projectContentService.insert(new ProjectContent(content));
        project.setContentId(id);
        Member member = RealmUtils.getCurrentUser();
        project.setAuthorId(member.getId());
        projectService.insert(project);
        return project.getId();
    }


    /**
     * 更新众筹项目
     * @param project 项目信息
     * @param content 项目详情
     */
    @Transactional
    public void update(Project project, String content){
        Project dbProject = projectService.get(project.getId());
        if (dbProject.getContentId() != null){
            ProjectContent projectContent = projectContentService.get(dbProject.getContentId());
            projectContent.setContent(projectContent.getContent());
            projectContentService.update(projectContent);
        }
        else {
            String id = projectContentService.insert(new ProjectContent(content));
            project.setContentId(id);
        }
        projectService.update(project);
    }

    /**
     * 获取详情
     * @param id 编号
     * @return
     */
    public DetailOutput details(String id){
        Project project = projectService.get(id);
        DetailOutput detailOutput = DetailOutput.transform(project);
        ProjectContent projectContent = projectContentService.get(project.getContentId());
        if (null != projectContent && null != projectContent.getContent()){
            detailOutput.setContent(projectContent.getContent());
        }
        return detailOutput;
    }
}
