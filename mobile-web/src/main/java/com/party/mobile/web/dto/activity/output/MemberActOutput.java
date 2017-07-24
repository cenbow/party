package com.party.mobile.web.dto.activity.output;

import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.party.core.model.activity.Activity;
import com.party.core.model.member.Member;
import com.party.core.model.member.MemberAct;

public class MemberActOutput {
	
	//logo
	private String logo;
	// 活动编号
	private Activity activity;

	// 收藏
	private Integer collect;

	// 参与
	private Integer joinin;

	// 附加项
	private String extra;

	// 姓名
	private String name;

	// 手机
	private String mobile;

	// 公司编号
	private String company;

	// 职位
	private String jobTitle;

	// 活动报名状态(0,"审核中",1,"审核通过，待支付",2,"审核拒绝",3,"已支付，报名成功",4,"已取消",5,"未参与")
	private Integer checkStatus;

	// 订单id,用户提交报名后系统自动生成
	private String orderId;

	// 商铺商品编号
	private String storeGoodsId;

	// 活动费用
	private Float payment;

	// 主键
	private String id;

	// 创建人
	private String createBy;

	// 创建时间
	private Date createDate;

	// 更新人
	private String updateBy;

	// 更新时间
	private Date updateDate;

	// 备注
	private String remarks;

	// 删除标记
	private String delFlag;
	
	// 微信号
	private String wechatNum;

	
	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getWechatNum() {
		return wechatNum;
	}

	public void setWechatNum(String wechatNum) {
		this.wechatNum = wechatNum;
	}


	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
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

	public Float getPayment() {
		return payment;
	}

	public void setPayment(Float payment) {
		this.payment = payment;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	
	public static MemberActOutput transform(MemberAct memberAct){
		MemberActOutput output = new MemberActOutput();
        BeanUtils.copyProperties(memberAct, output);
        return output;
    }

}
