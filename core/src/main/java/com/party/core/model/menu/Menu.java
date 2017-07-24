package com.party.core.model.menu;

import com.party.core.model.BaseModel;

import java.io.Serializable;

/**
 * 菜单实体
 * party
 * Created by wei.li
 * on 2016/8/26 0026.
 */
public class Menu extends BaseModel implements Serializable {

    private static final long serialVersionUID = 8228105242767305458L;

    //父级编号
    private String parentId;

    //所有父级编号
    private String parentIds;

    //名称
    private String name;

    //排序
    private String sort;

    //链接
    private String href;

    //目标
    private String target;

    //图标
    private String icon;

    //是否在菜单显示
    private Integer isShow;

    //权限标识
    private String permission;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getIsShow() {
        return isShow;
    }

    public void setIsShow(Integer isShow) {
        this.isShow = isShow;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Menu menu = (Menu) o;

        if (parentId != null ? !parentId.equals(menu.parentId) : menu.parentId != null) return false;
        if (parentIds != null ? !parentIds.equals(menu.parentIds) : menu.parentIds != null) return false;
        if (name != null ? !name.equals(menu.name) : menu.name != null) return false;
        if (sort != null ? !sort.equals(menu.sort) : menu.sort != null) return false;
        if (href != null ? !href.equals(menu.href) : menu.href != null) return false;
        if (target != null ? !target.equals(menu.target) : menu.target != null) return false;
        if (icon != null ? !icon.equals(menu.icon) : menu.icon != null) return false;
        if (isShow != null ? !isShow.equals(menu.isShow) : menu.isShow != null) return false;
        return permission != null ? permission.equals(menu.permission) : menu.permission == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (parentId != null ? parentId.hashCode() : 0);
        result = 31 * result + (parentIds != null ? parentIds.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (sort != null ? sort.hashCode() : 0);
        result = 31 * result + (href != null ? href.hashCode() : 0);
        result = 31 * result + (target != null ? target.hashCode() : 0);
        result = 31 * result + (icon != null ? icon.hashCode() : 0);
        result = 31 * result + (isShow != null ? isShow.hashCode() : 0);
        result = 31 * result + (permission != null ? permission.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "parentId='" + parentId + '\'' +
                ", parentIds='" + parentIds + '\'' +
                ", name='" + name + '\'' +
                ", sort='" + sort + '\'' +
                ", href='" + href + '\'' +
                ", target='" + target + '\'' +
                ", icon='" + icon + '\'' +
                ", isShow=" + isShow +
                ", permission='" + permission + '\'' +
                '}';
    }
}
