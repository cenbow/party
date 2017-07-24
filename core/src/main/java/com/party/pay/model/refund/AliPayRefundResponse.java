package com.party.pay.model.refund;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 支付宝退款响应参数
 * 
 * @author yy-pc
 *
 */
public class AliPayRefundResponse {
	// 网关返回码
	private String code;

	// 网关返回码描述
	private String msg;

	// 业务返回码
	private String subCode;

	// 业务返回码描述
	private String subMsg;

	// 用户的登录id
	@XStreamAlias("buyer_logon_id")
	private String buyerLogonId;

	// 买家在支付宝的用户id
	@XStreamAlias("buyer_user_id")
	private String buyerUserId;

	// 本次退款是否发生了资金变化
	@XStreamAlias("fund_change")
	private String fundChange;

	// 退款支付时间
	@XStreamAlias("gmt_refund_pay")
	private String gmtRefundPay;

	@XStreamAlias("open_id")
	private String openId;

	// 商户订单号
	@XStreamAlias("out_trade_no")
	private String outTradeNo;

	// 退款总金额
	@XStreamAlias("refund_fee")
	private String refundFee;

	@XStreamAlias("send_back_fee")
	private String sendBackFee;

	// 支付宝交易号
	@XStreamAlias("trade_no")
	private String tradeNo;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getBuyerLogonId() {
		return buyerLogonId;
	}

	public void setBuyerLogonId(String buyerLogonId) {
		this.buyerLogonId = buyerLogonId;
	}

	public String getBuyerUserId() {
		return buyerUserId;
	}

	public void setBuyerUserId(String buyerUserId) {
		this.buyerUserId = buyerUserId;
	}

	public String getFundChange() {
		return fundChange;
	}

	public void setFundChange(String fundChange) {
		this.fundChange = fundChange;
	}

	public String getGmtRefundPay() {
		return gmtRefundPay;
	}

	public void setGmtRefundPay(String gmtRefundPay) {
		this.gmtRefundPay = gmtRefundPay;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public String getRefundFee() {
		return refundFee;
	}

	public void setRefundFee(String refundFee) {
		this.refundFee = refundFee;
	}

	public String getSendBackFee() {
		return sendBackFee;
	}

	public void setSendBackFee(String sendBackFee) {
		this.sendBackFee = sendBackFee;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getSubCode() {
		return subCode;
	}

	public void setSubCode(String subCode) {
		this.subCode = subCode;
	}

	public String getSubMsg() {
		return subMsg;
	}

	public void setSubMsg(String subMsg) {
		this.subMsg = subMsg;
	}

}
