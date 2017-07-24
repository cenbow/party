package com.party.core.model.circle;

import com.party.core.model.BaseModel;

import java.io.Serializable;
import java.util.Date;

/**
 * 圈子邀请表
 * 
 * @author Juliana
 *
 */
public class CircleInvite extends BaseModel implements Serializable {
	private static final long serialVersionUID = -8875301494122131167L;

	private Integer isVerify;//是否需要审核 1 是，0 不是 默认为1
	private String circle;//被邀请进入的圈子
	private Date endTime;//免验证邀请截止时间

	public Integer getIsVerify() {
		return isVerify;
	}

	public void setIsVerify(Integer isVerify) {
		this.isVerify = isVerify;
	}

	public String getCircle() {
		return circle;
	}

	public void setCircle(String circle) {
		this.circle = circle;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

}
