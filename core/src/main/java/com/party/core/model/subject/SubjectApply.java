package com.party.core.model.subject;

import com.party.core.model.BaseModel;

/**
 * 专题应用
 * 
 * @author Administrator
 *
 */
public class SubjectApply extends BaseModel {

	private String picture;
	private String name;
	private String url;
	private Integer sort; // 排序
	private String subjectId;
	private String subjectName;
	private String member; // 前端发布者

	private String type; // 栏目类型 url:单链接 article:单篇文章 channel:文章列表（频道）
	private String targetId; // 文章id或者频道id

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
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

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

}
