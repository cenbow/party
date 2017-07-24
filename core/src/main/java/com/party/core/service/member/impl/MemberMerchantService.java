package com.party.core.service.member.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.party.common.paging.Page;
import com.party.core.dao.read.member.MemberMerchantReadDao;
import com.party.core.dao.write.member.MemberMerchantWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.member.MemberMerchant;
import com.party.core.service.member.IMemberMerchantService;
import com.sun.istack.NotNull;

@Service
public class MemberMerchantService implements IMemberMerchantService {

	@Autowired
	private MemberMerchantReadDao memberMerchantReadDao;

	@Autowired
	private MemberMerchantWriteDao memberMerchantWriteDao;

	/**
	 * 插入会员信息
	 * 
	 * @param merchant
	 *            会员信息
	 * @return 插入结果（true/false）
	 */
	public String insert(MemberMerchant merchant) {
		BaseModel.preInsert(merchant);
		boolean result = memberMerchantWriteDao.insert(merchant);
		if (result) {
			return merchant.getId();
		}
		return null;
	}

	/**
	 * 更新会员信息
	 * 
	 * @param merchant
	 *            会员信息
	 * @return 更新结果（true/false）
	 */
	public boolean update(MemberMerchant merchant) {
		merchant.setUpdateDate(new Date());
		return memberMerchantWriteDao.update(merchant);
	}

	/**
	 * 逻辑删除会员信息
	 * 
	 * @param id
	 *            会员主键
	 * @return 删除结果（true/false）
	 */
	public boolean deleteLogic(@NotNull String id) {
		if (Strings.isNullOrEmpty(id)) {
			return false;
		}
		return memberMerchantWriteDao.deleteLogic(id);
	}

	/**
	 * 物理删除会员信息
	 * 
	 * @param id
	 *            会员主键
	 * @return 删除结果（true/false）
	 */
	public boolean delete(@NotNull String id) {
		if (Strings.isNullOrEmpty(id)) {
			return false;
		}
		return memberMerchantWriteDao.delete(id);
	}

	/**
	 * 批量逻辑删除会员信息
	 * 
	 * @param ids
	 *            主键集合
	 * @return 删除结果（true/false）
	 */
	public boolean batchDeleteLogic(@NotNull Set<String> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			return false;
		}
		return memberMerchantWriteDao.batchDeleteLogic(ids);
	}

	/**
	 * 批量物理删除会员信息
	 * 
	 * @param ids
	 *            主键集合
	 * @return 删除结果（true/false）
	 */
	public boolean batchDelete(@NotNull Set<String> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			return false;
		}
		return memberMerchantWriteDao.batchDelete(ids);
	}

	/**
	 * 根据主键获取会员信息
	 * 
	 * @param id
	 *            主键
	 * @return 会员信息
	 */
	public MemberMerchant get(String id) {
		return memberMerchantReadDao.get(id);
	}

	/**
	 * 分页查询会员列表
	 * 
	 * @param merchant
	 *            会员信息
	 * @param page
	 *            分页信息
	 * @return 会员列表
	 */
	public List<MemberMerchant> listPage(MemberMerchant merchant, Page page) {
		return memberMerchantReadDao.listPage(merchant, page);
	}

	/**
	 * 查询所有会员信息
	 * 
	 * @param merchant
	 *            会员信息
	 * @return 会员列表
	 */
	public List<MemberMerchant> list(MemberMerchant merchant) {
		return memberMerchantReadDao.listPage(merchant, null);
	}

	/**
	 * 分页查询会员信息
	 * 
	 * @param ids
	 *            主键集合
	 * @param merchant
	 *            会员信息
	 * @param page
	 *            分页信息
	 * @return 会员列表
	 */
	public List<MemberMerchant> batchList(@NotNull Set<String> ids, MemberMerchant merchant, Page page) {
		if (CollectionUtils.isEmpty(ids)) {
			return Collections.EMPTY_LIST;
		}
		return memberMerchantReadDao.batchList(ids, new HashedMap(), page);
	}


	/**
	 * 是否拥有商户信息
	 * @param memberId 会员编号
	 * @return 是否（true/false）
	 */
	@Override
	public boolean hasMerchant(String memberId) {
		MemberMerchant memberMerchant = this.findByMemberId(memberId);
		if (null == memberMerchant){
			return false;
		}
		return true;
	}

	@Override
	public MemberMerchant findByMemberId(String memberId) {
		return memberMerchantReadDao.findByMemberId(memberId);
	}

	@Override
	public List<MemberMerchant> findByMerchantId(String merchantId) {
		return memberMerchantReadDao.findByMerchantId(merchantId);
	}

	/**
	 * 根据微信账户查询商户信息
	 * @param accountId 微信账户
	 * @return 微信商户信息
	 */
	@Override
	public MemberMerchant findByAccountId(String accountId) {
		return memberMerchantReadDao.findByAccountId(accountId);
	}
}
