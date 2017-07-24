package com.party.admin.web.dto.output.competition;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import org.apache.commons.beanutils.BeanUtils;

import com.party.common.annotation.ExcelField;
import com.party.core.model.BaseModel;
import com.party.core.model.competition.CompetitionMember;
import com.party.core.model.member.Member;

/**
 * 参赛人员输出
 * 
 * @author Administrator
 *
 */
public class CompetitionMemberOutput extends BaseModel {
	private Member member;
	private String groupId;
	private String groupName;
	private String projectId; // 项目ID
	private String number; // 号码牌

	private String fullName;
	private String company;
	private String jobTitle;
	private String mobile;
	private String sexName;

	private Date createTime;

	@ExcelField(title = "姓名", align = 2, sort = 1)
	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	@ExcelField(title = "公司", align = 2, sort = 4)
	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	@ExcelField(title = "职务", align = 2, sort = 5)
	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	@ExcelField(title = "手机", align = 2, sort = 6)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@ExcelField(title = "创建时间", align = 2, sort = 11)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	@ExcelField(title = "小组名", align = 2, sort = 1)
	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	@ExcelField(title = "号码牌", align = 2, sort = 10)
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public static CompetitionMemberOutput transform(CompetitionMember member) {
		CompetitionMemberOutput output = new CompetitionMemberOutput();
		try {
			BeanUtils.copyProperties(output, member);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return output;
	}

	@ExcelField(title = "性别", align = 2, sort = 3)
	public String getSexName() {
		return sexName;
	}

	public void setSexName(String sexName) {
		this.sexName = sexName;
	}
}
