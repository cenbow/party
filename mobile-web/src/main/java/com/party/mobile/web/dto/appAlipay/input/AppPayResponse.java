package com.party.mobile.web.dto.appAlipay.input;

import com.party.common.annotation.Ignore;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.Date;

/**
 * app支付宝支付异步通知参数
 * @author Wesley
 * @version 2016/11/15 0013
 */
public class AppPayResponse {

    //通知时间
    @XStreamAlias("notify_time")
    private String notifyTime;

    //通知类型
    @XStreamAlias("notify_type")
    private String notifyType;

    //通知校验ID
    @XStreamAlias("notify_id")
    private String notifyId;

    //签名方式
    @Ignore
    @XStreamAlias("sign_type")
    private String signType;

    //签名
    @Ignore
    private String sign;

    //商户网站唯一订单号
    @XStreamAlias("out_trade_no")
    private String outTradeNo;

    //商品名称
    private String subject;

    //支付类型
    @XStreamAlias("payment_type")
    private String paymentType;

    //支付宝交易号
    @XStreamAlias("trade_no")
    private String tradeNo;

    //交易状态
    @XStreamAlias("trade_status")
    private String tradeStatus;

    //卖家支付宝用户号
    @XStreamAlias("seller_id")
    private String sellerId;

    //卖家支付宝账号
    @XStreamAlias("seller_email")
    private String sellerEmail;

    //买家支付宝用户号
    @XStreamAlias("buyer_id")
    private String buyerId;

    //买家支付宝账号
    @XStreamAlias("buyer_email")
    private String buyerEmail;

    //交易金额
    @XStreamAlias("total_fee")
    private Float totalFee;

    //购买数量
    private Integer quantity;

    //商品单价
    private Float price;

    //商品描述
    private String body;

    //交易创建时间
    @XStreamAlias("gmt_create")
    private String gmtCreate;

    //交易付款时间
    @XStreamAlias("gmt_payment")
    private String gmtPayment;

    //是否调整总价
    @XStreamAlias("is_total_fee_adjust")
    private String isTotalFeeAdjust;

    //是否使用红包买家
    @XStreamAlias("use_coupon")
    private String useCoupon;

    //折扣
    private String discount;

    //退款状态
    @XStreamAlias("refund_status")
    private String refundStatus;

    //退款时间
    @XStreamAlias("gmt_refund")
    private Date gmtRefund;




    public String getNotifyType() {
        return notifyType;
    }

    public void setNotifyType(String notifyType) {
        this.notifyType = notifyType;
    }

    public String getNotifyId() {
        return notifyId;
    }

    public void setNotifyId(String notifyId) {
        this.notifyId = notifyId;
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

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(String tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getSellerEmail() {
        return sellerEmail;
    }

    public void setSellerEmail(String sellerEmail) {
        this.sellerEmail = sellerEmail;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public String getBuyerEmail() {
        return buyerEmail;
    }

    public void setBuyerEmail(String buyerEmail) {
        this.buyerEmail = buyerEmail;
    }

    public Float getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Float totalFee) {
        this.totalFee = totalFee;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getNotifyTime() {
        return notifyTime;
    }

    public void setNotifyTime(String notifyTime) {
        this.notifyTime = notifyTime;
    }

    public String getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(String gmtCreate) {
        this.gmtCreate = gmtCreate;
    }


    public String getGmtPayment() {
        return gmtPayment;
    }

    public void setGmtPayment(String gmtPayment) {
        this.gmtPayment = gmtPayment;
    }

    public String getIsTotalFeeAdjust() {
        return isTotalFeeAdjust;
    }

    public void setIsTotalFeeAdjust(String isTotalFeeAdjust) {
        this.isTotalFeeAdjust = isTotalFeeAdjust;
    }

    public String getUseCoupon() {
        return useCoupon;
    }

    public void setUseCoupon(String useCoupon) {
        this.useCoupon = useCoupon;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(String refundStatus) {
        this.refundStatus = refundStatus;
    }

    public Date getGmtRefund() {
        return gmtRefund;
    }

    public void setGmtRefund(Date gmtRefund) {
        this.gmtRefund = gmtRefund;
    }

    @Override
    public String toString() {
        return "AppPayResponse{" +
                "notifyTime=" + notifyTime +
                ", notifyType='" + notifyType + '\'' +
                ", notifyId='" + notifyId + '\'' +
                ", signType='" + signType + '\'' +
                ", sign='" + sign + '\'' +
                ", outTradeNo='" + outTradeNo + '\'' +
                ", subject='" + subject + '\'' +
                ", paymentType='" + paymentType + '\'' +
                ", tradeNo='" + tradeNo + '\'' +
                ", tradeStatus='" + tradeStatus + '\'' +
                ", sellerId='" + sellerId + '\'' +
                ", sellerEmail='" + sellerEmail + '\'' +
                ", buyerId='" + buyerId + '\'' +
                ", buyerEmail='" + buyerEmail + '\'' +
                ", totalFee=" + totalFee +
                ", quantity=" + quantity +
                ", price=" + price +
                ", body='" + body + '\'' +
                ", gmtCreate=" + gmtCreate +
                ", gmtPayment=" + gmtPayment +
                ", isTotalFeeAdjust='" + isTotalFeeAdjust + '\'' +
                ", useCoupon='" + useCoupon + '\'' +
                ", discount='" + discount + '\'' +
                ", refundStatus='" + refundStatus + '\'' +
                ", gmtRefund=" + gmtRefund +
                '}';
    }
}
