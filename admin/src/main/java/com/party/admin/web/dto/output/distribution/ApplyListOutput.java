package com.party.admin.web.dto.output.distribution;

import com.party.core.model.member.ApplyWithActivity;
import com.party.core.model.member.MemberAct;
import org.springframework.beans.BeanUtils;

/**
 * 分销活动报名输出视图
 * Created by wei.li
 *
 * @date 2017/3/9 0009
 * @time 17:45
 */
public class ApplyListOutput {

    //报名编号
    private String id;

    //报名者
    private String applicant;

    //活动报名状态(0,"审核中",1,"审核通过，待支付",2,"审核拒绝",3,"已支付，报名成功",4,"已取消",5,"未参与")
    private Integer checkStatus;

    //活动费用
    private Float payment;

    //活动名称
    private String activityName;

    //手机
    private String mobile;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApplicant() {
        return applicant;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }

    public Integer getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(Integer checkStatus) {
        this.checkStatus = checkStatus;
    }

    public Float getPayment() {
        return payment;
    }

    public void setPayment(Float payment) {
        this.payment = payment;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public static ApplyListOutput transform(ApplyWithActivity applyWithActivity){
        ApplyListOutput applyListOutput = new ApplyListOutput();
        BeanUtils.copyProperties(applyWithActivity, applyListOutput);
        return applyListOutput;
    }
}
