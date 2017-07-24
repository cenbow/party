package com.party.mobile.web.dto.login.input;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 第三方授权账号绑定手机号输入视图
 * party
 * Created by Wesley
 * on 2016/10/31.
 */
public class BindInput {

    //手机号
    @NotBlank(message = "手机号码不能为空")
    private String phone;

    //手机验证码
    @NotBlank(message = "手机验证码不能为空")
    private String verifyCode;

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
}
