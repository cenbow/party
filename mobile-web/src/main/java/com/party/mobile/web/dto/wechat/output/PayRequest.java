package com.party.mobile.web.dto.wechat.output;

import com.alibaba.fastjson.annotation.JSONField;
import com.party.common.annotation.Ignore;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 微信网页调起支付参数
 * party
 * Created by wei.li
 * on 2016/9/21 0021.
 */
public class PayRequest {

    //应用ID
    private String appId;
    
    //商户号
    private String mchId;

    //预支付交易会话标识
    private String prepayId;
    
    //时间戳
    private String timeStamp;

    //随机字符串
    private String nonceStr;

    //订单详情扩展字符串
    @XStreamAlias("package")
    @JSONField(name = "package")
    private String packages;

    //签名方式
    private String signType;

    //签名
    @Ignore
    private String paySign;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getPackages() {
        return packages;
    }

    public void setPackages(String packages) {
        this.packages = packages;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getPaySign() {
        return paySign;
    }

    public void setPaySign(String paySign) {
        this.paySign = paySign;
    }

	public String getMchId() {
		return mchId;
	}

	public void setMchId(String mchId) {
		this.mchId = mchId;
	}

	public String getPrepayId() {
		return prepayId;
	}

	public void setPrepayId(String prepayId) {
		this.prepayId = prepayId;
	}
    
}
