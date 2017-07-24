package com.party.core.dao.read.system;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.system.SysPrivilege;

import java.util.List;
import java.util.Set;

/**
 * 权限读取
 * 
 * @author Administrator
 *
 */

@Repository
public interface SysPrivilegeReadDao extends BaseReadDao<SysPrivilege> {

    /**
     * 根据会员编号查询权限
     * @param memberId 会员编号
     * @return 权限集合
     */
    List<SysPrivilege> findByMemberId(@Param(value = "memberId") String memberId);

    /**
     * 根据角色查询权限
     * @param roles 角色集合
     * @return 权限集合
     */
    List<SysPrivilege> findByRoles(@Param(value = "roles")Set<String> roles);
}
