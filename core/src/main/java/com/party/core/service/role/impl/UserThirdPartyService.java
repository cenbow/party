package com.party.core.service.role.impl;

import com.google.common.base.Strings;
import com.party.common.paging.Page;
import com.party.common.utils.UUIDUtils;
import com.party.core.dao.read.user.UserThirdPartyReadDao;
import com.party.core.dao.write.user.UserThirdPartyWriteDao;
import com.party.core.model.user.UserThirdParty;
import com.party.core.service.role.IUserThirdPartyService;
import com.sun.istack.NotNull;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * 用户供应商关系服务实现
 * party
 * Created by wei.li
 * on 2016/9/13 0013.
 */

@Service
public class UserThirdPartyService implements IUserThirdPartyService{

    @Autowired
    UserThirdPartyReadDao userThirdPartyReadDao;

    @Autowired
    UserThirdPartyWriteDao userThirdPartyWriteDao;

    /**
     * 用户供应商关系插入
     * @param userThirdParty 用户供应商关系
     * @return 插入结果（true/false）
     */
    @Override
    public String insert(UserThirdParty userThirdParty) {
        userThirdParty.setId(UUIDUtils.generateRandomUUID());
        boolean result = userThirdPartyWriteDao.insert(userThirdParty);
        if (result){
            return userThirdParty.getId();
        }
        return null;
    }


    /**
     * 用户供应商关系更新
     * @param userThirdParty 用户供应商关系
     * @return 更新结果（true/false）
     */
    @Override
    public boolean update(UserThirdParty userThirdParty) {
        return userThirdPartyWriteDao.update(userThirdParty);
    }


    /**
     * 用户供应商关系逻辑删除
     * @param id 实体主键
     * @return 删除结果（true/false）
     */
    @Override
    public boolean deleteLogic(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return userThirdPartyWriteDao.deleteLogic(id);
    }

    /**
     * 用户供应商关系物理删除
     * @param id 实体主键
     * @return 删除结果(true/false)
     */
    @Override
    public boolean delete(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return userThirdPartyWriteDao.delete(id);
    }


    /**
     * 用户供应商关系逻辑批量删除
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    @Override
    public boolean batchDeleteLogic(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return userThirdPartyWriteDao.batchDeleteLogic(ids);
    }

    /**
     * 用户供应商关系批量物理删除
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    @Override
    public boolean batchDelete(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return userThirdPartyWriteDao.batchDelete(ids);
    }

    /**
     * 根据主键获取用户供应商关系
     * @param id 主键
     * @return 用户供应商关系
     */
    @Override
    public UserThirdParty get(String id) {
        return userThirdPartyReadDao.get(id);
    }


    /**
     * 用户供应商关系分页查询
     * @param userThirdParty 用户供应商关系
     * @param page 分页信息
     * @return 用户供应商关系列表
     */
    @Override
    public List<UserThirdParty> listPage(UserThirdParty userThirdParty, Page page) {
        return userThirdPartyReadDao.listPage(userThirdParty, page);
    }

    /**
     * 查询所有的用户供应商关系
     * @param userThirdParty 用户供应商关系
     * @return 用户供应商关系列表
     */
    @Override
    public List<UserThirdParty> list(UserThirdParty userThirdParty) {
        return userThirdPartyReadDao.listPage(userThirdParty, null);
    }

    /**
     * 批量查询用户供应商关系
     * @param ids 主键集合
     * @param userThirdParty 用户供应商关系
     * @param page 分页信息
     * @return 用户供应商关系列表
     */
    @Override
    public List<UserThirdParty> batchList(@NotNull Set<String> ids, UserThirdParty userThirdParty, Page page) {
        if (CollectionUtils.isEmpty(ids)){
            return Collections.EMPTY_LIST;
        }
        return userThirdPartyReadDao.batchList(ids, new HashedMap(), page);
    }
}
