package com.party.core.service.role.impl;


import com.party.common.paging.Page;
import com.party.common.utils.LangUtils;
import com.party.common.utils.UUIDUtils;
import com.party.core.dao.read.role.RoleReadDao;
import com.party.core.dao.write.role.RoleWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.role.Role;
import com.party.core.model.user.UserRole;
import com.party.core.service.role.IRoleService;
import com.party.core.service.user.IUserRoleService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 角色服务实现
 * party
 * Created by wei.li
 * on 2016/8/27 0027.
 */

@Service
public class RoleService implements IRoleService {

    @Autowired
    private RoleReadDao roleReadDao;

    @Autowired
    private RoleWriteDao roleWriteDao;

    @Autowired
    private IUserRoleService userRoleService;

    /**
     * 角色插入
     * @param role 角色信息
     * @return 插入结果（true/false）
     */
    public String insert(Role role) {
        BaseModel.preInsert(role);
        boolean result = roleWriteDao.insert(role);
        if (result){
            return role.getId();
        }
        return null;
    }

    /**
     * 角色更新
     * @param role 角色信息
     * @return 更新结果（true/false）
     */
    public boolean update(Role role) {
        role.setUpdateDate(new Date());
        return roleWriteDao.update(role);
    }

    /**
     * 角色逻辑删除
     * @param id 实体主键
     * @return 删除结果（true/false）
     */
    public boolean deleteLogic(String id) {
        return roleWriteDao.deleteLogic(id);
    }

    /**
     * 角色物理删除
     * @param id 实体主键
     * @return 删除结果（true/false）
     */
    public boolean delete(String id) {
        return roleWriteDao.delete(id);
    }

    /**
     * 角色批量逻辑删除
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    public boolean batchDeleteLogic(Set<String> ids) {
        return roleWriteDao.batchDeleteLogic(ids);
    }

    /**
     * 角色批量物理删除
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    public boolean batchDelete(Set<String> ids) {
        return roleWriteDao.batchDelete(ids);
    }

    /**
     * 根据主键获取角色信息
     * @param id 主键
     * @return 角色信息
     */
    public Role get(String id) {
        return roleReadDao.get(id);
    }


    /**
     * 根据用户主键查找角色列表
     * @param userId 用户id
     * @return 角色列表
     */
    public List<Role> findByUserId(String userId) {
        List<UserRole> userRoleList = userRoleService.findByUserId(userId);
        List<String> roleIdList = LangUtils.transform(userRoleList, input -> input.getRoleId());
        if (CollectionUtils.isEmpty(roleIdList)){
            return Collections.EMPTY_LIST;
        }
        List<Role> roleList = roleReadDao.batchList(new HashSet<>(roleIdList), null, null);
        return roleList;
    }

    /**
     * 角色分页查询
     * @param role 角色信息
     * @param page 分页信息
     * @return 角色列表
     */
    public List<Role> listPage(Role role, Page page) {
        return roleReadDao.listPage(role, page);
    }

    /**
     * 查询所有角色信息
     * @param role 角色信息
     * @return 角色列表
     */
    public List<Role> list(Role role) {
        return roleReadDao.listPage(role, null);
    }

    /**
     * 批量查询角色信息
     * @param ids 主键集合
     * @param role 角色信息
     * @param page 分页信息
     * @return 角色列表
     */
    public List<Role> batchList(Set<String> ids, Role role, Page page) {
        return roleReadDao.batchList(ids, new HashedMap(), page);
    }
}
