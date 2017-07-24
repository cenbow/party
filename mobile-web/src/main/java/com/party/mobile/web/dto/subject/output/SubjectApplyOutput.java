package com.party.mobile.web.dto.subject.output;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import org.apache.commons.beanutils.BeanUtils;

import com.party.core.model.subject.Subject;
import com.party.core.model.subject.SubjectApply;

/**
 * 专题应用输出视图
 * 
 * @author Administrator
 *
 */
public class SubjectApplyOutput {
	// 主键id
	private String id;

	// 名称
	private String name;

	// 创建时间
	private Date createDate;

	// 图标
	private String picture;
	
	// 链接
	private String url;

	// 描述
	private String remarks;

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
	
	public static SubjectApplyOutput transform(SubjectApply subject) {
		SubjectApplyOutput output = new SubjectApplyOutput();
		try {
			BeanUtils.copyProperties(output, subject);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return output;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
