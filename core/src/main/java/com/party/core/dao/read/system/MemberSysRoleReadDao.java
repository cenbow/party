package com.party.core.dao.read.system;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.system.MemberSysRole;
import com.party.core.model.system.SysRolePrivilege;

/**
 * 用户角色关联数据读取 party Created by wei.li on 2016/8/26 0026.
 */
@Repository
public interface MemberSysRoleReadDao extends BaseReadDao<MemberSysRole> {

	/**
	 * 根据角色查询角色权限集合
	 * 
	 * @param roleIds
	 * @return
	 */
	List<SysRolePrivilege> findByRoleIds(@Param("roleIds") Set<String> roleIds);
}
