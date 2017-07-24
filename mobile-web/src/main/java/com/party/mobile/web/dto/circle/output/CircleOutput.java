package com.party.mobile.web.dto.circle.output;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;

import com.party.core.model.circle.Circle;

/**
 * 圈子输出视图 party Created by Juliana on 2016-12-14
 */
public class CircleOutput {

	// 活动编号
	private String id;

	// 名称
	private String name;

	// 封面图
	private String logo;

	// 圈子类型
	private String circleType;

	// 更新时间
	private Date updateDate;

	// 备注
	private String memo;
	
	//会员数
	private Integer memberNum;


	//访问数
	private Integer visitNum;

	//话题数
	private Integer topicNum;

	//成员列表
	private List<Map<String,Object>> memberList;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public Integer getVisitNum() {
		return visitNum;
	}

	public void setVisitNum(Integer visitNum) {
		this.visitNum = visitNum;
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

	public List<Map<String, Object>> getMemberList() {
		return memberList;
	}

	public void setMemberList(List<Map<String, Object>> memberList) {
		this.memberList = memberList;
	}

	public Integer getTopicNum() {
		return topicNum;
	}

	public void setTopicNum(Integer topicNum) {
		this.topicNum = topicNum;
	}

	/**
	 * 转换列表查询输出视图
	 * 
	 * @param circle
	 *            活动实体
	 * @return 活动列表输出视图
	 */
	public static CircleOutput transform(Circle circle) {
		CircleOutput listOutput = new CircleOutput();
		BeanUtils.copyProperties(circle, listOutput);
		return listOutput;
	}
}
