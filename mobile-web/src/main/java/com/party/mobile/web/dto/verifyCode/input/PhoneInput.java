package com.party.mobile.web.dto.verifyCode.input;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

/**
 *
 * 验证码输入视图
 * party
 * Created by wei.li
 * on 2016/9/22 0022.
 */
public class PhoneInput {

    //验证手机号
    @NotBlank(message = "手机不能为空")
    private String phone;

    //手机验证码
    @NotBlank(message = "验证码不能为空")
    private String imgCode;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public String getImgCode() {
        return imgCode;
    }

    public void setImgCode(String imgCode) {
        this.imgCode = imgCode;
    }
}
