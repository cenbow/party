package com.party.pay.model.refund;

import com.party.common.annotation.Ignore;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 微信退款参数
 * 
 * @author Administrator
 *
 */
public class WechatPayRefundRequest {

	// 微信开放平台审核通过的应用APPID
	private String appid;

	// 微信支付分配的商户号
	@XStreamAlias("mch_id")
	private String mchId;

	// 随机字符串，不长于32位
	@XStreamAlias("nonce_str")
	private String nonceStr;

	// 签名
	@Ignore
	private String sign;

	// 操作员帐号, 默认为商户号
	@XStreamAlias("op_user_id")
	private String opUserId;

	// 商户系统内部的退款单号
	@XStreamAlias("out_refund_no")
	private String outRefundNo;

	// 商户订单号
	@XStreamAlias("out_trade_no")
	private String outTradeNo;

	// 微信生成的订单号
	@XStreamAlias("transaction_id")
	private String transactionId;

	// 退款总金额，订单总金额，单位为分
	@XStreamAlias("refund_fee")
	private int refundFee;

	// 订单总金额，单位为分
	@XStreamAlias("total_fee")
	private int totalFee;
	
	//支付类型 JSAPI/APP
    @XStreamAlias("trade_type")
    private String tradeType;

	//退款资金来源
	@XStreamAlias("refund_account")
	private String refundAccount;

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getMchId() {
		return mchId;
	}

	public void setMchId(String mchId) {
		this.mchId = mchId;
	}

	public String getNonceStr() {
		return nonceStr;
	}

	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}

	public String getOpUserId() {
		return opUserId;
	}

	public void setOpUserId(String opUserId) {
		this.opUserId = opUserId;
	}

	public String getOutRefundNo() {
		return outRefundNo;
	}

	public void setOutRefundNo(String outRefundNo) {
		this.outRefundNo = outRefundNo;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public int getRefundFee() {
		return refundFee;
	}

	public void setRefundFee(int refundFee) {
		this.refundFee = refundFee;
	}

	public int getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(int totalFee) {
		this.totalFee = totalFee;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public String getRefundAccount() {
		return refundAccount;
	}

	public void setRefundAccount(String refundAccount) {
		this.refundAccount = refundAccount;
	}
}
