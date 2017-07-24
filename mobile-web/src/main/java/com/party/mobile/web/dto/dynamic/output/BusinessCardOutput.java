package com.party.mobile.web.dto.dynamic.output;

import com.party.core.model.member.Member;
import org.springframework.beans.BeanUtils;

/**
 * 名片输出视图
 *
 * @Author: Wesley
 * @Description:
 * @Date: Created in 12:05 16/11/18
 * @Modified by:
 */
public class BusinessCardOutput {

    //姓名
    private String realname;

    //头像
    private String logo;

    //手机号
    private String mobile;

    //职位
    private String jobTitle;

    //公司名
    private String company;

    //行业名称
    private String industryName;

    //城市名称
    private String cityName;

    //真实姓名
    private String fullname;

    //资源对接
    private String wantRes;

    // 性别（0：女 1：男）
    private Integer sex;

    //微信号
    private String wechatNum;

    //个性签名
    private String signature;

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getWechatNum() {
        return wechatNum;
    }

    public void setWechatNum(String wechatNum) {
        this.wechatNum = wechatNum;
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

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getWantRes() {
        return wantRes;
    }

    public void setWantRes(String wantRes) {
        this.wantRes = wantRes;
    }

    /**
     * 会员实体转输出视图
     * @param member 会员实体
     * @return 输出视图
     */
    public static BusinessCardOutput transform(Member member){
        BusinessCardOutput dyMemberOutput = new BusinessCardOutput();
        BeanUtils.copyProperties(member, dyMemberOutput);
        return dyMemberOutput;
    }
}
