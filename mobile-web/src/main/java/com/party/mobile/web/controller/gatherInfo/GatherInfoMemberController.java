package com.party.mobile.web.controller.gatherInfo;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Strings;
import com.party.authorization.annotation.Authorization;
import com.party.common.utils.PartyCode;
import com.party.common.utils.StringUtils;
import com.party.core.model.gatherInfo.GatherInfoMember;
import com.party.core.model.member.Member;
import com.party.core.model.member.MemberInfo;
import com.party.core.service.gatherInfo.IGatherInfoMemberService;
import com.party.mobile.biz.gatherInfo.GatherInfoMemberBizService;
import com.party.mobile.web.dto.AjaxResult;
import com.party.mobile.web.dto.gatherInfo.input.GatherInfoMemberInput;
import com.party.mobile.web.dto.login.output.CurrentUser;
import com.party.mobile.web.dto.login.output.MemberOutput;

/**
 * 用户信息收集
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "gatherInfo/member")
public class GatherInfoMemberController {
	@Autowired
	private GatherInfoMemberBizService gatherInfoMemberBizService;
	@Autowired
	private IGatherInfoMemberService gatherInfoMemberService;
	
	protected static Logger logger = LoggerFactory.getLogger(GatherInfoMemberController.class);

	/**
	 * 获取基本信息
	 * 
	 * @param projectId
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Authorization
	@ResponseBody
	@RequestMapping(value = "/getBaseInfo", method = RequestMethod.POST)
	public AjaxResult getBaseInfo(String projectId, HttpServletRequest request) throws Exception {
		// 数据验证
		if (Strings.isNullOrEmpty(projectId)) {
			return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, "项目主键不能为空");
		}
		Map<String, Object> map = gatherInfoMemberBizService.getBaseInfo(projectId, request);
		return AjaxResult.success(map);
	}

	/**
	 * 基本信息收集
	 * 
	 * @param baseInfoInput
	 *            输入视图
	 * @param result
	 *            验证结果
	 * @return 交互数据
	 */
	@ResponseBody
	@Authorization
	@RequestMapping(value = "/saveBaseInfo", method = RequestMethod.POST)
	public AjaxResult saveBaseInfo(Member memberInfo, String verifyCode, String projectId, HttpServletRequest request) {
		if (StringUtils.isEmpty(projectId)) {
			return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, "项目id不能为空");
		}
		try {
			MemberOutput memberOutput = new MemberOutput();
			CurrentUser currentUser = gatherInfoMemberBizService.bindPhone(memberInfo, verifyCode, request, memberOutput);
			gatherInfoMemberBizService.saveBaseInfo(currentUser.getId(), memberInfo, request);
			memberOutput = gatherInfoMemberBizService.fillMember(currentUser.getId());
			
			gatherInfoMemberBizService.addMember(projectId, currentUser.getId());
			return AjaxResult.success(memberOutput);
		} catch (Exception e) {
			logger.error("", e);
			return AjaxResult.error(100, "保存信息失敗");
		}
	}

	/**
	 * 获取保险信息
	 * 
	 * @param request
	 * @return
	 */
	@Authorization
	@ResponseBody
	@RequestMapping(value = "/getInsuranceInfo", method = RequestMethod.POST)
	public AjaxResult getInsuranceInfo(String projectId, HttpServletRequest request) {
		// 数据验证
		if (Strings.isNullOrEmpty(projectId)) {
			return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, "项目主键不能为空");
		}
		Map<String, Object> map = gatherInfoMemberBizService.getInsuranceInfo(projectId, request);
		return AjaxResult.success(map);
	}

	/**
	 * 保险保险信息
	 * 
	 * @param memberInfo
	 * @param member
	 * @param verifyCode
	 * @param request
	 * @return
	 */
	@ResponseBody
	@Authorization
	@RequestMapping("saveInsuranceInfo")
	public AjaxResult saveInsuranceInfo(MemberInfo memberInfo, Member member, String verifyCode, String projectId, HttpServletRequest request) {
		try {
			MemberOutput memberOutput = new MemberOutput();
			CurrentUser currentUser = gatherInfoMemberBizService.bindPhone(member, verifyCode, request, memberOutput);
			gatherInfoMemberBizService.saveInsuranceInfo(currentUser.getId(), memberInfo, member);
			memberOutput = gatherInfoMemberBizService.fillMember(currentUser.getId());
			gatherInfoMemberBizService.addMember(projectId, currentUser.getId());
			return AjaxResult.success(memberOutput);
		} catch (Exception e) {
			return AjaxResult.error(100, "保存信息失敗");
		}
	}

	/**
	 * 获取信息
	 * 
	 * @param id
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Authorization
	@ResponseBody
	@RequestMapping(value = "/getItineraryInfo", method = RequestMethod.POST)
	public AjaxResult getItineraryInfo(String projectId, HttpServletRequest request) throws Exception {
		// 数据验证
		if (Strings.isNullOrEmpty(projectId)) {
			return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, "项目主键不能为空");
		}
		Map<String, Object> map = gatherInfoMemberBizService.getItineraryInfo(projectId, request);
		return AjaxResult.success(map);
	}

	/**
	 * 行程信息收集
	 * 
	 * @param baseInfoInput
	 *            输入视图
	 * @param result
	 *            验证结果
	 * @return 交互数据
	 */
	@ResponseBody
	@Authorization
	@RequestMapping(value = "/saveItineraryInfo", method = RequestMethod.POST)
	public AjaxResult saveItineraryInfo(GatherInfoMember memberInfo, Member member, GatherInfoMemberInput input, HttpServletRequest request) {
		try {
			MemberOutput memberOutput = new MemberOutput();
			CurrentUser currentUser = gatherInfoMemberBizService.bindPhone(member, input.getVerifyCode(), request, memberOutput);
			gatherInfoMemberBizService.saveItineraryInfo(currentUser.getId(), memberInfo, member, input, request);
			memberOutput = gatherInfoMemberBizService.fillMember(currentUser.getId());
			return AjaxResult.success(memberOutput);
		} catch (Exception e) {
			return AjaxResult.error(100, "保存信息失敗");
		}
	}
}
