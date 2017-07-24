package com.party.core.service.member;

import com.party.core.model.member.MemberBank;
import com.party.core.service.IBaseService;

public interface IMemberBankService extends IBaseService<MemberBank> {

	MemberBank findByMemberId(String memberId);

}
