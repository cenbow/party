package com.party.mobile.biz.user;

import com.party.authorization.manager.impl.RedisTokenManager;
import com.party.core.model.user.User;
import com.party.mobile.utils.RealmUtils;
import com.party.mobile.web.dto.login.output.CurrentUser;
import com.party.mobile.web.dto.user.output.LoginOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户业务服务接口
 * party
 * Created by wei.li
 * on 2016/10/24 0024.
 */

@Service
public class UserBizService {

    @Autowired
    RedisTokenManager redisTokenManager;


    /**
     * 用戶登陸
     * @param user 用戶信息
     * @return 登陸輸出視圖
     */
    public LoginOutput login(User user){
        String token = this.getToken(user);

        LoginOutput loginOutput = new LoginOutput();
        loginOutput.setToken(token);
        return loginOutput;
    }

    /**
     * 获取token
     * @param user 用户信息
     * @return token值
     */
    public String getToken(User user){
        CurrentUser currentUser = CurrentUser.transform(user);
        String token = RealmUtils.getToken(currentUser);
        redisTokenManager.createRelationship(user.getId(), token);
        return token;
    }
}
