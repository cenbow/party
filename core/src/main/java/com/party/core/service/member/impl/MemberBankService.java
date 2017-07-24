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
import com.party.core.dao.read.member.MemberBankReadDao;
import com.party.core.dao.write.member.MemberBankWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.member.MemberBank;
import com.party.core.service.member.IMemberBankService;
import com.sun.istack.NotNull;

@Service
public class MemberBankService implements IMemberBankService {
	@Autowired
	private MemberBankReadDao memberBankReadDao;

	@Autowired
	private MemberBankWriteDao memberBankWriteDao;

	/**
	 * 插入会员信息
	 * 
	 * @param bank
	 *            会员信息
	 * @return 插入结果（true/false）
	 */
	public String insert(MemberBank bank) {
		BaseModel.preInsert(bank);
		boolean result = memberBankWriteDao.insert(bank);
		if (result) {
			return bank.getId();
		}
		return null;
	}

	/**
	 * 更新会员信息
	 * 
	 * @param bank
	 *            会员信息
	 * @return 更新结果（true/false）
	 */
	public boolean update(MemberBank bank) {
		bank.setUpdateDate(new Date());
		return memberBankWriteDao.update(bank);
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
		return memberBankWriteDao.deleteLogic(id);
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
		return memberBankWriteDao.delete(id);
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
		return memberBankWriteDao.batchDeleteLogic(ids);
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
		return memberBankWriteDao.batchDelete(ids);
	}

	/**
	 * 根据主键获取会员信息
	 * 
	 * @param id
	 *            主键
	 * @return 会员信息
	 */
	public MemberBank get(String id) {
		return memberBankReadDao.get(id);
	}

	/**
	 * 分页查询会员列表
	 * 
	 * @param Merchant
	 *            会员信息
	 * @param page
	 *            分页信息
	 * @return 会员列表
	 */
	public List<MemberBank> listPage(MemberBank Merchant, Page page) {
		return memberBankReadDao.listPage(Merchant, page);
	}

	/**
	 * 查询所有会员信息
	 * 
	 * @param Merchant
	 *            会员信息
	 * @return 会员列表
	 */
	public List<MemberBank> list(MemberBank Merchant) {
		return memberBankReadDao.listPage(Merchant, null);
	}

	/**
	 * 分页查询会员信息
	 * 
	 * @param ids
	 *            主键集合
	 * @param Merchant
	 *            会员信息
	 * @param page
	 *            分页信息
	 * @return 会员列表
	 */
	public List<MemberBank> batchList(@NotNull Set<String> ids, MemberBank Merchant, Page page) {
		if (CollectionUtils.isEmpty(ids)) {
			return Collections.EMPTY_LIST;
		}
		return memberBankReadDao.batchList(ids, new HashedMap(), page);
	}

	@Override
	public MemberBank findByMemberId(String memberId) {
		return memberBankReadDao.findByMemberId(memberId);
	}

}
