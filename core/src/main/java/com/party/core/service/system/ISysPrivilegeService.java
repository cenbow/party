package com.party.core.service.system;

import java.util.List;
import java.util.Set;

import com.party.core.model.system.SysPrivilege;
import com.party.core.service.IBaseService;

/**
 * 权限接口
 * 
 * @author Administrator
 *
 */
public interface ISysPrivilegeService extends IBaseService<SysPrivilege> {
	/**
	 * 根据角色查询权限
	 * 
	 * @param roleId
	 * @return
	 */
	List<SysPrivilege> findByRoleId(String roleId);


	/**
	 * 查询顶级权限
	 * @return 权限列表
	 */
	List<SysPrivilege> findTop();


	/**
	 * 根据父编号查询
	 * @param parentId 父编号
	 * @return 权限列表
	 */
	List<SysPrivilege> findByParent(String parentId);

	/**
	 * 查询用户的权限
	 * @param memberId 会员编号
	 * @return 权限集合
	 */
	List<SysPrivilege> findByMemberId(String memberId);

	/**
	 * 根据角色查询资源
	 * @param roles 角色集合
	 * @return 资源集合
	 */
	List<SysPrivilege> findByRoles(Set<String> roles);

	/**
	 * 提取资源代码
	 * @param privileges 资源集合
	 * @return 代码集合
	 */
	Set<String> extractCode(List<SysPrivilege> privileges);
}
