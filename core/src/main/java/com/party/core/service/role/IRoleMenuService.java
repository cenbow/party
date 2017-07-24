package com.party.core.service.role;

import com.party.core.model.role.RoleMenu;
import com.party.core.service.IBaseService;
import com.sun.istack.NotNull;

import java.util.List;
import java.util.Set;

/**
 * 角色菜单关系服务接口
 * party
 * Created by wei.li
 * on 2016/8/27 0027.
 */
public interface IRoleMenuService extends IBaseService<RoleMenu> {

    /**
     * 根据角色主键查找角色菜单
     * @param roleId 角色主键
     * @return 角色菜单列表
     */
    List<RoleMenu> findByRoleId(String roleId);

    /**
     * 根据角色查找角色菜单
     * @param roleIds 角色集合
     * @return 角色菜单列表
     */
    List<RoleMenu> findByRoleIds(Set<String> roleIds);

    /**
     * 根据角色ID删除 角色菜单关联
     * @param roleId 角色主键
     * @return 删除结果（true/false）
     */
    boolean deleteByRoleId(@NotNull String roleId);

    /**
     * 根据菜单主键查询角色菜单关系
     * @param menuId 菜单主键
     * @return 角色菜单关系列表
     */
    List<RoleMenu> findByMenuId(String menuId);

    /**
     * 根据菜单主键删除角色菜单关系
     * @param menuId 菜单主键
     * @return 觉得菜单关系列表
     */
    boolean deleteByMenuId(@NotNull String menuId);
}
