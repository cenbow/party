package com.party.mobile.web.dto.alipay.output;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 支付宝请求参数
 * party
 * Created by wei.li
 * on 2016/9/21 0021.
 */
public class Content {

    //商品的标题
    private String subject;

    // 	商户网站唯一订单号
    @JSONField(name = "out_trade_no")
    private String outTradeNo;

    //订单总金额
    @JSONField(name = "total_amount")
    private String totalAmount;

    //销售产品码
    @JSONField(name = "product_code")
    private String productCode;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }
}
