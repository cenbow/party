package com.party.admin.web.dto.output.gatherForm;

import com.party.core.model.BaseModel;
import com.party.core.model.member.Member;

/**
 * 表单项目
 * 
 * @author Administrator
 *
 */
public class GatherProjectOutput extends BaseModel {
	
	private static final long serialVersionUID = -7840561412805016821L;
	private String title;
	private String picture;
	private String content;
	private Member member;
	
	private Integer totalNum; // 收集数量
	private Integer fieldNum; // 字段数量
	private String qrCodeUrl;

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public Integer getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}

	public String getQrCodeUrl() {
		return qrCodeUrl;
	}

	public void setQrCodeUrl(String qrCodeUrl) {
		this.qrCodeUrl = qrCodeUrl;
	}

	public Integer getFieldNum() {
		return fieldNum;
	}

	public void setFieldNum(Integer fieldNum) {
		this.fieldNum = fieldNum;
	}

}
