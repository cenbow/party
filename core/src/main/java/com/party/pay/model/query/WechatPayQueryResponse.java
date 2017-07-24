package com.party.pay.model.query;

import com.party.common.annotation.Ignore;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 微信查询接口响应
 * 
 * @author Administrator
 *
 */
public class WechatPayQueryResponse {
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

	// 随机字符串
	@XStreamAlias("nonce_str")
	private String nonceStr;

	// 签名
	@Ignore
	private String sign;

	// 商户订单号
	@XStreamAlias("out_trade_no")
	private String outTradeNo;

	// 微信订单号
	@XStreamAlias("transaction_id")
	private String transactionId;

	// 应用APPID
	@XStreamAlias("appid")
	private String appid;

	// 商户号
	@XStreamAlias("mch_id")
	private String mchId;

	// 订单支付时间，格式为yyyyMMddHHmmss
	@XStreamAlias("time_end")
	private String timeEnd;

	// 用户标识
	@XStreamAlias("openid")
	private String openid;

	/**
	 * 交易类型 JSAPI，NATIVE，APP，MICROPAY
	 */
	@XStreamAlias("trade_type")
	private String tradeType;

	/**
	 * 交易状态 SUCCESS—支付成功
	 * 
	 * REFUND—转入退款
	 * 
	 * NOTPAY—未支付
	 * 
	 * CLOSED—已关闭
	 * 
	 * REVOKED—已撤销（刷卡支付）
	 * 
	 * USERPAYING--用户支付中
	 * 
	 * PAYERROR--支付失败(其他原因，如银行返回失败)
	 */
	@XStreamAlias("trade_state")
	private String tradeState;

	// 付款银行
	@XStreamAlias("bank_type")
	private String bankType;

	// 订单总金额，单位为分
	@XStreamAlias("total_fee")
	private String totalFee;

	@XStreamAlias("cash_fee")
	private String cashFee;

	@XStreamAlias("fee_type")
	private String feeType;

	@XStreamAlias("is_subscribe")
	private String isSubscribe;

	// 应结订单金额
	@XStreamAlias("settlement_total_fee")
	private int settlementTotalFee;

	// 交易状态描述
	@XStreamAlias("trade_state_desc")
	private String tradeStateDesc;

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

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
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

	public String getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(String timeEnd) {
		this.timeEnd = timeEnd;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public String getTradeState() {
		return tradeState;
	}

	public void setTradeState(String tradeState) {
		this.tradeState = tradeState;
	}

	public String getBankType() {
		return bankType;
	}

	public void setBankType(String bankType) {
		this.bankType = bankType;
	}

	public String getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}

	public int getSettlementTotalFee() {
		return settlementTotalFee;
	}

	public void setSettlementTotalFee(int settlementTotalFee) {
		this.settlementTotalFee = settlementTotalFee;
	}

	public String getTradeStateDesc() {
		return tradeStateDesc;
	}

	public void setTradeStateDesc(String tradeStateDesc) {
		this.tradeStateDesc = tradeStateDesc;
	}

	public String getCashFee() {
		return cashFee;
	}

	public void setCashFee(String cashFee) {
		this.cashFee = cashFee;
	}

	public String getFeeType() {
		return feeType;
	}

	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}

	public String getIsSubscribe() {
		return isSubscribe;
	}

	public void setIsSubscribe(String isSubscribe) {
		this.isSubscribe = isSubscribe;
	}

}
