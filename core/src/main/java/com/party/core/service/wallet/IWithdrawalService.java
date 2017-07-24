package com.party.core.service.wallet;

import java.util.List;
import java.util.Map;

import com.party.common.paging.Page;
import com.party.core.model.wallet.Withdrawals;
import com.party.core.service.IBaseService;

public interface IWithdrawalService extends IBaseService<Withdrawals> {

	/**
	 * 获取总金额
	 * 
	 * @param orderForm
	 * @return
	 */
	Double getTotalPayment(Withdrawals withdrawals, Map<String, Object> params);

	List<Withdrawals> webListPage(Withdrawals withdrawal, Map<String, Object> params, Page page);
}
