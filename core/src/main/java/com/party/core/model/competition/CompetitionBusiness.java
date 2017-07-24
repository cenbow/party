package com.party.core.model.competition;

import java.io.Serializable;

import com.party.core.model.BaseModel;

/**
 * 赛事项目与业务表关系
 * 
 * @author Administrator
 *
 */
public class CompetitionBusiness extends BaseModel implements Serializable {
	private String businessId; // 业务ID
	private String competitionId; // 赛事项目ID
	private String type; // 1 已成功 2全部

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public String getCompetitionId() {
		return competitionId;
	}

	public void setCompetitionId(String competitionId) {
		this.competitionId = competitionId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
