package com.party.mobile.web.dto.gatherInfo.input;

public class GatherInfoMemberInput {

	private String goDepartTimeStr; // 启程-出发时间
	private String goTimeStr; // 启程-到达时间
	private String backDepartTimeStr; // 返程-出发时间
	private String backTimeStr; // 返程-到达时间

	private String verifyCode;

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

	public String getVerifyCode() {
		return verifyCode == null ? "" : verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

}
