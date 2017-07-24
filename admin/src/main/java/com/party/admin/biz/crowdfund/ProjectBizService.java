package com.party.admin.biz.crowdfund;

import java.util.List;

import com.party.admin.biz.refund.RefundBizService;
import com.party.admin.task.QuartzCrowdfundRefund;
import com.party.core.model.count.DataCount;
import com.party.core.model.crowdfund.*;
import com.party.core.model.distributor.DistributorCount;
import com.party.core.model.distributor.DistributorDetail;
import com.party.core.model.member.MemberAct;
import com.party.core.service.count.IDataCountService;
import com.party.core.service.crowdfund.*;
import com.party.core.service.distributor.IDistributorCountService;
import com.party.core.service.distributor.IDistributorDetailService;
import com.party.core.service.member.IMemberActService;
import com.party.pay.model.refund.WechatPayRefundResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Function;
import com.party.admin.web.dto.output.crowdfund.ProjectForActivityOutput;
import com.party.common.constant.Constant;
import com.party.common.paging.Page;
import com.party.common.utils.LangUtils;
import com.party.core.model.activity.Activity;
import com.party.core.model.member.Member;
import com.party.core.service.activity.IActivityService;
import com.party.core.service.member.IMemberService;

/**
 * 众筹项目业务接口 Created by wei.li
 *
 * @date 2017/2/17 0017
 * @time 10:21
 */
@Service
public class ProjectBizService {

	@Autowired
	private IProjectService projectService;
	
	@Autowired
	private IMemberService memberService;

	@Autowired
	private ISupportService supportService;


	@Autowired
	private IDistributorDetailService distributorDetailService;

	@Autowired
	private IDistributorCountService distributorCountService;

	@Autowired
	private IActivityService activityService;

	@Autowired
	private ITargetProjectService targetProjectService;

	@Autowired
	private IMemberActService memberActService;

	@Autowired
	private SupportBizService supportBizService;

	@Autowired
	private IAnalyzeService analyzeService;

	protected static Logger logger = LoggerFactory.getLogger(ProjectBizService.class);

	/**
	 * 查询活动下的众筹
	 * 
	 * @param isSuccess
	 *            是否筹集成功
	 * @param targetId
	 *            目标编号
	 * @param page
	 *            分页参数
	 * @return 众筹列表
	 */
	public List<ProjectForActivityOutput> projectForActivityList(boolean isSuccess, String targetId, Page page) {
		ProjectWithAuthor projectWithAuthor = new ProjectWithAuthor();

		// 众筹成功？
		Integer success = isSuccess ? 1 : 0;
		projectWithAuthor.setIsSuccess(success);
		projectWithAuthor.setTargetId(targetId);
		List<ProjectWithAuthor> projectWithAuthorList = projectService.listPage(projectWithAuthor, page);
		List<ProjectForActivityOutput> projectForActivityOutputList = LangUtils.transform(projectWithAuthorList,
				new Function<ProjectWithAuthor, ProjectForActivityOutput>() {
					public ProjectForActivityOutput apply(ProjectWithAuthor projectWithAuthor) {
						return ProjectForActivityOutput.transform(projectWithAuthor);
					}
				});
		return projectForActivityOutputList;
	}


	
	/**
	 * 查询活动下的众筹
	 * @param projectWithAuthor 众筹信息
	 * @param page 分页参数
	 * @return 众筹列表
	 */
	public List<ProjectForActivityOutput> projectForActivityList(ProjectWithAuthor projectWithAuthor, Page page) {
		List<ProjectWithAuthor> projectWithAuthorList = projectService.listPage(projectWithAuthor, page);
		List<ProjectForActivityOutput> projectForActivityOutputList = LangUtils.transform(projectWithAuthorList, new Function<ProjectWithAuthor, ProjectForActivityOutput>() {
			public ProjectForActivityOutput apply(ProjectWithAuthor projectWithAuthor) {
				return transform(projectWithAuthor);
			}
		});
		return projectForActivityOutputList;
	}
	
	/**
	 * 根据分销关系查询众筹项目
	 * @param relationId 分销关系
	 * @param page 分页参数
	 * @return 输出视图
	 */
	public List<ProjectForActivityOutput> listForDistributorId(String relationId, Page page){
		ProjectWithAuthor projectWithAuthor = new ProjectWithAuthor();
		projectWithAuthor.setRelationId(relationId);
		List<ProjectWithAuthor> projectWithAuthorList = projectService.listPage(projectWithAuthor, page);
		List<ProjectForActivityOutput> projectForActivityOutputList = LangUtils.transform(projectWithAuthorList, new Function<ProjectWithAuthor, ProjectForActivityOutput>() {
			public ProjectForActivityOutput apply(ProjectWithAuthor projectWithAuthor) {
				return transform(projectWithAuthor);
			}
		});
		return projectForActivityOutputList;
	}

	/**
	 * 转换输出视图
	 * @param projectWithAuthor 众筹信息
	 * @return 输出视图
	 */
	public ProjectForActivityOutput transform(ProjectWithAuthor projectWithAuthor){
		ProjectForActivityOutput projectForActivityOutput = ProjectForActivityOutput.transform(projectWithAuthor);
		if (projectWithAuthor.getIsSuccess().equals(0)){
			projectForActivityOutput.setStatus("众筹中");
		}
		else if (projectForActivityOutput.getIsSuccess().equals(1)){
			projectForActivityOutput.setStatus("众筹成功");
		}
		else if (projectForActivityOutput.getIsSuccess().equals(2)){
			projectForActivityOutput.setStatus("众筹失败");
		}
		return projectForActivityOutput;
	}

	/**
	 * 众筹退款
	 * @param id 众筹编号
	 */
	public void refund(String id){
		Project project = projectService.get(id);
		this.refund(project);
	}


	/**
	 * 众筹退款
	 * @param project 众筹
	 */
	public void refund(Project project){

		TargetProject targetProject = targetProjectService.findByProjectId(project.getId());
		Activity activity = activityService.get(targetProject.getTargetId());
		if (Constant.IS_CROWFUND_ING.equals(project.getIsSuccess())){
			activity.setBeCrowdfundNum(activity.getBeCrowdfundNum() - 1);
		}
		else if (Constant.CROWDFUND_PROJECT_SUCCESS.equals(project.getIsSuccess())){
			activity.setCrowdfundedNum(activity.getCrowdfundedNum() - 1);
		}
		activityService.update(activity);

		project.setIsSuccess(Constant.CROWDFUND_PROJECT_REFUNDING);
		projectService.update(project);

		List<SupportWithMember> supportList = supportService.findByProjectId(project.getId());
		for (SupportWithMember supportWithMember : supportList){
			supportBizService.sendRefund(supportWithMember.getId());
		}
	}

	/**
	 * 删除众筹
	 * @param id 众筹编号
	 */
	@Transactional
	public void delete(String id){

		//删除分销信息
		DistributorDetail distributorDetail = distributorDetailService.findByTargetId(id);
		if (null != distributorDetail){
			DistributorCount distributorCount = distributorCountService.findByRelationId(distributorDetail.getDistributorRelationId());
			if (null != distributorCount){
				distributorCount.setCrowdfundNum(distributorCount.getCrowdfundNum() - 1);
				distributorCount.setApplyNum(distributorCount.getApplyNum() -1);
				distributorCountService.update(distributorCount);
			}

			distributorDetailService.delete(distributorDetail.getId());
		}

		//众筹关系
		TargetProject targetProject = targetProjectService.findByProjectId(id);

		if (null != targetProject){
			//修改众筹项目
			Activity activity = activityService.get(targetProject.getTargetId());
			Project project = projectService.get(id);
			if (Constant.IS_CROWFUND_ING.equals(project.getIsSuccess())){
				activity.setBeCrowdfundNum(activity.getBeCrowdfundNum() -1);
			}
			activity.setJoinNum(activity.getJoinNum() - 1);
		    activityService.update(activity);

			//删除报名记录
			MemberAct memberAct = memberActService.findByOrderId(targetProject.getOrderId());
			memberActService.delete(memberAct.getId());
			targetProjectService.delete(targetProject.getId());
		}

		Analyze analyze = analyzeService.findByTargetId(id);
		if (null != analyze){
			analyzeService.delete(analyze.getId());
		}
		//删除众筹
		projectService.delete(id);
	}

	public void synchronization(){
		List<Project> projectList = projectService.list(new Project());
		for (Project project : projectList){
			Float sum = supportService.sumByProjectId(project.getId());
			sum = null == sum ? 0f : sum;
			project.setRealTimeAmount(sum);
			projectService.update(project);
		}
	}
}
