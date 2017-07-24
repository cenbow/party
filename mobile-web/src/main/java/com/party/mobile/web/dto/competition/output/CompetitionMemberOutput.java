package com.party.mobile.web.dto.competition.output;

/**
 * 参赛人员输出
 * 
 * @author Administrator
 *
 */
public class CompetitionMemberOutput {
	private String groupId;
	private String projectId; // 项目ID
	private String cmemberId;
	private String memberId;

	private String avgResult; // 平均用时
	private String totalSecondsResult; // 总用时
	private String totalDistance; // 总里程
	private String projectName;
	private String projectRemarks;
	private String projectPicture;
	private String fullName;
	private String realName;
	private String groupName;
	private String number; // 号码牌
	private String memberRank; // 总榜排名
	private String groupMemberRank; // 小组人员排名

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
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

	public String getAvgResult() {
		return avgResult;
	}

	public void setAvgResult(String avgResult) {
		this.avgResult = avgResult;
	}

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

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectRemarks() {
		return projectRemarks;
	}

	public void setProjectRemarks(String projectRemarks) {
		this.projectRemarks = projectRemarks;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getMemberRank() {
		return memberRank;
	}

	public void setMemberRank(String memberRank) {
		this.memberRank = memberRank;
	}

	public String getGroupMemberRank() {
		return groupMemberRank;
	}

	public void setGroupMemberRank(String groupMemberRank) {
		this.groupMemberRank = groupMemberRank;
	}

	public String getProjectPicture() {
		return projectPicture;
	}

	public void setProjectPicture(String projectPicture) {
		this.projectPicture = projectPicture;
	}

	public String getCmemberId() {
		return cmemberId;
	}

	public void setCmemberId(String cmemberId) {
		this.cmemberId = cmemberId;
	}

}
