package com.party.core.model.member;

import com.party.core.model.BaseModel;

/**
 * 用户附属信息
 * 
 * @author Administrator
 *
 */
public class MemberInfo extends BaseModel {
	private String memberId;
	private String size; // 衣服尺码
	private String idCard; // 身份证
	private String bloodGroup; // 血型
	private String medicalHistory; // 病史
	private String contactName; // 紧急联系人名称
	private String contactRelation; // 紧急联系人关系
	private String contactMobile; // 紧急联系人手机
	
	private String perfectState; // 完善状态

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getBloodGroup() {
		return bloodGroup;
	}

	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}

	public String getMedicalHistory() {
		return medicalHistory;
	}

	public void setMedicalHistory(String medicalHistory) {
		this.medicalHistory = medicalHistory;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactRelation() {
		return contactRelation;
	}

	public void setContactRelation(String contactRelation) {
		this.contactRelation = contactRelation;
	}

	public String getContactMobile() {
		return contactMobile;
	}

	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getPerfectState() {
		return perfectState;
	}

	public void setPerfectState(String perfectState) {
		this.perfectState = perfectState;
	}

}
