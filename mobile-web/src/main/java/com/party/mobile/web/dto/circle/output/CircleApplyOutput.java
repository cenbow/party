package com.party.mobile.web.dto.circle.output;

import org.springframework.beans.BeanUtils;

import com.party.core.model.circle.CircleApply;

/**
 * 圈子输出视图 party Created by Juliana on 2016-12-14
 */
public class CircleApplyOutput {
	private String id;

	private Integer checkStatus;
	
	private CircleMemberOutput info;
	
	private String phone;
	 
	private String cityName;
	
	private String industryName;
	
	public Integer getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(Integer checkStatus) {
		this.checkStatus = checkStatus;
	}

	public CircleMemberOutput getInfo() {
		return info;
	}

	public void setInfo(CircleMemberOutput info) {
		this.info = info;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getIndustryName() {
		return industryName;
	}

	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 转换列表查询输出视图
	 * 
	 * @param circle
	 *            活动实体
	 * @return 活动列表输出视图
	 */
	public static CircleApplyOutput transform(CircleApply circle) {
		CircleApplyOutput listOutput = new CircleApplyOutput();
		BeanUtils.copyProperties(circle, listOutput);
		return listOutput;
	}
}
