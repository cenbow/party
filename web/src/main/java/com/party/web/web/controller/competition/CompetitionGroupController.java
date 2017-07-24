package com.party.web.web.controller.competition;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.party.common.paging.Page;
import com.party.common.utils.LangUtils;
import com.party.common.utils.StringUtils;
import com.party.core.model.BaseModel;
import com.party.core.model.competition.CompetitionGroup;
import com.party.core.model.competition.CompetitionMember;
import com.party.core.model.competition.CompetitionProject;
import com.party.core.service.competition.ICompetitionGroupService;
import com.party.core.service.competition.ICompetitionMemberService;
import com.party.core.service.competition.ICompetitionProjectService;
import com.party.core.service.competition.ICompetitionResultService;
import com.party.core.service.competition.biz.CompetitionResultBizService;
import com.party.web.utils.MyBeanUtils;
import com.party.web.utils.RealmUtils;
import com.party.web.web.dto.AjaxResult;
import com.party.web.web.dto.input.common.CommonInput;
import com.party.web.web.dto.output.competition.CompetitionGroupOutput;

/**
 * 赛事小组
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("competition/group")
public class CompetitionGroupController {

	@Autowired
	private ICompetitionGroupService competitionGroupService;
	@Autowired
	private ICompetitionProjectService competitionProjectService;
	@Autowired
	private ICompetitionMemberService competitionMemberService;
	@Autowired
	private ICompetitionResultService competitionResultService;
	@Autowired
	private CompetitionResultBizService competitionResultBizService;

	@RequestMapping("list")
	public ModelAndView list(Page page, CompetitionGroup group, CommonInput commonInput) {
		page.setLimit(15);
		group.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
		ModelAndView mv = new ModelAndView("competition/groupList");
		Map<String, Object> params = CommonInput.appendParams(commonInput);
		List<CompetitionGroup> groups = competitionGroupService.webListPage(group, params, page);
		List<CompetitionGroupOutput> outputs = LangUtils.transform(groups, input -> {
			CompetitionGroupOutput output = CompetitionGroupOutput.transform(input);
			CompetitionMember t = new CompetitionMember();
			t.setGroupId(input.getId());
			int totalNum = competitionMemberService.listPage(t, null).size();
			output.setTotalNum(totalNum);
			return output;
		});

		mv.addObject("groups", outputs);
		mv.addObject("input", commonInput);
		mv.addObject("group", group);
		mv.addObject("page", page);

		CompetitionProject memberInfoProject = competitionProjectService.get(group.getProjectId());
		mv.addObject("project", memberInfoProject);
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
		ModelAndView mv = new ModelAndView("competition/groupForm");
		if (StringUtils.isNotEmpty(id)) {
			CompetitionGroup group = competitionGroupService.get(id);
			mv.addObject("group", group);
		}

		CompetitionProject memberInfoProject = competitionProjectService.get(projectId);
		mv.addObject("project", memberInfoProject);
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
	public AjaxResult save(CompetitionGroup group) {
		try {
			if (StringUtils.isEmpty(group.getId())) {
				group.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
				String memberId = RealmUtils.getCurrentUser().getId();
				group.setCreateBy(memberId);
				group.setUpdateBy(memberId);
				competitionGroupService.insert(group);
			} else {
				CompetitionGroup t = competitionGroupService.get(group.getId());
				MyBeanUtils.copyBeanNotNull2Bean(group, t);
				competitionGroupService.update(t);
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
		CompetitionMember t = new CompetitionMember();
		t.setGroupId(id);
		List<CompetitionMember> memberInfoForms = competitionMemberService.listPage(t, null);
		if (memberInfoForms.size() == 0) {
			competitionGroupService.delete(id);
			return AjaxResult.success();
		} else {
			return AjaxResult.error("该小组下已有成员");
		}
	}

	/**
	 * 分组
	 * 
	 * @param memberInfoId
	 * @return
	 */
	@RequestMapping("setGroup")
	public ModelAndView setGroup(String cMemberId) {
		ModelAndView mv = new ModelAndView("competition/setGroup");
		CompetitionMember memberInfo = competitionMemberService.get(cMemberId);
		mv.addObject("memberInfo", memberInfo);

		CompetitionGroup t = new CompetitionGroup();
		t.setProjectId(memberInfo.getProjectId());
		t.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
		List<CompetitionGroup> groups = competitionGroupService.list(t);
		mv.addObject("groups", groups);
		return mv;
	}

	@ResponseBody
	@RequestMapping("checkGroupName")
	public boolean checkGroupName(String groupId, String groupName, String projectId) {
		// 同一个项目里，不能存在相同的组名
		if (StringUtils.isEmpty(projectId)) {
			return false;
		}

		List<CompetitionGroup> competitionGroups = competitionGroupService.checkGroupName(groupId, groupName, projectId);
		if (competitionGroups.size() > 0) {
			return false;
		}
		return true;
	}

	/**
	 * 保存分组
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("saveMemberGroup")
	public AjaxResult saveMemberGroup(String memberInfoId, String groupId) {
		if (StringUtils.isEmpty(memberInfoId)) {
			return AjaxResult.error("用户信息id不能为空");
		}

		CompetitionMember memberInfo = competitionMemberService.get(memberInfoId);
		String newGroupId = memberInfo.getGroupId();
		if (StringUtils.isEmpty(newGroupId)) {
			newGroupId = groupId;
		}
		memberInfo.setGroupId(groupId);
		competitionMemberService.update(memberInfo);

		// if (StringUtils.isEmpty(groupId)) {
		// groupId = newGroupId;
		//
		// CompetitionResult t = new CompetitionResult();
		// t.setMemberId(memberInfoId);
		// List<CompetitionResult> results = competitionResultService.list(t);
		// for (CompetitionResult competitionResult : results) {
		// competitionResult.setGroupId("");
		// competitionResultService.update(competitionResult);
		// }
		// }

		competitionResultBizService.updateGroupResult(newGroupId);

		return AjaxResult.success();
	}
}
