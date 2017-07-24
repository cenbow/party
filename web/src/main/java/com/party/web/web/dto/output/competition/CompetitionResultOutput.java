package com.party.web.web.dto.output.competition;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

import com.party.core.model.BaseModel;
import com.party.core.model.competition.CompetitionResult;
import com.party.core.model.competition.CompetitionSchedule;

/**
 * 人员成绩输出
 * 
 * @author Administrator
 *
 */
public class CompetitionResultOutput extends BaseModel {
	private CompetitionSchedule schedule; // 赛程
	private String memberId; // 用户ID
	private String projectId; // 项目ID
	private String result; // 成绩
	private Integer isComplete; // 是否完赛
	private Integer rankNo; // 排名
	private String pace; // 配速

	public String getPace() {
		return pace;
	}

	public void setPace(String pace) {
		this.pace = pace;
	}

	public CompetitionSchedule getSchedule() {
		return schedule;
	}

	public void setSchedule(CompetitionSchedule schedule) {
		this.schedule = schedule;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Integer getIsComplete() {
		return isComplete;
	}

	public void setIsComplete(Integer isComplete) {
		this.isComplete = isComplete;
	}

	public Integer getRankNo() {
		return rankNo;
	}

	public void setRankNo(Integer rankNo) {
		this.rankNo = rankNo;
	}

	public static CompetitionResultOutput transform(CompetitionResult result) {
		CompetitionResultOutput output = new CompetitionResultOutput();
		try {
			BeanUtils.copyProperties(output, result);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return output;
	}

}
