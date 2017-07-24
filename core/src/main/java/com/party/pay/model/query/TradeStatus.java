package com.party.pay.model.query;

import java.util.Map;

import com.google.common.collect.Maps;
import com.party.core.model.order.OrderStatus;

/**
 * 交易状态
 * 
 * @author Administrator
 *
 */
public enum TradeStatus {
	FREE("FREE", "免费"),
	
	ZCXM("ZCXM", "众筹项目"),
	
	WX_SUCCESS("SUCCESS", "支付成功"),

	WX_REFUND("REFUND", "已退款"),

	WX_NOTPAY("NOTPAY", "未支付"),

	WX_CLOSED("CLOSED", "已关闭"),

	WX_USERPAYING("USERPAYING", "用户支付中"),

	WX_PAYERROR("PAYERROR", "支付失败"),

	WX_ORDERNOTEXIST("ORDERNOTEXIST", "交易不存在"),

	ALI_WAIT_BUYER_PAY("WAIT_BUYER_PAY", "待支付"),

	ALI_TRADE_CLOSED("TRADE_CLOSED", "已关闭"),

	ALI_TRADE_SUCCESS("TRADE_SUCCESS", "支付成功"),

	ALI_TRADE_FINISHED("TRADE_FINISHED", "交易结束"),

	ALI_TRADE_NOT_EXIST("ACQ.TRADE_NOT_EXIST", "交易不存在");

	// 状态码
	private String code;

	// 状态值
	private String value;

	TradeStatus(String code, String value) {
		this.code = code;
		this.value = value;
	}

	/**
	 * 根据状态码获取状态值
	 * 
	 * @param code
	 *            状态码
	 * @return 状态值
	 */
	public static String getValue(String code) {
		for (OrderStatus orderType : OrderStatus.values()) {
			if (orderType.getCode().equals(code)) {
				return orderType.getValue();
			}
		}
		return null;
	}

	/**
	 * 枚举类型转换为map
	 * 
	 * @return 转换后的map
	 */
	public static Map<Integer, String> convertMap() {
		Map<Integer, String> map = Maps.newHashMap();
		for (OrderStatus orderType : OrderStatus.values()) {
			map.put(orderType.getCode(), orderType.getValue());
		}
		return map;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
