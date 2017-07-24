package com.party.mobile.biz.gatherInfo;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.party.common.utils.DateUtils;
import com.party.common.utils.PinyinUtil;
import com.party.common.utils.StringUtils;
import com.party.core.model.BaseModel;
import com.party.core.model.city.Area;
import com.party.core.model.gatherInfo.GatherInfoMember;
import com.party.core.model.gatherInfo.GatherInfoProject;
import com.party.core.model.member.Industry;
import com.party.core.model.member.Member;
import com.party.core.model.member.MemberInfo;
import com.party.core.service.city.IAreaService;
import com.party.core.service.gatherInfo.IGatherInfoMemberService;
import com.party.core.service.gatherInfo.IGatherInfoProjectService;
import com.party.core.service.gatherInfo.biz.GatherInfoMembersBizService;
import com.party.core.service.member.IIndustryService;
import com.party.core.service.member.IMemberInfoService;
import com.party.core.service.member.IMemberService;
import com.party.mobile.biz.currentUser.CurrentUserBizService;
import com.party.mobile.biz.member.MemberBizService;
import com.party.mobile.biz.verifyCode.VerifyCodeBizService;
import com.party.mobile.utils.MyBeanUtils;
import com.party.mobile.web.dto.gatherInfo.input.GatherInfoMemberInput;
import com.party.mobile.web.dto.gatherInfo.output.GatherInfoMemberOutput;
import com.party.mobile.web.dto.login.output.CurrentUser;
import com.party.mobile.web.dto.login.output.MemberOutput;

@Service
public class GatherInfoMemberBizService {

	@Autowired
	private GatherInfoMembersBizService gatherInfoMembersBizService;
	@Autowired
	private IGatherInfoMemberService gatherInfoMemberService;
	@Autowired
	private IGatherInfoProjectService gatherInfoProjectService;
	@Autowired
	private IMemberService memberService;
	@Autowired
	private IAreaService areaService;
	@Autowired
	private IIndustryService industryService;
	@Autowired
	private IMemberInfoService memberInfoService;
	@Autowired
	private MemberBizService memberBizService;
	@Autowired
	private VerifyCodeBizService verifyCodeBizService;
	@Autowired
	private CurrentUserBizService currentUserBizService;

	/**
	 * 所有用户自主填写的内容都需过滤Emoji表情
	 * 
	 * @param memberInfo
	 * @return
	 */
	public GatherInfoMember filterEmoji(GatherInfoMember memberInfo) {
		memberInfo.setGoDepartCity(PinyinUtil.filterEmoji(memberInfo.getGoDepartCity(), ""));
		memberInfo.setGoType(PinyinUtil.filterEmoji(memberInfo.getGoType(), ""));
		memberInfo.setGoStation(PinyinUtil.filterEmoji(memberInfo.getGoStation(), ""));
		memberInfo.setGoNumber(PinyinUtil.filterEmoji(memberInfo.getGoNumber(), ""));

		memberInfo.setBackDepartCity(PinyinUtil.filterEmoji(memberInfo.getBackDepartCity(), ""));
		memberInfo.setBackType(PinyinUtil.filterEmoji(memberInfo.getBackType(), ""));
		memberInfo.setBackStation(PinyinUtil.filterEmoji(memberInfo.getBackStation(), ""));
		memberInfo.setBackNumber(PinyinUtil.filterEmoji(memberInfo.getBackNumber(), ""));
		return memberInfo;
	}

	/**
	 * 获取行程信息
	 * 
	 * @param projectId
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getItineraryInfo(String projectId, HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		/************************ 项目信息 ************************/
		GatherInfoProject project = gatherInfoProjectService.get(projectId);
		map.put("project", project);
		/************************ 基本信息 ************************/
		CurrentUser currentUser = currentUserBizService.getCurrentUser(request);
		MemberOutput memberOutput = fillMember(currentUser.getId());
		map.put("member", memberOutput);
		/************************ 行程信息 ************************/
		GatherInfoMemberOutput output = new GatherInfoMemberOutput();
		GatherInfoMember memberInfoDB = gatherInfoMemberService.findByProjectAndMember(currentUser.getId(), projectId);
		if (memberInfoDB != null) {
			MyBeanUtils.copyBeanNotNull2Bean(memberInfoDB, output);

			if (memberInfoDB.getGoDepartTime() != null) {
				String formatDate = DateUtils.formatDate(memberInfoDB.getGoDepartTime(), "yyyy-MM-dd'T'HH:mm");
				output.setGoDepartTimeStr(formatDate);
			}

			if (memberInfoDB.getGoTime() != null) {
				String formatDate = DateUtils.formatDate(memberInfoDB.getGoTime(), "yyyy-MM-dd'T'HH:mm");
				output.setGoTimeStr(formatDate);
			}

			if (memberInfoDB.getBackDepartTime() != null) {
				String formatDate = DateUtils.formatDate(memberInfoDB.getBackDepartTime(), "yyyy-MM-dd'T'HH:mm");
				output.setBackDepartTimeStr(formatDate);
			}

			if (memberInfoDB.getBackTime() != null) {
				String formatDate = DateUtils.formatDate(memberInfoDB.getBackTime(), "yyyy-MM-dd'T'HH:mm");
				output.setBackTimeStr(formatDate);
			}
		}
		map.put("memberInfo", output);
		return map;
	}

	/**
	 * 添加人员到项目中
	 * 
	 * @param projectId
	 *            项目id
	 * @param memberId
	 *            人员id
	 * @return
	 */
	public GatherInfoMember addMember(String projectId, String memberId) {
		GatherInfoMember memberInfoDB = gatherInfoMemberService.findByProjectAndMember(memberId, projectId);
		if (memberInfoDB == null) {
			memberInfoDB = new GatherInfoMember();
			memberInfoDB.setProjectId(projectId);
			memberInfoDB.setMemberId(memberId);
			memberInfoDB.setCreateBy(memberId);
			memberInfoDB.setUpdateBy(memberId);
			memberInfoDB.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
			memberInfoDB.setItineraryStatus("1");
		}

		if (StringUtils.isEmpty(memberInfoDB.getId())) {
			gatherInfoMemberService.insert(memberInfoDB);
		} else {
			gatherInfoMemberService.update(memberInfoDB);
		}
		return memberInfoDB;
	}

	/**
	 * 保存用户信息
	 * 
	 * @param currentId
	 * 
	 * @param t
	 * @param member
	 * @param goTimeStr
	 * @param backTimeStr
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public GatherInfoMember saveItineraryInfo(String currentId, GatherInfoMember t, Member member, GatherInfoMemberInput input,
			HttpServletRequest request) throws Exception {
		/************************ 基本信息 ************************/
		gatherInfoMembersBizService.updateMemberBaseInfo(member, currentId);
		/************************ 行程信息 ************************/
		// 验证当前登录用户数据
		GatherInfoMember memberInfoDB = addMember(t.getProjectId(), currentId);
		MyBeanUtils.copyBeanNotNull2Bean(t, memberInfoDB);

		// 所有用户自主填写的内容都需过滤Emoji表情
		filterEmoji(memberInfoDB);

		// 启程出发时间
		Date goDepartTime = parseDateStr(input.getGoDepartTimeStr());
		if (goDepartTime != null) {
			memberInfoDB.setGoDepartTime(goDepartTime);
		}

		// 启程到达时间
		Date goTime = parseDateStr(input.getGoTimeStr());
		if (goTime != null) {
			memberInfoDB.setGoTime(goTime);
		}

		// 返程出发时间
		Date backDepartTime = parseDateStr(input.getBackDepartTimeStr());
		if (backDepartTime != null) {
			memberInfoDB.setBackDepartTime(backDepartTime);
		}

		// 返程到达时间
		Date backTime = parseDateStr(input.getBackTimeStr());
		if (backTime != null) {
			memberInfoDB.setBackTime(backTime);
		}
		gatherInfoMemberService.update(memberInfoDB);
		return memberInfoDB;
	}

	private Date parseDateStr(String dateStr) throws ParseException {
		if (StringUtils.isNotEmpty(dateStr)) {
			Date datetime = DateUtils.parse(dateStr.replace("T", " "), "yyyy-MM-dd HH:mm");
			return datetime;
		}
		return null;
	}

	/**
	 * 填充用户
	 * 
	 * @param memberId
	 * @return
	 */
	public MemberOutput fillMember(String memberId) {
		Member member = memberService.get(memberId);// 根据当前登录用户获取会员实体
		MemberOutput memberOutput = MemberOutput.transform(member);
		// 设置城市名字
		if (!Strings.isNullOrEmpty(member.getCity())) {
			Area area = areaService.get(member.getCity());
			if (null != area) {
				memberOutput.setCityId(area.getId());
				memberOutput.setCityName(area.getName());
			}
		}
		// 设置行业名字
		if (!Strings.isNullOrEmpty(member.getIndustry())) {
			Industry industry = industryService.get(member.getIndustry());
			if (null != industry) {
				memberOutput.setIndustryId(industry.getId());
				memberOutput.setIndustry(industry.getName());
			}
		}
		return memberOutput;
	}

	/**
	 * 保险保险信息
	 * 
	 * @param currentId
	 * @param memberInfo
	 * @param member
	 * @throws Exception
	 */
	public void saveInsuranceInfo(String currentId, MemberInfo memberInfo, Member member) throws Exception {
		/************************ 基本信息 ************************/
		gatherInfoMembersBizService.updateMemberBaseInfo(member, currentId);

		/************************ 保险信息 ************************/
		String infoId = gatherInfoMembersBizService.updateMemberSatelliteInfo(memberInfo, currentId);

		MemberInfo dbInfo = memberInfoService.get(infoId);
		dbInfo.setPerfectState("1");
		memberInfoService.update(dbInfo);
	}

	/**
	 * 获取保险信息
	 * 
	 * @param request
	 * @return
	 */
	public Map<String, Object> getInsuranceInfo(String projectId, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		/************************ 项目信息 ************************/
		GatherInfoProject project = gatherInfoProjectService.get(projectId);
		map.put("project", project);
		/************************ 基本信息 ************************/
		CurrentUser currentUser = currentUserBizService.getCurrentUser(request);
		Member member = memberService.get(currentUser.getId());
		map.put("member", member);
		/************************ 保险信息 ************************/
		MemberInfo memberInfo = memberInfoService.findByMemberId(currentUser.getId());
		if (memberInfo == null) {
			memberInfo = new MemberInfo();
		}
		map.put("memberInfo", memberInfo);
		return map;
	}

	/**
	 * 获取基本信息
	 * 
	 * @param projectId
	 * @param request
	 * @return
	 */
	public Map<String, Object> getBaseInfo(String projectId, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		/************************ 项目信息 ************************/
		GatherInfoProject project = gatherInfoProjectService.get(projectId);
		map.put("project", project);
		/************************ 基本信息 ************************/
		CurrentUser currentUser = currentUserBizService.getCurrentUser(request);
		MemberOutput memberOutput = fillMember(currentUser.getId());
		map.put("memberInfo", memberOutput);
		return map;
	}

	/**
	 * 保存基本信息
	 * 
	 * @param id
	 * @param memberInfo
	 * @param request
	 */
	public void saveBaseInfo(String currentId, Member member, HttpServletRequest request) {
		/************************ 基本信息 ************************/
		gatherInfoMembersBizService.updateMemberBaseInfo(member, currentId);

		Member dbMember = memberService.get(currentId);
		dbMember.setPerfectState("1");
		memberService.update(dbMember);
	}

	/**
	 * 绑定手机
	 * 
	 * @param member
	 * @param verifyCode
	 * @param request
	 * @param memberOutput
	 * @return
	 */
	public CurrentUser bindPhone(Member member, String verifyCode, HttpServletRequest request, MemberOutput memberOutput) {
		// 获取登陆用户
		CurrentUser currentUser = currentUserBizService.getCurrentUser(request);
		// 如果当前登录用户是第三方授权用户，且手机号为空，则需要绑定手机号
		if (CurrentUser.isThirdparty(currentUser)) {
			// 验证码验证
			boolean verifyResult = verifyCodeBizService.verify(member.getMobile(), verifyCode);
			if (verifyResult) {
				// 绑定手机号
				memberOutput = memberBizService.bindPhone(member.getMobile(), member.getRealname(), member.getCompany(), member.getJobTitle(),
						member.getIndustry(), request);
				// 当前登录用户需重新赋值
				currentUser = currentUserBizService.getCurrentUserByToken(memberOutput.getToken());
			}
		}
		return currentUser;
	}
}
