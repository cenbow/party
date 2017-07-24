package com.party.web.web.dto.output.gatherInfo;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

import com.party.core.model.BaseModel;
import com.party.core.model.gatherInfo.GatherInfoGroup;

/**
 * 信息收集分组
 * @author Administrator
 *
 */
public class GatherInfoGroupOutput extends BaseModel {
	private String groupName; // 组名
	private String groupNo; // 组编号
	private String projectId;
	private int totalNum; // 收集信息数
	
	public static GatherInfoGroupOutput transform(GatherInfoGroup group) {
		GatherInfoGroupOutput output = new GatherInfoGroupOutput();
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

}
