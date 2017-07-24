package com.party.core.model.wallet;

import com.party.core.model.BaseModel;

/**
 * 提现
 * 
 * @author Administrator
 *
 */
public class Withdrawals extends BaseModel {

	private static final long serialVersionUID = -86613760375597595L;

	private Integer status; // 状态
	private float payment; // 金额
	private String name; // 账户
	private String phone; // 账户所留手机号
	private String bankName; // 提现银行
	private String accountNumber; // 提现账号
	private String openedPlace; // 开户行

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public float getPayment() {
		return payment;
	}

	public void setPayment(float payment) {
		this.payment = payment;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getOpenedPlace() {
		return openedPlace;
	}

	public void setOpenedPlace(String openedPlace) {
		this.openedPlace = openedPlace;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

}
