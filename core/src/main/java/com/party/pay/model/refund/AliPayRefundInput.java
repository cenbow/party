package com.party.pay.model.refund;

/**
 * 输入参数实体
 * 
 * @author yy-pc
 *
 */
public class AliPayRefundInput {
	// 支付网关
	private String gateway;

	// 接口名称
	private String method;

	// 应用id
	private String appId;

	// 应用私钥
	private String privateKey;

	// 支付宝公钥
	private String publickey;

	// 签名方式
	private String signType;

	public String getGateway() {
		return gateway;
	}

	public void setGateway(String gateway) {
		this.gateway = gateway;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	public String getPublickey() {
		return publickey;
	}

	public void setPublickey(String publickey) {
		this.publickey = publickey;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

}
