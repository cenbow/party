package com.party.core.dao.read.system;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.system.SysRole;

/**
 * 角色读取
 * 
 * @author Administrator
 *
 */
@Repository
public interface SysRoleReadDao extends BaseReadDao<SysRole> {

	SysRole uniqueProperty(@Param(value = "property") String property, @Param(value = "value") String value);

}
