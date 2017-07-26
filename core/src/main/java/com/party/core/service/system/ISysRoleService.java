package com.party.core.service.system;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.party.core.model.system.SysRole;
import com.party.core.service.IBaseService;

/**
 * 角色接口
 * 
 * @author Administrator
 *
 */
public interface ISysRoleService extends IBaseService<SysRole> {

	/**
	 * 根据用户查询角色
	 * 
	 * @param memberId
	 * @return
	 */
	List<SysRole> findByMemberId(String memberId);

	/**
	 * 根据会员编号查询
	 * @param memberId 会员编号
	 * @param type 类型
	 * @return 角色集合
	 */
	List<SysRole> findByMemberId(String memberId, Integer type);

	SysRole uniqueProperty(String property, String value);

	/**
	 * 提取角色代码
	 * @param roles 角色集合
	 * @return 代码集合
	 */
	Set<String> extractCode(List<SysRole> roles);

	List<SysRole> getRoleByMemberId(Map<String, Object> params);
}
