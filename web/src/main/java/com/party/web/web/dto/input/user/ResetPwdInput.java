package com.party.web.web.dto.input.user;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 登陆输入视图
 * party
 * Created by wei.li
 * on 2016/8/29 0029.
 */
public class ResetPwdInput {

    //登录名
    @NotBlank(message = "登陆名不不能为空")
    private String loginName;

    //密码
    @NotBlank(message = "密码不能为空")
    private String password;
    
    //手机验证码
    @NotBlank(message = "手机验证码不能为空")
    private String verifyCode;

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

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}
    
}
