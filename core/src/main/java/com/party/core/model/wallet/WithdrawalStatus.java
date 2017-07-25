package com.party.core.model.wallet;

import java.util.Map;

import com.google.common.collect.Maps;
import com.party.core.model.order.OrderStatus;

public enum WithdrawalStatus {
	STATUS_IN_REVIEW(1, "处理中"),

	STATUS_HAVE_PAID(2, "已付款"),

	STATUS_AUDIT_REJECT(3, "已拒绝");

	// 状态码
	private Integer code;

	// 状态值
	private String value;

	WithdrawalStatus(Integer code, String value) {
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
	public static String getValue(Integer code) {
		for (WithdrawalStatus status : WithdrawalStatus.values()) {
			if (status.getCode().equals(code)) {
				return status.getValue();
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

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
