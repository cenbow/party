package com.party.web.web.controller.gatherInfo;

import java.text.SimpleDateFormat;
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

import com.google.common.base.Strings;
import com.party.common.paging.Page;
import com.party.common.utils.DateUtils;
import com.party.common.utils.StringUtils;
import com.party.core.model.BaseModel;
import com.party.core.model.city.Area;
import com.party.core.model.gatherInfo.GatherInfoGroup;
import com.party.core.model.gatherInfo.GatherInfoMember;
import com.party.core.model.gatherInfo.GatherInfoMemberOutput;
import com.party.core.model.gatherInfo.GatherInfoProject;
import com.party.core.model.member.Industry;
import com.party.core.model.member.Member;
import com.party.core.model.member.MemberInfo;
import com.party.core.service.city.IAreaService;
import com.party.core.service.gatherInfo.IGatherInfoGroupService;
import com.party.core.service.gatherInfo.IGatherInfoMemberService;
import com.party.core.service.gatherInfo.IGatherInfoProjectService;
import com.party.core.service.gatherInfo.biz.GatherInfoMembersBizService;
import com.party.core.service.member.IIndustryService;
import com.party.core.service.member.IMemberInfoService;
import com.party.core.service.member.IMemberService;
import com.party.web.biz.system.member.MemberBizService;
import com.party.web.utils.MyBeanUtils;
import com.party.web.utils.RealmUtils;
import com.party.web.utils.excel.ExportExcel;
import com.party.web.web.dto.AjaxResult;
import com.party.web.web.dto.input.common.CommonInput;
import com.party.web.web.dto.input.gatherInfo.GatherInfoMemberInput;

/**
 * 人员信息采集表单
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("gatherInfo/member")
public class GatherInfoMemberController {
	@Autowired
	private IGatherInfoMemberService gatherInfoMemberService;
	@Autowired
	private IGatherInfoProjectService gatherInfoProjectService;
	@Autowired
	private IGatherInfoGroupService gatherInfoGroupService;
	@Autowired
	private IAreaService areaService;
	@Autowired
	private IIndustryService industryService;
	@Autowired
	private IMemberService memberService;
	@Autowired
	private MemberBizService memberBizService;
	@Autowired
	private IMemberInfoService memberInfoService;
	@Autowired
	private GatherInfoMembersBizService gatherInfoMembersBizService;

	/**
	 * 列表
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	public ModelAndView list(Page page, GatherInfoMember memberInfo, Member member, String pGroupId, CommonInput commonInput) throws Exception {
		memberInfo.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
		ModelAndView mv = new ModelAndView("gatherInfo/memberList");
		Map<String, Object> params = CommonInput.appendParams(commonInput);

		params.put("fullname", member.getFullname());
		params.put("mobile", member.getMobile());
		params.put("sex", member.getSex());

		if (commonInput.getLimit() == null) {
			commonInput.setLimit(100);
		}
		page.setLimit(commonInput.getLimit());

		String groupId = memberInfo.getGroupId();

		if (StringUtils.isNotEmpty(pGroupId)) {
			memberInfo.setGroupId(pGroupId);
		}

		List<Map<String, Object>> memberInfos = gatherInfoMemberService.webListPage(memberInfo, params, page);
		if (StringUtils.isEmpty(groupId)) {
			memberInfo.setGroupId("");
		} else {
			memberInfo.setGroupId(groupId);
		}

		mv.addObject("page", page);
		mv.addObject("memberInfos", memberInfos);
		mv.addObject("memberInfo", memberInfo);
		mv.addObject("input", commonInput);

		String projectId = memberInfo.getProjectId();

		if (StringUtils.isNotEmpty(pGroupId)) {
			GatherInfoGroup gatherInfoGroup = gatherInfoGroupService.get(pGroupId);
			mv.addObject("group", gatherInfoGroup);

			projectId = gatherInfoGroup.getProjectId();
		}

		if (StringUtils.isNotEmpty(projectId)) {
			GatherInfoProject gatherInfoProject = gatherInfoProjectService.get(projectId);
			mv.addObject("project", gatherInfoProject);
		}

		GatherInfoGroup t = new GatherInfoGroup();
		t.setProjectId(projectId);
		List<GatherInfoGroup> groups = gatherInfoGroupService.list(t);
		mv.addObject("groups", groups);

		mv.addObject("pGroupId", pGroupId);
		return mv;
	}

	/**
	 * 人员信息导出
	 * 
	 * @param commonInput
	 * @param memberInfo
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "exportMemberInfo", method = RequestMethod.POST)
	public String exportMemberInfo(CommonInput commonInput, GatherInfoMember memberInfo, Member member, HttpServletResponse response) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String fileName = "人员信息" + sdf.format(new Date()) + ".xlsx";

			Map<String, Object> params = CommonInput.appendParams(commonInput);
			params.put("fullname", member.getFullname());
			params.put("mobile", member.getMobile());
			params.put("sex", member.getSex());
			List<GatherInfoMemberOutput> memberInfos = gatherInfoMemberService.exportList(memberInfo, params);
			for (GatherInfoMemberOutput output : memberInfos) {
				output.setRemarkInfo(output.getRemarks());
				if (StringUtils.isEmpty(output.getGroupName())) {
					output.setGroupName("未分配");
				}
				if (output.getSex() != null) {
					output.setSexName(output.getSex() == 1 ? "男" : "女");
				}
				if (output.getGoShuttle() != null) {
					output.setGoShuttleName(output.getGoShuttle() == 1 ? "是" : "否");
				}
				if (output.getBackShuttle() != null) {
					output.setBackShuttleName(output.getBackShuttle() == 1 ? "是" : "否");
				}

				if (StringUtils.isNotEmpty(output.getBaseStatus())) {
					switch (Integer.valueOf(output.getBaseStatus())) {
					case 1:
						output.setBaseStatus("待审核");
						break;
					case 2:
						output.setBaseStatus("已通过");
						break;
					case 3:
						output.setBaseStatus("未通过");
					default:
						break;
					}
				}

				if (StringUtils.isNotEmpty(output.getInsuranceStatus())) {
					switch (Integer.valueOf(output.getInsuranceStatus())) {
					case 1:
						output.setInsuranceStatus("待审核");
						break;
					case 2:
						output.setInsuranceStatus("已通过");
						break;
					case 3:
						output.setInsuranceStatus("未通过");
					default:
						break;
					}
				}

				if (StringUtils.isNotEmpty(output.getItineraryStatus())) {
					switch (Integer.valueOf(output.getItineraryStatus())) {
					case 1:
						output.setItineraryStatus("待审核");
						break;
					case 2:
						output.setItineraryStatus("已通过");
						break;
					case 3:
						output.setItineraryStatus("未通过");
					default:
						break;
					}
				}
			}

			new ExportExcel("", GatherInfoMemberOutput.class).setDataList(memberInfos).write(response, fileName).dispose();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 编辑人员信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("toForm")
	public ModelAndView toForm(String id, String projectId, String groupId) {
		ModelAndView mv = new ModelAndView("gatherInfo/memberInfoForm");
		if (StringUtils.isNotEmpty(id)) {
			/** 行程信息 **/
			GatherInfoMember memberInfo = gatherInfoMemberService.get(id);
			mv.addObject("memberInfo", memberInfo);
			/** 基本信息 **/
			Member member = memberService.get(memberInfo.getMemberId());
			mv.addObject("member", member);
			/** 保险信息 **/
			MemberInfo mInfo = memberInfoService.findByMemberId(member.getId());
			mv.addObject("mInfo", mInfo);

			if (StringUtils.isNotEmpty(member.getIndustry())) {
				Industry industry = industryService.get(member.getIndustry());
				mv.addObject("inParent", industry.getParentId());
			}

			if (StringUtils.isNotEmpty(member.getCity())) {
				Area area = areaService.get(member.getCity());
				mv.addObject("arParent", area.getParentId());
			}
		}

		Area area = new Area();
		area.setParentId("1");
		List<Area> areas = areaService.list(area);
		mv.addObject("areas", areas);

		Industry industry = new Industry();
		industry.setParentId("0");
		List<Industry> industries = industryService.list(industry);
		mv.addObject("industries", industries);

		if (StringUtils.isNotEmpty(groupId)) {
			GatherInfoGroup gatherInfoGroup = gatherInfoGroupService.get(groupId);
			mv.addObject("group", gatherInfoGroup);
		}

		if (StringUtils.isNotEmpty(projectId)) {
			GatherInfoProject gatherInfoProject = gatherInfoProjectService.get(projectId);
			mv.addObject("project", gatherInfoProject);
		}
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
		ModelAndView mv = new ModelAndView("gatherInfo/addMember");

		if (StringUtils.isNotEmpty(groupId)) {
			GatherInfoGroup gatherInfoGroup = gatherInfoGroupService.get(groupId);
			mv.addObject("group", gatherInfoGroup);

			projectId = gatherInfoGroup.getProjectId();
		}

		if (StringUtils.isNotEmpty(projectId)) {
			GatherInfoProject gatherInfoProject = gatherInfoProjectService.get(projectId);
			mv.addObject("project", gatherInfoProject);
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

	/**
	 * 查看基本信息
	 * 
	 * @param memberId
	 * @return
	 */
	@RequestMapping("getBaseInfo")
	public ModelAndView getBaseInfo(String gMemberId) {
		ModelAndView mv = new ModelAndView("gatherInfo/baseInfo");

		GatherInfoMember infoMember = gatherInfoMemberService.get(gMemberId);
		mv.addObject("infoMember", infoMember);
		Member member = memberService.get(infoMember.getMemberId());
		mv.addObject("memberInfo", member);

		// 设置城市名字
		if (!Strings.isNullOrEmpty(member.getCity())) {
			Area area = areaService.get(member.getCity());
			if (null != area) {
				mv.addObject("cityName", area.getName());
			}
		}
		// 设置行业名字
		if (!Strings.isNullOrEmpty(member.getIndustry())) {
			Industry industry = industryService.get(member.getIndustry());
			if (null != industry) {
				mv.addObject("industryName", industry.getName());
			}
		}
		return mv;
	}

	/**
	 * 基本信息审核
	 * 
	 * @param id
	 * @param perfectState
	 * @return
	 */
	@ResponseBody
	@RequestMapping("verifyBaseInfo")
	public AjaxResult verifyBaseInfo(String id, String perfectState) {
		if (StringUtils.isNotEmpty(id)) {
			Member member = memberService.get(id);
			member.setPerfectState(perfectState);
			memberService.update(member);
		}
		return AjaxResult.success();
	}

	/**
	 * 保险信息审核
	 * 
	 * @param id
	 * @param perfectState
	 * @return
	 */
	@ResponseBody
	@RequestMapping("verifyInsuranceInfo")
	public AjaxResult verifyInsuranceInfo(String id, String perfectState) {
		if (StringUtils.isNotEmpty(id)) {
			MemberInfo memberInfo = memberInfoService.get(id);
			memberInfo.setPerfectState(perfectState);
			memberInfoService.update(memberInfo);
		}
		return AjaxResult.success();
	}

	@ResponseBody
	@RequestMapping("verifyItineraryInfo")
	public AjaxResult verifyItineraryInfo(String id, String perfectState) {
		if (StringUtils.isNotEmpty(id)) {
			GatherInfoMember memberInfo = gatherInfoMemberService.get(id);
			memberInfo.setItineraryStatus(perfectState);
			gatherInfoMemberService.update(memberInfo);
		}
		return AjaxResult.success();
	}

	/**
	 * 查看保险信息
	 * 
	 * @param memberId
	 * @return
	 */
	@RequestMapping("getInsuranceInfo")
	public ModelAndView getInsuranceInfo(String memberId) {
		ModelAndView mv = new ModelAndView("gatherInfo/insuranceInfo");
		Member member = memberService.get(memberId);
		mv.addObject("member", member);
		MemberInfo memberInfo = memberInfoService.findByMemberId(memberId);
		mv.addObject("memberInfo", memberInfo);
		return mv;
	}

	/**
	 * 详情
	 * 
	 * @param id
	 * @param type
	 * @return
	 */
	@RequestMapping("getItineraryInfo")
	public ModelAndView getItineraryInfo(String id) {
		ModelAndView mv = new ModelAndView("gatherInfo/itineraryInfo");
		GatherInfoMember memberInfo = gatherInfoMemberService.get(id);
		mv.addObject("memberInfo", memberInfo);
		Member member = memberService.get(memberInfo.getMemberId());
		mv.addObject("member", member);
		return mv;
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
			GatherInfoMember infoMemberDB = gatherInfoMemberService.findByProjectAndMember(memberId, projectId);
			if (infoMemberDB != null && StringUtils.isNotEmpty(groupId)) {
				infoMemberDB.setGroupId(groupId);
			}
			if (infoMemberDB == null) {
				infoMemberDB = new GatherInfoMember();

				String currentUserId = RealmUtils.getCurrentUser().getId();
				infoMemberDB.setMemberId(memberId);
				infoMemberDB.setProjectId(projectId);
				infoMemberDB.setGroupId(groupId);
				infoMemberDB.setCreateBy(currentUserId);
				infoMemberDB.setUpdateBy(currentUserId);

				if (StringUtils.isEmpty(memberId)) {
					Member mm = memberBizService.saveBiz(member);
					memberId = mm.getId();
				}

				infoMemberDB.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
				gatherInfoMemberService.insert(infoMemberDB);
			}
			return AjaxResult.success();
		} catch (Exception e) {
			e.printStackTrace();
			return AjaxResult.error("添加人员失败");
		}
	}

	/**
	 * 保存基本信息
	 * 
	 * @param member
	 * @param gMemberId
	 * @param groupJobTitle
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "saveBaseInfo", method = RequestMethod.POST)
	public AjaxResult saveBaseInfo(Member member, String gMemberId, String groupJobTitle) throws Exception {
		try {
			if (StringUtils.isNotEmpty(member.getId())) {
				Member dbMember = memberService.get(member.getId());
				MyBeanUtils.copyBeanNotNull2Bean(member, dbMember);
				if (StringUtils.isEmpty(dbMember.getPerfectState())) {
					dbMember.setPerfectState("1");
				}
				memberService.update(dbMember);
			}

			if (StringUtils.isNotEmpty(gMemberId)) {
				GatherInfoMember dbGMember = gatherInfoMemberService.get(gMemberId);
				dbGMember.setGroupJobTitle(groupJobTitle);
				gatherInfoMemberService.update(dbGMember);
			}
			return AjaxResult.success();
		} catch (Exception e) {
			return AjaxResult.error(e.getMessage());
		}
	}

	/**
	 * 保存保险信息
	 * 
	 * @param memberInfo
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "saveInsuranceInfo", method = RequestMethod.POST)
	public AjaxResult saveInsuranceInfo(MemberInfo memberInfo) throws Exception {
		try {
			if (StringUtils.isNotEmpty(memberInfo.getId())) {
				MemberInfo dbInfo = memberInfoService.get(memberInfo.getId());
				MyBeanUtils.copyBeanNotNull2Bean(memberInfo, dbInfo);
				if (StringUtils.isEmpty(dbInfo.getPerfectState())) {
					dbInfo.setPerfectState("1");
				}
				memberInfoService.update(dbInfo);
			} else {
				memberInfoService.insert(memberInfo);
			}
			return AjaxResult.success();
		} catch (Exception e) {
			return AjaxResult.error(e.getMessage());
		}
	}

	/**
	 * 保存人员信息
	 * 
	 * @param project
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "saveItineraryInfo", method = RequestMethod.POST)
	public AjaxResult saveItineraryInfo(GatherInfoMember memberInfo, GatherInfoMemberInput input) throws Exception {
		try {
			if (StringUtils.isNotEmpty(input.getGoDate())) {
				Date goTime = DateUtils.parse(input.getGoDate(), "yyyy-MM-dd HH:mm");
				memberInfo.setGoTime(goTime);
			} else {
				memberInfo.setGoTime(null);
			}

			if (StringUtils.isNotEmpty(input.getBackDate())) {
				Date backTime = DateUtils.parse(input.getBackDate(), "yyyy-MM-dd HH:mm");
				memberInfo.setBackTime(backTime);
			} else {
				memberInfo.setBackTime(null);
			}

			if (StringUtils.isNotEmpty(input.getGoDepartDate())) {
				Date goDate = DateUtils.parse(input.getGoDepartDate(), "yyyy-MM-dd HH:mm");
				memberInfo.setGoDepartTime(goDate);
			} else {
				memberInfo.setGoDepartTime(null);
			}

			if (StringUtils.isNotEmpty(input.getBackDepartDate())) {
				Date backDate = DateUtils.parse(input.getBackDepartDate(), "yyyy-MM-dd HH:mm");
				memberInfo.setBackDepartTime(backDate);
			} else {
				memberInfo.setBackDepartTime(null);
			}

			if (StringUtils.isEmpty(memberInfo.getId())) {
				memberInfo.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
				String memberId = RealmUtils.getCurrentUser().getId();
				memberInfo.setCreateBy(memberId);
				memberInfo.setUpdateBy(memberId);
				memberInfo.setItineraryStatus("1");
				gatherInfoMemberService.insert(memberInfo);
			} else {
				GatherInfoMember t = gatherInfoMemberService.get(memberInfo.getId());
				MyBeanUtils.copyBeanNotNull2Bean(memberInfo, t);
				if (StringUtils.isEmpty(t.getItineraryStatus())) {
					t.setItineraryStatus("1");
				}
				t.setGoTime(memberInfo.getGoTime());
				t.setBackTime(memberInfo.getBackTime());
				t.setGoDepartTime(memberInfo.getGoDepartTime());
				t.setBackDepartTime(memberInfo.getBackDepartTime());

				gatherInfoMemberService.update(t);

			}
			return AjaxResult.success();
		} catch (Exception e) {
			return AjaxResult.error(e.getMessage());
		}
	}

	/**
	 * 删除用户信息
	 * 
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public AjaxResult delete(String id) {
		if (StringUtils.isEmpty(id)) {
			return AjaxResult.error("ID不能为空");
		}
		gatherInfoMemberService.delete(id);
		return AjaxResult.success();
	}

	/**
	 * 手动同步用户信息 暂时解决赛事那边基本信息显示问题
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "syncMemberInfo", method = RequestMethod.POST)
	public AjaxResult syncMemberInfo(String id) throws Exception {
		if (StringUtils.isEmpty(id)) {
			return AjaxResult.error("信息收集id不能为空");
		}
		GatherInfoMember infoMember = gatherInfoMemberService.get(id);
		if (infoMember == null) {
			return AjaxResult.error("信息收集对象不存在");
		}
		if (StringUtils.isEmpty(infoMember.getMemberId())) {
			return AjaxResult.error("用户id不能为空");
		}
		Member member = gatherInfoMembersBizService.updateMemberBaseInfo(infoMember, infoMember.getMemberId());
		if (member == null) {
			return AjaxResult.error("用户不存在");
		}
		
		gatherInfoMembersBizService.updateMemberSatelliteInfo(infoMember, infoMember.getMemberId());
		return AjaxResult.success();
	}
}
