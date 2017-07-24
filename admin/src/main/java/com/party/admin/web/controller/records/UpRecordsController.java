package com.party.admin.web.controller.records;

import com.google.common.base.Strings;
import com.party.admin.web.dto.AjaxResult;
import com.party.common.paging.Page;
import com.party.core.model.crowdfund.Project;
import com.party.core.model.member.Member;
import com.party.core.model.records.UpRecordWithProject;
import com.party.core.model.records.UpRecords;
import com.party.core.service.crowdfund.IProjectService;
import com.party.core.service.member.IMemberService;
import com.party.core.service.records.IUpRecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * 跟进记录控制器
 * Created by wei.li
 *
 * @date 2017/7/12 0012
 * @time 10:20
 */

@Controller
@RequestMapping(value = "records/records")
public class UpRecordsController {

    @Autowired
    private IUpRecordsService upRecordsService;

    @Autowired
    private IProjectService projectService;

    @Autowired
    private IMemberService memberService;


    /**
     * 跟进记录列表
     * @param upRecordWithProject 跟进记录
     * @param page 分页参数
     * @return 交互数据
     */
    @RequestMapping(value = "list")
    public ModelAndView list(UpRecordWithProject upRecordWithProject,String activityId, String eventId, Page page){
        ModelAndView modelAndView = new ModelAndView("records/list");
        List<UpRecordWithProject> recordList = upRecordsService.listWithProject(upRecordWithProject, page);

        if (!Strings.isNullOrEmpty(activityId)){
            modelAndView.addObject("targetId", activityId);
        }
        modelAndView.addObject("eventId", eventId);
        modelAndView.addObject("recordList", recordList);
        modelAndView.addObject("page", page);
        return modelAndView;
    }

    /**
     * 跟进记录视图
     * @param id 记录编号
     * @return 交互数据
     */
    @RequestMapping(value = "view")
    public ModelAndView view(String id, String projectId, String activityId, String eventId){
        ModelAndView modelAndView = new ModelAndView("records/view");
        UpRecords upRecords;
        if (Strings.isNullOrEmpty(id)){
            upRecords = new UpRecords();
            upRecords.setTargetId(projectId);
        }
        else {
            upRecords = upRecordsService.get(id);
        }
        Project project = projectService.get(upRecords.getTargetId());
        Member member = memberService.get(project.getAuthorId());
        modelAndView.addObject("authorName", member.getRealname());
        modelAndView.addObject("upRecords", upRecords);
        modelAndView.addObject("activityId", activityId);
        modelAndView.addObject("eventId", eventId);
        return modelAndView;
    }

    /**
     * 保存记录信息
     * @param upRecords 跟进记录
     * @return 交互数据
     */
    @ResponseBody
    @RequestMapping(value = "save")
    public AjaxResult save(UpRecords upRecords){
        AjaxResult ajaxResult = new AjaxResult();
        if (Strings.isNullOrEmpty(upRecords.getId())){
            upRecordsService.insert(upRecords);
        }
        else {
            upRecordsService.update(upRecords);
        }
        ajaxResult.setSuccess(true);
        return ajaxResult;
    }

    /**
     * 删除沟通记录
     * @param id 编号
     * @return 交互数据
     */
    @ResponseBody
    @RequestMapping(value = "delete")
    public AjaxResult delete(String id){
        AjaxResult ajaxResult = new AjaxResult();
        upRecordsService.delete(id);
        ajaxResult.setSuccess(true);
        return ajaxResult;
    }
}
