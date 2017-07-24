/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.party.core.model.circle;

import java.io.Serializable;

import com.party.core.model.BaseModel;
import com.party.core.model.member.Member;

/**
 * 圈子成员管理Entity
 * @author Juliana
 * @version 2016-12-14
 */
public class CircleMember extends BaseModel implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String circle;		// 圈子id
	private String member;		// 用户id
	private Integer isAdmin;		// 是否管理员
	private Integer source; // 用户来源 1 生成业务圈子，2后台新增，3前端申请，4前端邀请

	private Member cMember;

	public CircleMember() {
	}

	public CircleMember(String circle, String member) {
		this.circle = circle;
		this.member = member;
	}

	public CircleMember(String circle) {
		this.circle = circle;
	}

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

	public Integer getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Integer isAdmin) {
		this.isAdmin = isAdmin;
	}
	public Member getcMember() {
		return cMember;
	}
	public void setcMember(Member cMember) {
		this.cMember = cMember;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

}