package com.party.core.model.gatherInfo;

import com.party.core.model.BaseModel;

/**
 * 信息收集项目
 * 
 * @author Administrator
 *
 */
public class GatherInfoProject extends BaseModel {

	private static final long serialVersionUID = -5921676579043881162L;
	private String title;
	private String picture;
	private String baseInfoDesc; // 基本信息描述
	private String itineraryInfoDesc; // 行程信息 描述
	private String insuranceInfoDesc; // 保险信息描述

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBaseInfoDesc() {
		return baseInfoDesc;
	}

	public void setBaseInfoDesc(String baseInfoDesc) {
		this.baseInfoDesc = baseInfoDesc;
	}

	public String getItineraryInfoDesc() {
		return itineraryInfoDesc;
	}

	public void setItineraryInfoDesc(String itineraryInfoDesc) {
		this.itineraryInfoDesc = itineraryInfoDesc;
	}

	public String getInsuranceInfoDesc() {
		return insuranceInfoDesc;
	}

	public void setInsuranceInfoDesc(String insuranceInfoDesc) {
		this.insuranceInfoDesc = insuranceInfoDesc;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

}
