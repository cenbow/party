package com.party.mobile.web.dto.member.output;

import com.party.core.model.member.Member;
import org.springframework.beans.BeanUtils;

/**
 * party
 * Created by wei.li
 * on 2016/9/23 0023.
 */
public class RegisterOutput {

    //主键
    private String id;

    //公司主键
    private String company;

    //用户名
    private String username;

    //姓名
    private String realname;

    //拼音
    private String pinyin;

    //手机号
    private String mobile;

    //职位
    private String jobTitle;

    //行业
    private String industry;

    //城市
    private String city;

    //资源对接
    private String wantRes;

    //用户状态
    private Integer userStatus;

    //头像
    private String logo;

    //是否为管理员
    private Integer isAdmin;

    //是否达人(0：不是 1：是)
    private Integer isExpert;

    //是否允许分销（0:：不是 1：是）
    private Integer isDistributor;

    //推荐理由
    private String recommend;

    //个性签名
    private String signature;

    //消息推送id
    private String registrationId;

    //动态图片数量
    private Integer picNum;

    // 是否公开资料(0:不公开，1：公开)
    private Integer isOpen;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }

    public Integer getPicNum() {
        return picNum;
    }

    public void setPicNum(Integer picNum) {
        this.picNum = picNum;
    }

    public Integer getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Integer isOpen) {
        this.isOpen = isOpen;
    }

    /**
     * 会员转注册输出视图
     * @param member 会员
     * @return 交互数据
     */
    public static RegisterOutput transform(Member member){
        RegisterOutput registerOutput = new RegisterOutput();
        BeanUtils.copyProperties(member, registerOutput);
        return registerOutput;
    }
}
