package com.party.core.service.role.impl;

import com.google.common.base.Strings;
import com.party.common.paging.Page;
import com.party.common.utils.UUIDUtils;
import com.party.core.dao.read.role.RoleMenuReadDao;
import com.party.core.dao.write.role.RoleMenuWriteDao;
import com.party.core.model.role.RoleMenu;
import com.party.core.service.role.IRoleMenuService;
import com.sun.istack.NotNull;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * 角色菜单关系服务实现
 * party
 * Created by wei.li
 * on 2016/8/27 0027.
 */

@Service
public class RoleMenuService implements IRoleMenuService {

    @Autowired
    private RoleMenuReadDao roleMenuReadDao;

    @Autowired
    private RoleMenuWriteDao roleMenuWriteDao;

    /**
     * 角色菜单插入
     * @param roleMenu 角色菜单插入
     * @return 插入结果（true/false）
     */
    public String insert(RoleMenu roleMenu) {
        roleMenuWriteDao.insert(roleMenu);
        return null;
    }

    /**
     * 角色菜单更新
     * @param roleMenu 角色菜单
     * @return 更新结果（true/false）
     */
    public boolean update(RoleMenu roleMenu) {
        return roleMenuWriteDao.update(roleMenu);
    }

    /**
     * 角色菜单逻辑删除
     * @param id 实体主键
     * @return 删除结果（true/false）
     */
    public boolean deleteLogic(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return roleMenuWriteDao.deleteLogic(id);
    }

    /**
     * 角色菜单物理删除
     * @param id 实体主键
     * @return 删除结果（true/false）
     */
    public boolean delete(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return roleMenuWriteDao.delete(id);
    }

    /**
     * 根据角色主键删除角色菜单关系
     * @param roleId 角色主键
     * @return 删除结果（true/false）
     */
    public boolean deleteByRoleId(@NotNull String roleId) {
        if (Strings.isNullOrEmpty(roleId)){
            return false;
        }
        return roleMenuWriteDao.deleteByRoleId(roleId);
    }

    /**
     * 批量逻辑删除
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    public boolean batchDeleteLogic(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return roleMenuWriteDao.batchDeleteLogic(ids);
    }

    /**
     * 角色菜单批量物理删除
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    public boolean batchDelete(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return roleMenuWriteDao.batchDelete(ids);
    }


    public RoleMenu get(String id) {
        return null;
    }

    /**
     * 分页查询角色菜单关系
     * @param roleMenu 角色菜单关系
     * @param page 分页信息
     * @return 角色菜单关系列表
     */
    public List<RoleMenu> listPage(RoleMenu roleMenu, Page page) {
        return roleMenuReadDao.listPage(roleMenu, page);
    }

    /**
     * 查询所有角色菜单关系
     * @param roleMenu 角色菜单关系
     * @return 角色菜单关系列表
     */
    public List<RoleMenu> list(RoleMenu roleMenu) {
        return roleMenuReadDao.listPage(roleMenu, null);
    }


    /**
     * 根据角色主键查找角色菜单关系
     * @param roleId 角色主键
     * @return 角色菜单列表
     */
    public List<RoleMenu> findByRoleId(String roleId) {
        if (Strings.isNullOrEmpty(roleId)){
            return Collections.EMPTY_LIST;
        }
        RoleMenu roleMenu = new RoleMenu();
        roleMenu.setRoleId(roleId);
        List<RoleMenu> roleMenuList = this.list(roleMenu);
        return roleMenuList;
    }

    /**
     * 根据菜单主键查找角色菜单关系
     * @param menuId 菜单主键
     * @return 角色菜单关系列表
     */
    public List<RoleMenu> findByMenuId(String menuId) {
        RoleMenu roleMenu = new RoleMenu();
        roleMenu.setMenuId(menuId);
        List<RoleMenu> roleMenuList = this.list(roleMenu);
        return roleMenuList;
    }

    /**
     * 根据菜单主键删除角色菜单关系
     * @param menuId 菜单主键
     * @return 删除结果（true/false）
     */
    public boolean deleteByMenuId(@NotNull String menuId) {
        if (Strings.isNullOrEmpty(menuId)){
            return false;
        }
        return roleMenuWriteDao.deleteByMenuId(menuId);
    }

    /**
     * 根据角色查找角色菜单
     * @param roleIds 角色集合
     * @return 角色菜单集合
     */
    public List<RoleMenu> findByRoleIds(Set<String> roleIds) {
        return roleMenuReadDao.findByRoleIds(roleIds);
    }

    public List<RoleMenu> batchList(@NotNull Set<String> ids, RoleMenu roleMenu, Page page) {
        if (CollectionUtils.isEmpty(ids)){
            return Collections.EMPTY_LIST;
        }
        return null;
    }
}
