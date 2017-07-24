package com.party.mobile.web.dto.appAlipay.output;


import com.party.common.annotation.Ignore;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * app支付宝支付请求参数
 * @author Wesley
 * @version 2016/11/15 0013
 */
public class AppPayRequest {

    //接口名称
    private String service;

    //合作者身份ID
    private String partner;

    //参数编码字符集
    @XStreamAlias("_input_charset")
    private String inputCharset;

    //未付款交易的超时时间
    @XStreamAlias("it_b_pay")
    private String itBpay;

    //签名方式
    @Ignore
    @XStreamAlias("sign_type")
    private String signType;

    //签名
    @Ignore
    private String sign;

    //服务器异步通知页面路径
    @XStreamAlias("notify_url")
    private String notifyUrl;

    //商户网站唯一订单号
    @XStreamAlias("out_trade_no")
    private String outTradeNo;

    //商品名称
    private String subject;

    //支付类型
    @XStreamAlias("payment_type")
    private String paymentType;

    //卖家支付宝账号
    @XStreamAlias("seller_id")
    private String sellerId;

    //总金额
    @XStreamAlias("total_fee")
    private String totalFee;

    //商品详情
    private String body;

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public String getInputCharset() {
        return inputCharset;
    }

    public void setInputCharset(String inputCharset) {
        this.inputCharset = inputCharset;
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

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
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

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(String totalFee) {
        this.totalFee = totalFee;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getItBpay() {
        return itBpay;
    }

    public void setItBpay(String itBpay) {
        this.itBpay = itBpay;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("partner=\"").append(partner).append("\"")
                .append("&seller_id=\"").append(sellerId).append("\"")
                .append("&out_trade_no=\"").append(outTradeNo).append("\"")
                .append("&subject=\"").append(subject).append("\"")
                .append("&body=\"").append(body).append("\"")
                .append("&total_fee=\"").append(totalFee).append("\"")
                .append("&notify_url=\"").append(notifyUrl).append("\"")
                .append("&service=\"").append(service).append("\"")
                .append("&payment_type=\"").append(paymentType).append("\"")
                .append("&_input_charset=\"").append(inputCharset).append("\"")
                .append("&it_b_pay=\"").append(itBpay).append("\"")
                .append("&sign=\"").append(sign).append("\"")
                .append("&sign_type=\"").append(signType).append("\"");
        return  stringBuilder.toString();
    }
}
