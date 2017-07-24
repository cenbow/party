package com.party.core.dao.read.member;

import org.springframework.stereotype.Repository;

import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.member.MemberBank;

/**
 * 用户银行卡数据读取
 * 
 * @author Administrator
 *
 */
@Repository
public interface MemberBankReadDao extends BaseReadDao<MemberBank> {

	MemberBank findByMemberId(String memberId);
}
