package com.party.mobile.web.dto.wechat.output;

/**
 * 微信 js 接口输出视图
 * party
 * Created by wei.li
 * on 2016/10/13 0013.
 */
public class WechatJsConfg {

    //公众号的唯一标识
    private String appId;

    //生成签名的时间戳
    private String timestamp;

    //生成签名的随机串
    private String nonceStr;

    //签名
    private String signature;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
