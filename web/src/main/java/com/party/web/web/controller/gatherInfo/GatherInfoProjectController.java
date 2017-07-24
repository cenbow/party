package com.party.web.web.controller.gatherInfo;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
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
import com.party.core.model.competition.CompetitionBusiness;
import com.party.core.model.competition.CompetitionProject;
import com.party.core.model.gatherInfo.GatherInfoGroup;
import com.party.core.model.gatherInfo.GatherInfoMember;
import com.party.core.model.gatherInfo.GatherInfoProject;
import com.party.core.service.competition.ICompetitionBusinessService;
import com.party.core.service.competition.ICompetitionGroupService;
import com.party.core.service.competition.ICompetitionMemberService;
import com.party.core.service.competition.ICompetitionProjectService;
import com.party.core.service.competition.biz.CompetitionProjectBizService;
import com.party.core.service.gatherInfo.IGatherInfoGroupService;
import com.party.core.service.gatherInfo.IGatherInfoMemberService;
import com.party.core.service.gatherInfo.IGatherInfoProjectService;
import com.party.web.biz.file.FileBizService;
import com.party.web.utils.MyBeanUtils;
import com.party.web.utils.RealmUtils;
import com.party.web.web.dto.AjaxResult;
import com.party.web.web.dto.input.common.CommonInput;
import com.party.web.web.dto.output.gatherInfo.GatherInfoProjectOutput;

/**
 * 人员信息采集项目
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("gatherInfo/project")
public class GatherInfoProjectController {

	@Autowired
	private IGatherInfoProjectService gatherInfoProjectService;
	@Autowired
	private IGatherInfoMemberService gatherInfoMemberService;
	@Autowired
	private IGatherInfoGroupService gatherInfoGroupService;
	@Autowired
	private FileBizService fileBizService;
	@Autowired
	private ICompetitionBusinessService competitionBusinessService;
	@Autowired
	private ICompetitionProjectService competitionProjectService;
	@Autowired
	private ICompetitionMemberService competitionMemberService;
	@Autowired
	private ICompetitionGroupService competitionGroupService;
	@Autowired
	private CompetitionProjectBizService competitionProjectBizService;

	/**
	 * 列表
	 * 
	 * @param page
	 * @return
	 */
	@RequestMapping("list")
	@RequiresPermissions("gatherInfo:project:list")
	public ModelAndView list(Page page, GatherInfoProject project, CommonInput commonInput) {
		page.setLimit(15);
		project.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
		project.setCreateBy(RealmUtils.getCurrentUser().getId());
		ModelAndView mv = new ModelAndView("gatherInfo/projectList");
		Map<String, Object> params = CommonInput.appendParams(commonInput);
		List<GatherInfoProject> memberInfoProjects = gatherInfoProjectService.webListPage(project, params, page);
		List<GatherInfoProjectOutput> outputs = LangUtils.transform(memberInfoProjects, input -> {
			GatherInfoProjectOutput output = GatherInfoProjectOutput.transform(input);
			int totalNum = gatherInfoMemberService.getCountByProject(input.getId());
			output.setTotalNum(totalNum);

			GatherInfoGroup t = new GatherInfoGroup();
			t.setProjectId(input.getId());
			List<GatherInfoGroup> groups = gatherInfoGroupService.list(t);
			output.setGroupNum(groups.size());

			// 活动二维码
				String path = RealmUtils.getCurrentUser().getId() + "/gatherInfo/base/";
				String content = "gather_info/base_info.html?projectId=" + input.getId();
				String baseQrCodeUrl = fileBizService.getFileEntity(input.getId(), path, content);
				output.setBaseQrCodeUrl(baseQrCodeUrl);

				path = RealmUtils.getCurrentUser().getId() + "/gatherInfo/insurance/";
				content = "gather_info/insurance_info.html?projectId=" + input.getId();
				baseQrCodeUrl = fileBizService.getFileEntity(input.getId(), path, content);
				output.setInsuranceQrCodeUrl(baseQrCodeUrl);

				path = RealmUtils.getCurrentUser().getId() + "/gatherInfo/itinerary/";
				content = "gather_info/itinerary_info.html?projectId=" + input.getId();
				baseQrCodeUrl = fileBizService.getFileEntity(input.getId(), path, content);
				output.setItineraryQrCodeUrl(baseQrCodeUrl);
				return output;
			});

		mv.addObject("projectList", outputs);
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
		ModelAndView mv = new ModelAndView("gatherInfo/projectForm");
		if (StringUtils.isNotEmpty(id)) {
			GatherInfoProject project = gatherInfoProjectService.get(id);
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
	public AjaxResult save(GatherInfoProject project) {
		try {
			if (StringUtils.isEmpty(project.getId())) {
				project.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
				String memberId = RealmUtils.getCurrentUser().getId();
				project.setCreateBy(memberId);
				project.setUpdateBy(memberId);
				gatherInfoProjectService.insert(project);
			} else {
				GatherInfoProject t = gatherInfoProjectService.get(project.getId());
				MyBeanUtils.copyBeanNotNull2Bean(project, t);
				gatherInfoProjectService.update(t);
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
		List<GatherInfoMember> memberInfoForms = gatherInfoMemberService.findByProject(id);
		GatherInfoGroup t = new GatherInfoGroup();
		t.setProjectId(id);
		List<GatherInfoGroup> gatherInfoGroups = gatherInfoGroupService.list(t);
		if (memberInfoForms.size() > 0) {
			return AjaxResult.error("该项目下已有收集人员信息，不能删除");
		} else if (gatherInfoGroups.size() > 0) {
			return AjaxResult.error("该项目下已有小组，不能删除");
		} else {
			gatherInfoProjectService.delete(id);
			return AjaxResult.success();
		}
	}

	/**
	 * 同步生成圈子
	 * 
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("syncCircle")
	public AjaxResult syncCircle(String id) {
		return AjaxResult.success();
	}

	/**
	 * 同步生成赛事项目
	 * 
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("syncCompetition")
	public AjaxResult syncCompetition(String id, String type) {
		GatherInfoProject gProject = gatherInfoProjectService.get(id);
		String cProjectId = "";
		String currentId = RealmUtils.getCurrentUser().getId();
		CompetitionBusiness cBusiness = competitionBusinessService.findByBusinessId(id, type);
		if (cBusiness == null) {
			cBusiness = new CompetitionBusiness();
			cBusiness.setBusinessId(id);
			cBusiness.setType(type);
			cBusiness.setCreateBy(currentId);
			cBusiness.setUpdateBy(currentId);
			String competitionId = competitionProjectBizService.createCompetition(gProject.getTitle(), gProject.getCreateBy());
			cBusiness.setCompetitionId(competitionId);
			competitionBusinessService.insert(cBusiness);

			cProjectId = competitionId;
		} else {
			String competitionId = cBusiness.getCompetitionId();
			CompetitionProject cProject = competitionProjectService.get(competitionId);
			if (cProject == null) {
				competitionId = competitionProjectBizService.createCompetition(gProject.getTitle(), gProject.getCreateBy());
				cBusiness.setCompetitionId(competitionId);
				competitionBusinessService.update(cBusiness);

				cProjectId = competitionId;
			} else {
				cProject.setTitle(gProject.getTitle());
				competitionProjectService.update(cProject);

				cProjectId = cProject.getId();
			}
		}

		competitionProjectBizService.insertCompetitionMember(cProjectId, gProject.getId(), gProject.getCreateBy());

		return AjaxResult.success();
	}

}
