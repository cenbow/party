package com.party.core.model.verifyCode;

import com.party.core.model.BaseModel;

/**
 * 手机验证码实体
 * party
 * Created by wei.li
 * on 2016/9/14 0014.
 */
public class VerifyCode extends BaseModel {

    private static final long serialVersionUID = -4974982012706366913L;

    //手机号
    private String phone;

    //验证码
    private String code;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        VerifyCode that = (VerifyCode) o;

        if (phone != null ? !phone.equals(that.phone) : that.phone != null) return false;
        return code != null ? code.equals(that.code) : that.code == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (code != null ? code.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "VerifyCode{" +
                "phone='" + phone + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
