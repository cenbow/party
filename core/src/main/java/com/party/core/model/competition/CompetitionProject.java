package com.party.core.model.competition;

import java.util.Date;

import com.party.core.model.BaseModel;

/**
 * 赛事项目
 * 
 * @author Administrator
 *
 */
public class CompetitionProject extends BaseModel {
	private String title; // 标题
	private String picture; // 封面
	private Date startTime; // 开始时间

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

}
