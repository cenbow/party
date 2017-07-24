package com.party.core.service.user;

import com.party.core.model.user.UserRole;
import com.party.core.service.IBaseService;

import java.util.List;

/**
 * 用户角色关系服务接口
 * party
 * Created by wei.li
 * on 2016/8/27 0027.
 */
public interface IUserRoleService extends IBaseService<UserRole>{

    /**
     * 根据用户主键查找用户角色关系
     * @param userId 用户主键
     * @return 用户角色列表
     */
    List<UserRole> findByUserId(String userId);

    /**
     * 根据角色主键查找用户角色关系
     * @param roleId 角色主键
     * @return 用户角色列表
     */
    List<UserRole> findByRoleId(String roleId);

    /**
     * 根据用户ID删除 用户角色关系
     * @param userId 用户主键
     * @return 删除结果（true/false）
     */
    boolean deleteByUserId(String userId);

    /**
     * 根据角色ID删除 用户角色关系
     * @param roleId 角色主键
     * @return 删除结果（true/false）
     */
    boolean deleteByRoleId(String roleId);
}
