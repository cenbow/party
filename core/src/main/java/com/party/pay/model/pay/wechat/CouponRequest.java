package com.party.pay.model.pay.wechat;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * User: wei.li
 * Date: 2017/5/28
 * Time: 12:46
 */
public class CouponRequest {

    @XStreamAlias("coupon_id")
    private String couponId;

    @XStreamAlias("coupon_fee")
    private String couponFee;

    @XStreamAlias("coupon_type")
    private String couponType;

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public String getCouponFee() {
        return couponFee;
    }

    public void setCouponFee(String couponFee) {
        this.couponFee = couponFee;
    }

    public String getCouponType() {
        return couponType;
    }

    public void setCouponType(String couponType) {
        this.couponType = couponType;
    }
}
