package com.party.mobile.web.dto.crowdfund.input;

import com.party.core.model.activity.Activity;
import com.party.core.model.member.Member;
import com.party.mobile.web.dto.crowdfund.output.ActivityDetailOutput;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.BeanUtils;

/**
 * 众筹活动报名输入
 * Created by wei.li
 *
 * @date 2017/2/25 0025
 * @time 10:35
 */
public class ApplyInput {

    //活动编号
    @NotBlank(message = "活动编号不能为空")
    private String id;

    //报名者姓名
    @NotBlank(message = "姓名不能为空")
    private String realname;

    //手机号
    @NotBlank(message = "电话号码不能为空")
    private String mobile;

    //分销对象编号
    private String distributorTargetId;

    //被分销者编号
    private String distributorParentId;

    //分销者
    private String distributorId;

    //项目题目
    private String projectTitle;

    //风格
    private String style;

    //验证码
    private String verifyCode;

    //公司信息
    private String company;

    //职位
    private String title;

    //行业
    private String industry;

    //区域
    private String city;

    //众筹宣言
    private String declaration;

    //来源于哪一个众筹
    private String sourceProjectId;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getDeclaration() {
        return declaration;
    }

    public void setDeclaration(String declaration) {
        this.declaration = declaration;
    }

    public String getProjectTitle() {
        return projectTitle;
    }

    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
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

    public String getSourceProjectId() {
        return sourceProjectId;
    }

    public void setSourceProjectId(String sourceProjectId) {
        this.sourceProjectId = sourceProjectId;
    }


    public static Member transformMember(ApplyInput input) {
        Member member = new Member();
        member.setCompany(input.getCompany());
        member.setCity(input.getCity());
        member.setJobTitle(input.getTitle());
        return member;
    }
}
