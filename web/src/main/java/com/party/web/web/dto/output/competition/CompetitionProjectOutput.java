package com.party.web.web.dto.output.competition;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import org.apache.commons.beanutils.BeanUtils;

import com.party.core.model.BaseModel;
import com.party.core.model.competition.CompetitionProject;
import com.party.web.web.dto.output.gatherInfo.GatherInfoProjectOutput;

/**
 * 赛事项目
 * 
 * @author Administrator
 *
 */
public class CompetitionProjectOutput extends BaseModel {
	private String title; // 标题
	private String pircture; // 封面
	private Date startTime; // 开始时间

	private int totalNum; // 参数人数
	private int groupNum; // 小组数量
	private int scheduleNum; // 日程数量

	private String qrCodeUrl; // 项目详情二维码

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public String getPircture() {
		return pircture;
	}

	public void setPircture(String pircture) {
		this.pircture = pircture;
	}

	public String getQrCodeUrl() {
		return qrCodeUrl;
	}

	public void setQrCodeUrl(String qrCodeUrl) {
		this.qrCodeUrl = qrCodeUrl;
	}

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

	public int getGroupNum() {
		return groupNum;
	}

	public void setGroupNum(int groupNum) {
		this.groupNum = groupNum;
	}

	public static CompetitionProjectOutput transform(CompetitionProject project) {
		CompetitionProjectOutput output = new CompetitionProjectOutput();
		try {
			BeanUtils.copyProperties(output, project);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return output;
	}

	public int getScheduleNum() {
		return scheduleNum;
	}

	public void setScheduleNum(int scheduleNum) {
		this.scheduleNum = scheduleNum;
	}

}
