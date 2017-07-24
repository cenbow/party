package com.party.pay.model.pay.wechat;

import com.party.common.annotation.Ignore;
import com.party.common.annotation.ListSuffixResult;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.List;

/**
 * 支付通知请求参数
 * @author wei.li
 * @version 2016/7/15 0015
 */
public class NotifyRequest {


    //返回状态码
    @XStreamAlias("return_code")
    private String returnCode;

    //返回信息
    @XStreamAlias("return_msg")
    private String returnMsg;

    //应用ID
    private String appid;

    //商户号
    @XStreamAlias("mch_id")
    private String mchId;

    //设备号
    @XStreamAlias("device_info")
    private String deviceInfo;

    //随机字符串
    @XStreamAlias("nonce_str")
    private String nonceStr;

    //签名
    @Ignore
    private String sign;

    //签名类型
    @XStreamAlias("sign_type")
    private String signType;

    //业务结果
    @XStreamAlias("result_code")
    private String resultCode;

    //错误代码
    @XStreamAlias("err_code")
    private String errCode;

    //错误代码描述
    @XStreamAlias("err_code_des")
    private String errCodeDes;

    //用户标识
    private String openid;

    //是否关注公众账号
    @XStreamAlias("is_subscribe")
    private String isSubscribe;

    //交易类型
    @XStreamAlias("trade_type")
    private String tradeType;

    //付款银行
    @XStreamAlias("bank_type")
    private String bankType;

    //总金额
    @XStreamAlias("total_fee")
    private String totalFee;

    //应结订单金额
    @XStreamAlias("settlement_total_fee")
    private String settlementTotalFee;

    //货币种类
    @XStreamAlias("fee_type")
    private String feeType;

    //现金支付金额
    @XStreamAlias("cash_fee")
    private String cashFee;

    //现金支付货币类型
    @XStreamAlias("cash_fee_type")
    private String cashFeeType;

    @XStreamAlias("coupon_fee")
    private String couponFee;

    @XStreamAlias("coupon_count")
    private String couponCount;

    @ListSuffixResult
    private List<CouponRequest> couponRequestList;

    //微信支付订单号
    @XStreamAlias("transaction_id")
    private String transactionId;

    //商户订单号
    @XStreamAlias("out_trade_no")
    private String outTradeNo;

    //商家数据包
    @XStreamAlias("attach")
    private String attach;

    //支付完成时间
    @XStreamAlias("time_end")
    private String timeEnd;

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

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
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

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getIsSubscribe() {
        return isSubscribe;
    }

    public void setIsSubscribe(String isSubscribe) {
        this.isSubscribe = isSubscribe;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getBankType() {
        return bankType;
    }

    public void setBankType(String bankType) {
        this.bankType = bankType;
    }

    public String getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(String totalFee) {
        this.totalFee = totalFee;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public String getCashFee() {
        return cashFee;
    }

    public void setCashFee(String cashFee) {
        this.cashFee = cashFee;
    }

    public String getCashFeeType() {
        return cashFeeType;
    }

    public void setCashFeeType(String cashFeeType) {
        this.cashFeeType = cashFeeType;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getSettlementTotalFee() {
        return settlementTotalFee;
    }

    public void setSettlementTotalFee(String settlementTotalFee) {
        this.settlementTotalFee = settlementTotalFee;
    }

    public String getCouponFee() {
        return couponFee;
    }

    public void setCouponFee(String couponFee) {
        this.couponFee = couponFee;
    }

    public String getCouponCount() {
        return couponCount;
    }

    public void setCouponCount(String couponCount) {
        this.couponCount = couponCount;
    }

    public List<CouponRequest> getCouponRequestList() {
        return couponRequestList;
    }

    public void setCouponRequestList(List<CouponRequest> couponRequestList) {
        this.couponRequestList = couponRequestList;
    }
}
