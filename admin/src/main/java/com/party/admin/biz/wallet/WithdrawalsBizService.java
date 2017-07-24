package com.party.admin.biz.wallet;

import org.springframework.stereotype.Service;

import com.party.common.utils.StringUtils;

@Service
public class WithdrawalsBizService {
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
}
