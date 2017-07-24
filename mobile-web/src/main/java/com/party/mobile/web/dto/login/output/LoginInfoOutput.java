package com.party.mobile.web.dto.login.output;

import com.party.core.model.version.VersionManager;
import org.springframework.beans.BeanUtils;

/**
 * 第三方登录开关输出视图
 *
 * @Author: Wesley
 * @Description:
 * @Date: Created in 10:55 16/11/23
 * @Modified by:
 */
public class LoginInfoOutput {

    //qq登录开关(0：关，1：开)
    private Integer loginQQ;

    //wx登录开关(0：关，1：开)
    private Integer loginWX;

    public Integer getLoginQQ() {
        return loginQQ;
    }

    public void setLoginQQ(Integer loginQQ) {
        if (null == loginQQ)
            loginQQ = 0;
        this.loginQQ = loginQQ;
    }

    public Integer getLoginWX() {

        return loginWX;
    }

    public void setLoginWX(Integer loginWX) {
        if (null == loginWX)
            loginWX = 0;

        this.loginWX = loginWX;
    }

    public static LoginInfoOutput transform(VersionManager input){
        LoginInfoOutput output = new LoginInfoOutput();
        BeanUtils.copyProperties(input, output);

        return output;
    }
}
