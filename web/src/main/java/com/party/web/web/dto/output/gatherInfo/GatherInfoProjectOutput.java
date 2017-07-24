package com.party.web.web.dto.output.gatherInfo;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

import com.party.core.model.BaseModel;
import com.party.core.model.gatherInfo.GatherInfoProject;
import com.party.core.model.member.Member;

/**
 * 人员信息采集项目
 * 
 * @author Administrator
 *
 */
public class GatherInfoProjectOutput extends BaseModel {

	private static final long serialVersionUID = -5921676579043881162L;
	private String title;
	private String picture;
	private String baseInfoDesc; // 基本信息描述
	private String itineraryInfoDesc; // 行程信息 描述
	private String insuranceInfoDesc; // 保险信息描述
	private Member member;
	private int totalNum; // 收集信息数
	private int groupNum; // 小组数量

	private String baseQrCodeUrl; // 基本信息二维码
	private String itineraryQrCodeUrl; // 行程信息二维码
	private String insuranceQrCodeUrl; // 保险信息二维码

	public String getBaseQrCodeUrl() {
		return baseQrCodeUrl;
	}

	public void setBaseQrCodeUrl(String baseQrCodeUrl) {
		this.baseQrCodeUrl = baseQrCodeUrl;
	}

	public String getItineraryQrCodeUrl() {
		return itineraryQrCodeUrl;
	}

	public void setItineraryQrCodeUrl(String itineraryQrCodeUrl) {
		this.itineraryQrCodeUrl = itineraryQrCodeUrl;
	}

	public String getInsuranceQrCodeUrl() {
		return insuranceQrCodeUrl;
	}

	public void setInsuranceQrCodeUrl(String insuranceQrCodeUrl) {
		this.insuranceQrCodeUrl = insuranceQrCodeUrl;
	}

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

	public String getBaseInfoDesc() {
		return baseInfoDesc;
	}

	public void setBaseInfoDesc(String baseInfoDesc) {
		this.baseInfoDesc = baseInfoDesc;
	}

	public String getItineraryInfoDesc() {
		return itineraryInfoDesc;
	}

	public void setItineraryInfoDesc(String itineraryInfoDesc) {
		this.itineraryInfoDesc = itineraryInfoDesc;
	}

	public String getInsuranceInfoDesc() {
		return insuranceInfoDesc;
	}

	public void setInsuranceInfoDesc(String insuranceInfoDesc) {
		this.insuranceInfoDesc = insuranceInfoDesc;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

	public static GatherInfoProjectOutput transform(GatherInfoProject project) {
		GatherInfoProjectOutput output = new GatherInfoProjectOutput();
		try {
			BeanUtils.copyProperties(output, project);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return output;
	}

	public int getGroupNum() {
		return groupNum;
	}

	public void setGroupNum(int groupNum) {
		this.groupNum = groupNum;
	}

}
