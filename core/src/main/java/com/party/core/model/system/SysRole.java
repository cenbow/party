package com.party.core.model.system;

import com.party.core.model.BaseModel;

/**
 * 角色
 * 
 * @author Administrator
 *
 */
public class SysRole extends BaseModel {

	//角色名称
	private String name;

	//角色代码
	private String code;

	//角色类型（0:系统角色 1:合作商角色）
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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;

		SysRole role = (SysRole) o;

		if (name != null ? !name.equals(role.name) : role.name != null) return false;
		if (code != null ? !code.equals(role.code) : role.code != null) return false;
		return type != null ? type.equals(role.type) : role.type == null;

	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (code != null ? code.hashCode() : 0);
		result = 31 * result + (type != null ? type.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "SysRole{" +
				"name='" + name + '\'' +
				", code='" + code + '\'' +
				", type=" + type +
				'}';
	}
}
