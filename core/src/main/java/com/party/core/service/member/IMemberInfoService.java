package com.party.core.service.member;

import com.party.core.model.member.MemberInfo;
import com.party.core.service.IBaseService;

/**
 * 用户附属信息
 * 
 * @author Administrator
 *
 */
public interface IMemberInfoService extends IBaseService<MemberInfo> {
	MemberInfo findByMemberId(String memberId);
}
