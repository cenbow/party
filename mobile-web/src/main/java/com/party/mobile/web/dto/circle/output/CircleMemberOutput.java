package com.party.mobile.web.dto.circle.output;

import com.party.core.model.member.Member;
import com.party.mobile.web.dto.dynamic.output.DyMemberOutput;

import org.springframework.beans.BeanUtils;

/**
 * 圈子会员输出视图
 *
 * @Author: Juliana
 * @Description:
 * @Date: 2016-12-14
 * @Modified by:
 */
public class CircleMemberOutput {
    //会员主键
    private String id;

    //姓名
    private String realname;

    //头像
    private String logo;

    //公司
    private String company;
    
    //职务
    private String jobTitle;
    
    //拼音
    private String pinyin;   

    //是否圈子管理员
    private Integer isAdmin;

    //是否为圈子创建者
	private Integer isCreator;
    
    //圈子类型id
    private String tags;

    //圈子会员关系id
	private String cmId;

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCmId() {
		return cmId;
	}

	public void setCmId(String cmId) {
		this.cmId = cmId;
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

	public Integer getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Integer isAdmin) {
		this.isAdmin = isAdmin;
	}

	public Integer getIsCreator() {
		return isCreator;
	}

	public void setIsCreator(Integer isCreator) {
		this.isCreator = isCreator;
	}

	/**
     * 会员实体转输出视图
     * @param member 会员实体
     * @return 输出视图
     */
    public static CircleMemberOutput transform(Member member){
    	CircleMemberOutput circleMemberOutput = new CircleMemberOutput();
        BeanUtils.copyProperties(member, circleMemberOutput);
        return circleMemberOutput;
    }
}
