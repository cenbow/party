package com.party.core.model.role;

import com.party.core.model.BaseModel;

import java.io.Serializable;

/**
 * 角色实体
 * party
 * Created by wei.li
 * on 2016/8/26 0026.
 */
public class Role extends BaseModel implements Serializable {

    private static final long serialVersionUID = -6556012788728363519L;

    //归属机构
    private String officeId;

    //角色名称
    private String name;

    //英文名称
    private String enname;

    //角色类型
    private String roleType;

    //数据类型
    private String dataScope;

    //是否系统数据
    private String isSys;

    //是否可用
    private String useable;

    public String getOfficeId() {
        return officeId;
    }

    public void setOfficeId(String officeId) {
        this.officeId = officeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnname() {
        return enname;
    }

    public void setEnname(String enname) {
        this.enname = enname;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public String getDataScope() {
        return dataScope;
    }

    public void setDataScope(String dataScope) {
        this.dataScope = dataScope;
    }

    public String getIsSys() {
        return isSys;
    }

    public void setIsSys(String isSys) {
        this.isSys = isSys;
    }

    public String getUseable() {
        return useable;
    }

    public void setUseable(String useable) {
        this.useable = useable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Role role = (Role) o;

        if (officeId != null ? !officeId.equals(role.officeId) : role.officeId != null) return false;
        if (name != null ? !name.equals(role.name) : role.name != null) return false;
        if (enname != null ? !enname.equals(role.enname) : role.enname != null) return false;
        if (roleType != null ? !roleType.equals(role.roleType) : role.roleType != null) return false;
        if (dataScope != null ? !dataScope.equals(role.dataScope) : role.dataScope != null) return false;
        if (isSys != null ? !isSys.equals(role.isSys) : role.isSys != null) return false;
        return useable != null ? useable.equals(role.useable) : role.useable == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (officeId != null ? officeId.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (enname != null ? enname.hashCode() : 0);
        result = 31 * result + (roleType != null ? roleType.hashCode() : 0);
        result = 31 * result + (dataScope != null ? dataScope.hashCode() : 0);
        result = 31 * result + (isSys != null ? isSys.hashCode() : 0);
        result = 31 * result + (useable != null ? useable.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Role{" +
                "officeId='" + officeId + '\'' +
                ", name='" + name + '\'' +
                ", enname='" + enname + '\'' +
                ", roleType='" + roleType + '\'' +
                ", dataScope='" + dataScope + '\'' +
                ", isSys='" + isSys + '\'' +
                ", useable='" + useable + '\'' +
                '}';
    }
}
