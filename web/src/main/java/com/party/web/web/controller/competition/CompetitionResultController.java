package com.party.web.web.controller.competition;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.party.common.paging.Page;
import com.party.common.utils.BigDecimalUtils;
import com.party.common.utils.StringUtils;
import com.party.core.model.competition.CompetitionGroup;
import com.party.core.model.competition.CompetitionMember;
import com.party.core.model.competition.CompetitionProject;
import com.party.core.model.competition.CompetitionResult;
import com.party.core.model.competition.CompetitionSchedule;
import com.party.core.model.member.Member;
import com.party.core.service.competition.ICompetitionGroupService;
import com.party.core.service.competition.ICompetitionMemberService;
import com.party.core.service.competition.ICompetitionProjectService;
import com.party.core.service.competition.ICompetitionResultService;
import com.party.core.service.competition.ICompetitionScheduleService;
import com.party.core.service.competition.biz.CompetitionResultBizService;
import com.party.core.service.member.IMemberService;
import com.party.core.utils.MyBeanUtils;
import com.party.web.utils.RealmUtils;
import com.party.web.utils.excel.ExportExcel;
import com.party.web.web.dto.AjaxResult;
import com.party.web.web.dto.input.competition.RankInput;
import com.party.web.web.dto.output.competition.CGroupResultExportOutput;
import com.party.web.web.dto.output.competition.CPersonResultExportOutput;
import com.party.web.web.dto.output.competition.CompetitionMemberOutput;

/**
 * 参赛人员成绩
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/competition/result")
public class CompetitionResultController {
	@Autowired
	private IMemberService memberService;
	@Autowired
	private ICompetitionScheduleService competitionScheduleService;
	@Autowired
	private ICompetitionMemberService competitionMemberService;
	@Autowired
	private ICompetitionResultService competitionResultService;
	@Autowired
	private ICompetitionGroupService competitionGroupService;
	@Autowired
	private CompetitionResultBizService competitionResultBizService;
	@Autowired
	private ICompetitionProjectService competitionProjectService;

	/**
	 * 增加成绩
	 * 
	 * @param memberId
	 * @param projectId
	 * @return
	 */
	@RequestMapping("toForm")
	public ModelAndView toForm(String memberId, String projectId) {
		ModelAndView mv = new ModelAndView("competition/resultForm");

		CompetitionMember competitionMember = competitionMemberService.get(memberId);
		CompetitionMemberOutput output = CompetitionMemberOutput.transform(competitionMember);
		// 用户
		if (StringUtils.isNotEmpty(competitionMember.getMemberId())) {
			Member member = memberService.get(competitionMember.getMemberId());
			output.setMember(member);
		}
		output.setProjectId(projectId);
		if (StringUtils.isNotEmpty(competitionMember.getGroupId())) {
			CompetitionGroup group = competitionGroupService.get(competitionMember.getGroupId());
			output.setGroupName(group.getGroupName());
		}
		mv.addObject("competitionMember", output);

		// 成绩
		CompetitionResult result = new CompetitionResult();
		result.setMemberId(memberId);
		result.setProjectId(projectId);
		List<CompetitionResult> results = competitionResultService.listPage(result, null);
		mv.addObject("results", results);

		// 赛程
		CompetitionSchedule t = new CompetitionSchedule();
		t.setProjectId(projectId);
		List<CompetitionSchedule> schedules = competitionScheduleService.listPage(t, null);
		mv.addObject("schedules", schedules);
		return mv;
	}

	/**
	 * 查看成绩
	 * 
	 * @param memberId
	 * @param projectId
	 * @return
	 */
	@RequestMapping("resultView")
	public ModelAndView resultView(String memberId, String projectId) {
		ModelAndView mv = new ModelAndView("competition/resultView");

		CompetitionMember competitionMember = competitionMemberService.get(memberId);
		CompetitionMemberOutput memberOutput = CompetitionMemberOutput.transform(competitionMember);
		// 用户
		if (StringUtils.isNotEmpty(competitionMember.getMemberId())) {
			Member member = memberService.get(competitionMember.getMemberId());
			memberOutput.setMember(member);
		}
		memberOutput.setProjectId(projectId);
		if (StringUtils.isNotEmpty(competitionMember.getGroupId())) {
			CompetitionGroup group = competitionGroupService.get(competitionMember.getGroupId());
			memberOutput.setGroupName(group.getGroupName());
		}
		mv.addObject("competitionMember", memberOutput);

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("isComplete", "1");
		List<CompetitionSchedule> schedules = competitionScheduleService.listPage(new CompetitionSchedule(projectId), null);

		List<Map<String, Object>> memberResults = competitionResultBizService.getPersonResult(projectId, memberId, schedules);
		mv.addObject("results", memberResults);
		return mv;
	}

	/**
	 * 新增/编辑成绩
	 * 
	 * @param cMember
	 * @param bindingResult
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "saveResult", method = RequestMethod.POST)
	public AjaxResult saveResult(CompetitionMember cMember, BindingResult bindingResult) throws Exception {
		// 数据验证
		if (bindingResult.hasErrors()) {
			List<ObjectError> allErrors = bindingResult.getAllErrors();
			String description = allErrors.get(0).getDefaultMessage();
			return AjaxResult.error(description);
		}
		List<CompetitionResult> results = cMember.getResults();
		for (CompetitionResult cr : results) {
			// isComplete为1表示已完赛
			if (null != cr.getIsComplete()) {
				if (cr.getIsComplete().equals(1)) {
					if (StringUtils.isEmpty(cr.getHours()) && StringUtils.isEmpty(cr.getMinutes()) && StringUtils.isEmpty(cr.getSeconds())) {
						return AjaxResult.error("已完赛，用时不能为空");
					}
				}
			} else if (null == cr.getIsComplete()
					&& !(StringUtils.isEmpty(cr.getHours()) && StringUtils.isEmpty(cr.getMinutes()) && StringUtils.isEmpty(cr.getSeconds()))) {
				return AjaxResult.error("参赛状态不能为空");
			} else if (null == cr.getIsComplete() && StringUtils.isEmpty(cr.getHours()) && StringUtils.isEmpty(cr.getMinutes())
					&& StringUtils.isEmpty(cr.getSeconds())) {
				continue;
			}
			
			Double total = 0.0;
			if (StringUtils.isNotEmpty(cr.getHours())) {
				Double hours = BigDecimalUtils.mul(Double.valueOf(cr.getHours()), 3600);
				total = BigDecimalUtils.add(hours, total);
			}
			if (StringUtils.isNotEmpty(cr.getMinutes())) {
				Double minutes = BigDecimalUtils.mul(Double.valueOf(cr.getMinutes()), 60);
				total = BigDecimalUtils.add(minutes, total);
			}
			if (StringUtils.isNotEmpty(cr.getSeconds())) {
				Double seconds = Double.valueOf(cr.getSeconds());
				total = BigDecimalUtils.add(seconds, total);
			}
			cr.setSecondsResult(total + "");

			if (StringUtils.isEmpty(cr.getId())) {
				CompetitionResult t = new CompetitionResult();
				MyBeanUtils.copyBeanNotNull2Bean(cr, t);
				t.setMemberId(cMember.getId());
				t.setProjectId(cMember.getProjectId());
				String currentId = RealmUtils.getCurrentUser().getId();
				t.setCreateBy(currentId);
				t.setUpdateBy(currentId);

				competitionResultService.insert(t);
			} else {
				CompetitionResult t = competitionResultService.get(cr.getId());
				MyBeanUtils.copyBeanNotNull2Bean(cr, t);
				competitionResultService.update(t);
			}
		}

		// 更新人员 小组总成绩
		if (StringUtils.isNotEmpty(cMember.getId())) {
			String groupId = competitionResultBizService.updateMemberResult(cMember.getId());
			competitionResultBizService.updateGroupResult(groupId);
		}
		return AjaxResult.success();
	}

	/**
	 * 删除成绩
	 * 
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "deleteResult", method = RequestMethod.POST)
	public AjaxResult deleteResult(String id) {
		CompetitionResult cResult = competitionResultService.get(id);

		if (StringUtils.isEmpty(id)) {
			return AjaxResult.error("id不能为空");
		}
		competitionResultService.delete(id);

		// 更新人员 小组总成绩
		if (StringUtils.isNotEmpty(cResult.getMemberId())) {
			String groupId = competitionResultBizService.updateMemberResult(cResult.getMemberId());
			competitionResultBizService.updateGroupResult(groupId);
		}
		return AjaxResult.success();
	}

	/**
	 * 人员成绩排行
	 * 
	 * @param projectId
	 * @param groupId
	 * @param scheduleId
	 * @param searchName
	 * @param page
	 * @return
	 */
	@RequestMapping("personalRank")
	public ModelAndView personalRank(RankInput rankInput, Page page) {
		page.setLimit(20);
		ModelAndView mv = new ModelAndView("competition/personalRank");
		// 项目下所有参赛人员
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("projectId", rankInput.getProjectId());
		params.put("scheduleId", rankInput.getScheduleId());
		params.put("groupId", rankInput.getGroupId());
		params.put("isComplete", "1");
		params.put("fullName", rankInput.getFullName());
		params.put("numberPai", rankInput.getNumberPai());
		List<Map<String, Object>> memberResults = competitionResultService.getAllResult(params, page);
		competitionResultBizService.getMemberResultRank(memberResults, rankInput.getProjectId(), rankInput.getScheduleId());
		for (Map<String, Object> map : memberResults) {
			String memberId = map.get("memberId2").toString();
			Member member = memberService.get(memberId);
			map.put("member", member);
		}
		mv.addObject("memberResults", memberResults);

		commonCode(mv, rankInput);

		mv.addObject("rankInput", rankInput);
		mv.addObject("page", page);
		return mv;
	}

	private void commonCode(ModelAndView mv, RankInput rankInput) {
		CompetitionProject project = competitionProjectService.get(rankInput.getProjectId());
		mv.addObject("project", project);

		List<CompetitionSchedule> schedules = competitionScheduleService.list(new CompetitionSchedule(rankInput.getProjectId()));
		mv.addObject("schedules", schedules);

		List<CompetitionGroup> groups = competitionGroupService.list(new CompetitionGroup(rankInput.getProjectId()));
		mv.addObject("groups", groups);

		if (StringUtils.isNotEmpty(rankInput.getGroupId())) {
			CompetitionGroup group = competitionGroupService.get(rankInput.getGroupId());
			mv.addObject("group", group);
		}
	}

	/**
	 * 小组所有成员排行
	 * 
	 * @param rankInput
	 * @param page
	 * @return
	 */
	@RequestMapping("groupPersonalRank")
	public ModelAndView groupPersonalRank(RankInput rankInput, Page page) {
		page.setLimit(20);
		ModelAndView mv = new ModelAndView("competition/groupPersonalRank");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("projectId", rankInput.getProjectId());
		params.put("scheduleId", rankInput.getScheduleId());
		params.put("groupId", rankInput.getGroupId());
		params.put("fullName", rankInput.getFullName());
		params.put("isComplete", "1");
		params.put("numberPai", rankInput.getNumberPai());
		List<Map<String, Object>> memberResults = competitionResultService.getGroupAllPersonResult(params, page);
		competitionResultBizService.getMemberResultRank(memberResults, rankInput.getProjectId(), rankInput.getScheduleId());
		for (Map<String, Object> map : memberResults) {
			String memberId = map.get("memberId2").toString();
			Member member = memberService.get(memberId);
			map.put("member", member);
		}
		mv.addObject("memberResults", memberResults);

		commonCode(mv, rankInput);

		mv.addObject("rankInput", rankInput);
		mv.addObject("page", page);
		return mv;
	}

	/**
	 * 小组成绩排行
	 * 
	 * @param rankInput
	 * @param page
	 * @return
	 */
	@RequestMapping("groupRank")
	public ModelAndView groupRank(RankInput rankInput, Page page) {
		page.setLimit(20);
		ModelAndView mv = new ModelAndView("competition/groupRank");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("projectId", rankInput.getProjectId());
		params.put("scheduleId", rankInput.getScheduleId());
		params.put("groupId", rankInput.getGroupId());
		params.put("isComplete", "1");
		params.put("groupName", rankInput.getGroupName());
		List<Map<String, Object>> memberResults = competitionResultService.getByGroup(params, page);
		competitionResultBizService.getGroupResultRank(memberResults, rankInput.getProjectId(), rankInput.getScheduleId());
		mv.addObject("memberResults", memberResults);

		CompetitionProject project = competitionProjectService.get(rankInput.getProjectId());
		mv.addObject("project", project);

		List<CompetitionSchedule> schedules = competitionScheduleService.list(new CompetitionSchedule(rankInput.getProjectId()));
		mv.addObject("schedules", schedules);

		List<CompetitionGroup> groups = competitionGroupService.list(new CompetitionGroup(rankInput.getProjectId()));
		mv.addObject("groups", groups);

		mv.addObject("rankInput", rankInput);
		mv.addObject("page", page);
		return mv;
	}

	/**
	 * 小组成绩导出
	 * 
	 * @param rankInput
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "exportAllGroupResult", method = RequestMethod.POST)
	public AjaxResult exportAllGroupResult(RankInput rankInput, HttpServletResponse response) throws IOException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String fileName = "小组排行" + sdf.format(new Date()) + ".xlsx";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("projectId", rankInput.getProjectId());
		params.put("scheduleId", rankInput.getScheduleId());
		params.put("groupId", rankInput.getGroupId());
		params.put("fullName", rankInput.getFullName());
		params.put("numberPai", rankInput.getNumberPai());
		params.put("isComplete", "1");
		List<Map<String, Object>> memberResults = competitionResultService.getByGroup(params, null);
		competitionResultBizService.getMemberResultRank(memberResults, rankInput.getProjectId(), rankInput.getScheduleId());

		String playDay = "";
		if (StringUtils.isNotEmpty(rankInput.getScheduleId())) {
			CompetitionSchedule schedule = competitionScheduleService.get(rankInput.getScheduleId());
			playDay = DateUtil.formatDate(schedule.getPlayDay(), "yyyy年MM月dd日");
		}

		DecimalFormat df=(DecimalFormat)NumberFormat.getInstance();
		df.applyPattern("#");
		List<CGroupResultExportOutput> outputs = new LinkedList<CGroupResultExportOutput>();
		for (Map<String, Object> map : memberResults) {
			CGroupResultExportOutput output = new CGroupResultExportOutput();
			output.setPlayDay(playDay);
			output.setRankNo(df.format(Double.valueOf(map.get("rowno").toString())));
			if (map.get("distance") != null) {
				output.setDistance(map.get("distance").toString());
			} else {
				output.setDistance("0");
			}
			if (map.get("secondsResult") != null) {
				String result = competitionResultBizService.getResult(Double.valueOf(map.get("secondsResult").toString()));
				output.setSecondsResult(result);
			} else {
				output.setSecondsResult("00:00:00");
			}
			if (map.get("groupName") != null) {
				output.setGroupName(map.get("groupName").toString());
			}
			outputs.add(output);
		}

		new ExportExcel("", CGroupResultExportOutput.class).setDataList(outputs).write(response, fileName).dispose();

		return null;
	}

	/**
	 * 小组人员排行
	 * 
	 * @param rankInput
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "exportGroupMemberResult", method = RequestMethod.POST)
	public AjaxResult exportGroupMemberResult(RankInput rankInput, HttpServletResponse response) throws IOException {
		CompetitionGroup group = competitionGroupService.get(rankInput.getGroupId());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String fileName = group.getGroupName() + "的小组成员成绩排行" + sdf.format(new Date()) + ".xlsx";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("projectId", rankInput.getProjectId());
		params.put("scheduleId", rankInput.getScheduleId());
		params.put("groupId", rankInput.getGroupId());
		params.put("fullName", rankInput.getFullName());
		params.put("numberPai", rankInput.getNumberPai());
		params.put("isComplete", "1");
		List<Map<String, Object>> memberResults = competitionResultService.getGroupAllPersonResult(params, null);
		competitionResultBizService.getMemberResultRank(memberResults, rankInput.getProjectId(), rankInput.getScheduleId());

		List<CPersonResultExportOutput> outputs = memberExport(rankInput, memberResults);

		new ExportExcel("", CPersonResultExportOutput.class).setDataList(outputs).write(response, fileName).dispose();

		return null;
	}

	private List<CPersonResultExportOutput> memberExport(RankInput rankInput, List<Map<String, Object>> memberResults) {
		String playDay = "";
		if (StringUtils.isNotEmpty(rankInput.getScheduleId())) {
			CompetitionSchedule schedule = competitionScheduleService.get(rankInput.getScheduleId());
			playDay = DateUtil.formatDate(schedule.getPlayDay(), "yyyy年MM月dd日");
		}

		String groupName = "";
		if (StringUtils.isNotEmpty(rankInput.getGroupId())) {
			CompetitionGroup group = competitionGroupService.get(rankInput.getGroupId());
			groupName = group.getGroupName();
		}

		DecimalFormat df=(DecimalFormat)NumberFormat.getInstance();
		df.applyPattern("#");
		List<CPersonResultExportOutput> outputs = new LinkedList<CPersonResultExportOutput>();
		for (Map<String, Object> map : memberResults) {
			CPersonResultExportOutput output = new CPersonResultExportOutput();
			output.setPlayDay(playDay);
			output.setRankNo(df.format(Double.valueOf(map.get("rowno").toString())));
			if (map.get("distance") != null) {
				output.setDistance(map.get("distance").toString());
			} else {
				output.setDistance("0");
			}
			if (map.get("secondsResult") != null) {
				String result = competitionResultBizService.getResult(Double.valueOf(map.get("secondsResult").toString()));
				output.setSecondsResult(result);
			} else {
				output.setSecondsResult("00:00:00");
			}
			if (map.get("groupName") != null) {
				output.setGroupName(map.get("groupName").toString());
			} else if (StringUtils.isNotEmpty(groupName)) {
				output.setGroupName(groupName);
			}
			if (map.get("numberPai") != null) {
				output.setNumberPai(map.get("numberPai").toString());
			}

			if (map.get("fullName") != null) {
				output.setFullName(map.get("fullName").toString());
			} else if (map.get("realName") != null) {
				output.setFullName(map.get("realName").toString());
			}
			if (map.get("isComplete") != null) {
				String isComplete = map.get("isComplete").toString();
				output.setIsComplete(isComplete.equals("1") ? "完赛" : "未完赛");
			} else {
				output.setIsComplete("未完赛");
			}
			outputs.add(output);
		}
		return outputs;
	}

	/**
	 * 总排行导出
	 * 
	 * @param rankInput
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "exportAllMemberResult", method = RequestMethod.POST)
	public AjaxResult exportAllMemberResult(RankInput rankInput, HttpServletResponse response) throws IOException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String fileName = "总排行" + sdf.format(new Date()) + ".xlsx";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("projectId", rankInput.getProjectId());
		params.put("scheduleId", rankInput.getScheduleId());
		params.put("groupId", rankInput.getGroupId());
		params.put("fullName", rankInput.getFullName());
		params.put("numberPai", rankInput.getNumberPai());
		params.put("isComplete", "1");
		List<Map<String, Object>> memberResults = competitionResultService.getAllResult(params, null);
		competitionResultBizService.getMemberResultRank(memberResults, rankInput.getProjectId(), rankInput.getScheduleId());

		List<CPersonResultExportOutput> outputs = memberExport(rankInput, memberResults);

		new ExportExcel("", CPersonResultExportOutput.class).setDataList(outputs).write(response, fileName).dispose();

		return null;
	}
}
