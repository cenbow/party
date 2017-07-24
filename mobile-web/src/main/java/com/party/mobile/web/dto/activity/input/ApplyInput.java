package com.party.mobile.web.dto.activity.input;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 活动报名输入视图
 * party
 * Created by wei.li
 * on 2016/9/26 0026.
 */
public class ApplyInput {

    //活动编号
    @NotBlank(message = "活动编号不能为空")
    private String id;

    //报名者姓名
    @NotBlank(message = "姓名不能为空")
    private String realname;

    //手机号
    @NotBlank(message = "电话号码不能为空")
    private String mobile;

    //验证码
    private String verifyCode;

    //公司信息
    private String company;

    //职位
    private String title;

    //行业
    private String industry;

    //备注信息
    private String extra;
    
    //微信号
    private String wechatNum;
    
    private String from; // 是否从签到页面来

    //分享商品主键
    private String storeGoodsId;
    
    // 票据ID
    private String counterfoilId;
    
    private Float payment;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRealname() {
        return realname;
    }

    public String getWechatNum() {
		return wechatNum;
	}

	public void setWechatNum(String wechatNum) {
		this.wechatNum = wechatNum;
	}

	public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getStoreGoodsId() {
        return storeGoodsId;
    }

    public void setStoreGoodsId(String storeGoodsId) {
        this.storeGoodsId = storeGoodsId;
    }

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getCounterfoilId() {
		return counterfoilId;
	}

	public void setCounterfoilId(String counterfoilId) {
		this.counterfoilId = counterfoilId;
	}

	public Float getPayment() {
		return payment;
	}

	public void setPayment(Float payment) {
		this.payment = payment;
	}
}
