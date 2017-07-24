package com.party.admin.web.controller.gatherInfo;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.party.admin.utils.MyBeanUtils;
import com.party.admin.utils.RealmUtils;
import com.party.admin.web.dto.AjaxResult;
import com.party.admin.web.dto.input.common.CommonInput;
import com.party.admin.web.dto.output.gatherInfo.GatherInfoGroupOutput;
import com.party.common.paging.Page;
import com.party.common.utils.LangUtils;
import com.party.common.utils.StringUtils;
import com.party.core.model.BaseModel;
import com.party.core.model.gatherInfo.GatherInfoGroup;
import com.party.core.model.gatherInfo.GatherInfoMember;
import com.party.core.model.gatherInfo.GatherInfoProject;
import com.party.core.service.gatherInfo.IGatherInfoGroupService;
import com.party.core.service.gatherInfo.IGatherInfoMemberService;
import com.party.core.service.gatherInfo.IGatherInfoProjectService;

/**
 * 人员信息小组
 * @author Administrator
 *
 */
@Controller
@RequestMapping("gatherInfo/group")
public class GatherInfoGroupController {

	@Autowired
	private IGatherInfoGroupService gatherInfoGroupService;

	@Autowired
	private IGatherInfoProjectService gatherInfoProjectService;

	@Autowired
	private IGatherInfoMemberService gatherInfoMemberService;

	@RequestMapping("list")
	public ModelAndView list(Page page, GatherInfoGroup group, CommonInput commonInput) {
		page.setLimit(15);
		group.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
		ModelAndView mv = new ModelAndView("gatherInfo/groupList");
		Map<String, Object> params = CommonInput.appendParams(commonInput);
		List<GatherInfoGroup> groups = gatherInfoGroupService.webListPage(group, params, page);
		List<GatherInfoGroupOutput> outputs = LangUtils.transform(groups, input -> {
			GatherInfoGroupOutput output = GatherInfoGroupOutput.transform(input);
			int totalNum = gatherInfoMemberService.getCountByGroup(input.getId());
			output.setTotalNum(totalNum);
			return output;
		});
		
		mv.addObject("groups", outputs);
		mv.addObject("input", commonInput);
		mv.addObject("group", group);
		mv.addObject("page", page);

		GatherInfoProject memberInfoProject = gatherInfoProjectService.get(group.getProjectId());
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
		ModelAndView mv = new ModelAndView("gatherInfo/groupForm");
		if (StringUtils.isNotEmpty(id)) {
			GatherInfoGroup group = gatherInfoGroupService.get(id);
			mv.addObject("group", group);
		}

		GatherInfoProject memberInfoProject = gatherInfoProjectService.get(projectId);
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
	public AjaxResult save(GatherInfoGroup group){
		try {
			if (StringUtils.isEmpty(group.getId())) {
				group.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
				String memberId = RealmUtils.getCurrentUser().getId();
				group.setCreateBy(memberId);
				group.setUpdateBy(memberId);
				gatherInfoGroupService.insert(group);
			} else {
				GatherInfoGroup t = gatherInfoGroupService.get(group.getId());
				MyBeanUtils.copyBeanNotNull2Bean(group, t);
				gatherInfoGroupService.update(t);
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
		List<GatherInfoMember> memberInfoForms = gatherInfoMemberService.findByGroup(id);
		if (memberInfoForms.size() == 0) {
			gatherInfoGroupService.delete(id);
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
	public ModelAndView setGroup(String memberInfoId) {
		ModelAndView mv = new ModelAndView("gatherInfo/setGroup");
		GatherInfoMember memberInfo = gatherInfoMemberService.get(memberInfoId);
		mv.addObject("memberInfo", memberInfo);

		GatherInfoGroup t = new GatherInfoGroup();
		t.setProjectId(memberInfo.getProjectId());
		t.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
		List<GatherInfoGroup> groups = gatherInfoGroupService.list(t);
		mv.addObject("groups", groups);
		return mv;
	}

	/**
	 * 保存分组
	 * @return
	 */
	@ResponseBody
	@RequestMapping("saveMemberGroup")
	public AjaxResult saveMemberGroup(String memberInfoId, String groupId) {
		if (StringUtils.isEmpty(memberInfoId)) {
			return AjaxResult.error("用户信息id不能为空");
		}
		
		GatherInfoMember memberInfo = gatherInfoMemberService.get(memberInfoId);
		memberInfo.setGroupId(groupId);
		gatherInfoMemberService.update(memberInfo);
		return AjaxResult.success();
	}
}
