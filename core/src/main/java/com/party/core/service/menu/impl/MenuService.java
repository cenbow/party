package com.party.core.service.menu.impl;


import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.party.common.paging.Page;
import com.party.common.utils.LangUtils;
import com.party.core.dao.read.menu.MenuReadDao;
import com.party.core.dao.write.menu.MenuWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.menu.Menu;
import com.party.core.model.role.RoleMenu;
import com.party.core.redisDao.menu.MenuRedisDao;
import com.party.core.service.menu.IMenuService;
import com.party.core.service.role.IRoleMenuService;
import com.sun.istack.NotNull;


/**
 * 菜单服务实现
 * party
 * Created by wei.li
 * on 2016/8/27 0027.
 */

@Service
public class MenuService implements IMenuService {

    @Autowired
    private MenuReadDao menuReadDao;

    @Autowired
    private MenuWriteDao menuWriteDao;

    @Autowired
    private IRoleMenuService roleMenuService;

    @Autowired
    private MenuRedisDao menuRedisDao;

    private static final String REDIS_LIST_KEY = "menuListKey";
    /**
     * 菜单插入
     * @param menu 菜单信息
     * @return 插入结果（true/false）
     */
    public String insert(Menu menu) {
        BaseModel.preInsert(menu);
        menu.setIsShow(0);
        Menu parent = menuReadDao.get(menu.getParentId());
        String parentIds = parent.getParentIds() + "," + parent.getParentIds();
        menu.setParentIds(parentIds);

        //删除缓存
        menuRedisDao.delete(REDIS_LIST_KEY);
        boolean result = menuWriteDao.insert(menu);
        if (result){
            return menu.getId();
        }
        return null;
    }


    /**
     * 菜单更新
     * @param menu 菜单信息
     * @return 更新结果（true/false）
     */
    public boolean update(Menu menu) {
        menu.setUpdateDate(new Date());
        //删除缓存
        menuRedisDao.delete(REDIS_LIST_KEY);
        return menuWriteDao.update(menu);
    }


    /**
     * 菜单逻辑删除
     * @param id 实体主键
     * @return 删除结果（true/false）
     */
    public boolean deleteLogic(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return menuWriteDao.deleteLogic(id);
    }

    /**
     * 菜单物理删除
     * @param id 实体主键
     * @return 删除结果（true/false）
     */
    public boolean delete(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }

        //删除缓存
        menuRedisDao.delete(REDIS_LIST_KEY);
        return menuWriteDao.delete(id);
    }

    /**
     * 菜单批量逻辑删除
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    public boolean batchDeleteLogic(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return menuWriteDao.batchDeleteLogic(ids);
    }

    /**
     * 菜单批量物理删除
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    public boolean batchDelete(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return menuWriteDao.batchDelete(ids);
    }

    /**
     * 根据主键获取菜单信息
     * @param id 主键
     * @return 菜单信息
     */
    public Menu get(String id) {
        return menuReadDao.get(id);
    }

    /**
     * 菜单分页查询
     * @param menu 菜单信息
     * @param page 分页信息
     * @return 菜单列表
     */
    public List<Menu> listPage(Menu menu, Page page) {
        return menuReadDao.listPage(menu, page);
    }

    /**
     * 查询所有菜单信息
     * @param menu 菜单信息
     * @return 菜单列表
     */
    public List<Menu> list(Menu menu) {
        return menuReadDao.listPage(menu, null);
    }

    /**
     * 查找所有菜单
     * @return 菜单列表
     */
    public List<Menu> listAll() {

        //缓存是否存在
        List<Menu> menuRedisList = menuRedisDao.listAll(REDIS_LIST_KEY);
        if (!CollectionUtils.isEmpty(menuRedisList)){
            return menuRedisList;
        }

        List<Menu> menuList = menuReadDao.listPage(null, null);
        menuRedisDao.listPushAll(REDIS_LIST_KEY, menuList);
        return menuList;
    }

    /**
     * 根据角色查找菜单
     * @param roleId 角色主键
     * @return 菜单列表
     */
    @Override
    public List<Menu> findByRoleId(String roleId) {
        List<RoleMenu> roleMenuList = roleMenuService.findByRoleId(roleId);
        return findByRoleMenu(roleMenuList);
    }

    /**
     * 根据角色查询菜单
     * @param roleIds 角色集合
     * @return 菜单列表
     */
    @Override
    public List<Menu> findByRoleIds(Set<String> roleIds) {
        if (CollectionUtils.isEmpty(roleIds)){
            return Collections.EMPTY_LIST;
        }
        List<RoleMenu> roleMenuList = roleMenuService.findByRoleIds(roleIds);
        return findByRoleMenu(roleMenuList);
    }

    /**
     * 根据角色菜单关系查询菜单
     * @param roleMenuList 角色菜单列表
     * @return 菜单列表
     */
    public List<Menu> findByRoleMenu(List<RoleMenu> roleMenuList){
        List<String> menuIdList = LangUtils.transform(roleMenuList, input -> input.getMenuId());
        List<Menu> menuList = menuReadDao.batchList(new HashSet<>(menuIdList), null, null);
        return menuList;
    }
    /**
     * 菜单批量查询
     * @param ids 主键集合
     * @param menu 菜单信息
     * @param page 分页信息
     * @return 菜单列表
     */
    public List<Menu> batchList(@NotNull Set<String> ids, Menu menu, Page page) {
        if (CollectionUtils.isEmpty(ids)){
            return Collections.EMPTY_LIST;
        }
        return menuReadDao.batchList(ids, new HashedMap(), page);
    }


	@Override
	public List<Menu> findByPermission(List<String> permissions) {
		if (CollectionUtils.isEmpty(permissions)){
            return Collections.EMPTY_LIST;
        }
		return menuReadDao.findByPermission(permissions);
	}
}
