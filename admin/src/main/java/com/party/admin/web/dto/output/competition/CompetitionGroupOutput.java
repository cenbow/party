package com.party.admin.web.dto.output.competition;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

import com.party.core.model.BaseModel;
import com.party.core.model.competition.CompetitionGroup;

/**
 * 信息收集分组
 * @author Administrator
 *
 */
public class CompetitionGroupOutput extends BaseModel {
	private String groupName; // 组名
	private String groupNo; // 组编号
	private String projectId;
	private int totalNum; // 收集信息数
	private String baseQrCodeUrl; // 基本信息二维码
	
	public static CompetitionGroupOutput transform(CompetitionGroup group) {
		CompetitionGroupOutput output = new CompetitionGroupOutput();
		try {
			BeanUtils.copyProperties(output, group);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return output;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupNo() {
		return groupNo;
	}

	public void setGroupNo(String groupNo) {
		this.groupNo = groupNo;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

	public String getBaseQrCodeUrl() {
		return baseQrCodeUrl;
	}

	public void setBaseQrCodeUrl(String baseQrCodeUrl) {
		this.baseQrCodeUrl = baseQrCodeUrl;
	}

}
