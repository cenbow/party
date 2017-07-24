package com.party.mobile.web.dto.dynamic.output;

import com.party.core.model.member.Member;
import org.springframework.beans.BeanUtils;

/**
 * 动态会员输出视图
 *
 * @Author: Wesley
 * @Description:
 * @Date: Created in 12:05 16/11/8
 * @Modified by:
 */
public class DyMemberOutput {
    //会员主键
    private String id;

    //姓名
    private String realname;

    //头像
    private String logo;

    //用户状态,是否认证通过(0：否，1：是)
    private Integer userStatus;

    //是否达人(0：不是 1：是)
    private Integer isExpert;

    // 是否公开资料(0:不公开，1：公开)
    private Integer isOpen;

    //粉丝个数
    private int fansNum;

    //关注个数
    private int focusNum;
    
    //动态图片数量
    private Integer picNum;

    //当前登录用户与会员之间的相互关注关系(-1：该会员于当前会员是同一个人，不能互相关注；0：当前登录用户没有关注该会员；1：当前登录用户已经关注该会员；2：当前用户与该会员互相关注)
    private Integer focusStatus;

    //描述
    private String recommend;
    
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

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Integer getIsExpert() {
        return isExpert;
    }

    public void setIsExpert(Integer isExpert) {
        this.isExpert = isExpert;
    }

    public Integer getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Integer isOpen) {
        this.isOpen = isOpen;
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

    public Integer getFocusStatus() {
        return focusStatus;
    }

    public void setFocusStatus(Integer focusStatus) {
        this.focusStatus = focusStatus;
    }

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    public String getRecommend() {
		return recommend;
	}

	public void setRecommend(String recommend) {
		this.recommend = recommend;
	}

	/**
     * 会员实体转输出视图
     * @param member 会员实体
     * @return 输出视图
     */
    public static DyMemberOutput transform(Member member){
        DyMemberOutput dyMemberOutput = new DyMemberOutput();
        BeanUtils.copyProperties(member, dyMemberOutput);
        return dyMemberOutput;
    }


	public Integer getPicNum() {
		return picNum;
	}

	public void setPicNum(Integer picNum) {
		this.picNum = picNum;
	}
}
