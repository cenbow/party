package com.party.admin.web.dto.input.distribution;

/**
 * Created by wei.li
 *
 * @date 2017/3/17 0017
 * @time 9:46
 */
public class DistributionListInput {

    //类型(0: 商品，1：活动， 2.文章， 3.众筹)
    private Integer type;

    //题目
    private String title;

    //分销商名称
    private String distributorName;

    //时间类型
    private Integer timeType;

    //开始时间
    private String startDate;

    //结束时间
    private String endDate;


    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDistributorName() {
        return distributorName;
    }

    public void setDistributorName(String distributorName) {
        this.distributorName = distributorName;
    }

    public Integer getTimeType() {
        return timeType;
    }

    public void setTimeType(Integer timeType) {
        this.timeType = timeType;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
