package com.party.core.model.competition;

import com.party.core.model.BaseModel;

/**
 * 赛事小组
 * 
 * @author Administrator
 *
 */
public class CompetitionGroup extends BaseModel {
	private String groupName; // 组名
	private String groupNo; // 组编号
	private String projectId;

	private String totalSecondsResult; // 总用时
	private String totalDistance; // 总里程

	public String getTotalSecondsResult() {
		return totalSecondsResult;
	}

	public void setTotalSecondsResult(String totalSecondsResult) {
		this.totalSecondsResult = totalSecondsResult;
	}

	public String getTotalDistance() {
		return totalDistance;
	}

	public void setTotalDistance(String totalDistance) {
		this.totalDistance = totalDistance;
	}

	public CompetitionGroup() {
		super();
	}

	public CompetitionGroup(String projectId) {
		super();
		this.projectId = projectId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupNo() {
		return groupNo;
	}

	public void setGroupNo(String groupNo) {
		this.groupNo = groupNo;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
}
