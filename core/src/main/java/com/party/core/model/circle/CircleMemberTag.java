/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.party.core.model.circle;

import java.io.Serializable;

import com.party.core.model.BaseModel;

/**
 * 圈子用户标签关联Entity
 * @author Juliana
 * @version 2016-12-14
 */
public class CircleMemberTag extends BaseModel implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String circle;		// 圈子id
	private String member;		// 用户id
	private String tag;		// 标签

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
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	
}