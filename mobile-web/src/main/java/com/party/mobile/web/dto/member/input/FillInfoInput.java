package com.party.mobile.web.dto.member.input;

import com.party.core.model.member.Member;
import com.party.mobile.utils.MyBeanUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.BeanUtils;

/**
 * 完善用户资料输入视图
 * party
 * Created by wei.li
 * on 2016/9/23 0023.
 */
public class FillInfoInput {

    //用户名
    @NotBlank(message = "昵称不能为空")
    private String realname;

    //真实姓名
    @NotBlank(message = "真实姓名不能为空")
    private String fullname;
    //城市
    @NotBlank(message = "城市id不能为空")
    private String city;

    //公司名
    @NotBlank(message = "公司名不能为空")
    private String company;

    //职位
    @NotBlank(message = "职位不能为空")
    private String jobTitle;

    //行业
    @NotBlank(message = "行业id不能为空")
    private String industry;

    // 性别（0：女 1：男）
    private Integer sex;

    //微信号
    @NotBlank(message = "微信号不能为空")
    private String wechatNum;

    //个性签名
    private String signature;

    //对接资源
    @NotBlank(message = "对接资源不能为空")
    private String wantRes;

    private Integer isOpen;

    private Integer userStatus;

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getWechatNum() {
        return wechatNum;
    }

    public void setWechatNum(String wechatNum) {
        this.wechatNum = wechatNum;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getWantRes() {
        return wantRes;
    }

    public void setWantRes(String wantRes) {
        this.wantRes = wantRes;
    }

    /**
     * 将输入视图转换为实体
     *
     * @param member        会员
     * @param fillInfoInput 会员输入视图
     * @return 会员
     */
    public static Member transform(Member member, FillInfoInput fillInfoInput) throws Exception {
        MyBeanUtils.copyBeanNotNull2Bean(fillInfoInput, member);
        return member;
    }

    public Integer getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Integer isOpen) {
        this.isOpen = isOpen;
    }
}
