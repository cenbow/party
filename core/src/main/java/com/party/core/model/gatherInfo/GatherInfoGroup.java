package com.party.core.model.gatherInfo;

import com.party.core.model.BaseModel;

/**
 * 信息收集分组
 * 
 * @author Administrator
 *
 */
public class GatherInfoGroup extends BaseModel {
	private String groupName; // 组名
	private String groupNo; // 组编号
	private String projectId;

	public GatherInfoGroup() {
		super();
	}

	public GatherInfoGroup(String projectId) {
		super();
		this.projectId = projectId;
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

}
