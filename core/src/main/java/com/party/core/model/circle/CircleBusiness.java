package com.party.core.model.circle;

import java.io.Serializable;

import com.party.core.model.BaseModel;

/**
 * 圈子和业务的关联表
 * 
 * @author Administrator
 *
 */
public class CircleBusiness extends BaseModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8875301494122131167L;
	private String businessId; // 业务ID
	private String circleId; // 圈子ID
	private String type; // 1 已成功 2全部 

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public String getCircleId() {
		return circleId;
	}

	public void setCircleId(String circleId) {
		this.circleId = circleId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
