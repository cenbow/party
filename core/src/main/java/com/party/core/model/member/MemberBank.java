package com.party.core.model.member;

import com.party.core.model.BaseModel;

/**
 * 用户银行卡信息实体
 * 
 * @author Administrator
 *
 */
public class MemberBank extends BaseModel {

	private static final long serialVersionUID = 4789149441318848139L;
	private String memberId; // 对应用户
	private String name; // 姓名
	private String phone; // 手机号码
	private String bankName; // 银行名称
	private String accountNumber; // 银行卡号
	private String openedPlace; // 开户行

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getOpenedPlace() {
		return openedPlace;
	}

	public void setOpenedPlace(String openedPlace) {
		this.openedPlace = openedPlace;
	}

}
