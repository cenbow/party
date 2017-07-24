package com.party.core.dao.write.role;

import com.party.core.dao.write.BaseWriteDao;
import com.party.core.model.role.RoleMenu;
import org.springframework.stereotype.Repository;

/**
 * 角色菜单关系数据写入
 * party
 * Created by wei.li
 * on 2016/8/26 0026.
 */

@Repository
public interface RoleMenuWriteDao extends BaseWriteDao<RoleMenu>{

    /**
     * 根据角色ID删除 角色菜单关联
     * @param roleId 角色主键
     * @return 删除结果（true/false）
     */
    boolean deleteByRoleId(String roleId);

    /**
     * 根据菜单主键删除角色菜单关系
     * @param menuId 菜单主键
     * @return 觉得菜单关系列表
     */
    boolean deleteByMenuId(String menuId);
}
