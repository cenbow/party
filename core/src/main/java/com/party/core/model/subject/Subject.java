package com.party.core.model.subject;

import java.util.List;

import com.party.core.model.BaseModel;

/**
 * 专题实体
 * 
 * @author Administrator
 *
 */
public class Subject extends BaseModel {

	private static final long serialVersionUID = 1L;
	private String name;
	private String picture;
	private Integer sort; // 排序
	private String member; // 前端发布者
    private String qrCodeUrl;
    private String memberId;//会员编号
    private Integer tempType;//模板类型
    private String bgPic;//背景图
    private Integer showPic;//是否显示封面图
    private Integer colNum;//一列显示多少个
	
	private List<SubjectApply> subjectApplies;

	
	public Integer getColNum() {
		return colNum;
	}

	public void setColNum(Integer colNum) {
		this.colNum = colNum;
	}

	public Integer getTempType() {
		return tempType;
	}

	public Integer getShowPic() {
		return showPic;
	}

	public void setShowPic(Integer showPic) {
		this.showPic = showPic;
	}

	public String getBgPic() {
		return bgPic;
	}

	public void setBgPic(String bgPic) {
		this.bgPic = bgPic;
	}

	public void setTempType(Integer tempType) {
		this.tempType = tempType;
	}

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

	public List<SubjectApply> getSubjectApplies() {
		return subjectApplies;
	}

	public void setSubjectApplies(List<SubjectApply> subjectApplies) {
		this.subjectApplies = subjectApplies;
	}

	public String getQrCodeUrl() {
		return qrCodeUrl;
	}

	public void setQrCodeUrl(String qrCodeUrl) {
		this.qrCodeUrl = qrCodeUrl;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

}
