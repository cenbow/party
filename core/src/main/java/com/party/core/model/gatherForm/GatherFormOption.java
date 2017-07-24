package com.party.core.model.gatherForm;

import com.party.core.model.BaseModel;

/**
 * 表单选项
 * 
 * @author Administrator
 *
 */
public class GatherFormOption extends BaseModel {
	private static final long serialVersionUID = 7478872474976192674L;
	private String name;
	private String fieldId; // 字段id
	private Integer sort;

	public GatherFormOption(String fieldId) {
		super();
		this.fieldId = fieldId;
	}

	public GatherFormOption() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFieldId() {
		return fieldId;
	}

	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

}
