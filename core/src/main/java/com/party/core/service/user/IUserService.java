package com.party.core.service.user;

import com.party.core.model.user.User;
import com.party.core.service.IBaseService;

import java.util.List;

/**
 * 系统用户接口
 * party
 * Created by wei.li
 * on 2016/8/18 0018.
 */
public interface IUserService extends IBaseService<User>{

    /**
     * 查询所有系统用户
     * @return 系统用户列表
     */
    List<User> listAll();

    /**
     * 查询所有合作商
     * @return 合作商列表
     */
    List<User> listPartner();

    /**
     * 根据用户名获取用户
     * @param name 用户名
     * @return 用户信息
     */
    User findByLoginName(String name);


    /**
     * 缓存用户实体
     * @param user 用户实体
     */
    void setValue(User user);

    /**
     * 获取缓存实体
     * @param key 缓存key
     * @return 缓存用户
     */
    User getValue(String key);

    /**
     * 删除缓存
     * @param key 缓存key
     */
    void deleteValue(String key);

}
