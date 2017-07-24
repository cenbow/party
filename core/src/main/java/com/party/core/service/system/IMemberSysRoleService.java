package com.party.core.service.system;

import java.util.List;

import com.party.core.model.system.MemberSysRole;
import com.party.core.service.IBaseService;

/**
 * 用户角色关系服务接口 party Created by wei.li on 2016/8/27 0027.
 */
public interface IMemberSysRoleService extends IBaseService<MemberSysRole> {

	/**
	 * 根据用户主键查找用户角色关系
	 * 
	 * @param memberId
	 *            用户主键
	 * @return 用户角色列表
	 */
	List<MemberSysRole> findByMemberId(String memberId);

	/**
	 * 根据角色主键查找用户角色关系
	 * 
	 * @param roleId
	 *            角色主键
	 * @return 用户角色列表
	 */
	List<MemberSysRole> findByRoleId(String roleId);

	/**
	 * 根据用户ID删除 用户角色关系
	 * 
	 * @param memberId
	 *            用户主键
	 * @return 删除结果（true/false）
	 */
	boolean deleteByMemberId(String memberId);

	/**
	 * 根据角色ID删除 用户角色关系
	 * 
	 * @param roleId
	 *            角色主键
	 * @return 删除结果（true/false）
	 */
	boolean deleteByRoleId(String roleId);

	/**
	 * 根据用户ID和角色ID删除用户角色关系
	 * 
	 * @param roleId
	 *            角色编号
	 * @param memberId
	 *            角色编号
	 * @return 删除结果（true/false）
	 */
	boolean deleteByRoleIdAndMemberId(String roleId, String memberId);
}
