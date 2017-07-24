package com.party.core.model.system;

import com.party.core.model.BaseModel;

/**
 * 权限
 * 
 * @author Administrator
 *
 */
public class SysPrivilege extends BaseModel {

	//名称
	private String name;

	//代码
	private String code;

	//父编号
	private String parentId;

	//父编号串
	private String parentIds;

	//排序
	private String sort;

	//权限标识
	private String permission;

	//权限类型（0:菜单权限 1:其他权限）
	private Integer type;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}


	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "SysPrivilege{" +
				"name='" + name + '\'' +
				", code='" + code + '\'' +
				", parentId='" + parentId + '\'' +
				", parentIds='" + parentIds + '\'' +
				", sort='" + sort + '\'' +
				", permission='" + permission + '\'' +
				", type=" + type +
				'}';
	}
}
