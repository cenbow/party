package com.party.core.model.gatherInfo;

import com.party.core.model.BaseModel;

/**
 * 人员小组中间表
 * 
 * @author Administrator
 *
 */
public class GatherInfoGroupMember extends BaseModel {
	private String memberId;
	private String groupId;
	private String jobTitle;

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

}
