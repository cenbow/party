package com.party.web.web.dto.output.wallet;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import org.apache.commons.beanutils.BeanUtils;

import com.party.common.annotation.ExcelField;
import com.party.core.model.wallet.Withdrawals;

public class WithdrawalOutput {
	// 主键
	private String id;

	// 创建人
	private String createBy;

	// 创建时间
	private Date createDate;

	// 更新人
	private String updateBy;

	// 更新时间
	private Date updateDate;

	// 备注
	private String remarks;

	// 删除标记
	private String delFlag;

	private String status2; // 导出字段

	private Integer status; // 状态
	private float payment; // 金额
	private String name; // 账户
	private String phone; // 账户所留手机号
	private String bankName; // 提现银行
	private String accountNumber; // 提现账号
	private String openedPlace; // 开户行

	private double serviceFee; // 手续费
	private double netAmount; // 支付净额

	@ExcelField(title = "应扣手续费", align = 2, sort = 5)
	public double getServiceFee() {
		return serviceFee;
	}

	public void setServiceFee(double serviceFee) {
		this.serviceFee = serviceFee;
	}

	@ExcelField(title = "支付净额", align = 2, sort = 6)
	public double getNetAmount() {
		return netAmount;
	}

	public void setNetAmount(double netAmount) {
		this.netAmount = netAmount;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	@ExcelField(title = "提现时间", align = 2, sort = 7)
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	@ExcelField(title = "到账时间", align = 2, sort = 8)
	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@ExcelField(title = "提现金额", align = 2, sort = 4)
	public float getPayment() {
		return payment;
	}

	public void setPayment(float payment) {
		this.payment = payment;
	}

	@ExcelField(title = "开户名", align = 2, sort = 3)
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

	@ExcelField(title = "提现银行", align = 2, sort = 3)
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	@ExcelField(title = "提现账号", align = 2, sort = 1)
	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	@ExcelField(title = "开户行", align = 2, sort = 2)
	public String getOpenedPlace() {
		return openedPlace;
	}

	public void setOpenedPlace(String openedPlace) {
		this.openedPlace = openedPlace;
	}

	@ExcelField(title = "状态", align = 2, sort = 9)
	public String getStatus2() {
		return status2;
	}

	public void setStatus2(String status2) {
		this.status2 = status2;
	}

	public static WithdrawalOutput transform(Withdrawals withdrawals) {
		WithdrawalOutput output = new WithdrawalOutput();
		try {
			BeanUtils.copyProperties(output, withdrawals);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return output;
	}

}
