package com.party.mobile.web.dto.partner.output;

import com.alibaba.fastjson.annotation.JSONField;
import com.party.core.model.member.MemberAct;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * 合作商活动报名输出视图
 * party
 * Created by wei.li
 * on 2016/10/24 0024.
 */
public class SignUpListOutput {

    //姓名
    private String name;

    //手机
    private String mobile;

    //审核状态
    private Integer checkStatus;

    //公司
    private String company;

    //职位
    private String jobTitle;

    //头像
    private String logo;

    //更新时间
    @JSONField(format = "yyyy-MM-dd HH:mm")
    private Date updateDate;

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(Integer checkStatus) {
        this.checkStatus = checkStatus;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * 合作商报名输出视图
     * @param memberAct 报名实体
     * @return 输出视图
     */
    public static SignUpListOutput transform(MemberAct memberAct){
        SignUpListOutput signUpListOutput = new SignUpListOutput();
        BeanUtils.copyProperties(memberAct, signUpListOutput);
        return signUpListOutput;
    }
}
