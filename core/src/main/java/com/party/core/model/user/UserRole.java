package com.party.core.model.user;

import com.party.core.model.BaseModel;

import java.io.Serializable;

/**
 * 用户角色关联实体
 * party
 * Created by wei.li
 * on 2016/8/26 0026.
 */
public class UserRole implements Serializable {

    private static final long serialVersionUID = 7036418741691671589L;
    //用户编号
    private String userId;

    //角色编号
    private String roleId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        UserRole userRole = (UserRole) o;

        if (userId != null ? !userId.equals(userRole.userId) : userRole.userId != null) return false;
        return roleId != null ? roleId.equals(userRole.roleId) : userRole.roleId == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (roleId != null ? roleId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserRole{" +
                "userId='" + userId + '\'' +
                ", roleId='" + roleId + '\'' +
                '}';
    }
}
