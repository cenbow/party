package com.party.pay.model.pay.wechat.pc;

import com.party.common.annotation.Ignore;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 扫码支付二维码响应参数
 */
public class QrCodeResponse {

    //应用APPID
    private String appid;

    //用户标识
    private String openid;

    //商户号
    @XStreamAlias("mch_id")
    private String mchId;

    //是否关注公众号
    @XStreamAlias("is_subscribe")
    private String isSubscribe;

    //随机字符串
    @Ignore
    private String sign;

    //随机字符串
    @XStreamAlias("nonce_str")
    private String nonceStr;

    //商品id
    @XStreamAlias("product_id")
    private String productId;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getIsSubscribe() {
        return isSubscribe;
    }

    public void setIsSubscribe(String isSubscribe) {
        this.isSubscribe = isSubscribe;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
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
}
