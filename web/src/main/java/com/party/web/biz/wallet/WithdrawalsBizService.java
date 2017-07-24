package com.party.web.biz.wallet;

import com.google.common.collect.Lists;
import com.party.common.paging.Page;
import com.party.common.utils.BigDecimalUtils;
import com.party.common.utils.LangUtils;
import com.party.common.utils.StringUtils;
import com.party.core.model.BaseModel;
import com.party.core.model.wallet.Withdrawals;
import com.party.core.service.wallet.IWithdrawalService;
import com.party.web.utils.RealmUtils;
import com.party.web.web.dto.output.wallet.WithdrawalOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WithdrawalsBizService {

	@Autowired
	private IWithdrawalService withdrawalService;

	/**
	 * 格式化银行账号
	 * 
	 * @param account
	 * @return
	 */
	public String formatAccountNumber(String account) {
		StringBuilder sb = new StringBuilder(account);
		if (StringUtils.isNotEmpty(account)) {
			int length = account.length() / 4 + account.length();

			for (int i = 0; i < length; i++) {
				if (i % 5 == 0) {
					sb.insert(i, " ");
				}
			}
		}
		if (sb.length() > 0) {
			sb.deleteCharAt(0);
		}
		return sb.toString();
	}

	/**
	 * 用户提现列表
	 * @param page
	 * @return
	 */
	public List<WithdrawalOutput> withdrawList(Page page) {
		Withdrawals withdrawal = new Withdrawals();
		withdrawal.setCreateBy(RealmUtils.getCurrentUser().getId());
		withdrawal.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
		List<Withdrawals> withdrawals = withdrawalService.listPage(withdrawal, page);
		List<WithdrawalOutput> withdrawalOutputs = LangUtils.transform(withdrawals, input -> {
			WithdrawalOutput output = WithdrawalOutput.transform(input);
			String account = input.getAccountNumber();
			account = formatAccountNumber(account);
			output.setAccountNumber(account);

			// 手续费
			double serviceFee = BigDecimalUtils.mul(input.getPayment(), 0.006);
			serviceFee = BigDecimalUtils.round(serviceFee, 2);
			// 净额
			double netAmount = BigDecimalUtils.sub(input.getPayment(), serviceFee);

			output.setServiceFee(serviceFee);
			output.setNetAmount(netAmount);
			return output;
		});
		return withdrawalOutputs;
	}
}
