package com.party.web.web.dto.output.subject;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.party.core.model.subject.Subject;
import com.party.core.model.subject.SubjectApply;

/**
 * 专题实体
 * 
 * @author Administrator
 *
 */
public class SubjectOutput {

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

	private String name;
	private String picture;
	private Integer sort; // 排序
	private String member; // 前端发布者
	private String memberId;// 会员编号
	private Integer tempType;// 模板类型
	private String bgPic;// 背景图
	private Integer showPic;// 是否显示封面图
	private Integer colNum;// 一列显示多少个

	private List<SubjectApply> subjectApplies;

	private Map<String, String> qrCodeMap; // 二维码

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

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
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

	public static SubjectOutput transform(Subject subject) {
		SubjectOutput output = new SubjectOutput();
		try {
			BeanUtils.copyProperties(output, subject);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return output;
	}

	public Map<String, String> getQrCodeMap() {
		return qrCodeMap;
	}

	public void setQrCodeMap(Map<String, String> qrCodeMap) {
		this.qrCodeMap = qrCodeMap;
	}

}
