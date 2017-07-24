package com.party.web.web.dto.output.user;

import com.party.core.model.user.User;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * 用户输出视图
 * party
 * Created by wei.li
 * on 2016/8/27 0027.
 */
public class UserOutput {

    //主键
    private String id;

    //登录名
    private String loginName;

    //密码
    private String password;

    //姓名
    private String name;

    //手机
    private String mobile;

    //邮箱
    private String email;

    //最后登陆时间
    private Date loginDate;

    //删除标记
    private String delFlag;

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * 实体转输出视图
     * @param user 用户实体
     * @return 用户输出视图
     */
    public static UserOutput transform(User user){
        UserOutput userOutput = new UserOutput();
        BeanUtils.copyProperties(user, userOutput);
        return userOutput;
    }
}
