package com.party.mobile.web.dto.subject.output;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.party.core.model.subject.Subject;

/**
 * 专题输出视图
 * 
 * @author Administrator
 *
 */
public class SubjectOutput {

	// 主键id
	private String id;

	// 名称
	private String name;

	// 创建时间
	private Date createDate;

	// 封面图
	private String picture;

	// 描述
	private String remarks;
	
	//类型
	private Integer tempType;
	
	//是否显示封面图
	private Integer showPic;
	
	//背景图
	private String bgPic;
	
	//行显示图标数
	private Integer colNum;
	
	// 应用
	private List<SubjectApplyOutput> subjectApplies;

	
	public Integer getColNum() {
		return colNum;
	}

	public void setColNum(Integer colNum) {
		this.colNum = colNum;
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

	public Integer getTempType() {
		return tempType;
	}

	public void setTempType(Integer tempType) {
		this.tempType = tempType;
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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public List<SubjectApplyOutput> getSubjectApplies() {
		return subjectApplies;
	}

	public void setSubjectApplies(List<SubjectApplyOutput> subjectApplies) {
		this.subjectApplies = subjectApplies;
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

}
