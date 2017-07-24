package com.party.mobile.web.dto.member.input;

/**
 * 绑定手机输入
 */
public class BindPhoneInput {

    //验证码
    private String verifyCode;

    //手机
    private String mobile;

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
