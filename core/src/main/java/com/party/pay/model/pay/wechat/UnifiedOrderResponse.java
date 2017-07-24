package com.party.pay.model.pay.wechat;

import com.party.common.annotation.Ignore;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 微信统一下单响应结果
 * @author wei.li
 * @version 2016/7/15 0015
 */
public class UnifiedOrderResponse {


    //返回状态码
    @XStreamAlias("return_code")
    private String returnCode;

    //返回信息
    @XStreamAlias("return_msg")
    private String returnMsg;

    //应用APPID
    private String appid;

    //商户号
    @XStreamAlias("mch_id")
    private String mchId;

    //随机字符串
    @XStreamAlias("nonce_str")
    private String nonceStr;

    //签名
    @Ignore
    private String sign;

    //业务结果
    @XStreamAlias("result_code")
    private String resultCode;

    //错误代码
    @XStreamAlias("err_code")
    private String errCode;

    //错误代码描述
    @XStreamAlias("err_code_des")
    private String errCodeDes;

    //交易类型
    @XStreamAlias("trade_type")
    private String tradeType;

    //预支付交易会话标识
    @XStreamAlias("prepay_id")
    private String prepayId;

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

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrCodeDes() {
        return errCodeDes;
    }

    public void setErrCodeDes(String errCodeDes) {
        this.errCodeDes = errCodeDes;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }
}
