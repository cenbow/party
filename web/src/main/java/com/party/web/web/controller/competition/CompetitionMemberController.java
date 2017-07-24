package com.party.web.web.controller.competition;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.party.common.paging.Page;
import com.party.common.utils.StringUtils;
import com.party.core.model.BaseModel;
import com.party.core.model.city.Area;
import com.party.core.model.competition.CompetitionGroup;
import com.party.core.model.competition.CompetitionMember;
import com.party.core.model.competition.CompetitionProject;
import com.party.core.model.competition.CompetitionResult;
import com.party.core.model.member.Industry;
import com.party.core.model.member.Member;
import com.party.core.service.city.IAreaService;
import com.party.core.service.competition.ICompetitionGroupService;
import com.party.core.service.competition.ICompetitionMemberService;
import com.party.core.service.competition.ICompetitionProjectService;
import com.party.core.service.competition.ICompetitionResultService;
import com.party.core.service.member.IIndustryService;
import com.party.core.service.member.IMemberService;
import com.party.web.biz.system.member.MemberBizService;
import com.party.web.utils.RealmUtils;
import com.party.web.utils.excel.ExportExcel;
import com.party.web.web.dto.AjaxResult;
import com.party.web.web.dto.input.common.CommonInput;
import com.party.web.web.dto.output.competition.CompetitionMemberOutput;

/**
 * 参赛人员
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/competition/member")
public class CompetitionMemberController {

	@Autowired
	private IAreaService areaService;
	@Autowired
	private IIndustryService industryService;
	@Autowired
	private MemberBizService memberBizService;
	@Autowired
	private IMemberService memberService;
	@Autowired
	private ICompetitionGroupService competitionGroupService;
	@Autowired
	private ICompetitionProjectService competitionProjectService;
	@Autowired
	private ICompetitionMemberService competitionMemberService;
	@Autowired
	private ICompetitionResultService competitionResultService;

	/**
	 * 列表
	 * 
	 * @param page
	 * @return
	 */
	@RequestMapping("list")
	public ModelAndView list(Page page, CompetitionMember memberInfo, Member member, String pGroupId, CommonInput commonInput) {
		memberInfo.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
		ModelAndView mv = new ModelAndView("competition/memberList");
		Map<String, Object> params = CommonInput.appendParams(commonInput);

		if (commonInput.getLimit() == null) {
			commonInput.setLimit(100);
		}
		page.setLimit(commonInput.getLimit());

		String groupId = memberInfo.getGroupId();

		if (StringUtils.isNotEmpty(pGroupId)) {
			memberInfo.setGroupId(pGroupId);
		}

		List<CompetitionMember> competitionMembers = competitionMemberService.webListPage(memberInfo, member, params, page);
		List<CompetitionMemberOutput> outputs = new ArrayList<CompetitionMemberOutput>();
		for (CompetitionMember competitionMember : competitionMembers) {
			CompetitionMemberOutput output = CompetitionMemberOutput.transform(competitionMember);
			Member mm = memberService.get(competitionMember.getMemberId());
			output.setMember(mm);
			outputs.add(output);
		}

		if (StringUtils.isEmpty(groupId)) {
			memberInfo.setGroupId("");
		} else {
			memberInfo.setGroupId(groupId);
		}

		mv.addObject("page", page);
		mv.addObject("competitionMembers", outputs);
		mv.addObject("memberInfo", memberInfo);
		mv.addObject("input", commonInput);

		String projectId = memberInfo.getProjectId();

		if (StringUtils.isNotEmpty(pGroupId)) {
			CompetitionGroup competitionGroup = competitionGroupService.get(pGroupId);
			mv.addObject("group", competitionGroup);
			projectId = competitionGroup.getProjectId();
		}

		if (StringUtils.isNotEmpty(projectId)) {
			CompetitionProject competitionProject = competitionProjectService.get(projectId);
			mv.addObject("project", competitionProject);
		}

		CompetitionGroup t = new CompetitionGroup();
		t.setProjectId(projectId);
		List<CompetitionGroup> groups = competitionGroupService.list(t);
		mv.addObject("groups", groups);

		mv.addObject("pGroupId", pGroupId);
		return mv;
	}

	/**
	 * 新增人员
	 * 
	 * @param projectId
	 * @return
	 */
	@RequestMapping(value = "addMember")
	public ModelAndView addMember(String projectId, String groupId) {
		ModelAndView mv = new ModelAndView("competition/addMember");

		if (StringUtils.isNotEmpty(groupId)) {
			CompetitionGroup competitionGroup = competitionGroupService.get(groupId);
			mv.addObject("group", competitionGroup);

			projectId = competitionGroup.getProjectId();
		}

		if (StringUtils.isNotEmpty(projectId)) {
			CompetitionProject project = competitionProjectService.get(projectId);
			mv.addObject("project", project);
		}

		Industry industry = new Industry();
		industry.setParentId("0");
		List<Industry> industries = industryService.list(industry);
		mv.addObject("industries", industries);

		Area area = new Area();
		area.setParentId("1");
		List<Area> areas = areaService.list(area);
		mv.addObject("areas", areas);
		return mv;
	}

	@RequestMapping("editMember")
	public ModelAndView editMember(String cMemberId, String projectId, String groupId) {
		ModelAndView mv = new ModelAndView("competition/editMember");

		if (StringUtils.isNotEmpty(groupId)) {
			CompetitionGroup competitionGroup = competitionGroupService.get(groupId);
			mv.addObject("group", competitionGroup);

			projectId = competitionGroup.getProjectId();
		}

		if (StringUtils.isNotEmpty(projectId)) {
			CompetitionProject project = competitionProjectService.get(projectId);
			mv.addObject("project", project);
		}

		CompetitionMember competitionMember = competitionMemberService.get(cMemberId);

		Member member = memberService.get(competitionMember.getMemberId());
		mv.addObject("competitionMember", competitionMember);
		mv.addObject("member", member);
		
		if (StringUtils.isNotEmpty(member.getIndustry())) {
			Industry industry = industryService.get(member.getIndustry());
			mv.addObject("inParent", industry.getParentId());
		}

		if (StringUtils.isNotEmpty(member.getCity())) {
			Area area = areaService.get(member.getCity());
			mv.addObject("arParent", area.getParentId());
		}
		
		Industry industry = new Industry();
		industry.setParentId("0");
		List<Industry> industries = industryService.list(industry);
		mv.addObject("industries", industries);

		Area area = new Area();
		area.setParentId("1");
		List<Area> areas = areaService.list(area);
		mv.addObject("areas", areas);
		return mv;
	}
	
	@ResponseBody
	@RequestMapping(value = "updateCMember", method = RequestMethod.POST)
	public AjaxResult updateCMember(CompetitionMember t) throws Exception {
		if (StringUtils.isNotEmpty(t.getId())) {
			CompetitionMember competitionMember = competitionMemberService.get(t.getId());
			competitionMember.setNumber(t.getNumber());
			competitionMemberService.update(competitionMember);
		}
		return AjaxResult.success();
	}

	/**
	 * 保存人员
	 * 
	 * @param memberInfo
	 * @param goDate
	 * @param backDate
	 * @param result
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "saveMember", method = RequestMethod.POST)
	public AjaxResult saveMember(String projectId, String memberId, String groupId, Member member, BindingResult result) throws Exception {
		// 数据验证
		if (result.hasErrors()) {
			List<ObjectError> allErrors = result.getAllErrors();
			String description = allErrors.get(0).getDefaultMessage();
			return AjaxResult.error(description);
		}

		try {
			CompetitionMember competitionMember = competitionMemberService.findByProjectAndMember(memberId, projectId);
			if (competitionMember == null) {
				competitionMember = new CompetitionMember();

				String currentUserId = RealmUtils.getCurrentUser().getId();
				competitionMember.setProjectId(projectId);
				competitionMember.setGroupId(groupId);
				competitionMember.setCreateBy(currentUserId);
				competitionMember.setUpdateBy(currentUserId);

				if (StringUtils.isEmpty(memberId)) {
					Member mm = memberBizService.saveBiz(member);
					memberId = mm.getId();
				}

				if (StringUtils.isEmpty(competitionMember.getId())) {
					competitionMember.setMemberId(memberId);
					competitionMember.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
					competitionMemberService.insert(competitionMember);
				}
			}
			return AjaxResult.success();
		} catch (Exception e) {
			e.printStackTrace();
			return AjaxResult.error("添加人员失败");
		}
	}

	/**
	 * 删除参赛人员
	 * 
	 * @param id
	 * @param projectId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public AjaxResult delete(String id, String projectId) {
		if (StringUtils.isEmpty(id) || StringUtils.isEmpty(projectId)) {
			AjaxResult ajaxResult = new AjaxResult();
			ajaxResult.setSuccess(false);
			return ajaxResult;
		}
		List<CompetitionResult> results = competitionResultService.listPage(new CompetitionResult(id, projectId), null);
		if (results.size() > 0) {
			return AjaxResult.error("该参赛人员已有录入成绩，不能删除");
		}

		competitionMemberService.delete(id);

		return AjaxResult.success();
	}

	/**
	 * 导出参赛人员
	 * 
	 * @param memberInfo
	 * @param member
	 * @param pGroupId
	 * @param commonInput
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("exportMember")
	public AjaxResult exportMember(CompetitionMember memberInfo, Member member, String pGroupId, CommonInput commonInput, HttpServletResponse response)
			throws IOException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String fileName = "参赛人员" + sdf.format(new Date()) + ".xlsx";
		memberInfo.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
		Map<String, Object> params = CommonInput.appendParams(commonInput);

		params.put("orderBy", commonInput.getOrderBy());

		if (StringUtils.isNotEmpty(pGroupId)) {
			memberInfo.setGroupId(pGroupId);
		}

		List<CompetitionMember> competitionMembers = competitionMemberService.webListPage(memberInfo, member, params, null);
		List<CompetitionMemberOutput> outputs = new ArrayList<CompetitionMemberOutput>();
		for (CompetitionMember competitionMember : competitionMembers) {
			CompetitionMemberOutput output = CompetitionMemberOutput.transform(competitionMember);
			Member mm = memberService.get(competitionMember.getMemberId());
			if (StringUtils.isNotEmpty(mm.getFullname())) {
				output.setFullName(mm.getFullname());
			} else if (StringUtils.isNotEmpty(mm.getRealname())) {
				output.setFullName(mm.getRealname());
			}
			if (mm.getSex() != null) {
				output.setSexName(mm.getSex().equals(1) ? "男" : "女");
			}
			output.setCompany(mm.getCompany());
			output.setJobTitle(mm.getJobTitle());
			output.setMobile(mm.getMobile());
			output.setCreateTime(competitionMember.getUpdateDate());
			outputs.add(output);
		}

		new ExportExcel("", CompetitionMemberOutput.class).setDataList(outputs).write(response, fileName).dispose();

		return null;
	}
	
	@ResponseBody
	@RequestMapping("checkNumberPai")
	public boolean checkNumberPai(String cMemberId, String numberPai, String projectId) {
		// 同一个项目里，不能存在相同的号码牌
		if (StringUtils.isEmpty(projectId)) {
			return false;
		}

		List<CompetitionMember> competitionMembers = competitionMemberService.checkNumberPai(cMemberId, numberPai, projectId);
		if (competitionMembers.size() > 0) {
			return false;
		}
		return true;
	}
}
