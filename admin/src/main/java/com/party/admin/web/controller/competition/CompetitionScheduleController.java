package com.party.admin.web.controller.competition;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.party.admin.utils.MyBeanUtils;
import com.party.admin.utils.RealmUtils;
import com.party.admin.web.dto.AjaxResult;
import com.party.admin.web.dto.input.common.CommonInput;
import com.party.common.paging.Page;
import com.party.common.utils.DateUtils;
import com.party.common.utils.StringUtils;
import com.party.core.model.BaseModel;
import com.party.core.model.competition.CompetitionProject;
import com.party.core.model.competition.CompetitionResult;
import com.party.core.model.competition.CompetitionSchedule;
import com.party.core.service.competition.ICompetitionProjectService;
import com.party.core.service.competition.ICompetitionResultService;
import com.party.core.service.competition.ICompetitionScheduleService;
import com.party.core.service.competition.biz.CompetitionResultBizService;

/**
 * 赛事日程
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/competition/schedule")
public class CompetitionScheduleController {
	@Autowired
	ICompetitionScheduleService competitionScheduleService;
	@Autowired
	ICompetitionProjectService competitionProjectService;
	@Autowired
	ICompetitionResultService competitionResultService;
	@Autowired
	private CompetitionResultBizService competitionResultBizService;

	/**
	 * 列表
	 * 
	 * @param page
	 * @return
	 */
	@RequestMapping("list")
	public ModelAndView list(Page page, CompetitionSchedule schedule, CommonInput commonInput) {
		ModelAndView mv = new ModelAndView("competition/scheduleList");
		if (StringUtils.isNotEmpty(schedule.getProjectId())) {
			CompetitionProject project = competitionProjectService.get(schedule.getProjectId());
			mv.addObject("project", project);
		}

		page.setLimit(15);
		schedule.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
		Map<String, Object> params = CommonInput.appendParams(commonInput);
		List<CompetitionSchedule> competitionSchedules = competitionScheduleService.webListPage(schedule, params, page);
		mv.addObject("scheduleList", competitionSchedules);
		mv.addObject("input", commonInput);
		mv.addObject("schedule", schedule);
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
	public ModelAndView toForm(String id, String projectId) {
		ModelAndView mv = new ModelAndView("competition/scheduleForm");
		if (StringUtils.isNotEmpty(id)) {
			CompetitionSchedule schedule = competitionScheduleService.get(id);
			mv.addObject("schedule", schedule);
		}

		if (StringUtils.isNotEmpty(projectId)) {
			CompetitionProject project = competitionProjectService.get(projectId);
			mv.addObject("project", project);

			if (project.getStartTime() != null) {
				String startTime = DateUtils.formatDate(project.getStartTime());
				mv.addObject("startTime", startTime);
			}
		}
		return mv;
	}

	/**
	 * 保存
	 * 
	 * @param schedule
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public AjaxResult save(String playDayStr, CompetitionSchedule schedule, BindingResult result) {
		// 数据验证
		if (result.hasErrors()) {
			List<ObjectError> allErrors = result.getAllErrors();
			String description = allErrors.get(0).getDefaultMessage();
			return AjaxResult.error(description);
		}

		try {
			if (StringUtils.isNotEmpty(playDayStr)) {
				Date startTime = DateUtils.parse(playDayStr, "yyyy-MM-dd");
				schedule.setPlayDay(startTime);
			}
			
			if (StringUtils.isEmpty(schedule.getId())) {
				schedule.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
				String memberId = RealmUtils.getCurrentUser().getId();
				schedule.setCreateBy(memberId);
				schedule.setUpdateBy(memberId);
				competitionScheduleService.insert(schedule);
			} else {
				CompetitionSchedule t = competitionScheduleService.get(schedule.getId());
				MyBeanUtils.copyBeanNotNull2Bean(schedule, t);
				competitionScheduleService.update(t);
			}
			competitionResultBizService.updateScheduleAllMember(schedule.getId());
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
		List<CompetitionResult> results = competitionResultService.list(new CompetitionResult(id));
		if (results.size() > 0) {
			return AjaxResult.error("该日程下已有添加成绩，不能删除");
		}
		competitionScheduleService.delete(id);
		return AjaxResult.success();
	}

	@ResponseBody
	@RequestMapping("checkPlayDay")
	public AjaxResult checkPlayDay(String playDayStr, String projectId, String id) throws ParseException {
		if (StringUtils.isEmpty(playDayStr)) {
			return AjaxResult.error("比赛日不能为空");
		}

		if (StringUtils.isEmpty(projectId)) {
			return AjaxResult.error("项目ID不能为空");
		}

		// 1. 比赛日不能早于项目赛事时间
		// 2. 同一个项目比赛日不能重复

		CompetitionProject project = competitionProjectService.get(projectId);
		if (project.getStartTime() == null) {
			return AjaxResult.error("项目赛事时间不能为空");
		}

		List<CompetitionSchedule> schedules = competitionScheduleService.checkPlayDay(playDayStr, projectId, id);
		if (schedules.size() > 0) {
			return AjaxResult.error("项目中该比赛日已存在，请重新选择");
		}

		Date playDay = DateUtils.parse(playDayStr, "yyyy-MM-dd");
		String startTimeStr = DateUtils.formatDate(project.getStartTime(), "yyyy-MM-dd");
		Date startDay = DateUtils.parse(startTimeStr, "yyyy-MM-dd");
		if (playDay.before(startDay)) {
			return AjaxResult.error("赛程比赛日不能早于项目赛事时间");
		}

		return AjaxResult.success();
	}
}
