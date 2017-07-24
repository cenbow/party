package com.party.core.service.system.impl;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.party.common.utils.UUIDUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.party.common.paging.Page;
import com.party.core.dao.read.system.SysRolePrivilegeReadDao;
import com.party.core.dao.write.system.SysRolePrivilegeWriteDao;
import com.party.core.model.system.SysRolePrivilege;
import com.party.core.service.system.ISysRolePrivilegeService;

/**
 * 角色权限关系服务实现
 */

@Service
public class SysRolePrivilegeService implements ISysRolePrivilegeService {

	@Autowired
	private SysRolePrivilegeReadDao sysRolePrivilegeReadDao;

	@Autowired
	private SysRolePrivilegeWriteDao sysRolePrivilegeWriteDao;

	/**
	 * 角色权限插入
	 * 
	 * @param roleMenu
	 *            角色权限插入
	 * @return 插入结果（true/false）
	 */
	public String insert(SysRolePrivilege roleMenu) {
		roleMenu.setId(UUIDUtils.generateRandomUUID());
		sysRolePrivilegeWriteDao.insert(roleMenu);
		return null;
	}

	/**
	 * 角色权限更新
	 * 
	 * @param roleMenu
	 *            角色权限
	 * @return 更新结果（true/false）
	 */
	public boolean update(SysRolePrivilege roleMenu) {
		return sysRolePrivilegeWriteDao.update(roleMenu);
	}

	/**
	 * 角色权限逻辑删除
	 * 
	 * @param id
	 *            实体主键
	 * @return 删除结果（true/false）
	 */
	public boolean deleteLogic(String id) {
		if (Strings.isNullOrEmpty(id)) {
			return false;
		}
		return sysRolePrivilegeWriteDao.deleteLogic(id);
	}

	/**
	 * 角色权限物理删除
	 * 
	 * @param id
	 *            实体主键
	 * @return 删除结果（true/false）
	 */
	public boolean delete(String id) {
		if (Strings.isNullOrEmpty(id)) {
			return false;
		}
		return sysRolePrivilegeWriteDao.delete(id);
	}

	/**
	 * 根据角色主键删除角色权限关系
	 * 
	 * @param roleId
	 *            角色主键
	 * @return 删除结果（true/false）
	 */
	public boolean deleteByRoleId(String roleId) {
		if (Strings.isNullOrEmpty(roleId)) {
			return false;
		}
		return sysRolePrivilegeWriteDao.deleteByRoleId(roleId);
	}

	/**
	 * 批量逻辑删除
	 * 
	 * @param ids
	 *            主键集合
	 * @return 删除结果（true/false）
	 */
	public boolean batchDeleteLogic(Set<String> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			return false;
		}
		return sysRolePrivilegeWriteDao.batchDeleteLogic(ids);
	}

	/**
	 * 角色权限批量物理删除
	 * 
	 * @param ids
	 *            主键集合
	 * @return 删除结果（true/false）
	 */
	public boolean batchDelete(Set<String> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			return false;
		}
		return sysRolePrivilegeWriteDao.batchDelete(ids);
	}

	public SysRolePrivilege get(String id) {
		return null;
	}

	/**
	 * 分页查询角色权限关系
	 * 
	 * @param roleMenu
	 *            角色权限关系
	 * @param page
	 *            分页信息
	 * @return 角色权限关系列表
	 */
	public List<SysRolePrivilege> listPage(SysRolePrivilege roleMenu, Page page) {
		return sysRolePrivilegeReadDao.listPage(roleMenu, page);
	}

	/**
	 * 查询所有角色权限关系
	 * 
	 * @param roleMenu
	 *            角色权限关系
	 * @return 角色权限关系列表
	 */
	public List<SysRolePrivilege> list(SysRolePrivilege roleMenu) {
		return sysRolePrivilegeReadDao.listPage(roleMenu, null);
	}

	/**
	 * 根据角色主键查找角色权限关系
	 * 
	 * @param roleId
	 *            角色主键
	 * @return 角色权限列表
	 */
	public List<SysRolePrivilege> findByRoleId(String roleId) {
		if (Strings.isNullOrEmpty(roleId)) {
			return Collections.EMPTY_LIST;
		}
		SysRolePrivilege roleMenu = new SysRolePrivilege();
		roleMenu.setRoleId(roleId);
		List<SysRolePrivilege> roleMenuList = this.list(roleMenu);
		return roleMenuList;
	}

	/**
	 * 根据权限主键查找角色权限关系
	 * 
	 * @param menuId
	 *            权限主键
	 * @return 角色权限关系列表
	 */
	public List<SysRolePrivilege> findByPrivilegeId(String menuId) {
		SysRolePrivilege roleMenu = new SysRolePrivilege();
		roleMenu.setPrivilegeId(menuId);
		List<SysRolePrivilege> roleMenuList = this.list(roleMenu);
		return roleMenuList;
	}

	/**
	 * 根据权限主键删除角色权限关系
	 * 
	 * @param menuId
	 *            权限主键
	 * @return 删除结果（true/false）
	 */
	public boolean deleteByPrivilegeId(String menuId) {
		if (Strings.isNullOrEmpty(menuId)) {
			return false;
		}
		return sysRolePrivilegeWriteDao.deleteByPrivilegeId(menuId);
	}

	/**
	 * 根据角色查找角色权限
	 * 
	 * @param roleIds
	 *            角色集合
	 * @return 角色权限集合
	 */
	public List<SysRolePrivilege> findByRoleIds(Set<String> roleIds) {
		return sysRolePrivilegeReadDao.findByRoleIds(roleIds);
	}

	public List<SysRolePrivilege> batchList(Set<String> ids, SysRolePrivilege roleMenu, Page page) {
		if (CollectionUtils.isEmpty(ids)) {
			return Collections.EMPTY_LIST;
		}
		return null;
	}
}
