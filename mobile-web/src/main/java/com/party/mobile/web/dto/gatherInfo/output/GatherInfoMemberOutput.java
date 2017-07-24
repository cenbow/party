package com.party.mobile.web.dto.gatherInfo.output;

import com.party.core.model.gatherInfo.GatherInfoMember;

/**
 * 人员信息收集
 * 
 * @author Administrator
 *
 */
public class GatherInfoMemberOutput extends GatherInfoMember {
	private static final long serialVersionUID = -6672984915662937448L;
	private String goDepartTimeStr;
	private String goTimeStr;
	private String backDepartTimeStr;
	private String backTimeStr;

	private String industry;
	private String cityName;

	public String getGoDepartTimeStr() {
		return goDepartTimeStr;
	}

	public void setGoDepartTimeStr(String goDepartTimeStr) {
		this.goDepartTimeStr = goDepartTimeStr;
	}

	public String getGoTimeStr() {
		return goTimeStr;
	}

	public void setGoTimeStr(String goTimeStr) {
		this.goTimeStr = goTimeStr;
	}

	public String getBackDepartTimeStr() {
		return backDepartTimeStr;
	}

	public void setBackDepartTimeStr(String backDepartTimeStr) {
		this.backDepartTimeStr = backDepartTimeStr;
	}

	public String getBackTimeStr() {
		return backTimeStr;
	}

	public void setBackTimeStr(String backTimeStr) {
		this.backTimeStr = backTimeStr;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
}
