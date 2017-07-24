package com.party.core.model.member;

import com.party.core.model.BaseModel;

import java.io.Serializable;

/** 会员实体
 * party
 * Created by wei.li
 * on 2016/8/10 0010.
 */
public class Member extends BaseModel implements Serializable {

    private static final long serialVersionUID = 1431816573773333829L;

    //公司名
    private String company;

    //用户名
    private String username;

    //密码
    private String password;

    //姓名
    private String realname;
    
    // 真实姓名
    private String fullname;

    //拼音
    private String pinyin;

    //手机号
    private String mobile;
    
    // 性别（0：女 1：男）
    private Integer sex;
    
    private String qq;
    
    private String wechatNum;

    //职位
    private String jobTitle;

    //行业主键id
    private String industry;

    //城市主键id
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

    //是否允许分销（0：不是 1：是）
    private Integer isDistributor;
    
    // 是否合作商（0：不是 1：是）
    private Integer isPartner;

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
    
    private String perfectState; // 完善状态


    public Integer getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Integer isOpen) {
        this.isOpen = isOpen;
    }


    public Integer getPicNum() {
        return picNum;
    }

    public void setPicNum(Integer picNum) {
        this.picNum = picNum;
    }

    public Integer getIsDistributor() {
        return isDistributor;
    }

    public void setIsDistributor(Integer isDistributor) {
        this.isDistributor = isDistributor;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

	public Integer getIsPartner() {
		return isPartner;
	}

	public void setIsPartner(Integer isPartner) {
		this.isPartner = isPartner;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getWechatNum() {
		return wechatNum;
	}

	public void setWechatNum(String wechatNum) {
		this.wechatNum = wechatNum;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Member member = (Member) o;

        if (company != null ? !company.equals(member.company) : member.company != null) return false;
        if (username != null ? !username.equals(member.username) : member.username != null) return false;
        if (password != null ? !password.equals(member.password) : member.password != null) return false;
        if (realname != null ? !realname.equals(member.realname) : member.realname != null) return false;
        if (pinyin != null ? !pinyin.equals(member.pinyin) : member.pinyin != null) return false;
        if (mobile != null ? !mobile.equals(member.mobile) : member.mobile != null) return false;
        if (sex != null ? !sex.equals(member.sex) : member.sex != null) return false;
        if (qq != null ? !qq.equals(member.qq) : member.qq != null) return false;
        if (wechatNum != null ? !wechatNum.equals(member.wechatNum) : member.wechatNum != null) return false;
        if (jobTitle != null ? !jobTitle.equals(member.jobTitle) : member.jobTitle != null) return false;
        if (industry != null ? !industry.equals(member.industry) : member.industry != null) return false;
        if (city != null ? !city.equals(member.city) : member.city != null) return false;
        if (wantRes != null ? !wantRes.equals(member.wantRes) : member.wantRes != null) return false;
        if (userStatus != null ? !userStatus.equals(member.userStatus) : member.userStatus != null) return false;
        if (logo != null ? !logo.equals(member.logo) : member.logo != null) return false;
        if (isAdmin != null ? !isAdmin.equals(member.isAdmin) : member.isAdmin != null) return false;
        if (isExpert != null ? !isExpert.equals(member.isExpert) : member.isExpert != null) return false;
        if (isDistributor != null ? !isDistributor.equals(member.isDistributor) : member.isDistributor != null)
            return false;
        if (isPartner != null ? !isPartner.equals(member.isPartner) : member.isPartner != null) return false;
        if (recommend != null ? !recommend.equals(member.recommend) : member.recommend != null) return false;
        if (signature != null ? !signature.equals(member.signature) : member.signature != null) return false;
        if (registrationId != null ? !registrationId.equals(member.registrationId) : member.registrationId != null)
            return false;
        if (picNum != null ? !picNum.equals(member.picNum) : member.picNum != null) return false;
        return isOpen != null ? isOpen.equals(member.isOpen) : member.isOpen == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (company != null ? company.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (realname != null ? realname.hashCode() : 0);
        result = 31 * result + (pinyin != null ? pinyin.hashCode() : 0);
        result = 31 * result + (mobile != null ? mobile.hashCode() : 0);
        result = 31 * result + (sex != null ? sex.hashCode() : 0);
        result = 31 * result + (qq != null ? qq.hashCode() : 0);
        result = 31 * result + (wechatNum != null ? wechatNum.hashCode() : 0);
        result = 31 * result + (jobTitle != null ? jobTitle.hashCode() : 0);
        result = 31 * result + (industry != null ? industry.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (wantRes != null ? wantRes.hashCode() : 0);
        result = 31 * result + (userStatus != null ? userStatus.hashCode() : 0);
        result = 31 * result + (logo != null ? logo.hashCode() : 0);
        result = 31 * result + (isAdmin != null ? isAdmin.hashCode() : 0);
        result = 31 * result + (isExpert != null ? isExpert.hashCode() : 0);
        result = 31 * result + (isDistributor != null ? isDistributor.hashCode() : 0);
        result = 31 * result + (isPartner != null ? isPartner.hashCode() : 0);
        result = 31 * result + (recommend != null ? recommend.hashCode() : 0);
        result = 31 * result + (signature != null ? signature.hashCode() : 0);
        result = 31 * result + (registrationId != null ? registrationId.hashCode() : 0);
        result = 31 * result + (picNum != null ? picNum.hashCode() : 0);
        result = 31 * result + (isOpen != null ? isOpen.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Member{" +
                "company='" + company + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", realname='" + realname + '\'' +
                ", pinyin='" + pinyin + '\'' +
                ", mobile='" + mobile + '\'' +
                ", sex=" + sex +
                ", qq='" + qq + '\'' +
                ", wechatNum='" + wechatNum + '\'' +
                ", jobTitle='" + jobTitle + '\'' +
                ", industry='" + industry + '\'' +
                ", city='" + city + '\'' +
                ", wantRes='" + wantRes + '\'' +
                ", userStatus=" + userStatus +
                ", logo='" + logo + '\'' +
                ", isAdmin=" + isAdmin +
                ", isExpert=" + isExpert +
                ", isDistributor=" + isDistributor +
                ", isPartner=" + isPartner +
                ", recommend='" + recommend + '\'' +
                ", signature='" + signature + '\'' +
                ", registrationId='" + registrationId + '\'' +
                ", picNum=" + picNum +
                ", isOpen=" + isOpen +
                '}';
    }
	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getPerfectState() {
		return perfectState;
	}

	public void setPerfectState(String perfectState) {
		this.perfectState = perfectState;
	}

}
