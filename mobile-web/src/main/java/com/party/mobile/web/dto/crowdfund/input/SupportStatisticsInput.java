package com.party.mobile.web.dto.crowdfund.input;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * 支持统计输入视图
 * Created by wei.li
 *
 * @date 2017/3/28 0028
 * @time 10:53
 */
public class SupportStatisticsInput {

    //分销关系类型
    @NotNull(message = "类型不能为空")
    private Integer distributorType;

    //分销对象编号
    @NotBlank(message = "分销对象编号不能为空")
    private String distributorTargetId;

    //被分销者编号
    @NotBlank(message = "分销父对象不能空")
    private String distributorParentId;

    @NotBlank(message = "分销者不能为空")
    private String distributorId;

    //订单号
    private String supportId;

    //支持金额
    private Float payment;

    public Integer getDistributorType() {
        return distributorType;
    }

    public void setDistributorType(Integer distributorType) {
        this.distributorType = distributorType;
    }

    public String getDistributorTargetId() {
        return distributorTargetId;
    }

    public void setDistributorTargetId(String distributorTargetId) {
        this.distributorTargetId = distributorTargetId;
    }

    public String getDistributorParentId() {
        return distributorParentId;
    }

    public void setDistributorParentId(String distributorParentId) {
        this.distributorParentId = distributorParentId;
    }

    public String getDistributorId() {
        return distributorId;
    }

    public void setDistributorId(String distributorId) {
        this.distributorId = distributorId;
    }

    public String getSupportId() {
        return supportId;
    }

    public void setSupportId(String supportId) {
        this.supportId = supportId;
    }

    public Float getPayment() {
        return payment;
    }

    public void setPayment(Float payment) {
        this.payment = payment;
    }
}
