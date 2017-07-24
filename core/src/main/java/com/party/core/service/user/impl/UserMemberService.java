package com.party.core.service.user.impl;

import com.google.common.base.Strings;
import com.party.common.paging.Page;
import com.party.common.utils.UUIDUtils;
import com.party.core.dao.read.user.UserMemberReadDao;
import com.party.core.dao.write.user.UserMemberWriteDao;
import com.party.core.model.user.UserMember;
import com.party.core.service.user.IUserMemberService;
import com.sun.istack.NotNull;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * 系统用户-会员实现
 * party
 * Created by wei.li
 * on 2016/8/18 0018.
 */
@Service
public class UserMemberService implements IUserMemberService {

    @Autowired
    private UserMemberReadDao userMemberReadDao;

    @Autowired
    private UserMemberWriteDao userMemberWriteDao;


    /**
     * 插入系统用户-会员
     * @param userMember 系统用户-会员
     * @return 插入结果（true/false）
     */
    public String insert(UserMember userMember) {
        String uuid = UUIDUtils.generateRandomUUID();
        userMember.setId(uuid);
        boolean result = userMemberWriteDao.insert(userMember);
        return null;
    }

    /**
     * 更新系统用户-会员
     * @param userMember 系统用户-会员
     * @return 更新结果（true/false）
     */
    public boolean update(UserMember userMember) {
        return userMemberWriteDao.update(userMember);
    }


    /**
     * 逻辑删除 系统用户-会员
     * @param id 实体主键
     * @return 删除结果（true/false）
     */
    public boolean deleteLogic(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return userMemberWriteDao.deleteLogic(id);
    }

    /**
     * 屋里删除 系统用户-会员
     * @param id 实体主键
     * @return 删除结果（true/false）
     */
    public boolean delete(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return userMemberWriteDao.delete(id);
    }


    /**
     * 批量逻辑删除 系统用户-会员
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    public boolean batchDeleteLogic(Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return userMemberWriteDao.batchDeleteLogic(ids);
    }

    /**
     * 批量物理删除 系统用户-会员
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    public boolean batchDelete(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return userMemberWriteDao.batchDelete(ids);
    }

    /**
     * 根据主键获取 系统用户-会员
     * @param id 主键
     * @return 系统用户-会员
     */
    public UserMember get(String id) {
        return userMemberReadDao.get(id);
    }


    /**
     * 根据会员主键查找 系统用户-会员关系
     * @param memberId 会员ID
     * @return 系统用户关系
     */
    public UserMember findByMemberId(String memberId) {
        UserMember userMember = new UserMember();
        userMember.setMemberId(memberId);
        List<UserMember> userMemberList = userMemberReadDao.listPage(userMember, null);

        if (!CollectionUtils.isEmpty(userMemberList)){
            return userMemberList.get(0);
        }
        return null;
    }

    /**
     * 分页查询 系统用户-会员
     * @param userMember 系统用户-会员
     * @param page 分页信息
     * @return 系统用户-会员列表
     */
    public List<UserMember> listPage(UserMember userMember, Page page) {
        return userMemberReadDao.listPage(userMember, page);
    }

    /**
     * 查询 所有系统用户-会员
     * @param userMember 系统用户-会员
     * @return 系统用户-会员列表
     */
    public List<UserMember> list(UserMember userMember) {
        return userMemberReadDao.listPage(userMember, null);
    }



    /**
     * 批量查询 系统用户-会员
     * @param ids 主键集合
     * @param userMember 系统用户-会员
     * @param page 分页信息
     * @return 系统用户-会员列表
     */
    public List<UserMember> batchList(@NotNull Set<String> ids, UserMember userMember, Page page) {
        if (CollectionUtils.isEmpty(ids)){
            return Collections.EMPTY_LIST;
        }
        return userMemberReadDao.batchList(ids, new HashedMap(), page);
    }
}
