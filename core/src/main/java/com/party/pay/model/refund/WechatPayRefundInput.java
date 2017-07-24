package com.party.pay.model.refund;

/**
 * 输入参数实体
 * 
 * @author yy-pc
 *
 */
public class WechatPayRefundInput {
	// 微信接口密钥
	private String apiKey;

	private String appId;

	private String mchId;

	// 微信APP商户证书
	private String appCert;

	// 微信公众平台商户证书
	private String publicCert;

	// 微信退款接口路径
	private String refundUrl;
	
	// 退款证书存放路径
	private String certPath;

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getAppCert() {
		return appCert;
	}

	public void setAppCert(String appCert) {
		this.appCert = appCert;
	}

	public String getPublicCert() {
		return publicCert;
	}

	public void setPublicCert(String publicCert) {
		this.publicCert = publicCert;
	}

	public String getRefundUrl() {
		return refundUrl;
	}

	public void setRefundUrl(String refundUrl) {
		this.refundUrl = refundUrl;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getMchId() {
		return mchId;
	}

	public void setMchId(String mchId) {
		this.mchId = mchId;
	}

	public String getCertPath() {
		return certPath;
	}

	public void setCertPath(String certPath) {
		this.certPath = certPath;
	}

}
