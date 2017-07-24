package com.party.core.dao.write.member;

import org.springframework.stereotype.Repository;

import com.party.core.dao.write.BaseWriteDao;
import com.party.core.model.member.MemberBank;

/**
 * 用户银行数据写入
 * 
 * @author Administrator
 *
 */
@Repository
public interface MemberBankWriteDao extends BaseWriteDao<MemberBank> {
}
