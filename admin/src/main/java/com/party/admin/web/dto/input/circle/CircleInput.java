/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.party.admin.web.dto.input.circle;

import java.io.Serializable;
import java.util.List;

import com.party.core.model.BaseModel;
import com.party.core.model.circle.CircleTag;

/**
 * 圈子管理Entity
 * @author Juliana
 * @version 2016-12-14
 */
public class CircleInput extends BaseModel implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 名称
	private String logo;		// 图片
	private String area;		// 区域id
	private String memo;		// 简介
	private String circleType;		// 圈子类型
	private String mustContent;		// 必填项
	private String extraContent;		// 选填项
	private Integer topicNum;		// 话题量
	private Integer visitNum;		// 访问量
	private Integer memberNum;		// 会员量
	private Integer showFront; 	//是否显示 1 显示 0 不显示
	private Integer isOpen; 	//是否公开 1 显示 0 不显示 2每个会员类型单独设置隐私
	private Integer sort;		//排序号
	private String subjectId; // 专题id
	private String subjectName;
	
	List<CircleTag> circleTags;
		
	public List<CircleTag> getCircleTags() {
		return circleTags;
	}
	public void setCircleTags(List<CircleTag> circleTags) {
		this.circleTags = circleTags;
	}
	public Integer getShowFront() {
		return showFront;
	}
	public void setShowFront(Integer showFront) {
		this.showFront = showFront;
	}
	public Integer getIsOpen() {
		return isOpen;
	}
	public void setIsOpen(Integer isOpen) {
		this.isOpen = isOpen;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getMemo() {
		return memo;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getCircleType() {
		return circleType;
	}
	public void setCircleType(String circleType) {
		this.circleType = circleType;
	}
	public String getMustContent() {
		return mustContent;
	}
	public void setMustContent(String mustContent) {
		this.mustContent = mustContent;
	}
	public String getExtraContent() {
		return extraContent;
	}
	public void setExtraContent(String extraContent) {
		this.extraContent = extraContent;
	}
	public Integer getTopicNum() {
		return topicNum;
	}
	public void setTopicNum(Integer topicNum) {
		this.topicNum = topicNum;
	}
	public Integer getVisitNum() {
		return visitNum;
	}
	public void setVisitNum(Integer visitNum) {
		this.visitNum = visitNum;
	}
	public Integer getMemberNum() {
		return memberNum;
	}
	public void setMemberNum(Integer memberNum) {
		this.memberNum = memberNum;
	}
	public String getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	
}