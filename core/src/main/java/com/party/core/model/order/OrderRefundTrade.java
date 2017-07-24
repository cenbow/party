package com.party.core.model.order;

import com.party.core.model.BaseModel;

/**
 * 退款交易Entity
 * 
 * @author wei.li
 * @version 2016/7/14
 */
public class OrderRefundTrade extends BaseModel {

	private static final long serialVersionUID = -1117695402070621757L;

	// 支付类型
	private Integer type;

	// 本地订单编号
	private String orderFormId;

	// 交易订单号
	private String transactionId;

	// 订单交易详情
	private String data;

	// 订单处理数据来源（0：商品，1：活动）
	private Integer origin;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getOrderFormId() {
		return orderFormId;
	}

	public void setOrderFormId(String orderFormId) {
		this.orderFormId = orderFormId;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Integer getOrigin() {
		return origin;
	}

	public void setOrigin(Integer origin) {
		this.origin = origin;
	}

}
