package com.party.core.model.member;

import java.io.Serializable;

import com.party.core.model.BaseModel;

/**
 * 用户商户信息实体
 * 
 * @author Administrator
 *
 */
public class MemberMerchant extends BaseModel implements Serializable {

	private static final long serialVersionUID = -2064387171309006161L;
	private Integer openStatus; // 是否启用
	private String memberId; // 对应用户
	private String officialAccountName; // 公众号名称
	private String officialAccountId; // 公众号ID appid
	private String officialAccountSecret; // 公众号的appsecret
	private String merchantName; // 商户名称
	private String merchantId; // 商户号 mchId
	private String merchantApiKey; // 商户平台api密钥 apiKey

	public String getOfficialAccountName() {
		return officialAccountName;
	}

	public void setOfficialAccountName(String officialAccountName) {
		this.officialAccountName = officialAccountName;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMerchantApiKey() {
		return merchantApiKey;
	}

	public void setMerchantApiKey(String merchantApiKey) {
		this.merchantApiKey = merchantApiKey;
	}

	public String getOfficialAccountId() {
		return officialAccountId;
	}

	public void setOfficialAccountId(String officialAccountId) {
		this.officialAccountId = officialAccountId;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getOfficialAccountSecret() {
		return officialAccountSecret;
	}

	public void setOfficialAccountSecret(String officialAccountSecret) {
		this.officialAccountSecret = officialAccountSecret;
	}

	public Integer getOpenStatus() {
		return openStatus;
	}

	public void setOpenStatus(Integer openStatus) {
		this.openStatus = openStatus;
	}

}
