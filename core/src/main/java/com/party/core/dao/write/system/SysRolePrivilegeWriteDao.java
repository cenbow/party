package com.party.core.dao.write.system;

import org.springframework.stereotype.Repository;

import com.party.core.dao.write.BaseWriteDao;
import com.party.core.model.system.SysRolePrivilege;

/**
 * 角色权限关系数据写入
 */

@Repository
public interface SysRolePrivilegeWriteDao extends BaseWriteDao<SysRolePrivilege> {

	/**
	 * 根据角色ID删除 角色权限关联
	 * 
	 * @param roleId
	 *            角色主键
	 * @return 删除结果（true/false）
	 */
	boolean deleteByRoleId(String roleId);

	/**
	 * 根据权限主键删除角色权限关系
	 * 
	 * @param privilegeId
	 *            权限主键
	 * @return 觉得权限关系列表
	 */
	boolean deleteByPrivilegeId(String privilegeId);
}
