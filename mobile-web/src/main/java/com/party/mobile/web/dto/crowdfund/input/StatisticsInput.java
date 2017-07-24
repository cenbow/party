package com.party.mobile.web.dto.crowdfund.input;


/**
 * 分销报名统计输入
 * Created by wei.li
 *
 * @date 2017/3/28 0028
 * @time 9:56
 */
public class StatisticsInput {


    //分销对象编号
    private String distributorTargetId;

    //被分销者编号
    private String distributorParentId;

    private String distributorId;

    //众筹编号
    private String projectId;

    //订单编号
    private String orderId;

    //报名编号
    private String applyId;


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

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }
}
