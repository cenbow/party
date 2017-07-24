package com.party.pay.model.query;

import java.util.Date;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 支付宝退款响应参数
 * 
 * @author yy-pc
 *
 */
public class AliPayQueryResponse {
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

	@XStreamAlias("buyer_pay_amount")
	private String buyerPayAmount;

	// 买家在支付宝的用户id
	@XStreamAlias("buyer_user_id")
	private String buyerUserId;

	@XStreamAlias("invoice_amount")
	private String invoiceAmount;

	@XStreamAlias("open_id")
	private String openId;

	// 商户订单号
	@XStreamAlias("out_trade_no")
	private String outTradeNo;

	@XStreamAlias("point_amount")
	private String pointAmount;

	// 实收金额，单位为元，两位小数
	@XStreamAlias("receipt_amount")
	private String receiptAmount;

	// 本次交易打款给卖家的时间
	@XStreamAlias("send_pay_date")
	private String sendPayDate;

	// 交易的订单金额，单位为元，两位小数
	@XStreamAlias("total_amount")
	private String totalAmount;

	// 支付宝交易号
	@XStreamAlias("trade_no")
	private String tradeNo;

	/**
	 * 交易状态： WAIT_BUYER_PAY（交易创建，等待买家付款）、 TRADE_CLOSED（未付款交易超时关闭，或支付完成后全额退款）、
	 * TRADE_SUCCESS（交易支付成功）、 TRADE_FINISHED（交易结束，不可退款）
	 */
	@XStreamAlias("trade_status")
	private String tradeStatus;

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

	public String getBuyerLogonId() {
		return buyerLogonId;
	}

	public void setBuyerLogonId(String buyerLogonId) {
		this.buyerLogonId = buyerLogonId;
	}

	public String getTradeStatus() {
		return tradeStatus;
	}

	public void setTradeStatus(String tradeStatus) {
		this.tradeStatus = tradeStatus;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getReceiptAmount() {
		return receiptAmount;
	}

	public void setReceiptAmount(String receiptAmount) {
		this.receiptAmount = receiptAmount;
	}

	public String getSendPayDate() {
		return sendPayDate;
	}

	public void setSendPayDate(String sendPayDate) {
		this.sendPayDate = sendPayDate;
	}

	public String getBuyerUserId() {
		return buyerUserId;
	}

	public void setBuyerUserId(String buyerUserId) {
		this.buyerUserId = buyerUserId;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getBuyerPayAmount() {
		return buyerPayAmount;
	}

	public void setBuyerPayAmount(String buyerPayAmount) {
		this.buyerPayAmount = buyerPayAmount;
	}

	public String getInvoiceAmount() {
		return invoiceAmount;
	}

	public void setInvoiceAmount(String invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getPointAmount() {
		return pointAmount;
	}

	public void setPointAmount(String pointAmount) {
		this.pointAmount = pointAmount;
	}

}
