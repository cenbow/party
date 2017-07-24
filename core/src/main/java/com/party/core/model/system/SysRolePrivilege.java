package com.party.core.model.system;

import java.io.Serializable;

/**
 * 角色权限关联
 * 
 * @author Administrator
 *
 */
public class SysRolePrivilege implements Serializable {

	private static final long serialVersionUID = -4364983570165397599L;

	private String id;

	// 角色编号
	private String roleId;

	// 权限编号
	private String privilegeId;

	public SysRolePrivilege() {
		super();
	}

	public SysRolePrivilege(String roleId, String privilegeId) {
		this.roleId = roleId;
		this.privilegeId = privilegeId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getPrivilegeId() {
		return privilegeId;
	}

	public void setPrivilegeId(String privilegeId) {
		this.privilegeId = privilegeId;
	}

}
