package com.party.mobile.web.dto.sign.output;

import com.alibaba.fastjson.annotation.JSONField;
import com.party.core.model.sign.SignProject;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * Created by wei.li
 *
 * @date 2017/6/8 0008
 * @time 18:18
 */
public class SignProjectOutput {

    //主键
    private String id;

    //项目题目
    private String title;

    //项目发布者
    private String publisher;

    //发布者图像
    private String publisherLogo;

    //项目图像
    private String projectLogo;

    //开始时间
    private Date startTime;

    //结束时间
    private Date endTime;

    //规则
    private String rule;

    //是否报名
    @JSONField(name = "isApply")
    private boolean isApply;

    //是否签到
    @JSONField(name = "isSign")
    private boolean isSign;

    //小组排行是否需要显示
    private Integer rankShow;

    //报名状态
    private Integer applyStatus;

    //报名编号
    private String applyId;

    //备注
    private String remarks;

    //是否在有效时间内
    @JSONField(name = "isValidTime")
    private boolean isValidTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublisherLogo() {
        return publisherLogo;
    }

    public void setPublisherLogo(String publisherLogo) {
        this.publisherLogo = publisherLogo;
    }

    public String getProjectLogo() {
        return projectLogo;
    }

    public void setProjectLogo(String projectLogo) {
        this.projectLogo = projectLogo;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public boolean isApply() {
        return isApply;
    }

    public void setApply(boolean apply) {
        isApply = apply;
    }

    public Integer getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(Integer applyStatus) {
        this.applyStatus = applyStatus;
    }

    public Integer getRankShow() {
        return rankShow;
    }

    public void setRankShow(Integer rankShow) {
        this.rankShow = rankShow;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public boolean isSign() {
        return isSign;
    }

    public void setSign(boolean sign) {
        isSign = sign;
    }

    public boolean isValidTime() {
        return isValidTime;
    }

    public void setValidTime(boolean validTime) {
        isValidTime = validTime;
    }

    public static SignProjectOutput transform(SignProject signProject){
        SignProjectOutput signProjectOutput = new SignProjectOutput();
        BeanUtils.copyProperties(signProject, signProjectOutput);
        return signProjectOutput;
    }
}
