package com.party.web.web.dto.input.competition;

public class RankInput {
	private String projectId;
	private String groupId;
	private String scheduleId;
	private String fullName;
	private String numberPai;
	private String groupName;

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(String scheduleId) {
		this.scheduleId = scheduleId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getNumberPai() {
		return numberPai;
	}

	public void setNumberPai(String numberPai) {
		this.numberPai = numberPai;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

}
