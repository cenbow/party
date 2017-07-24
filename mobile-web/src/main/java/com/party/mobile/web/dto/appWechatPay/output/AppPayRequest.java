package com.party.mobile.web.dto.appWechatPay.output;

import com.alibaba.fastjson.annotation.JSONField;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.party.common.annotation.Ignore;

/**
 * app微信调起支付参数列表
 * @author Wesley
 * @version 2016/11/15 0015
 */
public class AppPayRequest {

    //应用ID
    private String appid;

    //商户号
    private String partnerid;

    //预支付交易会话ID
    private String prepayid;

    //扩展字段
    @XStreamAlias("package")
    @JSONField(name = "package")
    private String packages;

    //随机字符串
    private String noncestr;

    //时间戳
    private String timestamp;

    //签名
    @Ignore
    private String sign;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getPackages() {
        return packages;
    }

    public void setPackages(String packages) {
        this.packages = packages;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        return "AppPayRequest{" +
                "appid='" + appid + '\'' +
                ", partnerid='" + partnerid + '\'' +
                ", prepayid='" + prepayid + '\'' +
                ", packages='" + packages + '\'' +
                ", noncestr='" + noncestr + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", sign='" + sign + '\'' +
                '}';
    }
}
