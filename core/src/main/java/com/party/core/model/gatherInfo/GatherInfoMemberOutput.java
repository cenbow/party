package com.party.core.model.gatherInfo;

import java.util.Date;

import com.party.common.annotation.ExcelField;
import com.party.core.model.BaseModel;
import com.party.core.model.member.Member;

/**
 * 人员信息收集
 * 
 * @author Administrator
 *
 */
public class GatherInfoMemberOutput extends BaseModel {
	private Member member;
	private String memberId;
	private String projectId;
	private String groupId;
	private String groupName;

	private String groupJobTitle; // 组内职务

	/********* 基本信息 ************/
	private String logo; // 头像
	private String realname; // 姓名
	private Integer sex; // 性别
	private String sexName;
	private String company; // 公司
	private String jobTitle; // 职务
	private String mobile; // 手机
	private String wechatNum; // 微信
	private String cityName; // 城市
	private String industryName; // 行业
	private String size; // 衣服尺码
	private String introduction; // 自我介绍
	/********* 保险信息 ************/
	private String idCard; // 身份证
	private String bloodGroup; // 血型
	private String medicalHistory; // 病史
	private String contactName; // 紧急联系人名称
	private String contactRelation; // 紧急联系人关系
	private String contactMobile; // 紧急联系人手机
	/********* 行程信息 ************/
	private String goDepartCity; // 出发城市 去程
	private String backDepartCity; // 出发城市 返程
	private Date goDepartTime; // 出发时间 去程
	private Date backDepartTime; // 出发时间 返程
	private Date goTime; // 到达时间
	private String goType; // 到达方式（飞机、火车、汽车、其它）
	private String goNumber; // 到达航班/列表编号
	private String goStation; // 到达站点名称
	private Integer goShuttle; // 到达是否需要接送
	private String goShuttleName; // 到达是否需要接送
	private Date backTime; // 返程时间
	private String backType; // 返程方式（飞机、火车、汽车、其它）
	private String backNumber; // 返程航班/列表编号
	private String backStation; // 返程站点名称
	private Integer backShuttle; // 返程是否需要接送
	private String backShuttleName; // 返程是否需要接送

	private String baseStatus; // 基本信息状态
	private String insuranceStatus; // 保险信息状态
	private String itineraryStatus; // 行程信息状态
	
	private String remarkInfo; // 备注信息

	@ExcelField(title = "组名", align = 2, sort = 1)
	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	@ExcelField(title = "组内职务", align = 2, sort = 2)
	public String getGroupJobTitle() {
		return groupJobTitle;
	}

	public void setGroupJobTitle(String groupJobTitle) {
		this.groupJobTitle = groupJobTitle;
	}

	@ExcelField(title = "姓名", align = 2, sort = 3)
	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	@ExcelField(title = "性别", align = 2, sort = 4)
	public String getSexName() {
		return sexName;
	}

	public void setSexName(String sexName) {
		this.sexName = sexName;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	@ExcelField(title = "公司", align = 2, sort = 5)
	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	@ExcelField(title = "职务", align = 2, sort = 6)
	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	@ExcelField(title = "手机", align = 2, sort = 7)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@ExcelField(title = "微信", align = 2, sort = 8)
	public String getWechatNum() {
		return wechatNum;
	}

	public void setWechatNum(String wechatNum) {
		this.wechatNum = wechatNum;
	}

	@ExcelField(title = "城市", align = 2, sort = 9)
	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	@ExcelField(title = "行业", align = 2, sort = 10)
	public String getIndustryName() {
		return industryName;
	}

	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	@ExcelField(title = "个人介绍", align = 2, sort = 11)
	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	
	@ExcelField(title = "基本信息审核状态", align = 2, sort = 12)
	public String getBaseStatus() {
		return baseStatus;
	}

	public void setBaseStatus(String baseStatus) {
		this.baseStatus = baseStatus;
	}

	@ExcelField(title = "身份证", align = 2, sort = 12)
	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	@ExcelField(title = "血型", align = 2, sort = 13)
	public String getBloodGroup() {
		return bloodGroup;
	}

	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}

	@ExcelField(title = "病史", align = 2, sort = 14)
	public String getMedicalHistory() {
		return medicalHistory;
	}

	public void setMedicalHistory(String medicalHistory) {
		this.medicalHistory = medicalHistory;
	}

	@ExcelField(title = "紧急联系人姓名", align = 2, sort = 15)
	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	@ExcelField(title = "紧急联系人关系", align = 2, sort = 16)
	public String getContactRelation() {
		return contactRelation;
	}

	public void setContactRelation(String contactRelation) {
		this.contactRelation = contactRelation;
	}

	@ExcelField(title = "紧急联系人手机", align = 2, sort = 17)
	public String getContactMobile() {
		return contactMobile;
	}

	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}
	
	@ExcelField(title = "保险信息审核状态", align = 2, sort = 18)
	public String getInsuranceStatus() {
		return insuranceStatus;
	}

	public void setInsuranceStatus(String insuranceStatus) {
		this.insuranceStatus = insuranceStatus;
	}

	@ExcelField(title = "出发城市", align = 2, sort = 19)
	public String getGoDepartCity() {
		return goDepartCity;
	}

	public void setGoDepartCity(String goDepartCity) {
		this.goDepartCity = goDepartCity;
	}

	@ExcelField(title = "出发时间", align = 2, sort = 22)
	public Date getGoDepartTime() {
		return goDepartTime;
	}

	public void setGoDepartTime(Date goDepartTime) {
		this.goDepartTime = goDepartTime;
	}

	@ExcelField(title = "到达时间", align = 2, sort = 23)
	public Date getGoTime() {
		return goTime;
	}

	public void setGoTime(Date goTime) {
		this.goTime = goTime;
	}

	@ExcelField(title = "出发方式", align = 2, sort = 20)
	public String getGoType() {
		return goType;
	}

	public void setGoType(String goType) {
		this.goType = goType;
	}

	@ExcelField(title = "航班/列车编号", align = 2, sort = 21)
	public String getGoNumber() {
		return goNumber;
	}

	public void setGoNumber(String goNumber) {
		this.goNumber = goNumber;
	}

	@ExcelField(title = "到达站点名称", align = 2, sort = 24)
	public String getGoStation() {
		return goStation;
	}

	public void setGoStation(String goStation) {
		this.goStation = goStation;
	}

	@ExcelField(title = "到达是否需要接", align = 2, sort = 25)
	public String getGoShuttleName() {
		return goShuttleName;
	}

	public void setGoShuttleName(String goShuttleName) {
		this.goShuttleName = goShuttleName;
	}

	@ExcelField(title = "到达城市", align = 2, sort = 31)
	public String getBackDepartCity() {
		return backDepartCity;
	}

	public void setBackDepartCity(String backDepartCity) {
		this.backDepartCity = backDepartCity;
	}

	@ExcelField(title = "出发时间", align = 2, sort = 29)
	public Date getBackDepartTime() {
		return backDepartTime;
	}

	public void setBackDepartTime(Date backDepartTime) {
		this.backDepartTime = backDepartTime;
	}

	@ExcelField(title = "到达时间", align = 2, sort = 30)
	public Date getBackTime() {
		return backTime;
	}

	public void setBackTime(Date backTime) {
		this.backTime = backTime;
	}

	@ExcelField(title = "返程方式", align = 2, sort = 26)
	public String getBackType() {
		return backType;
	}

	public void setBackType(String backType) {
		this.backType = backType;
	}

	@ExcelField(title = "航班/列车编号", align = 2, sort = 28)
	public String getBackNumber() {
		return backNumber;
	}

	public void setBackNumber(String backNumber) {
		this.backNumber = backNumber;
	}

	@ExcelField(title = "到达站点名称", align = 2, sort = 32)
	public String getBackStation() {
		return backStation;
	}

	public void setBackStation(String backStation) {
		this.backStation = backStation;
	}

	@ExcelField(title = "返程是否需要送", align = 2, sort = 27)
	public String getBackShuttleName() {
		return backShuttleName;
	}

	public void setBackShuttleName(String backShuttleName) {
		this.backShuttleName = backShuttleName;
	}

	public Integer getBackShuttle() {
		return backShuttle;
	}

	public void setBackShuttle(Integer backShuttle) {
		this.backShuttle = backShuttle;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public Integer getGoShuttle() {
		return goShuttle;
	}

	public void setGoShuttle(Integer goShuttle) {
		this.goShuttle = goShuttle;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	@ExcelField(title = "备注信息", align = 2, sort = 33)
	public String getRemarkInfo() {
		return remarkInfo;
	}

	public void setRemarkInfo(String remarkInfo) {
		this.remarkInfo = remarkInfo;
	}
	
	@ExcelField(title = "行程信息审核状态", align = 2, sort = 34)
	public String getItineraryStatus() {
		return itineraryStatus;
	}

	public void setItineraryStatus(String itineraryStatus) {
		this.itineraryStatus = itineraryStatus;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

}
