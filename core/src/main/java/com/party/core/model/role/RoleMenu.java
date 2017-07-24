package com.party.core.model.role;


import java.io.Serializable;

/**
 * 角色菜单关联
 * party
 * Created by wei.li
 * on 2016/8/26 0026.
 */
public class RoleMenu  implements Serializable {

    private static final long serialVersionUID = 1048667559157311782L;

    //角色编号
    private String roleId;

    //菜单编号
    private String menuId;


    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoleMenu roleMenu = (RoleMenu) o;

        if (roleId != null ? !roleId.equals(roleMenu.roleId) : roleMenu.roleId != null) return false;
        return menuId != null ? menuId.equals(roleMenu.menuId) : roleMenu.menuId == null;

    }

    @Override
    public int hashCode() {
        int result = roleId != null ? roleId.hashCode() : 0;
        result = 31 * result + (menuId != null ? menuId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "RoleMenu{" +
                "roleId='" + roleId + '\'' +
                ", menuId='" + menuId + '\'' +
                '}';
    }
}
