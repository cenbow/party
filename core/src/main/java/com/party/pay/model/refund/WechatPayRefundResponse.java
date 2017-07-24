package com.party.pay.model.refund;

import com.party.common.annotation.Ignore;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 微信退款响应
 * 
 * @author Administrator
 *
 */
public class WechatPayRefundResponse {
	// 返回状态码
	@XStreamAlias("return_code")
	private String returnCode;

	// 返回信息
	@XStreamAlias("return_msg")
	private String returnMsg;

	// 业务结果
	@XStreamAlias("result_code")
	private String resultCode;

	// 错误代码
	@XStreamAlias("err_code")
	private String errCode;

	// 错误代码描述
	@XStreamAlias("err_code_des")
	private String errCodeDes;

	// 应用APPID
	private String appid;

	// 商户号
	@XStreamAlias("mch_id")
	private String mchId;

	// 随机字符串
	@XStreamAlias("nonce_str")
	private String nonceStr;

	// 签名
	@Ignore
	private String sign;

	// 微信订单号
	@XStreamAlias("transaction_id")
	private String transactionId;

	// 商户系统内部的退款单号
	@XStreamAlias("out_refund_no")
	private String outRefundNo;

	// 商户订单号
	@XStreamAlias("out_trade_no")
	private String outTradeNo;

	// 微信退款单号
	@XStreamAlias("refund_id")
	private String refundId;

	// 微信退款单号 ORIGINAL—原路退款 BALANCE—退回到余额
	@XStreamAlias("refund_channel")
	private String refundChannel;

	// 退款总金额,单位为分,可以做部分退款
	@XStreamAlias("refund_fee")
	private int refundFee;

	// 订单总金额，单位为分
	@XStreamAlias("total_fee")
	private String totalFee;

	// 现金支付金额，单位为分
	@XStreamAlias("cash_fee")
	private String cashFee;

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public String getReturnMsg() {
		return returnMsg;
	}

	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getErrCodeDes() {
		return errCodeDes;
	}

	public void setErrCodeDes(String errCodeDes) {
		this.errCodeDes = errCodeDes;
	}

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

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
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

	public String getRefundId() {
		return refundId;
	}

	public void setRefundId(String refundId) {
		this.refundId = refundId;
	}

	public String getRefundChannel() {
		return refundChannel;
	}

	public void setRefundChannel(String refundChannel) {
		this.refundChannel = refundChannel;
	}

	public int getRefundFee() {
		return refundFee;
	}

	public void setRefundFee(int refundFee) {
		this.refundFee = refundFee;
	}

	public String getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}

	public String getCashFee() {
		return cashFee;
	}

	public void setCashFee(String cashFee) {
		this.cashFee = cashFee;
	}

}
