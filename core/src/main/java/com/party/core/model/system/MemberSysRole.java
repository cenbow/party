package com.party.core.model.system;

import java.io.Serializable;

/**
 * 用户角色关联
 * 
 * @author Administrator
 *
 */
public class MemberSysRole implements Serializable {

	private static final long serialVersionUID = 4987306297317592166L;

	// 用户编号
	private String memberId;

	// 角色编号
	private String roleId;

	public MemberSysRole(String memberId, String roleId) {
		this.memberId = memberId;
		this.roleId = roleId;
	}

	public MemberSysRole() {
		super();
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

}
