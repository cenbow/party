package com.party.core.model.user;

import com.party.core.model.BaseModel;

import java.io.Serializable;
import java.util.Date;
import java.util.Optional;

/**
 * 系统用户实体
 * party
 * Created by wei.li
 * on 2016/8/18 0018.
 */
public class User extends BaseModel implements Serializable {

    private static final long serialVersionUID = -7162892845128195905L;
    //归属公司
    private String companyId;

    //归属部门
    private String officeId;

    //登录名
    private String loginName;

    //密码
    private String password;

    //工号
    private String no;

    //姓名
    private String name;

    //邮箱
    private String email;

    //电话
    private String phone;

    //手机
    private String mobile;

    //用户类型
    private Integer userType;

    //用户头像
    private String photo;

    //最后登陆IP
    private String loginIp;

    //最后登陆时间
    private Date loginDate;

    //是否可登录
    private String loginFlag;

    //二维码
    private String qrcode;

    //最后登陆IP
    private String oldLoginIp;

    //最后登陆时间
    private Date oldLoginDate;


    public String getOldLoginIp() {
        return oldLoginIp;
    }

    public void setOldLoginIp(String oldLoginIp) {
        this.oldLoginIp = oldLoginIp;
    }

    public Date getOldLoginDate() {
        return oldLoginDate;
    }

    public void setOldLoginDate(Date oldLoginDate) {
        this.oldLoginDate = oldLoginDate;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getOfficeId() {
        return officeId;
    }

    public void setOfficeId(String officeId) {
        this.officeId = officeId;
    }

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

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public String getLoginFlag() {
        return loginFlag;
    }

    public void setLoginFlag(String loginFlag) {
        this.loginFlag = loginFlag;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }


    public boolean isAdmin(){
        return Optional.ofNullable(this.userType).map(t -> t.equals(0)).orElse(false);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        User user = (User) o;

        if (companyId != null ? !companyId.equals(user.companyId) : user.companyId != null) return false;
        if (officeId != null ? !officeId.equals(user.officeId) : user.officeId != null) return false;
        if (loginName != null ? !loginName.equals(user.loginName) : user.loginName != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (no != null ? !no.equals(user.no) : user.no != null) return false;
        if (name != null ? !name.equals(user.name) : user.name != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (phone != null ? !phone.equals(user.phone) : user.phone != null) return false;
        if (mobile != null ? !mobile.equals(user.mobile) : user.mobile != null) return false;
        if (userType != null ? !userType.equals(user.userType) : user.userType != null) return false;
        if (photo != null ? !photo.equals(user.photo) : user.photo != null) return false;
        if (loginIp != null ? !loginIp.equals(user.loginIp) : user.loginIp != null) return false;
        if (loginDate != null ? !loginDate.equals(user.loginDate) : user.loginDate != null) return false;
        if (loginFlag != null ? !loginFlag.equals(user.loginFlag) : user.loginFlag != null) return false;
        if (qrcode != null ? !qrcode.equals(user.qrcode) : user.qrcode != null) return false;
        if (oldLoginIp != null ? !oldLoginIp.equals(user.oldLoginIp) : user.oldLoginIp != null) return false;
        return oldLoginDate != null ? oldLoginDate.equals(user.oldLoginDate) : user.oldLoginDate == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (companyId != null ? companyId.hashCode() : 0);
        result = 31 * result + (officeId != null ? officeId.hashCode() : 0);
        result = 31 * result + (loginName != null ? loginName.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (no != null ? no.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (mobile != null ? mobile.hashCode() : 0);
        result = 31 * result + (userType != null ? userType.hashCode() : 0);
        result = 31 * result + (photo != null ? photo.hashCode() : 0);
        result = 31 * result + (loginIp != null ? loginIp.hashCode() : 0);
        result = 31 * result + (loginDate != null ? loginDate.hashCode() : 0);
        result = 31 * result + (loginFlag != null ? loginFlag.hashCode() : 0);
        result = 31 * result + (qrcode != null ? qrcode.hashCode() : 0);
        result = 31 * result + (oldLoginIp != null ? oldLoginIp.hashCode() : 0);
        result = 31 * result + (oldLoginDate != null ? oldLoginDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "companyId='" + companyId + '\'' +
                ", officeId='" + officeId + '\'' +
                ", loginName='" + loginName + '\'' +
                ", password='" + password + '\'' +
                ", no='" + no + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", mobile='" + mobile + '\'' +
                ", userType=" + userType +
                ", photo='" + photo + '\'' +
                ", loginIp='" + loginIp + '\'' +
                ", loginDate=" + loginDate +
                ", loginFlag='" + loginFlag + '\'' +
                ", qrcode='" + qrcode + '\'' +
                ", oldLoginIp='" + oldLoginIp + '\'' +
                ", oldLoginDate=" + oldLoginDate +
                '}';
    }
}
