package com.party.admin.web.dto.output.dynamic;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.party.core.model.dynamic.Dynamic;
import com.party.core.model.dynamic.Dypics;
import com.party.core.model.member.Member;

/**
 * 动态输出实体
 * 
 * @author Administrator
 *
 */
public class DynamicOutput {

	// 主键
	private String id;

	// 创建人
	private String createBy;

	// 创建时间
	private Date createDate;

	// 更新人
	private String updateBy;

	// 更新时间
	private Date updateDate;

	// 备注
	private String remarks;

	// 删除标记
	private String delFlag;

	// 地区
	private String areaId;

	// 城市
	private String cityId;

	// 动态图
	private List<Dypics> picList;

	// 内容
	private String content;

	// 动态总点赞数
	private Integer loveNum;

	// 动态总评论数
	private Integer commentNum;
	
	private Member authorMember;

	// 是否热门推荐（0：非热门；1：热门）
	private Integer isHot;

	// 点击查看详情数
	private Integer clickNum;

	// 动态类型（1：社区动态； 2：圈子动态；）
	private String dynamicType;

	// 发送方式（0:自发；1：代发）
	private Integer dynamicWay;

	// 排序
	private Integer sort;

	// 发布者名称
	private String memberName;

	// 二维码
	private String qrCodeUrl;

	public Integer getDynamicWay() {
		return dynamicWay;
	}

	public void setDynamicWay(Integer dynamicWay) {
		this.dynamicWay = dynamicWay;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getIsHot() {
		return isHot;
	}

	public void setIsHot(Integer isHot) {
		this.isHot = isHot;
	}


	public Integer getClickNum() {
		return clickNum;
	}

	public void setClickNum(Integer clickNum) {
		this.clickNum = clickNum;
	}

	public String getDynamicType() {
		return dynamicType;
	}

	public void setDynamicType(String dynamicType) {
		this.dynamicType = dynamicType;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public List<Dypics> getPicList() {
		return picList;
	}

	public void setPicList(List<Dypics> picList) {
		this.picList = picList;
	}

	public Integer getLoveNum() {
		return loveNum;
	}

	public void setLoveNum(Integer loveNum) {
		this.loveNum = loveNum;
	}

	public Integer getCommentNum() {
		return commentNum;
	}

	public void setCommentNum(Integer commentNum) {
		this.commentNum = commentNum;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}


	public String getQrCodeUrl() {
		return qrCodeUrl;
	}

	public void setQrCodeUrl(String qrCodeUrl) {
		this.qrCodeUrl = qrCodeUrl;
	}

	public Member getAuthorMember() {
		return authorMember;
	}

	public void setAuthorMember(Member authorMember) {
		this.authorMember = authorMember;
	}


	public static DynamicOutput transform(Dynamic input) {
		DynamicOutput output = new DynamicOutput();
		try {
			BeanUtils.copyProperties(output, input);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return output;
	}

}
