/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.party.core.model.circle;

import java.io.Serializable;

import com.party.core.model.BaseModel;

/**
 * 圈子申请表Entity
 * @author Juliana
 * @version 2016-12-14
 */
public class CircleApply extends BaseModel implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String circle;		// 圈子id
	private String member;		// 用户id
	private Integer checkStatus; //审核状态(0,"审核中",1,"拒绝",2,"审核通过")
	
	public String getCircle() {
		return circle;
	}
	public void setCircle(String circle) {
		this.circle = circle;
	}
	public String getMember() {
		return member;
	}
	public void setMember(String member) {
		this.member = member;
	}
	public Integer getCheckStatus() {
		return checkStatus;
	}
	public void setCheckStatus(Integer checkStatus) {
		this.checkStatus = checkStatus;
	}
	
}