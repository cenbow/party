package com.party.admin.web.dto.input.user;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 登陆输入视图
 * party
 * Created by wei.li
 * on 2016/8/29 0029.
 */
public class LoginInput {

    //登录名
    @NotBlank(message = "登陆名不不能为空")
    private String loginName;

    //密码
    @NotBlank(message = "密码不能为空")
    private String password;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
