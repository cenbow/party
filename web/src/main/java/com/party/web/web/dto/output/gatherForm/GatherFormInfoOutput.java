package com.party.web.web.dto.output.gatherForm;

import com.party.core.model.BaseModel;
import com.party.core.model.member.Member;

/**
 * 表单
 * 
 * @author Administrator
 *
 */
public class GatherFormInfoOutput extends BaseModel {

	private static final long serialVersionUID = 7939793249708064364L;
	private String maxIndex;
	private String projectId;
	private Member member;
	private String[] fieldValues;

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public String getMaxIndex() {
		return maxIndex;
	}

	public void setMaxIndex(String maxIndex) {
		this.maxIndex = maxIndex;
	}

	public String[] getFieldValues() {
		return fieldValues;
	}

	public void setFieldValues(String[] fieldValues) {
		this.fieldValues = fieldValues;
	}

}
