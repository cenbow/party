package com.party.pay.model.pay.wechat.pc;

import com.party.common.annotation.Ignore;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Native扫码支付请求参数
 */
public class NativePayRequest {

    //返回状态码
    @XStreamAlias("return_code")
    private String returnCode;

    //返回信息
    @XStreamAlias("return_msg")
    private String returnMsg;

    //应用ID
    @XStreamAlias("appid")
    private String appId;

    //商户号
    @XStreamAlias("mch_id")
    private String mchId;

    //随机字符串
    @XStreamAlias("nonce_str")
    private String nonceStr;

    //预支付交易会话标识
    @XStreamAlias("prepay_id")
    private String prepayId;

    //业务结果
    @XStreamAlias("result_code")
    private String resultCode;

    //错误描述
    @XStreamAlias("err_code_des")
    private String errCodeDes;

    //签名
    @Ignore
    private String sign;

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

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

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getErrCodeDes() {
        return errCodeDes;
    }

    public void setErrCodeDes(String errCodeDes) {
        this.errCodeDes = errCodeDes;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
