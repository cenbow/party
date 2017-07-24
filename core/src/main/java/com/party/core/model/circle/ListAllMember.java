/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.party.core.model.circle;

import com.party.core.model.BaseModel;

import java.io.Serializable;
import java.util.List;

/**
 * ListAllMember Mapper
 * @author Juliana
 * @version 2016-12-14
 */
public class ListAllMember{
	
	private String cmId;		// circleMember id
	private Integer isAdmin;		// 是否为管理员
	private String id;		// 用户id
	private String industryId;		// 行业id
	private String industryName;		// 行业名称
	private String industryPY;		// 行业拼音
	private String areaId;		// 区域id
	private String areaName;		// 区域名称
	private String areaPY;		// 区域拼音
	private String tagName;		// 标签名称
	private String realname; 	//用户名称
	private String jobTitle; 	//职务
	private String company; //公司
	private String mPY;		//用户名称拼音
	private String logo; // logo
	private List<ListAllTag> tags; //用户标签

	public String getCmId() {
		return cmId;
	}

	public void setCmId(String cmId) {
		this.cmId = cmId;
	}

	public Integer getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Integer isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIndustryId() {
		return industryId;
	}

	public void setIndustryId(String industryId) {
		this.industryId = industryId;
	}

	public String getIndustryName() {
		return industryName;
	}

	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}

	public String getIndustryPY() {
		return industryPY;
	}

	public void setIndustryPY(String industryPY) {
		this.industryPY = industryPY;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getAreaPY() {
		return areaPY;
	}

	public void setAreaPY(String areaPY) {
		this.areaPY = areaPY;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getMPY() {
		return mPY;
	}

	public void setMPY(String mPY) {
		this.mPY = mPY;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public List<ListAllTag> getTags() {
		return tags;
	}

	public void setTags(List<ListAllTag> tags) {
		this.tags = tags;
	}
}

class ListAllTag{
	private String id;		// 标签id
	private String name;		// 标签名称

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}