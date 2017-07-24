package com.party.pay.model.pay.wechat.pc;

import com.party.common.annotation.Ignore;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 扫码支付二维码请求参数
 */
public class QrCodeRequest {
    //公众账号ID
    @XStreamAlias("appid")
    private String appId;

    //商户号
    @XStreamAlias("mch_id")
    private String mchId;

    //时间戳
    @XStreamAlias("time_stamp")
    private String timeStamp;

    //随机字符串
    @XStreamAlias("nonce_str")
    private String nonceStr;

    //商品ID
    @XStreamAlias("product_id")
    private String productId;

    //签名
    @Ignore
    private String sign;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
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

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
