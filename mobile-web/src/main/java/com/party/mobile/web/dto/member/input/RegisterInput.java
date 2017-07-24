package com.party.mobile.web.dto.member.input;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 会员注册输入视图
 * party
 * Created by wei.li
 * on 2016/9/22 0022.
 */
public class RegisterInput {

    //手机号
    @NotBlank(message = "手机号码不能为空")
    private String phone;

    //手机验证码
    @NotBlank(message = "手机验证码不能为空")
    private String verifyCode;

    //登陆密码
    @NotBlank(message = "密码不能为空")
    private String password;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
