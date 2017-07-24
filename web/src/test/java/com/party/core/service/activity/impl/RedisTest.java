package com.party.core.service.activity.impl;

import com.party.basetest.BaseTest;
import com.party.core.model.user.User;
import com.party.core.redisDao.user.UserRedisDao;
import com.party.core.service.user.IUserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * party
 * Created by wei.li
 * on 2016/9/7 0007.
 */
public class RedisTest extends BaseTest {

    @Autowired
    UserRedisDao userRedisDao;

    @Test
    public void set(){
        User user = new User();
        user.setId("axiang");
        user.setName("fff");
        userRedisDao.setValue(user.getId(), user);

    }

    @Test
    public void get(){
        User user = userRedisDao.getValue("axiang");
        System.out.println(user.getName());
    }


}
