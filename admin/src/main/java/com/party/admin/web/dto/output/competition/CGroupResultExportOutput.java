package com.party.admin.web.dto.output.competition;

import com.party.common.annotation.ExcelField;

/**
 * 小组成绩导出输出
 * 
 * @author Administrator
 *
 */
public class CGroupResultExportOutput {
	private String rankNo;
	private String distance;
	private String secondsResult;
	private String groupName;
	private String groupId;
	private String isComplete;
	private String playDay;

	@ExcelField(title = "里程", align = 2, sort = 6)
	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	@ExcelField(title = "比赛成绩", align = 2, sort = 5)
	public String getSecondsResult() {
		return secondsResult;
	}

	public void setSecondsResult(String secondsResult) {
		this.secondsResult = secondsResult;
	}

	@ExcelField(title = "小组名称", align = 2, sort = 2)
	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getIsComplete() {
		return isComplete;
	}

	public void setIsComplete(String isComplete) {
		this.isComplete = isComplete;
	}

	@ExcelField(title = "排名", align = 2, sort = 1)
	public String getRankNo() {
		return rankNo;
	}

	public void setRankNo(String rankNo) {
		this.rankNo = rankNo;
	}

	@ExcelField(title = "比赛日程", align = 2, sort = 10)
	public String getPlayDay() {
		return playDay;
	}

	public void setPlayDay(String playDay) {
		this.playDay = playDay;
	}

}
