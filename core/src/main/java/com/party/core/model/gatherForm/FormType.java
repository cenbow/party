package com.party.core.model.gatherForm;

/**
 * 表单字段类型
 * 
 * @author Administrator
 *
 */
public enum FormType {
	INPUT(0, "input"), RADIO(1, "radio"), CHECKBOX(2, "checkbox"), TEXTAREA(3, "textarea"), SELECT(4, "select");

	FormType(Integer code, String value) {
		this.code = code;
		this.value = value;
	}

	// 状态码
	private Integer code;

	// 状态值
	private String value;

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
