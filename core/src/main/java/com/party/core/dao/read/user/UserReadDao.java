package com.party.core.dao.read.user;

import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.user.User;
import org.springframework.stereotype.Repository;

/**
 * 后台用户数据读取
 * party
 * Created by wei.li
 * on 2016/8/18 0018.
 */
@Repository
public interface UserReadDao extends BaseReadDao<User> {

    /**
     * 根据用户名查找用户信息
     * @param name 用户名
     * @return 用户信息
     */
    User findByLoginName(String name);
}
