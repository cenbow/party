package com.party.core.dao.read.member;

import org.springframework.stereotype.Repository;

import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.member.MemberMerchant;

import java.util.List;

/**
 * 商户数据读取
 * 
 * @author Administrator
 *
 */
@Repository
public interface MemberMerchantReadDao extends BaseReadDao<MemberMerchant> {

	/**
	 * 根据用户读取
	 * 
	 * @param memberId
	 * @return
	 */
	MemberMerchant findByMemberId(String memberId);

	/**
	 * 根据商户号读取
	 * 
	 * @param merchantId
	 * @return
	 */
	List<MemberMerchant> findByMerchantId(String merchantId);

	/**
	 * 根据微信账户查询商户信息
	 * @param accountId 微信账户
	 * @return 商户信息
	 */
	MemberMerchant findByAccountId(String accountId);
}
