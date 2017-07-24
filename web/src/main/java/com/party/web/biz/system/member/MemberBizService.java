package com.party.web.biz.system.member;

import com.party.notify.notifyPush.servce.INotifySendService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.party.common.utils.PinyinUtil;
import com.party.core.model.member.Expert;
import com.party.core.model.member.Member;
import com.party.core.service.member.IExpertService;
import com.party.core.service.member.IMemberService;
import com.party.core.service.message.IMessageService;
import com.party.core.service.message.IMessageSetService;
import com.party.web.utils.MyBeanUtils;
import com.party.web.utils.RealmUtils;

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
	INotifySendService notifySendService;


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
			if (StringUtils.isNotEmpty(member.getPassword())) {
				member.setPassword(RealmUtils.encryptPassword(member.getPassword()));
			} else {
				member.setPassword(RealmUtils.encryptPassword("123456"));
			}
			member.setPicNum(0);

			memberService.insert(member);// 保存
			messageSetService.initMessageSet(member);
			return member;
		} else {
			// 编辑
			Member t = memberService.get(member.getId());// 从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(member, t);// 将编辑表单中的非NULL值覆盖数据库记录中的值
			if (StringUtils.isNotEmpty(member.getPassword())) {
				member.setPassword(RealmUtils.encryptPassword(member.getPassword()));
			}
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

}
