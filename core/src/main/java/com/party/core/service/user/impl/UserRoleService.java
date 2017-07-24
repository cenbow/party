package com.party.core.service.user.impl;

import com.google.common.base.Strings;
import com.party.common.paging.Page;
import com.party.core.dao.read.user.UserRoleReadDao;
import com.party.core.dao.write.user.UserRoleWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.user.UserRole;
import com.party.core.service.user.IUserRoleService;
import com.sun.istack.NotNull;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;

/**
 * 用户角色服务实心
 * party
 * Created by wei.li
 * on 2016/8/27 0027.
 */

@Service
public class UserRoleService implements IUserRoleService {

    @Autowired
    private UserRoleWriteDao userRoleWriteDao;

    @Autowired
    private UserRoleReadDao userRoleReadDao;

    /**
     * 用户角色关系插入
     * @param userRole 用户角色
     * @return 插入结果（true/false）
     */
    public String insert(UserRole userRole) {
        userRoleWriteDao.insert(userRole);
        return null;
    }

    /**
     * 用户角色关系更新
     * @param userRole 用户角色
     * @return 更新结果（true/false）
     */
    public boolean update(UserRole userRole) {
        return userRoleWriteDao.update(userRole);
    }

    /**
     * 用户角色关系逻辑删除
     * @param id 实体主键
     * @return 删除结果（true/false）
     */
    public boolean deleteLogic(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return userRoleWriteDao.deleteLogic(id);
    }

    /**
     * 用户角色关系物理删除
     * @param id 实体主键
     * @return 删除结果（true/false）
     */
    public boolean delete(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return userRoleWriteDao.delete(id);
    }

    /**
     * 根据用户ID删除 用户角色关系
     * @param userId 用户主键
     * @return 删除结果（true/false）
     */
    public boolean deleteByUserId(@NotNull String userId) {
        if (Strings.isNullOrEmpty(userId)){
            return false;
        }
        return userRoleWriteDao.deleteByUserId(userId);
    }


    /**
     * 根据角色ID删除 用户角色关系
     * @param roleId 角色主键
     * @return 删除结果（true/false）
     */
    public boolean deleteByRoleId(@NotNull String roleId) {
        return false;
    }

    /**
     * 用户角色关系批量逻辑删除
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    public boolean batchDeleteLogic(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return userRoleWriteDao.batchDeleteLogic(ids);
    }

    /**
     * 用户角色关系批量物理删除
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    public boolean batchDelete(Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return userRoleWriteDao.batchDelete(ids);
    }


    public UserRole get(String id) {
        return null;
    }

    /**
     * 用户角色分页查询
     * @param userRole 用户角色
     * @param page 分页信息
     * @return 用户角色关系列表
     */
    public List<UserRole> listPage(UserRole userRole, Page page) {
        return userRoleReadDao.listPage(userRole, page);
    }

    /**
     * 查询所有用户角色关系
     * @param userRole 用户角色关系
     * @return 用户角色关系列表
     */
    public List<UserRole> list(UserRole userRole) {
        return userRoleReadDao.listPage(userRole, null);
    }

    /**
     * 根据用户主键查找用户角色关系
     * @param userId 用户主键
     * @return 用户角色列表
     */
    public List<UserRole> findByUserId(String userId) {
        UserRole userRole = new UserRole();
        userRole.setUserId(userId);
        List<UserRole> userRoleList = this.list(userRole);
        return userRoleList;
    }

    /**
     * 根据角色主键查找用户角色关系
     * @param roleId 角色主键
     * @return 用户角色列表
     */
    public List<UserRole> findByRoleId(String roleId) {
        UserRole userRole = new UserRole();
        userRole.setRoleId(roleId);
        List<UserRole> userRoleList = this.list(userRole);
        return userRoleList;
    }

    public List<UserRole> batchList(@NotNull Set<String> ids, UserRole userRole, Page page) {
        return null;
    }
}
