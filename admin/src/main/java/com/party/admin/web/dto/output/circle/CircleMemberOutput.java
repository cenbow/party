/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.party.admin.web.dto.output.circle;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.party.core.model.BaseModel;
import com.party.core.model.circle.CircleMember;
import com.party.core.model.circle.CircleMemberTag;
import com.party.core.model.circle.CircleTag;
import com.party.core.model.member.Member;

/**
 * 圈子成员管理Entity
 * 
 * @author Juliana
 * @version 2016-12-14
 */
public class CircleMemberOutput extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;
	private String circle; // 圈子id
	private String member; // 用户id
	private Integer isAdmin; // 是否管理员

	private Member cMember;

	private List<String> tagNames;

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

	public static CircleMemberOutput transform(CircleMember circleMember) {
		CircleMemberOutput output = new CircleMemberOutput();
		try {
			BeanUtils.copyProperties(output, circleMember);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return output;
	}

	public List<String> getTagNames() {
		return tagNames;
	}

	public void setTagNames(List<String> tagNames) {
		this.tagNames = tagNames;
	}
}