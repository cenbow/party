package com.party.mobile.web.controller.competition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.party.common.paging.Page;
import com.party.common.utils.BigDecimalUtils;
import com.party.common.utils.PartyCode;
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
import com.party.mobile.biz.currentUser.CurrentUserBizService;
import com.party.mobile.web.dto.AjaxResult;
import com.party.mobile.web.dto.competition.output.CompetitionMemberOutput;
import com.party.mobile.web.dto.login.output.CurrentUser;

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
	private ICompetitionProjectService competitionProjectService;
	@Autowired
	private ICompetitionScheduleService competitionScheduleService;
	@Autowired
	private CurrentUserBizService currentUserBizService;
	@Autowired
	private CompetitionResultBizService competitionResultBizService;
	@Autowired
	private ICompetitionMemberService competitionMemberService;
	@Autowired
	private ICompetitionResultService competitionResultService;
	@Autowired
	private ICompetitionGroupService competitionGroupService;
	@Autowired
	private IMemberService memberService;

	/**
	 * 项目列表
	 * 
	 * @param project
	 * @param page
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/list")
	public AjaxResult list(CompetitionProject project, Page page, HttpServletRequest request) {
		CurrentUser curUser = currentUserBizService.getCurrentUser(request);
		project.setCreateBy(curUser.getId());
		List<CompetitionProject> projects = competitionProjectService.listPage(project, page);
		return AjaxResult.success(projects, page);
	}

	/**
	 * 个人的所有成绩
	 * 
	 * @param projectId
	 * @param cMemberId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getPersonalResult")
	public AjaxResult getPersonalResult(String projectId, String cMemberId, Page page) {
		page.setLimit(20);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("isComplete", "1");
		List<CompetitionSchedule> schedules = competitionScheduleService.listPage(new CompetitionSchedule(projectId), page);
		List<Map<String, Object>> memberResults = competitionResultBizService.getPersonResult(projectId, cMemberId, schedules);
		return AjaxResult.success(memberResults);
	}

	/**
	 * 个人成绩
	 * 
	 * @param projectId
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getPersonalDetail")
	public AjaxResult getPersonalDetail(String projectId, String memberId, HttpServletRequest request) {
		if (StringUtils.isEmpty(projectId)) {
			return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, "项目id不能为空");
		}
		if (StringUtils.isEmpty(memberId)) {
			CurrentUser currentUser = currentUserBizService.getCurrentUser(request);
			memberId = currentUser.getId();
		}
		CompetitionMember cMember = competitionMemberService.findByProjectAndMember(memberId, projectId);
		if (cMember == null) {
			return AjaxResult.error(PartyCode.DATA_UNEXIST, "您还没有参加比赛");
		}
		CompetitionMemberOutput memberOutput = new CompetitionMemberOutput();
		memberOutput.setNumber(cMember.getNumber());
		memberOutput.setCmemberId(cMember.getId());
		memberOutput.setMemberId(memberId);
		// 用户
		if (StringUtils.isNotEmpty(cMember.getMemberId())) {
			Member member = memberService.get(cMember.getMemberId());
			memberOutput.setFullName(member.getFullname());
			memberOutput.setRealName(member.getRealname());
		}
		memberOutput.setProjectId(projectId);
		if (StringUtils.isNotEmpty(cMember.getGroupId())) {
			CompetitionGroup group = competitionGroupService.get(cMember.getGroupId());
			memberOutput.setGroupName(group.getGroupName());
		}
		CompetitionProject cProject = competitionProjectService.get(projectId);
		if (cProject != null) {
			memberOutput.setProjectName(cProject.getTitle());
			memberOutput.setProjectRemarks(cProject.getRemarks());
			memberOutput.setProjectPicture(cProject.getPicture());
		}

		if (StringUtils.isNotEmpty(cMember.getTotalSecondsResult())) {
			CompetitionResult t = new CompetitionResult();
			t.setMemberId(cMember.getId());
			List<CompetitionResult> results = competitionResultService.list(t);
			if (results.size() > 0) {
				double avgSecondsResult = BigDecimalUtils.div(Double.valueOf(cMember.getTotalSecondsResult()), results.size());
				String avgResult = competitionResultBizService.getResult(avgSecondsResult);
				memberOutput.setAvgResult(avgResult);
			}
			String result = competitionResultBizService.getResult(Double.valueOf(cMember.getTotalSecondsResult()));
			memberOutput.setTotalSecondsResult(result);
		}

		if (StringUtils.isNotEmpty(cMember.getTotalDistance())) {
			Double ss = BigDecimalUtils.round(Double.valueOf(cMember.getTotalDistance()), 1);
			memberOutput.setTotalDistance(ss.toString());
		}

		// 总排行第几
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("projectId", projectId);
		params.put("isComplete", "1");
		List<Map<String, Object>> memberResults = competitionResultService.getAllResult(params, null);
		competitionResultBizService.getMemberResultRank(memberResults, projectId, "");
		for (int i = 0; i < memberResults.size(); i++) {
			Map<String, Object> map = memberResults.get(i);
			if (cMember.getId().equals(map.get("memberId"))) {
				memberOutput.setMemberRank(i + 1 + "");
				break;
			}
		}

		// 小组排行第几
		if (StringUtils.isNotEmpty(cMember.getGroupId())) {
			params.put("groupId", cMember.getGroupId());
			List<Map<String, Object>> groupMemberResults = competitionResultService.getGroupAllPersonResult(params, null);
			competitionResultBizService.getMemberResultRank(groupMemberResults, projectId, "");
			for (int i = 0; i < groupMemberResults.size(); i++) {
				Map<String, Object> map = groupMemberResults.get(i);
				if (cMember.getId().equals(map.get("memberId"))) {
					memberOutput.setGroupMemberRank(i + 1 + "");
					break;
				}
			}
		}
		return AjaxResult.success(memberOutput);
	}

	/**
	 * 获取项目所有赛程
	 * 
	 * @param projectId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getScheduleByProject")
	public AjaxResult getScheduleByProject(String projectId, String groupId) {
		if (StringUtils.isEmpty(projectId)) {
			return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, "项目id不能为空");
		}
		List<CompetitionSchedule> schedules = competitionScheduleService.list(new CompetitionSchedule(projectId));
		Map<String, Object> output = new HashMap<String, Object>();
		CompetitionProject project = competitionProjectService.get(projectId);
		if (project != null) {
			Map<String, Object> projectMap = new HashMap<String, Object>();
			projectMap.put("title", project.getTitle());
			projectMap.put("remarks", project.getRemarks());
			projectMap.put("picture", project.getPicture());
			projectMap.put("id", project.getId());
			
			output.put("project", projectMap);
		}
		
		if (StringUtils.isNotEmpty(groupId)) {
			CompetitionGroup group = competitionGroupService.get(groupId);
			Map<String, Object> groupMap = new HashMap<String,Object>();
			groupMap.put("groupName", group.getGroupName());
			groupMap.put("groupNo", group.getGroupNo());
			groupMap.put("id", group.getId());
			output.put("group", groupMap);
		}
		output.put("schedules", schedules);
		return AjaxResult.success(output);
	}

	/**
	 * 全部小组的成绩排行
	 * 
	 * @param projectId
	 *            项目id
	 * @param scheduleId
	 *            日程id
	 * @param searchName
	 *            查询条件
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getGroupRank")
	public AjaxResult getGroupRank(String projectId, String scheduleId, String groupName, Page page) {
		page.setLimit(20);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("projectId", projectId);
		params.put("scheduleId", scheduleId);
		params.put("isComplete", "1");
		params.put("groupName", groupName);
		List<Map<String, Object>> memberResults = competitionResultService.getByGroup(params, page);
		competitionResultBizService.getGroupResultRank(memberResults, projectId, scheduleId);
		return AjaxResult.success(memberResults, page);
	}

	/**
	 * 全部人员的成绩排名
	 * 
	 * @param projectId
	 *            项目id
	 * @param scheduleId
	 *            日程id
	 * @param searchName
	 *            查询条件
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getAllPersonRank")
	public AjaxResult getAllPersonRank(String projectId, String scheduleId, String searchName, Page page) {
		page.setLimit(20);
		// 项目下所有参赛人员
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("projectId", projectId);
		params.put("scheduleId", scheduleId);
		params.put("isComplete", "1");
		params.put("searchName", searchName);
		List<Map<String, Object>> memberResults = competitionResultService.getAllResult(params, page);
		competitionResultBizService.getMemberResultRank(memberResults, projectId, scheduleId);
		return AjaxResult.success(memberResults, page);
	}

	/**
	 * 小组所有成员的成绩排行
	 * 
	 * @param projectId
	 *            项目id
	 * @param groupId
	 *            小组id
	 * @param scheduleId
	 *            日程id
	 * @param searchName
	 *            查询条件
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getGroupAllPersonalRank")
	public AjaxResult getGroupAllPersonalRank(String projectId, String groupId, String scheduleId, String searchName, Page page) {
		if (StringUtils.isEmpty(groupId)) {
			return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, "小组id不能为空");
		}
		page.setLimit(20);
		// 项目下所有参赛人员
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("projectId", projectId);
		params.put("scheduleId", scheduleId);
		params.put("groupId", groupId);
		params.put("isComplete", "1");
		params.put("searchName", searchName);
		List<Map<String, Object>> memberResults = competitionResultService.getGroupAllPersonResult(params, page);
		competitionResultBizService.getMemberResultRank(memberResults, projectId, scheduleId);
		return AjaxResult.success(memberResults, page);
	}
}
