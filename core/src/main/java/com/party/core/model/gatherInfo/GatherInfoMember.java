package com.party.core.model.gatherInfo;

import java.util.Date;

import com.party.core.model.BaseModel;
import com.party.core.model.member.Member;

/**
 * 人员信息收集
 * 
 * @author Administrator
 *
 */
public class GatherInfoMember extends BaseModel {
	private static final long serialVersionUID = 7339323404340342437L;
	private String memberId; // 用户id
	private String projectId; // 项目id
	private String groupId; // 小组id
	private String groupName; // 小组名称

	private String groupJobTitle; // 组内职务

	/********* 基本信息 ************/
	private String logo; // 头像
	private String realname; // 姓名
	private Integer sex; // 性别
	private String company; // 公司
	private String jobTitle; // 职务
	private String mobile; // 手机
	private String wechatNum; // 微信
	private String introduction; // 自我介绍
	private String cityId; // 城市
	private String industryId; // 行业
	private String size; // 衣服尺码
	/********* 保险信息 ************/
	private String idCard; // 身份证
	private String bloodGroup; // 血型
	private String medicalHistory; // 病史
	private String contactName; // 紧急联系人名称
	private String contactRelation; // 紧急联系人关系
	private String contactMobile; // 紧急联系人手机
	/********* 行程信息 ************/
	private String goDepartCity; // 启程出发城市
	private Date goDepartTime; // 启程出发时间
	private Date goTime; // 启程到达时间
	private String goType; // 到达方式（飞机、火车、汽车、其它）
	private String goNumber; // 到达航班/列表编号
	private String goStation; // 到达站点名称
	private Integer goShuttle; // 到达是否需要接送

	private String backDepartCity; // 返程出发城市
	private Date backDepartTime; // 返程出发时间
	private Date backTime; // 返程 到达时间
	private String backType; // 返程方式（飞机、火车、汽车、其它）
	private String backNumber; // 返程航班/列表编号
	private String backStation; // 返程站点名称
	private Integer backShuttle; // 返程是否需要接送

	private String baseStatus; // 基本信息状态
	private String insuranceStatus; // 保险信息状态
	private String itineraryStatus; // 行程信息状态

	public GatherInfoMember(String projectId) {
		this.projectId = projectId;
	}

	public GatherInfoMember() {
		super();
	}

	public String getGoDepartCity() {
		return goDepartCity;
	}

	public void setGoDepartCity(String goDepartCity) {
		this.goDepartCity = goDepartCity;
	}

	public Date getGoDepartTime() {
		return goDepartTime;
	}

	public void setGoDepartTime(Date goDepartTime) {
		this.goDepartTime = goDepartTime;
	}

	public String getBackDepartCity() {
		return backDepartCity;
	}

	public void setBackDepartCity(String backDepartCity) {
		this.backDepartCity = backDepartCity;
	}

	public Date getBackDepartTime() {
		return backDepartTime;
	}

	public void setBackDepartTime(Date backDepartTime) {
		this.backDepartTime = backDepartTime;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getBaseStatus() {
		return baseStatus;
	}

	public void setBaseStatus(String baseStatus) {
		this.baseStatus = baseStatus;
	}

	public String getInsuranceStatus() {
		return insuranceStatus;
	}

	public void setInsuranceStatus(String insuranceStatus) {
		this.insuranceStatus = insuranceStatus;
	}

	public String getItineraryStatus() {
		return itineraryStatus;
	}

	public void setItineraryStatus(String itineraryStatus) {
		this.itineraryStatus = itineraryStatus;
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

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getBloodGroup() {
		return bloodGroup;
	}

	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}

	public String getMedicalHistory() {
		return medicalHistory;
	}

	public void setMedicalHistory(String medicalHistory) {
		this.medicalHistory = medicalHistory;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactRelation() {
		return contactRelation;
	}

	public void setContactRelation(String contactRelation) {
		this.contactRelation = contactRelation;
	}

	public String getContactMobile() {
		return contactMobile;
	}

	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}

	public Date getGoTime() {
		return goTime;
	}

	public void setGoTime(Date goTime) {
		this.goTime = goTime;
	}

	public String getGoType() {
		return goType;
	}

	public void setGoType(String goType) {
		this.goType = goType;
	}

	public String getGoNumber() {
		return goNumber;
	}

	public void setGoNumber(String goNumber) {
		this.goNumber = goNumber;
	}

	public String getGoStation() {
		return goStation;
	}

	public void setGoStation(String goStation) {
		this.goStation = goStation;
	}

	public Date getBackTime() {
		return backTime;
	}

	public void setBackTime(Date backTime) {
		this.backTime = backTime;
	}

	public String getBackType() {
		return backType;
	}

	public void setBackType(String backType) {
		this.backType = backType;
	}

	public String getBackNumber() {
		return backNumber;
	}

	public void setBackNumber(String backNumber) {
		this.backNumber = backNumber;
	}

	public String getBackStation() {
		return backStation;
	}

	public void setBackStation(String backStation) {
		this.backStation = backStation;
	}

	public Integer getGoShuttle() {
		return goShuttle;
	}

	public void setGoShuttle(Integer goShuttle) {
		this.goShuttle = goShuttle;
	}

	public Integer getBackShuttle() {
		return backShuttle;
	}

	public void setBackShuttle(Integer backShuttle) {
		this.backShuttle = backShuttle;
	}

	public String getWechatNum() {
		return wechatNum;
	}

	public void setWechatNum(String wechatNum) {
		this.wechatNum = wechatNum;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getGroupJobTitle() {
		return groupJobTitle;
	}

	public void setGroupJobTitle(String groupJobTitle) {
		this.groupJobTitle = groupJobTitle;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getIndustryId() {
		return industryId;
	}

	public void setIndustryId(String industryId) {
		this.industryId = industryId;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

}
