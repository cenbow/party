package com.party.core.service.gatherInfo.biz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.party.common.utils.PinyinUtil;
import com.party.common.utils.StringUtils;
import com.party.core.model.gatherInfo.GatherInfoMember;
import com.party.core.model.member.Member;
import com.party.core.model.member.MemberInfo;
import com.party.core.service.member.IMemberInfoService;
import com.party.core.service.member.IMemberService;

/**
 * 信息收集业务
 * 
 * @author Administrator
 *
 */
@Service
public class GatherInfoMembersBizService {

	@Autowired
	private IMemberService memberService;
	@Autowired
	private IMemberInfoService memberInfoService;

	/**
	 * 更新用户基本信息
	 * 
	 * @param memberInfo
	 * @param memberId
	 */
	public Member updateMemberBaseInfo(GatherInfoMember memberInfo, String memberId) {
		Member memberNew = memberService.get(memberId);
		if (memberNew == null) {
			return null;
		}
		// 头像
		if (StringUtils.isNotEmpty(memberInfo.getLogo())) {
			memberNew.setLogo(memberInfo.getLogo());
		}
		// 真实姓名
		if (StringUtils.isNotEmpty(memberInfo.getRealname())) {
			memberNew.setFullname(PinyinUtil.filterEmoji(memberInfo.getRealname(), ""));
		}
		// 性别
		if (null != memberInfo.getSex()) {
			memberNew.setSex(memberInfo.getSex());
		}
		// 微信号
		if (StringUtils.isNotEmpty(memberInfo.getWechatNum())) {
			memberNew.setWechatNum(PinyinUtil.filterEmoji(memberInfo.getWechatNum(), ""));
		}
		// 公司
		if (StringUtils.isNotEmpty(memberInfo.getCompany())) {
			memberNew.setCompany(PinyinUtil.filterEmoji(memberInfo.getCompany(), ""));
		}
		// 职称
		if (StringUtils.isNotEmpty(memberInfo.getJobTitle())) {
			memberNew.setJobTitle(PinyinUtil.filterEmoji(memberInfo.getJobTitle(), ""));
		}
		// 行业
		if (StringUtils.isNotEmpty(memberInfo.getIndustryId())) {
			memberNew.setIndustry(memberInfo.getIndustryId());
		}
		// 城市
		if (StringUtils.isNotEmpty(memberInfo.getCityId())) {
			memberNew.setCity(memberInfo.getCityId());
		}
		// 个性签名
		if (StringUtils.isNotEmpty(memberInfo.getIntroduction())) {
			memberNew.setSignature(PinyinUtil.filterEmoji(memberInfo.getIntroduction(), ""));
		}
		memberService.update(memberNew);
		return memberNew;
	}

	/**
	 * 更新用户附属信息
	 * 
	 * @param infoMember
	 * @param memberId
	 */
	public void updateMemberSatelliteInfo(GatherInfoMember infoMember, String memberId) {
		MemberInfo memberInfo = memberInfoService.findByMemberId(memberId);
		if (memberInfo == null) {
			memberInfo = new MemberInfo();
		}
		memberInfo.setMemberId(memberId);
//		memberInfo.setSize(PinyinUtil.filterEmoji(infoMember.getSize(), ""));
		memberInfo.setIdCard(PinyinUtil.filterEmoji(infoMember.getIdCard(), ""));
		memberInfo.setBloodGroup(PinyinUtil.filterEmoji(infoMember.getBloodGroup(), ""));
		memberInfo.setMedicalHistory(PinyinUtil.filterEmoji(infoMember.getMedicalHistory(), ""));
		memberInfo.setContactMobile(PinyinUtil.filterEmoji(infoMember.getContactMobile(), ""));
		memberInfo.setContactName(PinyinUtil.filterEmoji(infoMember.getContactName(), ""));
		memberInfo.setContactRelation(PinyinUtil.filterEmoji(infoMember.getContactRelation(), ""));

		if (StringUtils.isEmpty(memberInfo.getId())) {
			memberInfoService.insert(memberInfo);
		} else {
			memberInfoService.update(memberInfo);
		}
	}

	/**
	 * 前端-更新用户附属信息
	 * 
	 * @param infoMember
	 * @param memberId
	 */
	public String updateMemberSatelliteInfo(MemberInfo infoMember, String memberId) {
		MemberInfo memberInfo = memberInfoService.findByMemberId(memberId);
		if (memberInfo == null) {
			memberInfo = new MemberInfo();
		}
		memberInfo.setMemberId(memberId);
		// memberInfo.setSize(PinyinUtil.filterEmoji(infoMember.getSize(), ""));
		memberInfo.setIdCard(PinyinUtil.filterEmoji(infoMember.getIdCard(), ""));
		memberInfo.setBloodGroup(PinyinUtil.filterEmoji(infoMember.getBloodGroup(), ""));
		memberInfo.setMedicalHistory(PinyinUtil.filterEmoji(infoMember.getMedicalHistory(), ""));
		memberInfo.setContactMobile(PinyinUtil.filterEmoji(infoMember.getContactMobile(), ""));
		memberInfo.setContactName(PinyinUtil.filterEmoji(infoMember.getContactName(), ""));
		memberInfo.setContactRelation(PinyinUtil.filterEmoji(infoMember.getContactRelation(), ""));

		if (StringUtils.isEmpty(memberInfo.getId())) {
			memberInfoService.insert(memberInfo);
		} else {
			memberInfoService.update(memberInfo);
		}
		
		return memberInfo.getId(); 
	}

	/**
	 * 前端-更新用户基本信息
	 * 
	 * @param memberInfo
	 * @param memberId
	 * @return
	 */
	public Member updateMemberBaseInfo(Member memberInfo, String memberId) {
		Member memberNew = memberService.get(memberId);
		if (memberNew == null) {
			return null;
		}
		// 头像
		if (StringUtils.isNotEmpty(memberInfo.getLogo())) {
			memberNew.setLogo(memberInfo.getLogo());
		}
		// 真实姓名
		if (StringUtils.isNotEmpty(memberInfo.getRealname())) {
			memberNew.setFullname(PinyinUtil.filterEmoji(memberInfo.getRealname(), ""));
		}
		// 性别
		if (null != memberInfo.getSex()) {
			memberNew.setSex(memberInfo.getSex());
		}
		// 微信号
		if (StringUtils.isNotEmpty(memberInfo.getWechatNum())) {
			memberNew.setWechatNum(PinyinUtil.filterEmoji(memberInfo.getWechatNum(), ""));
		}
		// 公司
		if (StringUtils.isNotEmpty(memberInfo.getCompany())) {
			memberNew.setCompany(PinyinUtil.filterEmoji(memberInfo.getCompany(), ""));
		}
		// 职称
		if (StringUtils.isNotEmpty(memberInfo.getJobTitle())) {
			memberNew.setJobTitle(PinyinUtil.filterEmoji(memberInfo.getJobTitle(), ""));
		}
		// 行业
		if (StringUtils.isNotEmpty(memberInfo.getIndustry())) {
			memberNew.setIndustry(memberInfo.getIndustry());
		}
		// 城市
		if (StringUtils.isNotEmpty(memberInfo.getCity())) {
			memberNew.setCity(memberInfo.getCity());
		}
		// 个性签名
		if (StringUtils.isNotEmpty(memberInfo.getSignature())) {
			memberNew.setSignature(PinyinUtil.filterEmoji(memberInfo.getSignature(), ""));
		}
		memberService.update(memberNew);
		return memberNew;
	}
}
