package com.party.core.service.member;

import com.party.core.model.member.MemberMerchant;
import com.party.core.service.IBaseService;

import java.util.List;

public interface IMemberMerchantService extends IBaseService<MemberMerchant> {
	/**
	 * 根据memberID查找商户信息
	 * 
	 * @param memberId
	 * @return
	 */
	MemberMerchant findByMemberId(String memberId);

	/**
	 * 根据商户号查找商户信息
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

	/**
	 * 是否拥有商户信息
	 * @param memberId 会员编号
	 * @return 是否（true/false）
	 */
	boolean hasMerchant(String memberId);
}
