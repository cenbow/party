package com.party.web.web.controller.crowdfund;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.party.common.utils.DateUtils;
import com.party.core.model.activity.Activity;
import com.party.core.model.crowdfund.CrowdfundEvent;
import com.party.core.model.crowdfund.ProjectWithAuthor;
import com.party.core.model.crowdfund.SupportWithMember;
import com.party.core.model.distributor.DistributorRelation;
import com.party.core.model.member.Member;
import com.party.core.model.order.OrderType;
import com.party.core.service.activity.IActivityService;
import com.party.core.service.crowdfund.ICrowdfundEventService;
import com.party.core.service.crowdfund.IProjectTransferService;
import com.party.core.service.distributor.IDistributorRelationService;
import com.party.core.service.member.IMemberService;
import com.party.core.service.order.IOrderFormService;
import com.party.web.biz.activity.ActivityBizService;
import com.party.web.utils.excel.ExportExcel;
import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.party.common.paging.Page;
import com.party.core.model.crowdfund.Project;
import com.party.core.service.crowdfund.IProjectService;
import com.party.web.biz.crowdfund.CrowdFundBizService;
import com.party.web.biz.crowdfund.ProjectBizService;
import com.party.web.biz.crowdfund.RepresentBizService;
import com.party.web.biz.file.FileBizService;
import com.party.web.utils.RealmUtils;
import com.party.web.web.dto.AjaxResult;
import com.party.web.web.dto.input.crowdfund.LaunchInput;
import com.party.web.web.dto.output.crowdfund.DetailOutput;
import com.party.web.web.dto.output.crowdfund.ListForTargetOutput;
import com.party.web.web.dto.output.crowdfund.ProjectForActivityOutput;

import javax.servlet.http.HttpServletResponse;

/**
 * 众筹控制器
 * Created by wei.li
 *
 * @date 2017/2/17 0017
 * @time 15:37
 */

@Controller
@RequestMapping(value = "crowdfund/project")
public class ProjectController {

    @Autowired
    private IProjectService projectService;
    @Autowired
    private CrowdFundBizService crowdFundBizService;
    
    @Autowired
    private ProjectBizService projectBizService;
    
    @Autowired
    private FileBizService fileBizService;
    
    @Autowired
	private RepresentBizService representBizService;

	@Autowired
	private IActivityService activityService;

	@Autowired
	private IOrderFormService orderFormService;

	@Autowired
	private ActivityBizService activityBizService;

	@Autowired
	private IDistributorRelationService distributorRelationService;

	@Autowired
	private IMemberService memberService;

	@Autowired
	private ICrowdfundEventService crowdfundEventService;

	@Autowired
	private IProjectTransferService projectTransferService;

    protected static Logger logger = LoggerFactory.getLogger(ProjectController.class);

    /**
     * 众筹发起
     * @param launchInput 众筹发起输入视图
     * @param result 参数验证
     * @return 交互数据
     */
    @ResponseBody
    @RequestMapping(value = "launch")
    public AjaxResult launch(@Validated LaunchInput launchInput, BindingResult result){
        //数据验证
        if (result.hasErrors()){
            List<ObjectError> allErrors = result.getAllErrors();
            String description = allErrors.get(0).getDefaultMessage();
            return AjaxResult.error(description);
        }
        //html编码
        String content = null == launchInput.getContent() ? null : StringEscapeUtils.escapeHtml4(launchInput.getContent().trim());
        launchInput.setContent(content);
        crowdFundBizService.save(launchInput);
        return AjaxResult.success();
    }

    /**
     * 发起众筹视图
     * @return 发起众筹
     */
    @ResponseBody
    @RequestMapping(value = "launchView")
    public ModelAndView launchView(String id){
        ModelAndView modelAndView = new ModelAndView("crowdfund/launchView");
        if (!Strings.isNullOrEmpty(id)){
            DetailOutput detailOutput = crowdFundBizService.details(id);
            modelAndView.addObject("project", detailOutput);
        }

        return modelAndView;
    }

    /**
     * 我发布的众筹
     * @return 众筹列表
     */
    @RequestMapping(value = "list")
    public ModelAndView list(){
        ModelAndView modelAndView = new ModelAndView("crowdfund/projectList");
        return modelAndView;
    }

    /**
     * 异步发布的众筹列表
     * @param page 分页参数
     * @return 交互数据
     */
    @ResponseBody
    @RequestMapping(value = "asynList")
    public Map<String, Object> asynList(Page page){
    	String path = RealmUtils.getCurrentUser().getId() + "/project/";
        List<Project> projectList = crowdFundBizService.launchList(page);
        for (Project project : projectList) {
        	String content = "project/project_detail.html?id=" + project.getId();
			String qrCodeUrl = fileBizService.getFileEntity(project.getId(), path, content);
			project.setQrCodeUrl(qrCodeUrl);
		}
        Map<String, Object> map = Maps.newHashMap();
        map.put("datas", projectList);
        map.put("page", page);
        return map;
    }

    /**
     * 我支持的众筹
     * @return 众筹列表
     */
    @RequestMapping(value = "supportList")
    public ModelAndView supportList(){
        ModelAndView modelAndView = new ModelAndView("crowdfund/mySupportList");
        return modelAndView;
    }

    /**
     * 异步我的
     * @param page
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "asynSupportList")
    public Map<String, Object> asynSupportList(Page page){
        List<Project> projectList = crowdFundBizService.supportList(page);
        Map<String, Object> map = Maps.newHashMap();
        map.put("datas", projectList);
        map.put("page", page);
        return map;
    }

    /**
     * 详情页面
     * @return
     */
    @RequestMapping(value = "detail")
    public ModelAndView detail(String id){
        if (Strings.isNullOrEmpty(id)){
            logger.debug("参数不能为空");
            return new ModelAndView("error/500");
        }
        DetailOutput detailOutput = crowdFundBizService.details(id);
        ModelAndView modelAndView = new ModelAndView("crowdfund/projectDetail");
        modelAndView.addObject("project", detailOutput);
        return modelAndView;
    }
    
	/**
	 * 众筹中的众筹
	 * 
	 * @param id
	 *            项目编号
	 * @param page
	 *            分页参数
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "crowdfunding")
	public AjaxResult crowdfunding(String id, Page page) {
		AjaxResult ajaxResult = new AjaxResult();
		if (Strings.isNullOrEmpty(id)) {
			ajaxResult.setDescription("众筹项目编号不能为空");
			return ajaxResult;
		}
		List<ProjectForActivityOutput> projectForActivityOutputList = projectBizService.projectForActivityList(false, id, page);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("datas", projectForActivityOutputList);
		map.put("page", page);
		ajaxResult.setSuccess(true);
		ajaxResult.setData(map);
		return ajaxResult;
	}

	/**
	 * 众筹成功的众筹
	 * 
	 * @param id
	 *            项目编号
	 * @param page
	 *            分页参数
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "crowdfunded")
	public AjaxResult crowdfunded(String id, Page page) {
		AjaxResult ajaxResult = new AjaxResult();
		if (Strings.isNullOrEmpty(id)) {
			ajaxResult.setDescription("众筹项目编号不能为空");
			return ajaxResult;
		}
		List<ProjectForActivityOutput> projectForActivityOutputList = projectBizService.projectForActivityList(true, id, page);
		ajaxResult.setSuccess(true);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("datas", projectForActivityOutputList);
		map.put("page", page);
		ajaxResult.setData(map);
		return ajaxResult;
	}

	/**
	 * 众筹列表
	 * @param id 项目编号
	 * @param page 分页参数
	 * @return 众筹列表
	 */
	@ResponseBody
	@RequestMapping(value = "crowdfundList")
	public AjaxResult crowdfundList(String id, Page page) {
		AjaxResult ajaxResult = new AjaxResult();
		if (Strings.isNullOrEmpty(id)) {
			ajaxResult.setDescription("众筹项目编号不能为空");
			return ajaxResult;
		}
		ProjectWithAuthor projectWithAuthor = new ProjectWithAuthor();
		projectWithAuthor.setTargetId(id);
		List<ProjectForActivityOutput> projectForActivityOutputList = projectBizService.projectForActivityList(projectWithAuthor, page);
		ajaxResult.setSuccess(true);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("datas", projectForActivityOutputList);
		map.put("page", page);
		ajaxResult.setData(map);
		return ajaxResult;
	}

	/**
	 * 项目下代言
	 * 
	 * @param id
	 *            项目编号
	 * @param page
	 *            分页参数
	 * @return 代言列表
	 */
	@ResponseBody
	@RequestMapping(value = "listForTarget")
	public AjaxResult listForTarget(String id, Page page) {
		AjaxResult ajaxResult = new AjaxResult();

		List<ListForTargetOutput> listForTargetOutputList;
		try {
			listForTargetOutputList = representBizService.listForTarget(page, null, id);
		} catch (Exception e) {
			e.printStackTrace();
			ajaxResult.setDescription("系统异常");
			return ajaxResult;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("datas", listForTargetOutputList);
		map.put("page", page);
		ajaxResult.setSuccess(true);
		ajaxResult.setData(map);
		return ajaxResult;
	}


	/**
	 * 根据分销关系查询众筹列表
	 * @param relationId 分销关系
	 * @param page 分页参数
	 * @return 众筹列表
	 */
	@RequestMapping(value = "listForDistributorId")
	public ModelAndView listForDistributorId(String id, String relationId, String name, Page page){
		page.setLimit(20);
		ModelAndView modelAndView = new ModelAndView("activity/crowdfundList");
		Activity activity = activityService.get(id);
		int joinNum = orderFormService.calculateBuyNum(activity.getId(), null, false, OrderType.ORDER_ACTIVITY.getCode());
		activity.setJoinNum(joinNum);
		modelAndView.addObject("activity", activity);
		//众筹金额
		float actualAmount = activityBizService.actualAmountForTargetId(id);
		modelAndView.addObject("actualAmount", actualAmount);
		List<ProjectForActivityOutput> projectForActivityOutputList = projectBizService.listForDistributorId(relationId, page);
		modelAndView.addObject("list", projectForActivityOutputList);
		modelAndView.addObject("page", page);
		modelAndView.addObject("name", name);
		modelAndView.addObject("relationId", relationId);
		return modelAndView;
	}

	/**
	 * 导出代言的众筹
	 * @param relationId 分销关系
	 * @param name 名称
	 * @param response 响应参数
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "listForDistributorIdExport")
	public String listForDistributorIdExport(String relationId, String name, HttpServletResponse response){
		String fileName = name + "代言的众筹" + DateUtils.todayDate() + ".xlsx";
		List<ProjectForActivityOutput> projectForActivityOutputList = projectBizService.listForDistributorId(relationId, null);
		try {
			new ExportExcel(fileName, ProjectForActivityOutput.class).setDataList(projectForActivityOutputList).write(response, fileName).dispose();
			return null;
		} catch (IOException e) {
			logger.error("导出代言的众筹异常", e);
		}
		return null;
	}

	/**
	 * 根据事件查询众筹列表
	 * @param projectWithAuthor 查询数据
	 * @param page 分页参数
	 * @return 交互数据
	 */
	@RequestMapping(value = "listForEvent")
	public ModelAndView listForEvent( ProjectWithAuthor projectWithAuthor, Page page){
		ModelAndView modelAndView = new ModelAndView("crowdfund/zcCrowdfund");
		page.setLimit(20);
		List<ProjectForActivityOutput> projectForActivityOutputList = projectBizService.projectForActivityList(projectWithAuthor, page);

		Integer representNum = distributorRelationService.countForEvent(projectWithAuthor.getEventId());
		// 众筹金额
		modelAndView.addObject("representNum", representNum);
		modelAndView.addObject("projectNum", page.getTotalCount());
		modelAndView.addObject("list", projectForActivityOutputList);
		modelAndView.addObject("page", page);
		return modelAndView;
	}

	/**
	 * 根据事件查询众筹导出
	 * @param projectWithAuthor 查询数据
	 * @param response 响应参数
	 * @return 交互数据
	 */
	@ResponseBody
	@RequestMapping(value = "listForEventExport")
	public String listForEventExport(ProjectWithAuthor projectWithAuthor, HttpServletResponse response) {
		CrowdfundEvent crowdfundEvent = crowdfundEventService.get(projectWithAuthor.getEventId());
		String fileName = crowdfundEvent.getName() + "的事项众筹" + DateUtils.todayDate() + ".xlsx";
		List<ProjectForActivityOutput> projectForActivityOutputList = projectBizService.projectForActivityList(projectWithAuthor, null);
		try {
			new ExportExcel(fileName, ProjectForActivityOutput.class).setDataList(projectForActivityOutputList).write(response, fileName).dispose();
			return null;
		} catch (IOException e) {
			logger.error("导出众筹异常", e);
		}
		return null;
	}

	/**
	 * 事项代言项目
	 * @param relationId 关系编号
	 * @param eventId 事件编号
	 * @param page 分页参数
	 * @return 交互数据
	 */
	@RequestMapping(value = "event/listForDistributorId")
	public ModelAndView listEventForDistributorId(String relationId, String eventId, Page page){
		page.setLimit(20);
		ModelAndView modelAndView = new ModelAndView("crowdfund/crowdfundList");

		//众筹金额
		List<ProjectForActivityOutput> projectForActivityOutputList = projectBizService.listForDistributorId(relationId, page);
		Integer representNum = distributorRelationService.countForEvent(eventId);
		Integer projectNum = projectService.countForEvent(eventId);
		DistributorRelation distributorRelation = distributorRelationService.get(relationId);
		Member author = memberService.get(distributorRelation.getDistributorId());

		modelAndView.addObject("eventId", eventId);
		modelAndView.addObject("representNum", representNum);
		modelAndView.addObject("projectNum", projectNum);
		modelAndView.addObject("list", projectForActivityOutputList);
		modelAndView.addObject("page", page);
		modelAndView.addObject("name", author.getRealname());
		modelAndView.addObject("relationId", relationId);
		return modelAndView;
	}


	/**
	 * 导出
	 * @param relationId 关系
	 * @param response 响应数据
	 * @return 交互数据
	 */
	@ResponseBody
	@RequestMapping(value = "event/listForDistributorIdExport")
	public String listEventForDistributorIdExport(String relationId, HttpServletResponse response){
		DistributorRelation distributorRelation = distributorRelationService.get(relationId);
		Member author = memberService.get(distributorRelation.getDistributorId());

		String fileName = author.getRealname() + "代言的众筹" + DateUtils.todayDate() + ".xlsx";
		List<ProjectForActivityOutput> projectForActivityOutputList = projectBizService.listForDistributorId(relationId, null);
		try {
			new ExportExcel(fileName, ProjectForActivityOutput.class).setDataList(projectForActivityOutputList).write(response, fileName).dispose();
			return null;
		} catch (IOException e) {
			logger.error("导出代言的众筹异常", e);
		}
		return null;
	}


	/**
	 * 删除众筹
	 * @param id 众筹编号
	 * @return 交互数据
	 */
	@ResponseBody
	@RequestMapping(value = "delete")
	public AjaxResult delete(String id){
		AjaxResult ajaxResult = new AjaxResult();
		projectBizService.delete(id);
		ajaxResult.setSuccess(true);
		return ajaxResult;
	}

	/**
	 * 移除众筹事项
	 * @param id 编号
	 * @return 交互结果
	 */
	@ResponseBody
	@RequestMapping(value = "removeEvent")
	public AjaxResult removeEvent(String id){
		AjaxResult ajaxResult = new AjaxResult();
		Activity activity = activityService.get(id);
		activity.setEventId("");
		activityService.update(activity);
		ajaxResult.setSuccess(true);
		return ajaxResult;
	}

	/**
	 * 查询发布商的其他众筹
	 * @param projectId 众筹编号
	 * @return 交互数据
	 */
	@RequestMapping(value = "otherProject")
	public ModelAndView otherProject(String projectId){
		ModelAndView modelAndView = new ModelAndView("crowdfund/otherProject");
		List<ProjectWithAuthor> list = projectService.otherProject(projectId);
		modelAndView.addObject("list", list);
		modelAndView.addObject("sourceId", projectId);
		return modelAndView;
	}


	/**
	 * 转移支持者
	 * @param sourceId 资源编号
	 * @param targetId 目标编号
	 * @return 交互数据
	 */
	@ResponseBody
	@RequestMapping(value = "dataTransfer")
	public AjaxResult dataTransfer(String sourceId, String targetId){
		AjaxResult ajaxResult = new AjaxResult();
		projectTransferService.transferSupport(sourceId, targetId);
		ajaxResult.setSuccess(true);
		return ajaxResult;
	}
}
