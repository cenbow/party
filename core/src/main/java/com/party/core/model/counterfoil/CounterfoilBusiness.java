package com.party.core.model.counterfoil;

import java.io.Serializable;

import com.party.core.model.BaseModel;

/**
 * 票据业务关联实体
 * 
 * @author Administrator
 *
 */
public class CounterfoilBusiness extends BaseModel implements Serializable {
	private String counterfoilId; // 票据ID
	private String businessId; // 业务ID

	public CounterfoilBusiness(String counterfoilId, String businessId) {
		this.counterfoilId = counterfoilId;
		this.businessId = businessId;
	}

	public CounterfoilBusiness() {
	}

	public String getCounterfoilId() {
		return counterfoilId;
	}

	public void setCounterfoilId(String counterfoilId) {
		this.counterfoilId = counterfoilId;
	}

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

}
