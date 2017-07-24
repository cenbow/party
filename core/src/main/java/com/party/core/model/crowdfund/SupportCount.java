package com.party.core.model.crowdfund;

import java.util.Date;

/**
 * 众筹支持统计
 * Created by wei.li
 *
 * @date 2017/6/28 0028
 * @time 10:37
 */
public class SupportCount {

    //时间
    private Date date;

    //金额
    private Float payment;

    //支持人数
    private Integer favorerNum;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Float getPayment() {
        return payment;
    }

    public void setPayment(Float payment) {
        this.payment = payment;
    }

    public Integer getFavorerNum() {
        return favorerNum;
    }

    public void setFavorerNum(Integer favorerNum) {
        this.favorerNum = favorerNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SupportCount that = (SupportCount) o;

        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (payment != null ? !payment.equals(that.payment) : that.payment != null) return false;
        return favorerNum != null ? favorerNum.equals(that.favorerNum) : that.favorerNum == null;

    }

    @Override
    public int hashCode() {
        int result = date != null ? date.hashCode() : 0;
        result = 31 * result + (payment != null ? payment.hashCode() : 0);
        result = 31 * result + (favorerNum != null ? favorerNum.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SupportCount{" +
                "date=" + date +
                ", payment=" + payment +
                ", favorerNum=" + favorerNum +
                '}';
    }
}
