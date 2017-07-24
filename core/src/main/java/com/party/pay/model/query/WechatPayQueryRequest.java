package com.party.pay.model.query;

import com.party.common.annotation.Ignore;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 微信查询接口请求参数
 * 
 * @author Administrator
 *
 */
public class WechatPayQueryRequest {
	// 公众账号ID
	@XStreamAlias("appid")
	private String appId;

	// 商户号
	@XStreamAlias("mch_id")
	private String mchId;

	// 随机字符串
	@XStreamAlias("nonce_str")
	private String nonceStr;

	// 商户订单号
	@XStreamAlias("out_trade_no")
	private String outTradeNo;

	// 签名
	@Ignore
	private String sign;

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

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public String getNonceStr() {
		return nonceStr;
	}

	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

}
