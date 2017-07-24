package com.party.mobile.web.dto.distributor.input;

import com.party.core.model.distributor.DistributorRelation;
import com.party.core.model.member.Member;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotNull;


/**
 * 获取分销详情输入视图
 * Created by wei.li
 *
 * @date 2017/3/1 0001
 * @time 17:32
 */
public class GetDistributorInput {

    //分销关系类型
    @NotNull(message = "类型不能为空")
    private Integer type;

    //分销对象编号
    @NotBlank(message = "分销对象编号不能为空")
    private String targetId;

    //被分销者编号
    @NotBlank(message = "分销父对象不能空")
    private String parentId;

    //报名者姓名
    private String realname;

    //手机号
    private String mobile;

    //分销者编号
    private String distributorId;

    //风格
    private String style;

    //内容
    private String content;

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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDistributorId() {
        return distributorId;
    }

    public void setDistributorId(String distributorId) {
        this.distributorId = distributorId;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public static DistributorRelation transform(GetDistributorInput input){
        DistributorRelation distributorRelation = new DistributorRelation();
        BeanUtils.copyProperties(input, distributorRelation);
        return distributorRelation;
    }

    public static Member transformMember(GetDistributorInput input) {
        Member member = new Member();
        member.setCompany(input.getCompany());
        member.setCity(input.getCity());
        member.setJobTitle(input.getTitle());
        return member;
    }

}
