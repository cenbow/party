package com.party.core.model.competition;

import com.party.common.utils.StringUtils;
import com.party.core.model.BaseModel;

/**
 * 赛事成绩
 * 
 * @author Administrator
 *
 */
public class CompetitionResult extends BaseModel {
	private String scheduleId; // 赛程ID
	private String memberId; // 用户ID
	private String projectId; // 项目ID
	private String result;
	private String actualRange; // 实际里程
	private String hours; // 小时
	private String minutes; // 分钟
	private String seconds; // 秒
	private String secondsResult; // 秒数成绩

	private Integer isComplete; // 是否完赛
	private String pace; // 配速

	public CompetitionResult() {
		super();
	}

	public CompetitionResult(String scheduleId) {
		super();
		this.scheduleId = scheduleId;
	}

	public CompetitionResult(String memberId, String projectId) {
		super();
		this.memberId = memberId;
		this.projectId = projectId;
	}

	public String getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(String scheduleId) {
		this.scheduleId = scheduleId;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getHours() {
		return hours;
	}

	public void setHours(String hours) {
		this.hours = hours;
	}

	public String getMinutes() {
		return minutes;
	}

	public void setMinutes(String minutes) {
		this.minutes = minutes;
	}

	public String getSeconds() {
		return seconds;
	}

	public void setSeconds(String seconds) {
		this.seconds = seconds;
	}

	public Integer getIsComplete() {
		return isComplete;
	}

	public void setIsComplete(Integer isComplete) {
		this.isComplete = isComplete;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getPace() {
		return pace;
	}

	public void setPace(String pace) {
		this.pace = pace;
	}

	public String getResult() {
		if (StringUtils.isNotEmpty(this.getHours())) {
			return this.getHours() + ":" + this.getMinutes() + ":" + this.getSeconds();
		} else {
			return this.result;
		}
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getActualRange() {
		return actualRange;
	}

	public void setActualRange(String actualRange) {
		this.actualRange = actualRange;
	}

	public String getSecondsResult() {
		return secondsResult;
	}

	public void setSecondsResult(String secondsResult) {
		this.secondsResult = secondsResult;
	}

}
