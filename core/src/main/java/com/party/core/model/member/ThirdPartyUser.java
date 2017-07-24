package com.party.core.model.member;

import com.party.core.model.BaseModel;

import java.io.Serializable;

/**
 * 第三方用户表
 * party
 * Created by wei.li
 * on 2016/8/10 0010.
 */
public class ThirdPartyUser extends BaseModel implements Serializable {

    private static final long serialVersionUID = 7136031529957451942L;

    //会员ID
    private String memberId;

    //会员信息类型(0:app微信授权，1：qq授权，2：微博授权，3:微信公众号授权,4:小程序授权)
    private Integer type;

    //第三方用户标识
    private String thirdPartyId;

    //第三方用户唯一标识
    private String unionId;

    //微信公众号编号
    private String appId;
    
    
    public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}

	public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getThirdPartyId() {
        return thirdPartyId;
    }

    public void setThirdPartyId(String thirdPartyId) {
        this.thirdPartyId = thirdPartyId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ThirdPartyUser that = (ThirdPartyUser) o;

        if (memberId != null ? !memberId.equals(that.memberId) : that.memberId != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (thirdPartyId != null ? !thirdPartyId.equals(that.thirdPartyId) : that.thirdPartyId != null) return false;
        if (unionId != null ? !unionId.equals(that.unionId) : that.unionId != null) return false;
        return appId != null ? appId.equals(that.appId) : that.appId == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (memberId != null ? memberId.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (thirdPartyId != null ? thirdPartyId.hashCode() : 0);
        result = 31 * result + (unionId != null ? unionId.hashCode() : 0);
        result = 31 * result + (appId != null ? appId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ThirdPartyUser{" +
                "memberId='" + memberId + '\'' +
                ", type=" + type +
                ", thirdPartyId='" + thirdPartyId + '\'' +
                ", unionId='" + unionId + '\'' +
                ", appId='" + appId + '\'' +
                '}';
    }
}
