package com.party.admin.web.controller.crowdfund;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.google.common.base.Strings;
import com.party.admin.biz.crowdfund.ProjectBizService;
import com.party.admin.web.dto.AjaxResult;
import com.party.admin.web.dto.output.crowdfund.ProjectForActivityOutput;
import com.party.admin.web.dto.output.crowdfund.SupportCountOutput;
import com.party.core.exception.BusinessException;
import com.party.core.model.crowdfund.Project;
import com.party.core.model.crowdfund.ProjectWithAuthor;
import com.party.core.model.distributor.DistributorRelation;
import com.party.core.model.member.Member;
import com.party.core.service.crowdfund.IProjectService;
import com.party.core.service.distributor.IDistributorRelationService;
import com.party.core.service.member.IMemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.party.admin.biz.activity.ActivityBizService;
import com.party.admin.biz.crowdfund.SupportBizService;
import com.party.admin.utils.excel.ExportExcel;
import com.party.common.paging.Page;
import com.party.common.utils.DateUtils;
import com.party.core.model.activity.Activity;
import com.party.core.model.crowdfund.SupportWithMember;
import com.party.core.model.order.OrderType;
import com.party.core.service.activity.IActivityService;
import com.party.core.service.order.IOrderFormService;

/**
 * Created by wei.li
 *
 * @date 2017/3/28 0028
 * @time 16:13
 */

@Controller
@RequestMapping(value = "crowdfund/support")
public class SupportController {

    @Autowired
    private SupportBizService supportBizService;

    @Autowired
    private IActivityService activityService;

    @Autowired
    private IOrderFormService orderFormService;

    @Autowired
    private ActivityBizService activityBizService;

    @Autowired
    private ProjectBizService projectBizService;

    @Autowired
    private IProjectService projectService;

    @Autowired
    private IMemberService memberService;

    @Autowired
    private IDistributorRelationService distributorRelationService;

    protected Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 根据分销关系编号查询支持列表
     * @param id 活动编号
     * @param relationId 分销关系
     * @param page 分页参数
     * @return
     */
    @RequestMapping(value = "listForDistributorId")
    public ModelAndView listForDistributorId(String id, String relationId, String name, Page page){
        page.setLimit(20);
        ModelAndView modelAndView = new ModelAndView("activity/supportList");
        Activity activity = activityService.get(id);
        int joinNum = orderFormService.calculateBuyNum(activity.getId(), null, false, OrderType.ORDER_ACTIVITY.getCode());
        activity.setJoinNum(joinNum);

        //众筹金额
        float actualAmount = activityBizService.actualAmountForTargetId(id);

        List<SupportWithMember> supportWithMemberList = supportBizService.listForDistributorId(relationId, page);
        modelAndView.addObject("actualAmount", actualAmount);
        modelAndView.addObject("activity", activity);
        modelAndView.addObject("page", page);
        modelAndView.addObject("relationId", relationId);
        modelAndView.addObject("name", name);
        modelAndView.addObject("list", supportWithMemberList);
        return modelAndView;
    }


    /**
     * 根据分销关系编号查询支持列表导出
     * @param relationId 分销关系
     * @param name 代言名称
     * @param response 响应参数
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "listForDistributorIdExport")
    public String listForDistributorIdExport(String relationId, String name, HttpServletResponse response){
        String fileName = name + "的代言的支持人" + DateUtils.todayDate() + ".xlsx";
        List<SupportWithMember> supportWithMemberList = supportBizService.listForDistributorId(relationId, null);
        try {
            new ExportExcel(fileName, SupportWithMember.class).setDataList(supportWithMemberList).write(response, fileName).dispose();
            return null;
        } catch (IOException e) {
            logger.error("导出支持人异常", e);
        }
        return null;
    }

    /**
     * 根据分销关系编号查询支持列表
     * @param relationId 分销关系
     * @param page 分页参数
     * @return
     */
    @RequestMapping(value = "event/listForDistributorId")
    public ModelAndView listEventForDistributorId(String relationId, String eventId, Page page){
        page.setLimit(20);
        ModelAndView modelAndView = new ModelAndView("crowdfund/supportList");
        DistributorRelation distributorRelation = distributorRelationService.get(relationId);
        Member author = memberService.get(distributorRelation.getDistributorId());

        Integer representNum = distributorRelationService.countForEvent(eventId);
        Integer projectNum = projectService.countForEvent(eventId);

        List<SupportWithMember> supportWithMemberList = supportBizService.listForDistributorId(relationId, page);
        modelAndView.addObject("page", page);
        modelAndView.addObject("eventId", eventId);
        modelAndView.addObject("representNum", representNum);
        modelAndView.addObject("projectNum", projectNum);
        modelAndView.addObject("relationId", relationId);
        modelAndView.addObject("name", author.getRealname());
        modelAndView.addObject("list", supportWithMemberList);
        return modelAndView;
    }


    /**
     * 根据分销关系编号查询支持列表导出
     * @param relationId 分销关系
     * @param response 响应参数
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "event/listForDistributorIdExport")
    public String listEventForDistributorIdExport(String relationId, HttpServletResponse response){
        DistributorRelation distributorRelation = distributorRelationService.get(relationId);
        Member author = memberService.get(distributorRelation.getDistributorId());
        String fileName = author.getRealname() + "的代言的支持人" + DateUtils.todayDate() + ".xlsx";
        List<SupportWithMember> supportWithMemberList = supportBizService.listForDistributorId(relationId, null);
        try {
            new ExportExcel(fileName, SupportWithMember.class).setDataList(supportWithMemberList).write(response, fileName).dispose();
            return null;
        } catch (IOException e) {
            logger.error("导出支持人异常", e);
        }
        return null;
    }


    /**
     * 众筹项目查询支持
     * @param id 活动编号
     * @param projectId 众筹编号
     * @param page 分页参数
     * @return 支持列表
     */
    @RequestMapping(value = "listForProjectId")
    public ModelAndView listForProjectId(String id, String projectId, String startTime, Page page){
        page.setLimit(20);
        ModelAndView modelAndView = new ModelAndView("activity/supportForProjectList");
        Activity activity = activityService.get(id);
        int joinNum = orderFormService.calculateBuyNum(activity.getId(), null, false, OrderType.ORDER_ACTIVITY.getCode());
        activity.setJoinNum(joinNum);

        //众筹金额
        float actualAmount = activityBizService.actualAmountForTargetId(id);

        startTime = Strings.isNullOrEmpty(startTime)? null : startTime;
        List<SupportWithMember> supportWithMemberList = supportBizService.listForProjectId(projectId, startTime, page);
        ProjectWithAuthor projectWithAuthor = new ProjectWithAuthor();
        projectWithAuthor.setTargetId(id);
        List<ProjectForActivityOutput> projectForActivityOutputList = projectBizService.projectForActivityList(projectWithAuthor, null);

        Project project = projectService.get(projectId);
        Member author = memberService.get(project.getAuthorId());

        modelAndView.addObject("actualAmount", actualAmount);
        modelAndView.addObject("activity", activity);
        modelAndView.addObject("projectId", projectId);
        modelAndView.addObject("page", page);
        modelAndView.addObject("id", id);
        modelAndView.addObject("list", supportWithMemberList);
        modelAndView.addObject("name", author.getRealname());
        modelAndView.addObject("crowdfundNum", projectForActivityOutputList.size());
        return modelAndView;
    }

    /**
     * 导出众筹项目的支持
     * @param projectId 项目编号
     * @param name 项目名称
     * @param response 响应参数
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "listForProjectIdExport")
    public String listForProjectIdExport(String projectId, String name, HttpServletResponse response){
        String fileName = name + "的众筹的支持人" + DateUtils.todayDate() + ".xlsx";
        List<SupportWithMember> supportWithMemberList = supportBizService.listForProjectId(projectId, null, null);
        try {
            new ExportExcel(fileName, SupportWithMember.class).setDataList(supportWithMemberList).write(response, fileName).dispose();
            return null;
        } catch (IOException e) {
            logger.error("导出支持人异常", e);
        }
        return null;
    }


    /**
     * 众筹项目查询支持
     * @param id 活动编号
     * @param projectId 众筹编号
     * @param page 分页参数
     * @return 支持列表
     */
    @RequestMapping(value = "listForRepresent")
    public ModelAndView listForRepresent(String id, String projectId, String name, Page page){
        page.setLimit(20);
        ModelAndView modelAndView = new ModelAndView("activity/supportForRepresentList");
        Activity activity = activityService.get(id);
        int joinNum = orderFormService.calculateBuyNum(activity.getId(), null, false, OrderType.ORDER_ACTIVITY.getCode());
        activity.setJoinNum(joinNum);

        //众筹金额
        float actualAmount = activityBizService.actualAmountForTargetId(id);

        List<SupportWithMember> supportWithMemberList = supportBizService.listForProjectId(projectId, null, page);
        modelAndView.addObject("actualAmount", actualAmount);
        modelAndView.addObject("activity", activity);
        modelAndView.addObject("projectId", projectId);
        modelAndView.addObject("page", page);
        modelAndView.addObject("list", supportWithMemberList);
        modelAndView.addObject("name", name);
        return modelAndView;
    }

    /**
     * 导出众筹项目的支持
     * @param projectId 项目编号
     * @param name 项目名称
     * @param response 响应参数
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "listForRepresentExport")
    public String listForRepresentExport(String projectId, String name, HttpServletResponse response){
        String fileName = name + "的众筹的支持人" + DateUtils.todayDate() + ".xlsx";
        List<SupportWithMember> supportWithMemberList = supportBizService.listForProjectId(projectId, null, null);
        try {
            new ExportExcel(fileName, SupportWithMember.class).setDataList(supportWithMemberList).write(response, fileName).dispose();
            return null;
        } catch (IOException e) {
            logger.error("导出支持人异常", e);
        }
        return null;
    }


    @RequestMapping(value = "event/listForProjectId")
    public ModelAndView listEventForProjectId(String projectId, String eventId,String startTime, Page page){
        page.setLimit(20);
        ModelAndView modelAndView = new ModelAndView("crowdfund/supportForProjectList");
        List<SupportWithMember> supportWithMemberList = supportBizService.listForProjectId(projectId, startTime, page);

        Integer representNum = distributorRelationService.countForEvent(eventId);
        Integer projectNum = projectService.countForEvent(eventId);

        Project project = projectService.get(projectId);
        Member author = memberService.get(project.getAuthorId());

        modelAndView.addObject("representNum", representNum);
        modelAndView.addObject("projectNum", projectNum);
        modelAndView.addObject("projectId", projectId);
        modelAndView.addObject("eventId", eventId);
        modelAndView.addObject("page", page);
        modelAndView.addObject("list", supportWithMemberList);
        modelAndView.addObject("name", author.getRealname());
        modelAndView.addObject("startTime", startTime);
        return modelAndView;
    }

    /**
     * 导出众筹项目的支持
     * @param projectId 项目编号
     * @param response 响应参数
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "event/listForProjectIdExport")
    public String listEventForProjectIdExport(String projectId, HttpServletResponse response){
        Project project = projectService.get(projectId);
        Member author = memberService.get(project.getAuthorId());
        String fileName = author.getRealname() + "的众筹的支持人" + DateUtils.todayDate() + ".xlsx";
        List<SupportWithMember> supportWithMemberList = supportBizService.listForProjectId(projectId, null, null);
        try {
            new ExportExcel(fileName, SupportWithMember.class).setDataList(supportWithMemberList).write(response, fileName).dispose();
            return null;
        } catch (IOException e) {
            logger.error("导出支持人异常", e);
        }
        return null;
    }


    /**
     * 众筹项目查询支持
     * @param projectId 众筹编号
     * @param page 分页参数
     * @return 支持列表
     */
    @RequestMapping(value = "event/listForRepresent")
    public ModelAndView listEventForRepresent( String projectId, String eventId, Page page){
        page.setLimit(20);
        ModelAndView modelAndView = new ModelAndView("crowdfund/supportForRepresentList");
        Project project = projectService.get(projectId);
        Member author = memberService.get(project.getAuthorId());
        List<SupportWithMember> supportWithMemberList = supportBizService.listForProjectId(projectId, null, page);
        Integer representNum = distributorRelationService.countForEvent(eventId);
        Integer projectNum = projectService.countForEvent(eventId);

        modelAndView.addObject("projectId", projectId);
        modelAndView.addObject("representNum", representNum);
        modelAndView.addObject("projectNum", projectNum);
        modelAndView.addObject("eventId", eventId);
        modelAndView.addObject("page", page);
        modelAndView.addObject("list", supportWithMemberList);
        modelAndView.addObject("name", author.getRealname());
        return modelAndView;
    }

    /**
     * 导出众筹项目的支持
     * @param projectId 项目编号
     * @param response 响应参数
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "event/listForRepresentExport")
    public String listEventForRepresentExport(String projectId, HttpServletResponse response){
        Project project = projectService.get(projectId);
        Member author = memberService.get(project.getAuthorId());
        String fileName = author.getRealname()+ "的众筹的支持人" + DateUtils.todayDate() + ".xlsx";
        List<SupportWithMember> supportWithMemberList = supportBizService.listForProjectId(projectId, null, null);
        try {
            new ExportExcel(fileName, SupportWithMember.class).setDataList(supportWithMemberList).write(response, fileName).dispose();
            return null;
        } catch (IOException e) {
            logger.error("导出支持人异常", e);
        }
        return null;
    }


    /**
     * 支持退款
     * @param id 支持编号
     * @return 交互数据
     */
    @ResponseBody
    @RequestMapping(value = "refund")
    public AjaxResult refund(String id){
        AjaxResult ajaxResult = new AjaxResult();
        try {
            supportBizService.sendRefund(id);
        } catch (BusinessException e) {
            ajaxResult.setDescription(e.getMessage());
            return ajaxResult;
        }
        ajaxResult.setSuccess(true);
        return ajaxResult;
    }

    /**
     * 统计图表
     * @param projectId 项目编号
     * @return 输出视图
     */
    @ResponseBody
    @RequestMapping(value = "chart")
    public AjaxResult chart(String projectId){
        AjaxResult ajaxResult = new AjaxResult();
        SupportCountOutput supportCountOutput = supportBizService.chart(projectId);
        ajaxResult.setSuccess(true);
        ajaxResult.setData(supportCountOutput);
        return ajaxResult;
    }

    /**
     * 统计图表视图
     * @param projectId 项目编号
     * @return 输出视图
     */
    @RequestMapping(value = "chartView")
    public ModelAndView chartView(String projectId, String targetId){
        ModelAndView modelAndView = new ModelAndView("crowdfund/supportChart");
        modelAndView.addObject("projectId", projectId);
        modelAndView.addObject("targetId", targetId);
        return modelAndView;
    }
}
