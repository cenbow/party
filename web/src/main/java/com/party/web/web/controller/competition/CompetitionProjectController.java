package com.party.web.web.controller.competition;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.party.common.paging.Page;
import com.party.common.utils.DateUtils;
import com.party.common.utils.LangUtils;
import com.party.common.utils.StringUtils;
import com.party.core.model.BaseModel;
import com.party.core.model.competition.CompetitionGroup;
import com.party.core.model.competition.CompetitionMember;
import com.party.core.model.competition.CompetitionProject;
import com.party.core.model.competition.CompetitionSchedule;
import com.party.core.service.competition.ICompetitionGroupService;
import com.party.core.service.competition.ICompetitionMemberService;
import com.party.core.service.competition.ICompetitionProjectService;
import com.party.core.service.competition.ICompetitionScheduleService;
import com.party.web.biz.file.FileBizService;
import com.party.web.utils.MyBeanUtils;
import com.party.web.utils.RealmUtils;
import com.party.web.web.dto.AjaxResult;
import com.party.web.web.dto.input.common.CommonInput;
import com.party.web.web.dto.output.competition.CompetitionProjectOutput;

/**
 * 赛事项目
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/competition/project")
public class CompetitionProjectController {

	@Autowired
	private ICompetitionProjectService competitionProjectService;
	@Autowired
	private ICompetitionScheduleService competitionScheduleService;
	@Autowired
	private ICompetitionMemberService competitionMemberService;
	@Autowired
	private ICompetitionGroupService competitionGroupService;
	@Autowired
	private FileBizService fileBizService;

	/**
	 * 列表
	 * 
	 * @param page
	 * @return
	 */
	@RequestMapping("list")
	@RequiresPermissions("competition:project:list")
	public ModelAndView list(Page page, CompetitionProject project, CommonInput commonInput) {
		page.setLimit(15);
		project.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
		project.setCreateBy(RealmUtils.getCurrentUser().getId());
		ModelAndView mv = new ModelAndView("competition/projectList");
		Map<String, Object> params = CommonInput.appendParams(commonInput);
		List<CompetitionProject> competitionProjects = competitionProjectService.webListPage(project, params, page);
		List<CompetitionProjectOutput> projectOutputs = LangUtils.transform(competitionProjects, input -> {
			CompetitionProjectOutput output = CompetitionProjectOutput.transform(input);
				// 总人数
				int totalNum = competitionMemberService.listPage(new CompetitionMember(input.getId()), null).size();
				output.setTotalNum(totalNum);
				// 小组数量
				int groupNum = competitionGroupService.listPage(new CompetitionGroup(input.getId()), null).size();
				output.setGroupNum(groupNum);
				// 日程数量
				int scheduleNum = competitionScheduleService.listPage(new CompetitionSchedule(input.getId()), null).size();
				output.setScheduleNum(scheduleNum);
				// 项目详情二维码
				String path = RealmUtils.getCurrentUser().getId() + "/competition/all/";
				String content = "competition/all_person_rank.html?projectId=" + input.getId();
				String baseQrCodeUrl = fileBizService.getFileEntity(input.getId(), path, content);
				output.setQrCodeUrl(baseQrCodeUrl);
				return output;
			});

		mv.addObject("projectList", projectOutputs);
		mv.addObject("input", commonInput);
		mv.addObject("project", project);
		mv.addObject("page", page);
		return mv;
	}

	/**
	 * 新增/编辑表单
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("toForm")
	public ModelAndView toForm(String id) {
		ModelAndView mv = new ModelAndView("competition/projectForm");
		if (StringUtils.isNotEmpty(id)) {
			CompetitionProject project = competitionProjectService.get(id);
			mv.addObject("project", project);
		}
		return mv;
	}

	/**
	 * 保存
	 * 
	 * @param project
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public AjaxResult save(String startTimeStr, CompetitionProject project, BindingResult result) {
		// 数据验证
		if (result.hasErrors()) {
			List<ObjectError> allErrors = result.getAllErrors();
			String description = allErrors.get(0).getDefaultMessage();
			return AjaxResult.error(description);
		}

		try {
			if (StringUtils.isNotEmpty(startTimeStr)) {
				Date startTime = DateUtils.parse(startTimeStr, "yyyy-MM-dd HH:mm");
				project.setStartTime(startTime);
			}
			
			if (StringUtils.isEmpty(project.getId())) {
				project.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
				String memberId = RealmUtils.getCurrentUser().getId();
				project.setCreateBy(memberId);
				project.setUpdateBy(memberId);
				competitionProjectService.insert(project);
			} else {
				CompetitionProject t = competitionProjectService.get(project.getId());
				MyBeanUtils.copyBeanNotNull2Bean(project, t);
				competitionProjectService.update(t);
			}
			return AjaxResult.success();
		} catch (Exception e) {
			return AjaxResult.error(e.getMessage());
		}
	}

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("delete")
	public AjaxResult delete(String id) {
		List<CompetitionSchedule> schedules = competitionScheduleService.findByProject(id);
		List<CompetitionMember> members = competitionMemberService.list(new CompetitionMember(id));
		List<CompetitionGroup> groups = competitionGroupService.list(new CompetitionGroup(id));
		if (schedules.size() > 0) {
			return AjaxResult.error("该项目下已有赛事日程，不能删除");
		} else if (members.size() > 0) {
			return AjaxResult.error("该项目下已有人员，不能删除");
		} else if (groups.size() > 0) {
			return AjaxResult.error("该项目下已有小组，不能删除");
		} else {
			competitionProjectService.delete(id);
		}
		return AjaxResult.success();
	}
}
