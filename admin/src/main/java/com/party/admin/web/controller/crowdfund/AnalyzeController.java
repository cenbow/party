package com.party.admin.web.controller.crowdfund;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Sets;
import com.party.admin.biz.crowdfund.AnalyzeBizService;
import com.party.admin.utils.excel.ExportExcel;
import com.party.admin.web.dto.AjaxResult;
import com.party.admin.web.dto.output.crowdfund.AnalyzeOutput;
import com.party.admin.web.dto.output.crowdfund.ProjectForActivityOutput;
import com.party.admin.web.dto.output.crowdfund.TypeCountOutput;
import com.party.common.paging.Page;
import com.party.common.utils.DateUtils;
import com.party.core.model.activity.Activity;
import com.party.core.model.crowdfund.*;
import com.party.core.model.label.Label;
import com.party.core.service.activity.IActivityService;
import com.party.core.service.crowdfund.IAnalyzeService;
import com.party.core.service.crowdfund.ICrowdfundEventService;
import com.party.core.service.crowdfund.IProjectLabelService;
import com.party.core.service.crowdfund.IProjectService;
import com.party.core.service.label.ILabelService;
import com.party.core.service.notify.IEventChannelService;
import com.party.core.service.notify.IEventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 众筹分析控制器
 * Created by wei.li
 *
 * @date 2017/7/10 0010
 * @time 14:31
 */

@Controller
@RequestMapping(value = "/crowdfund/analyze")
public class AnalyzeController {

    @Autowired
    private AnalyzeBizService analyzeBizService;

    @Autowired
    private IProjectService projectService;

    @Autowired
    private IAnalyzeService analyzeService;

    @Autowired
    private IProjectLabelService projectLabelService;

    @Autowired
    private ILabelService labelService;

    @Autowired
    private IActivityService activityService;

    @Autowired
    private ICrowdfundEventService crowdfundEventService;


    protected static Logger logger = LoggerFactory.getLogger(AnalyzeController.class);
    /**
     * 众筹分析列表
     * @param projectAnalyze 众筹分析
     * @param page 分页参数
     * @return 交互数据
     */
    @RequestMapping(value = "list")
    public ModelAndView list(ProjectAnalyze projectAnalyze, Page page){
        page.setLimit(15);
        ModelAndView modelAndView = new ModelAndView("crowdfund/analyzeList");
        List<AnalyzeOutput> outputList = analyzeBizService.list(projectAnalyze, page);
        List<String> dateList = analyzeBizService.dateStringList();
        List<Label> labelList = labelService.list(new Label());
        TypeCountOutput countOutput = analyzeBizService.countList(projectAnalyze);
        modelAndView.addObject("list", outputList);
        modelAndView.addObject("dateList", dateList);
        modelAndView.addObject("labelList", labelList);
        modelAndView.addObject("page", page);
        modelAndView.addObject("count", countOutput);
        return modelAndView;
    }


    /**
     * 数据导出
     * @param projectAnalyze 众筹分析
     * @param response 导出数据
     * @return 交互
     */
    @ResponseBody
    @RequestMapping(value = "listExport")
    public String listExport(ProjectAnalyze projectAnalyze, HttpServletResponse response){
        String fileName =  DateUtils.todayDate() + ".xlsx";
        if (!Strings.isNullOrEmpty(projectAnalyze.getTargetId())){
            Activity activity = activityService.get(projectAnalyze.getTargetId());
            fileName = activity.getTitle() + DateUtils.todayDate() + ".xlsx";
        }
        else if (!Strings.isNullOrEmpty(projectAnalyze.getEventId())){
            CrowdfundEvent crowdfundEvent = crowdfundEventService.get(projectAnalyze.getEventId());
            fileName = crowdfundEvent.getName() + DateUtils.todayDate() + ".xlsx";
        }

        List<AnalyzeOutput> outputList = analyzeBizService.list(projectAnalyze, null);
        try {
            analyzeBizService.export(fileName, outputList, response);
            return null;
        } catch (IOException e) {
            logger.error("导出代言的众筹异常", e);
        }
        return null;
    }


    /**
     * 修改朋友状态
     * @param  projectId 项目编号
     * @param status 状态
     * @return 交互数据
     */
    @ResponseBody
    @RequestMapping(value = "changeFriend")
    public AjaxResult changeFriend(String projectId, Integer status){
        AjaxResult ajaxResult = new AjaxResult();
        Analyze analyze = analyzeService.findByTargetId(projectId);
        analyze.setIsFriend(status);
        analyzeService.update(analyze);
        ajaxResult.setSuccess(true);
        return ajaxResult;
    }


    /**
     * 修改是否进群
     * @param projectId 项目编号
     * @param status 状态
     * @return 交互数据
     */
    @ResponseBody
    @RequestMapping(value = "changeGroup")
    public AjaxResult changeGroup(String projectId, Integer status){
        AjaxResult ajaxResult = new AjaxResult();
        Analyze analyze = analyzeService.findByTargetId(projectId);
        analyze.setIsGroup(status);
        analyzeService.update(analyze);
        ajaxResult.setSuccess(true);
        return ajaxResult;
    }


    /**
     * 我的标签列表
     * @param projectId 项目编号
     * @return 交互数据
     */
    @RequestMapping(value = "labelView")
    public ModelAndView labelView(String projectId){
        ModelAndView modelAndView = new ModelAndView("crowdfund/distributionLabel");
        List<ProjectLabel> myLabelList = projectLabelService.findByProjectId(projectId);
        List<Label> labelList = labelService.list(new Label());
        modelAndView.addObject("myLabelList", myLabelList);
        modelAndView.addObject("labelList", labelList);
        modelAndView.addObject("projectId", projectId);
        return modelAndView;
    }


    /**
     * 保存标签
     * @param id 标签集合
     * @param projectId 项目编号
     * @return 交互数据
     */
    @ResponseBody
    @RequestMapping(value = "labelSave")
    public AjaxResult labelSave(String id, String projectId){
        AjaxResult ajaxResult = new AjaxResult();
        /*Set<String> idSet = Sets.newHashSet(Splitter.on(",").split(ids));*/
        try {
            analyzeBizService.labelSave(id, projectId);
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            return ajaxResult;
        }
        ajaxResult.setSuccess(true);
        return ajaxResult;
    }


    @ResponseBody
    @RequestMapping(value = "init")
    public AjaxResult init(){
        List<Project> projectList = projectService.list(new Project());
        for (Project project : projectList){
            Analyze analyze = new Analyze();
            analyze.setTargetId(project.getId());
            analyzeService.insert(analyze);
        }
        return AjaxResult.success();
    }
}
