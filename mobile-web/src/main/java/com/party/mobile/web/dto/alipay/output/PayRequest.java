package com.party.mobile.web.dto.alipay.output;

import com.alibaba.fastjson.annotation.JSONField;
import com.party.common.annotation.Ignore;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 支付宝调起支付请求参数
 * party
 * Created by wei.li
 * on 2016/9/21 0021.
 */
public class PayRequest {

    //支付宝分配给开发者的应用ID
    @JSONField(name = "app_id")
    @XStreamAlias(value = "app_id")
    private String appId;

    //接口名称
    private String method;

    //编码
    private String charset;

    //签名类型
    @JSONField(name = "sign_type")
    @XStreamAlias(value = "sign_type")
    private String signType;

    //签名
    @Ignore
    private String sign;

    //时间戳
    private String timestamp;

    //接口版本
    private String version;

    //回调地址
    private String notifyUrl;

    //业务请求参数的集合
    @JSONField(name = "biz_content")
    @XStreamAlias(value = "biz_content")
    private String bizContent;


    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getBizContent() {
        return bizContent;
    }

    public void setBizContent(String bizContent) {
        this.bizContent = bizContent;
    }
}
