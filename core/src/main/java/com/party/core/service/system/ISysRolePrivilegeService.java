package com.party.core.service.system;

import java.util.List;
import java.util.Set;

import com.party.core.model.system.SysRolePrivilege;
import com.party.core.service.IBaseService;

/**
 * 角色权限关系服务接口
 */
public interface ISysRolePrivilegeService extends IBaseService<SysRolePrivilege> {

	/**
	 * 根据角色主键查找角色权限
	 * 
	 * @param roleId
	 *            角色主键
	 * @return 角色权限列表
	 */
	List<SysRolePrivilege> findByRoleId(String roleId);

	/**
	 * 根据角色查找角色权限
	 * 
	 * @param roleIds
	 *            角色集合
	 * @return 角色权限列表
	 */
	List<SysRolePrivilege> findByRoleIds(Set<String> roleIds);

	/**
	 * 根据角色ID删除 角色权限关联
	 * 
	 * @param roleId
	 *            角色主键
	 * @return 删除结果（true/false）
	 */
	boolean deleteByRoleId(String roleId);

	/**
	 * 根据权限主键查询角色权限关系
	 * 
	 * @param privilegeId
	 *            权限主键
	 * @return 角色权限关系列表
	 */
	List<SysRolePrivilege> findByPrivilegeId(String privilegeId);

	/**
	 * 根据权限主键删除角色权限关系
	 * 
	 * @param privilegeId
	 *            权限主键
	 * @return 觉得权限关系列表
	 */
	boolean deleteByPrivilegeId(String privilegeId);
}
