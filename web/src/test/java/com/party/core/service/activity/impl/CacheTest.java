package com.party.core.service.activity.impl;

import com.party.basetest.BaseTest;
import com.party.core.model.user.User;
import com.party.core.service.user.IUserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * party
 * Created by wei.li
 * on 2016/9/9 0009.
 */
public class CacheTest extends BaseTest{

    @Autowired
    private IUserService userService;


    @Test
    public void test(){
        User user = userService.get("1");
        System.out.println(user);
        //user.setOldIp("123");

        userService.update(user);
        user = userService.get("1");
        System.out.println("old"+user);
    }
}
