package com.party.core.model.activity;

import com.party.core.model.BaseModel;

/**
 * 众筹项目资源实体
 * 
 * @author Administrator
 *
 */
public class CrowfundResources extends BaseModel {

	private static final long serialVersionUID = 6362421553022845974L;

	// URL
	private String resourceUrl;

	// 关联编号
	private String refId;

	// 类型（1：图片；2：视频；）
	private String type;

	// 排序
	private Integer sort;

	// 前端发布者
	private String member;

	public String getResourceUrl() {
		return resourceUrl;
	}

	public void setResourceUrl(String resourceUrl) {
		this.resourceUrl = resourceUrl;
	}

	public String getRefId() {
		return refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getMember() {
		return member;
	}

	public void setMember(String member) {
		this.member = member;
	}

}
