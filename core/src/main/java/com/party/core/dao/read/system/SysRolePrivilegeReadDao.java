package com.party.core.dao.read.system;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.system.SysRolePrivilege;

/**
 * 角色权限关联数据读取
 */

@Repository
public interface SysRolePrivilegeReadDao extends BaseReadDao<SysRolePrivilege> {

	/**
	 * 根据角色查找权限
	 * 
	 * @param roleIds
	 *            角色集合
	 * @return 角色菜单列表
	 */
	List<SysRolePrivilege> findByRoleIds(@Param("roleIds") Set<String> roleIds);
}
