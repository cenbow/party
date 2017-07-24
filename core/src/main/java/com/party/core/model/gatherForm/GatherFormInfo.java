package com.party.core.model.gatherForm;

import com.party.core.model.BaseModel;

/**
 * 表单值
 * 
 * @author Administrator
 *
 */
public class GatherFormInfo extends BaseModel {
	private static final long serialVersionUID = 1933355153237902732L;
	private String fieldId; // 字段id
	private String fieldValue; // 字段值
	private String projectId; // 项目id
	private String memberId; // 用户id
	private Integer sort;
	private Integer maxIndex;

	public GatherFormInfo() {
		super();
	}

	public GatherFormInfo(String projectId) {
		this.projectId = projectId;
	}

	public String getFieldId() {
		return fieldId;
	}

	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}

	public String getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getMaxIndex() {
		return maxIndex;
	}

	public void setMaxIndex(Integer maxIndex) {
		this.maxIndex = maxIndex;
	}

}
