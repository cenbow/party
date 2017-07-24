package com.party.core.service.wallet.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.party.common.paging.Page;
import com.party.core.dao.read.wallet.WithdrawalsReadDao;
import com.party.core.dao.write.wallet.WithdrawalsWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.wallet.Withdrawals;
import com.party.core.service.wallet.IWithdrawalService;
import com.sun.istack.NotNull;

@Service
public class WithdrawalServiceImpl implements IWithdrawalService {

	@Autowired
	private WithdrawalsReadDao withdrawalsReadDao;

	@Autowired
	private WithdrawalsWriteDao withdrawalsWriteDao;

	/**
	 * 插入会员信息
	 * 
	 * @param bank
	 *            会员信息
	 * @return 插入结果（true/false）
	 */
	public String insert(Withdrawals bank) {
		BaseModel.preInsert(bank);
		bank.setUpdateDate(null);
		boolean result = withdrawalsWriteDao.insert(bank);
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
	public boolean update(Withdrawals bank) {
		// bank.setUpdateDate(new Date());
		return withdrawalsWriteDao.update(bank);
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
		return withdrawalsWriteDao.deleteLogic(id);
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
		return withdrawalsWriteDao.delete(id);
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
		return withdrawalsWriteDao.batchDeleteLogic(ids);
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
		return withdrawalsWriteDao.batchDelete(ids);
	}

	/**
	 * 根据主键获取会员信息
	 * 
	 * @param id
	 *            主键
	 * @return 会员信息
	 */
	public Withdrawals get(String id) {
		return withdrawalsReadDao.get(id);
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
	public List<Withdrawals> listPage(Withdrawals Merchant, Page page) {
		return withdrawalsReadDao.listPage(Merchant, page);
	}

	/**
	 * 查询所有会员信息
	 * 
	 * @param Merchant
	 *            会员信息
	 * @return 会员列表
	 */
	public List<Withdrawals> list(Withdrawals Merchant) {
		return withdrawalsReadDao.listPage(Merchant, null);
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
	public List<Withdrawals> batchList(@NotNull Set<String> ids, Withdrawals Merchant, Page page) {
		if (CollectionUtils.isEmpty(ids)) {
			return Collections.EMPTY_LIST;
		}
		return withdrawalsReadDao.batchList(ids, new HashedMap(), page);
	}

	@Override
	public Double getTotalPayment(Withdrawals withdrawals, Map<String, Object> params) {
		return withdrawalsReadDao.getTotalPayment(withdrawals, params);
	}

	@Override
	public List<Withdrawals> webListPage(Withdrawals withdrawal, Map<String, Object> params, Page page) {
		return withdrawalsReadDao.webListPage(withdrawal, params, page);
	}

}
