package com.party.core.dao.read.wallet;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.party.common.paging.Page;
import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.wallet.Withdrawals;

@Repository
public interface WithdrawalsReadDao extends BaseReadDao<Withdrawals> {

	/**
	 * 获取总金额
	 * 
	 * @param params
	 * 
	 * @param orderForm
	 * @return
	 */
	Double getTotalPayment(@Param("withdrawal") Withdrawals withdrawal, @Param("params") Map<String, Object> params);

	List<Withdrawals> webListPage(@Param("withdrawal") Withdrawals withdrawal, @Param("params") Map<String, Object> params, Page page);
}
