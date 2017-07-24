package com.party.core.model.competition;

import java.util.List;

import com.party.core.model.BaseModel;

/**
 * 赛事人员
 * 
 * @author Administrator
 *
 */
public class CompetitionMember extends BaseModel {
	private String memberId; // 用户ID
	private String groupId; // 小组ID
	private String groupName; // 小组名称
	private String projectId; // 项目ID
	private String number; // 号码牌
	private List<CompetitionResult> results;
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

	public CompetitionMember() {
		super();
	}

	public CompetitionMember(String projectId) {
		super();
		this.projectId = projectId;
	}

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

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public List<CompetitionResult> getResults() {
		return results;
	}

	public void setResults(List<CompetitionResult> results) {
		this.results = results;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

}
