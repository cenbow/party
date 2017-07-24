package com.party.mobile.web.dto.activity.output;

/**
 * 是否报名输出实体
 * party
 * Created by wei.li
 * on 2016/9/26 0026.
 */
public class ApplyOutput {

    //活动报名编号
    private String memberActId;

    //活动状态
    private Integer actStatus;
    
    //已报名数
    private Integer joinNum;
    
    // 活动报名订单编号
    private String orderId;
    
    // 活动编号
    private String actId;
    
    // 来源 从签到页面来自动签到
    private String from;

    public String getMemberActId() {
        return memberActId;
    }

    public void setMemberActId(String memberActId) {
        this.memberActId = memberActId;
    }

    public Integer getActStatus() {
        return actStatus;
    }

    public void setActStatus(Integer actStatus) {
        this.actStatus = actStatus;
    }

	public Integer getJoinNum() {
		return joinNum;
	}

	public void setJoinNum(Integer joinNum) {
		this.joinNum = joinNum;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getActId() {
		return actId;
	}

	public void setActId(String actId) {
		this.actId = actId;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}
    
}
