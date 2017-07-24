package com.party.mobile.web.dto.sign.input;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 签到报名输入视图
 * Created by wei.li
 *
 * @date 2017/6/12 0012
 * @time 15:28
 */
public class SignApplyInput {

    //项目编号
    @NotBlank(message = "签到项目编号不能为空")
    private String id;

    //真实姓名
    private String realname;

    //手机号
    private String mobile;

    //验证码
    private String verifyCode;

    //公司信息
    private String company;

    //职位
    private String title;

    //行业
    private String industry;

    //队伍编号
    private String groupId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}
