package com.party.core.model.counterfoil;

import java.io.Serializable;

import com.party.core.model.BaseModel;

/**
 * 票据实体
 * 
 * @author Administrator
 *
 */
public class Counterfoil extends BaseModel implements Serializable {

	private static final long serialVersionUID = 506675775357253959L;
	private String name; // 票种名称
	private Float payment; // 票价
	private Integer limitNum; // 票种数量
	private Integer joinNum; // 已购数量
	private Integer maxBuyNum; // 限购数量
	private String businessId; // 业务ID
	private String businessType; // 业务类型
	private Integer sort; // 排序
	private boolean hasBuy; // 是否已有人报名

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Float getPayment() {
		return payment;
	}

	public void setPayment(Float payment) {
		this.payment = payment;
	}

	public Integer getLimitNum() {
		return limitNum;
	}

	public void setLimitNum(Integer limitNum) {
		this.limitNum = limitNum;
	}

	public Integer getMaxBuyNum() {
		return maxBuyNum;
	}

	public void setMaxBuyNum(Integer maxBuyNum) {
		this.maxBuyNum = maxBuyNum;
	}

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public boolean isHasBuy() {
		return hasBuy;
	}

	public void setHasBuy(boolean hasBuy) {
		this.hasBuy = hasBuy;
	}

	public Integer getJoinNum() {
		return joinNum;
	}

	public void setJoinNum(Integer joinNum) {
		this.joinNum = joinNum;
	}
}
