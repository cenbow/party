package com.party.admin.biz.system;

import com.party.admin.biz.crowdfund.ProjectBizService;
import com.party.common.qcloud.picapi.UploadResult;
import com.party.core.service.picCloud.PicCloudBizService;
import com.party.notify.notifyPush.servce.INotifySendService;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.party.admin.utils.MyBeanUtils;
import com.party.admin.utils.PasswordUtils;
import com.party.common.utils.PinyinUtil;
import com.party.core.model.member.Expert;
import com.party.core.model.member.Member;
import com.party.core.model.system.MemberSysRole;
import com.party.core.model.system.SysRole;
import com.party.core.service.member.IExpertService;
import com.party.core.service.member.IMemberService;
import com.party.core.service.message.IMessageService;
import com.party.core.service.message.IMessageSetService;
import com.party.core.service.system.IMemberSysRoleService;
import com.party.core.service.system.ISysRoleService;

import java.util.List;

@Service
public class MemberBizService {

	@Autowired
	IMemberService memberService;

	@Autowired
	IMessageSetService messageSetService;

	@Autowired
	IMessageService messageService;

	@Autowired
	IExpertService expertService;
	
	@Autowired
	ISysRoleService sysRoleService;
	
	@Autowired
	IMemberSysRoleService memberSysRoleService;

	@Autowired
	INotifySendService notifySendService;

	@Autowired
	private PicCloudBizService picCloudBizService;

	protected static Logger logger = LoggerFactory.getLogger(MemberBizService.class);
	/**
	 * 保存业务数据
	 * 
	 * @param member
	 * @return
	 * @throws Exception
	 */
	public Member saveBiz(Member member) throws Exception {
		if (StringUtils.isEmpty(member.getId())) {
			// 新增
			String realname = member.getRealname();
			if (StringUtils.isNotBlank(realname)) {
				realname = filterEmoji(realname, "");
				member.setRealname(realname);
				member.setPinyin(PinyinUtil.hanziToPinyin(realname, ""));
			}
			member.setPassword(PasswordUtils.entryptPassword("123456"));
			member.setPicNum(0);

			String memberId = memberService.insert(member);// 保存
			messageSetService.initMessageSet(member);

			String roleName = "";
			if (member.getIsDistributor() != null && member.getIsDistributor() == 1) {
				roleName = "分销商";
			}
			if (member.getIsPartner() != null && member.getIsPartner() == 1) {
				roleName = "合作商";
			}
			if (StringUtils.isNotEmpty(roleName)) {
				SysRole sysRole = sysRoleService.uniqueProperty("name", roleName);
				MemberSysRole memberSysRole = new MemberSysRole(memberId, sysRole.getId());
				memberSysRoleService.insert(memberSysRole);
			}
			return member;
		} else {
			// 编辑
			Member t = memberService.get(member.getId());// 从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(member, t);// 将编辑表单中的非NULL值覆盖数据库记录中的值

			String realname = t.getRealname();
			if (StringUtils.isNoneBlank(realname)) {
				realname = filterEmoji(realname, "");
				t.setRealname(realname);
				t.setPinyin(PinyinUtil.hanziToPinyin(realname, ""));
			}
			memberService.update(t);
			messageSetService.initMessageSet(t);
			return t;
		}
	}

	/**
	 * emoji表情替换
	 *
	 * @param source
	 *            原字符串
	 * @param slipStr
	 *            emoji表情替换成的字符串
	 * @return 过滤后的字符串
	 */
	public String filterEmoji(String source, String slipStr) {
		if (StringUtils.isNotBlank(source)) {
			return source.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", slipStr);
		} else {
			return source;
		}
	}

	/**
	 * 保存达人
	 * 
	 * @param entity
	 */
	public void saveExpert(Member entity) {
		Expert ex = expertService.findByMemberId(entity.getId());
		Integer isExpert = entity.getIsExpert();
		if (ex == null && isExpert != null && isExpert == 1) {
			ex = new Expert();
			ex.setMemberId(entity.getId());
			expertService.insert(ex);
			notifySendService.sendExpertApprove(entity.getId());
		} else if (ex != null && isExpert != null && isExpert == 0) {
			expertService.delete(ex.getId());
			notifySendService.sendExpertCancel(entity.getId());
		}
	}

	/**
	 * 若用户头像不是万象优图的路径，则修改
	 */
	public void updateMemberLogoToCloud(Integer limit){
		List<Member> remoteLogoList = memberService.getRemoteLogoList(limit);
		for(Member member:remoteLogoList){
			UploadResult upload = picCloudBizService.upload(member.getLogo());
			if(upload != null){
				logger.info("万象优图更新用户{}头像{}为{}",member.getId(),member.getLogo(), upload.downloadUrl);
				member.setLogo(upload.downloadUrl);
				memberService.update(member);
			}
		}
	}
}
