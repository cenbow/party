package com.party.core.dao.read.role;

import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.role.RoleMenu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * 角色菜单关联数据读取
 * party
 * Created by wei.li
 * on 2016/8/26 0026.
 */

@Repository
public interface RoleMenuReadDao extends BaseReadDao<RoleMenu> {

    /**
     * 根据角色查找角色菜单
     * @param roleIds 角色集合
     * @return 角色菜单列表
     */
    List<RoleMenu> findByRoleIds(@Param("roleIds") Set<String> roleIds);
}
