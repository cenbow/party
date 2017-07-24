package com.party.core.model.competition;

import java.util.Date;
import java.util.List;

import com.party.core.model.BaseModel;

/**
 * 赛事日程
 * 
 * @author Administrator
 *
 */
public class CompetitionSchedule extends BaseModel {
	private String projectId; // 项目ID
	private Date playDay; // 比赛日
	private String place; // 比赛地点
	private String distance; // 日程距离
	private List<CompetitionResult> results;

	public CompetitionSchedule() {
		super();
	}

	public CompetitionSchedule(String projectId) {
		super();
		this.projectId = projectId;
	}

	public Date getPlayDay() {
		return playDay;
	}

	public void setPlayDay(Date playDay) {
		this.playDay = playDay;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
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

}
