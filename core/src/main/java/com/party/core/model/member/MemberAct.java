package com.party.core.model.member;

import com.party.core.model.BaseModel;

/**
 * 活动报名实体
 * party
 * Created by wei.li
 * on 2016/9/18 0018.
 */
public class MemberAct extends BaseModel{


    private static final long serialVersionUID = -1994132459301599977L;

    //会员编号
    private String memberId;

    //活动编号
    private String actId;

    //收藏
    private Integer collect;

    //参与
    private Integer joinin;

    //附加项
    private String extra;

    //姓名
    private String name;

    //手机
    private String mobile;

    //公司编号
    private String company;

    //职位
    private String jobTitle;

    //活动报名状态(0,"审核中",1,"审核通过，待支付",2,"审核拒绝",3,"已支付，报名成功",4,"已取消",5,"未参与")
    private Integer checkStatus;

    //订单id,用户提交报名后系统自动生成
    private String orderId;

    //商铺商品编号
    private String storeGoodsId;
    
    //活动费用
    private Float payment;

    //微信号
    private String wechatNum;
    
    // 签到
    private Integer signin;
    
    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getActId() {
        return actId;
    }

    public String getWechatNum() {
		return wechatNum;
	}

	public void setWechatNum(String wechatNum) {
		this.wechatNum = wechatNum;
	}

	public void setActId(String actId) {
        this.actId = actId;
    }

    public Integer getCollect() {
        return collect;
    }

    public void setCollect(Integer collect) {
        this.collect = collect;
    }

    public Integer getJoinin() {
        return joinin;
    }

    public void setJoinin(Integer joinin) {
        this.joinin = joinin;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public Integer getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(Integer checkStatus) {
        this.checkStatus = checkStatus;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getStoreGoodsId() {
        return storeGoodsId;
    }

    public void setStoreGoodsId(String storeGoodsId) {
        this.storeGoodsId = storeGoodsId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        MemberAct memberAct = (MemberAct) o;

        if (memberId != null ? !memberId.equals(memberAct.memberId) : memberAct.memberId != null) return false;
        if (actId != null ? !actId.equals(memberAct.actId) : memberAct.actId != null) return false;
        if (collect != null ? !collect.equals(memberAct.collect) : memberAct.collect != null) return false;
        if (joinin != null ? !joinin.equals(memberAct.joinin) : memberAct.joinin != null) return false;
        if (extra != null ? !extra.equals(memberAct.extra) : memberAct.extra != null) return false;
        if (name != null ? !name.equals(memberAct.name) : memberAct.name != null) return false;
        if (mobile != null ? !mobile.equals(memberAct.mobile) : memberAct.mobile != null) return false;
        if (company != null ? !company.equals(memberAct.company) : memberAct.company != null) return false;
        if (jobTitle != null ? !jobTitle.equals(memberAct.jobTitle) : memberAct.jobTitle != null) return false;
        if (checkStatus != null ? !checkStatus.equals(memberAct.checkStatus) : memberAct.checkStatus != null)
            return false;
        if (orderId != null ? !orderId.equals(memberAct.orderId) : memberAct.orderId != null) return false;
        return storeGoodsId != null ? storeGoodsId.equals(memberAct.storeGoodsId) : memberAct.storeGoodsId == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (memberId != null ? memberId.hashCode() : 0);
        result = 31 * result + (actId != null ? actId.hashCode() : 0);
        result = 31 * result + (collect != null ? collect.hashCode() : 0);
        result = 31 * result + (joinin != null ? joinin.hashCode() : 0);
        result = 31 * result + (extra != null ? extra.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (mobile != null ? mobile.hashCode() : 0);
        result = 31 * result + (company != null ? company.hashCode() : 0);
        result = 31 * result + (jobTitle != null ? jobTitle.hashCode() : 0);
        result = 31 * result + (checkStatus != null ? checkStatus.hashCode() : 0);
        result = 31 * result + (orderId != null ? orderId.hashCode() : 0);
        result = 31 * result + (storeGoodsId != null ? storeGoodsId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MemberAct{" +
                "memberId='" + memberId + '\'' +
                ", actId='" + actId + '\'' +
                ", collect=" + collect +
                ", joinin=" + joinin +
                ", extra='" + extra + '\'' +
                ", name='" + name + '\'' +
                ", mobile='" + mobile + '\'' +
                ", company='" + company + '\'' +
                ", jobTitle='" + jobTitle + '\'' +
                ", checkStatus=" + checkStatus +
                ", orderId='" + orderId + '\'' +
                ", storeGoodsId='" + storeGoodsId + '\'' +
                '}';
    }

	public Float getPayment() {
		return payment;
	}

	public void setPayment(Float payment) {
		this.payment = payment;
	}

	public Integer getSignin() {
		return signin;
	}

	public void setSignin(Integer signin) {
		this.signin = signin;
	}
}
