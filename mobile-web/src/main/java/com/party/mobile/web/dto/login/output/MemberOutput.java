package com.party.mobile.web.dto.login.output;


import com.party.core.model.member.Member;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * 会员输出视图
 * party
 * Created by wei.li
 * on 2016/9/21 0021.
 */
public class MemberOutput {

    //公司名
    private String company;

    //姓名
    private String realname;

    //手机号
    private String mobile;

    //职位
    private String jobTitle;

    //行业id
    private String industryId;

    //行业名
    private String industry;

    //城市area id
    private String cityId;

    //城市名
    private String cityName;

    //资源对接
    private String wantRes;

    //用户状态
    private Integer userStatus;

    //头像
    private String logo;

    //是否为管理员(0：不是 1：是)
    private Integer isAdmin;

    //是否达人(0：不是 1：是)
    private Integer isExpert;

    //是否允许分销（0:：不是 1：是）
    private Integer isDistributor;

    //推荐理由
    private String recommend;

    //个性签名
    private String signature;

    //粉丝个数
    private int fansNum;

    //关注个数
    private int focusNum;

    //动态图片数量
    private int picNum;

    // 是否公开资料(0:不公开，1：公开)
    private Integer isOpen;

    //交互令牌
    private String token;

    //消息推送id
    private String registrationId;
    
    //用户名
    private String username;

    //真实姓名
    private String fullname;

    // 是否合作商（0：不是 1：是）
    private Integer isPartner;

    //微信号
    private String wechatNum;

    // 性别（0：女 1：男）
    private Integer sex;

//    //绑定的第三方openIds
//    private List<ThirdPartyUserOutput> thirdPartyUserList;


    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getWechatNum() {
        return wechatNum;
    }

    public void setWechatNum(String wechatNum) {
        this.wechatNum = wechatNum;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getCompany() {
        return company;
    }

    public Integer getIsPartner() {
        return isPartner;
    }

    public void setIsPartner(Integer isPartner) {
        this.isPartner = isPartner;
    }

    public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setCompany(String company) {
        this.company = company;
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

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Integer getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Integer isAdmin) {
        this.isAdmin = isAdmin;
    }

    public Integer getIsExpert() {
        return isExpert;
    }

    public void setIsExpert(Integer isExpert) {
        this.isExpert = isExpert;
    }

    public Integer getIsDistributor() {
        return isDistributor;
    }

    public void setIsDistributor(Integer isDistributor) {
        this.isDistributor = isDistributor;
    }

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public int getFansNum() {
        return fansNum;
    }

    public void setFansNum(int fansNum) {
        this.fansNum = fansNum;
    }

    public int getFocusNum() {
        return focusNum;
    }

    public void setFocusNum(int focusNum) {
        this.focusNum = focusNum;
    }

    public int getPicNum() {
        return picNum;
    }

    public void setPicNum(int picNum) {
        this.picNum = picNum;
    }

    public Integer getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Integer isOpen) {
        this.isOpen = isOpen;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }

    public String getIndustryId() {
        return industryId;
    }

    public void setIndustryId(String industryId) {
        this.industryId = industryId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public static MemberOutput transform(Member member){
        MemberOutput memberOutput = new MemberOutput();
        BeanUtils.copyProperties(member, memberOutput);
        //将会员主键id作为jpush推送id
        memberOutput.setRegistrationId(member.getId());
        return memberOutput;
    }
}
