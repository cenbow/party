package com.party.admin.web.dto.output.crowdfund;

import java.util.List;

/**
 * 支持输出视图
 * Created by wei.li
 *
 * @date 2017/6/28 0028
 * @time 11:30
 */
public class SupportCountOutput {

    private List<String> dateList;

    //金额
    private List<Float> paymentList;

    //支持人数
    private List<Integer> favorerList;


    public List<String> getDateList() {
        return dateList;
    }

    public void setDateList(List<String> dateList) {
        this.dateList = dateList;
    }

    public List<Float> getPaymentList() {
        return paymentList;
    }

    public void setPaymentList(List<Float> paymentList) {
        this.paymentList = paymentList;
    }

    public List<Integer> getFavorerList() {
        return favorerList;
    }

    public void setFavorerList(List<Integer> favorerList) {
        this.favorerList = favorerList;
    }
}
