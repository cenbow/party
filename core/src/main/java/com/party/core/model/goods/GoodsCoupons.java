package com.party.core.model.goods;

import com.party.core.model.BaseModel;

import java.util.Date;

/**
 * 商品核销码
 * party
 * Created by wei.li
 * on 2016/9/19 0019.
 */
public class GoodsCoupons extends BaseModel{

    private static final long serialVersionUID = -7701898489635562694L;

    //订单编号
    private String orderId;

    //会员编号
    private String memberId;

    //商品编号
    private String goodsId;

    //核销码
    private String verifyCode;

    //是否消费
    private String isSpending;

    //消费日期
    private Date spendingTime;

    //是否预约消费
    private String isBookings;

    //预约消费日期
    private Date bookingsTime;

    //排序
    private Integer sort;

    //订单类型（0：正常预定商品，1：定制商品，2：活动）
    private Integer type;
    //状态
    private Integer status;




    public GoodsCoupons() {
        this.isSpending = "0";
        this.isBookings = "0";
        this.status = 1;
    }


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getIsSpending() {
        return isSpending;
    }

    public void setIsSpending(String isSpending) {
        this.isSpending = isSpending;
    }

    public Date getSpendingTime() {
        return spendingTime;
    }

    public void setSpendingTime(Date spendingTime) {
        this.spendingTime = spendingTime;
    }

    public String getIsBookings() {
        return isBookings;
    }

    public void setIsBookings(String isBookings) {
        this.isBookings = isBookings;
    }

    public Date getBookingsTime() {
        return bookingsTime;
    }

    public void setBookingsTime(Date bookingsTime) {
        this.bookingsTime = bookingsTime;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        GoodsCoupons that = (GoodsCoupons) o;

        if (orderId != null ? !orderId.equals(that.orderId) : that.orderId != null) return false;
        if (memberId != null ? !memberId.equals(that.memberId) : that.memberId != null) return false;
        if (goodsId != null ? !goodsId.equals(that.goodsId) : that.goodsId != null) return false;
        if (verifyCode != null ? !verifyCode.equals(that.verifyCode) : that.verifyCode != null) return false;
        if (isSpending != null ? !isSpending.equals(that.isSpending) : that.isSpending != null) return false;
        if (spendingTime != null ? !spendingTime.equals(that.spendingTime) : that.spendingTime != null) return false;
        if (isBookings != null ? !isBookings.equals(that.isBookings) : that.isBookings != null) return false;
        if (bookingsTime != null ? !bookingsTime.equals(that.bookingsTime) : that.bookingsTime != null) return false;
        if (sort != null ? !sort.equals(that.sort) : that.sort != null) return false;
        return type != null ? type.equals(that.type) : that.type == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (orderId != null ? orderId.hashCode() : 0);
        result = 31 * result + (memberId != null ? memberId.hashCode() : 0);
        result = 31 * result + (goodsId != null ? goodsId.hashCode() : 0);
        result = 31 * result + (verifyCode != null ? verifyCode.hashCode() : 0);
        result = 31 * result + (isSpending != null ? isSpending.hashCode() : 0);
        result = 31 * result + (spendingTime != null ? spendingTime.hashCode() : 0);
        result = 31 * result + (isBookings != null ? isBookings.hashCode() : 0);
        result = 31 * result + (bookingsTime != null ? bookingsTime.hashCode() : 0);
        result = 31 * result + (sort != null ? sort.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "GoodsCoupons{" +
                "orderId='" + orderId + '\'' +
                ", memberId='" + memberId + '\'' +
                ", goodsId='" + goodsId + '\'' +
                ", verifyCode='" + verifyCode + '\'' +
                ", isSpending='" + isSpending + '\'' +
                ", spendingTime=" + spendingTime +
                ", isBookings='" + isBookings + '\'' +
                ", bookingsTime=" + bookingsTime +
                ", sort=" + sort +
                ", type=" + type +
                '}';
    }

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
