package com.party.mobile.web.dto.circle.output;

import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.party.core.model.circle.Circle;

/**
 * 圈子列表输出视图 party Created by Juliana on 2016-12-14
 */
public class CircleListOutput {

	// 活动编号
	private String id;

	// 名称
	private String name;

	// 封面图
	private String logo;

	// 圈子类型
	private String circleType;

	// 圈子类型id
	private String circleTypeId;

	// 更新事件
	private Date updateDate;

	// 区,字符串，由后台录入
	private String areaId;

	// 区,字符串，由后台录入
	private String areaName;

	// 备注
	private String memo;
	
	//会员数
	private Integer memberNum;

	//访问数
	private Integer visitNum;

	//话题数
	private Integer topicNum;

	// 分享链接
	private String shareLink;
	
	// 待审核数
	private Integer verifyNum;

	// 创建者
	private String createBy;
	private String subjectId;
	private String subjectName;

	public Integer getTopicNum() {
		return topicNum;
	}

	public void setTopicNum(Integer topicNum) {
		this.topicNum = topicNum;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Integer getVisitNum() {
		return visitNum;
	}

	public void setVisitNum(Integer visitNum) {
		this.visitNum = visitNum;
	}

	public String getCircleTypeId() {
		return circleTypeId;
	}

	public void setCircleTypeId(String circleTypeId) {
		this.circleTypeId = circleTypeId;
	}

	public Integer getVerifyNum() {
		return verifyNum;
	}

	public void setVerifyNum(Integer verifyNum) {
		this.verifyNum = verifyNum;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Integer getMemberNum() {
		return memberNum;
	}

	public void setMemberNum(Integer memberNum) {
		this.memberNum = memberNum;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getCircleType() {
		return circleType;
	}

	public void setCircleType(String circleType) {
		this.circleType = circleType;
	}


	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}


	public String getShareLink() {
		return shareLink;
	}

	public void setShareLink(String shareLink) {
		this.shareLink = shareLink;
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

	/**
	 * 转换列表查询输出视图
	 * 
	 * @param circle
	 *            活动实体
	 * @return 活动列表输出视图
	 */
	public static CircleListOutput transform(Circle circle) {
		CircleListOutput listOutput = new CircleListOutput();
		BeanUtils.copyProperties(circle, listOutput);
		return listOutput;
	}
}
