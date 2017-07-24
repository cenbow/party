package com.party.core.service.user.impl;

import com.google.common.base.Strings;
import com.party.common.paging.Page;
import com.party.common.utils.UUIDUtils;
import com.party.core.dao.read.user.UserReadDao;
import com.party.core.dao.write.user.UserWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.user.User;
import com.party.core.redisDao.user.UserRedisDao;
import com.party.core.service.user.IUserService;

import com.sun.istack.NotNull;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


/**
 * 系统用户实现
 * party
 * Created by wei.li
 * on 2016/8/18 0018.
 */
@Service
public class UserService  implements IUserService{

    @Autowired
    private UserReadDao userReadDao;

    @Autowired
    private UserWriteDao userWriteDao;

    @Autowired
    private UserRedisDao userRedisDao;

    /**
     * 缓存实体
     * @param user 用户实体
     */
    @Override
    public void setValue(User user) {
        userRedisDao.setValue(user.getId(), user);
    }

    /**
     * 获取缓存实体
     * @param key 缓存key
     * @return 用户实体
     */
    @Override
    public User getValue(String key) {
        return userRedisDao.getValue(key);
    }

    /**
     * 删除缓存
     * @param key 缓存key
     */
    @Override
    public void deleteValue(String key) {
        userRedisDao.delete(key);
    }

    /**
     * 系统用户插入
     * @param user 系统用户信息
     * @return 插入结果（true/false）
     */
    public String insert(User user) {
        BaseModel.preInsert(user);
        user.setLoginDate(new Date());
        boolean result = userWriteDao.insert(user);
        if (result){
            return user.getId();
        }
        return null;
    }

    /**
     * 系统用户更新
     * @param user 系统用户信息
     * @return 更新结果（true/false）
     */
    public boolean update(User user) {
        //清除缓存
        user.setUpdateDate(new Date());
        deleteValue(user.getId());
        return userWriteDao.update(user);
    }



    /**
     * 逻辑删除系统用户
     * @param id 系统用户主键
     * @return 删除结果（true/false）
     */
    public boolean deleteLogic(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return userWriteDao.deleteLogic(id);
    }


    /**
     * 物理删除系统用户
     * @param id 系统用户主键
     * @return 删除结果（true/false）
     */
    public boolean delete(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }

        //清除缓存
        deleteValue(id);
        return userWriteDao.delete(id);
    }

    /**
     * 批量逻辑删除系统用户
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    public boolean batchDeleteLogic(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return userWriteDao.batchDelete(ids);
    }

    /**
     * 批量物理删除系统用户
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    public boolean batchDelete(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return userWriteDao.batchDelete(ids);
    }


    /**
     * 根据主键获取系统用户
     * @param id 主键
     * @return 系统用户信息
     */
    public User get(String id) {

        //是否存在缓存
        User redisUser = getValue(id);
        if (null != redisUser){
            return redisUser;
        }

        User user = userReadDao.get(id);
        setValue(user);
        return user;
    }

    /**
     * 根据用户名获取用户信息
     * @param name 用户名
     * @return 用户信息
     */
    public User findByLoginName(String name) {
        return userReadDao.findByLoginName(name);
    }

    /**
     * 分页查询系统用户信息
     * @param user 系统用户信息
     * @param page 分页信息
     * @return 系统用户列表
     */
    public List<User> listPage(User user, Page page) {
        return userReadDao.listPage(user, page);
    }

    /**
     * 条件查询所有系统用户信息
     * @param user 系统用户信息
     * @return 系统用户列表
     */
    public List<User> list(User user) {
        return userReadDao.listPage(user, null);
    }

    public List<User> listPartner() {
        User user = new User();
        user.setUserType(4);
        return this.list(user);
    }

    /**
     * 查询所有系统用户信息
     * @return 系统用户信息
     */
    public List<User> listAll() {
        return userReadDao.listPage(null, null);
    }


    /**
     * 批量查询系统用户信息
     * @param ids 主键集合
     * @param user 系统用户信息
     * @param page 分页信息
     * @return 系统用户列表
     */
    public List<User> batchList(@NotNull Set<String> ids, User user, Page page) {
        if (CollectionUtils.isEmpty(ids)){
            return Collections.EMPTY_LIST;
        }
        return userReadDao.batchList(ids, new HashedMap(), page);
    }
}
