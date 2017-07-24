package com.party.core.model.channel;

import com.party.core.model.BaseModel;

/**
 * 频道实体
 * 
 * @author Administrator
 *
 */
public class Channel extends BaseModel {

	private static final long serialVersionUID = 1L;
	private String name;
	private String picture;
	private Integer sort; // 排序
	
	private Integer isShow; // 是否显示在APP上 1 显示 0不显示
	
	private String member; // 前端发布者
	private String memberId;//会员编号

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getMember() {
		return member;
	}

	public void setMember(String member) {
		this.member = member;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getIsShow() {
		return isShow;
	}

	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

}
