package com.party.mobile.web.dto.appWechatPay.output;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.party.common.annotation.Ignore;

/**
 * app微信统一下单请求参数
 * @author Wesley
 * @version 2016/11/15 0015
 */
public class AppUnifiedorderRequest {

    //微信开放平台审核通过的应用APPID
    private String appid;

    //微信支付分配的商户号
    @XStreamAlias("mch_id")
    private String mchId;

    //随机字符串，不长于32位
    @XStreamAlias("nonce_str")
    private String nonceStr;

    //签名
    @Ignore
    private String sign;

    //商品描叙
    private String body;

    //商户订单号
    @XStreamAlias("out_trade_no")
    private String outTradeNo;

    //订单总金额，单位为分
    @XStreamAlias("total_fee")
    private Integer totalFee;


    //用户端实际ip
    @XStreamAlias("spbill_create_ip")
    private String spbillCreateIp;

    //异步通知回调地址
    @XStreamAlias("notify_url")
    private String notifyUrl;

    //支付类型
    @XStreamAlias("trade_type")
    private String tradeType;


    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }


    public Integer getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Integer totalFee) {
        this.totalFee = totalFee;
    }

    public String getSpbillCreateIp() {
        return spbillCreateIp;
    }

    public void setSpbillCreateIp(String spbillCreateIp) {
        this.spbillCreateIp = spbillCreateIp;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    @Override
    public String toString() {
        return "AppUnifiedorderRequest{" +
                "appid='" + appid + '\'' +
                ", mchId='" + mchId + '\'' +
                ", nonceStr='" + nonceStr + '\'' +
                ", sign='" + sign + '\'' +
                ", body='" + body + '\'' +
                ", outTradeNo='" + outTradeNo + '\'' +
                ", totalFee=" + totalFee +
                ", spbillCreateIp='" + spbillCreateIp + '\'' +
                ", notifyUrl='" + notifyUrl + '\'' +
                ", tradeType='" + tradeType + '\'' +
                '}';
    }
}
