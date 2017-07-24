package com.party.core.service.menu;

import com.party.core.model.menu.Menu;
import com.party.core.service.IBaseService;
import com.sun.istack.NotNull;

import java.util.List;
import java.util.Set;

/**
 * 菜单服务接口
 * party
 * Created by wei.li
 * on 2016/8/27 0027.
 */
public interface IMenuService extends IBaseService<Menu>{


    /**
     * 根据角色查询菜单
     * @param roleId 角色主键
     * @return 菜单列表
     */
    List<Menu> findByRoleId(String roleId);


    /**
     * 根据角色查找菜单
     * @param roleIds 角色集合
     * @return 菜单列表
     */
    List<Menu> findByRoleIds(@NotNull Set<String> roleIds);

    /**
     * 查找所有菜单
     * @return 菜单列表
     */
    List<Menu> listAll();
    
    /**
     * 根据权限代码查找菜单
     * @param permissions
     * @return
     */
    List<Menu> findByPermission(List<String> permissions);
}
