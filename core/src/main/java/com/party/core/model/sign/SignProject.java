package com.party.core.model.sign;

import com.party.core.model.BaseModel;

import java.io.Serializable;
import java.util.Date;

/**
 * 签到项目
 * Created by wei.li
 *
 * @date 2017/6/5 0005
 * @time 15:24
 */
public class SignProject extends BaseModel implements Serializable {

    private static final long serialVersionUID = 682524954042799605L;

    //项目题目
    private String title;

    //项目发布者
    private String publisher;

    //发布者图像
    private String publisherLogo;

    //项目图像
    private String projectLogo;

    //签到图像
    private String signLogo;

    //开始时间
    private Date startTime;

    //结束时间
    private Date endTime;

    //价格
    private float price;

    //报名是否需要审核
    private Integer applyCheck;

    //成绩是否需要审核
    private Integer gradeCheck;

    //小组排行是否需要显示
    private Integer rankShow;

    //规则
    private String rule;

    //报名数
    private Integer applyNum;

    //通过数
    private Integer passNum;

    //拒绝数
    private Integer refuseNum;

    //待审核数
    private Integer notAuditNum;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getSignLogo() {
        return signLogo;
    }

    public void setSignLogo(String signLogo) {
        this.signLogo = signLogo;
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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Integer getApplyCheck() {
        return applyCheck;
    }

    public void setApplyCheck(Integer applyCheck) {
        this.applyCheck = applyCheck;
    }

    public Integer getRankShow() {
        return rankShow;
    }

    public void setRankShow(Integer rankShow) {
        this.rankShow = rankShow;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public Integer getApplyNum() {
        return applyNum;
    }

    public void setApplyNum(Integer applyNum) {
        this.applyNum = applyNum;
    }

    public Integer getPassNum() {
        return passNum;
    }

    public void setPassNum(Integer passNum) {
        this.passNum = passNum;
    }

    public Integer getRefuseNum() {
        return refuseNum;
    }

    public void setRefuseNum(Integer refuseNum) {
        this.refuseNum = refuseNum;
    }

    public Integer getNotAuditNum() {
        return notAuditNum;
    }

    public void setNotAuditNum(Integer notAuditNum) {
        this.notAuditNum = notAuditNum;
    }

    public Integer getGradeCheck() {
        return gradeCheck;
    }

    public void setGradeCheck(Integer gradeCheck) {
        this.gradeCheck = gradeCheck;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        SignProject that = (SignProject) o;

        if (Float.compare(that.price, price) != 0) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (publisher != null ? !publisher.equals(that.publisher) : that.publisher != null) return false;
        if (publisherLogo != null ? !publisherLogo.equals(that.publisherLogo) : that.publisherLogo != null)
            return false;
        if (projectLogo != null ? !projectLogo.equals(that.projectLogo) : that.projectLogo != null) return false;
        if (signLogo != null ? !signLogo.equals(that.signLogo) : that.signLogo != null) return false;
        if (startTime != null ? !startTime.equals(that.startTime) : that.startTime != null) return false;
        if (endTime != null ? !endTime.equals(that.endTime) : that.endTime != null) return false;
        if (applyCheck != null ? !applyCheck.equals(that.applyCheck) : that.applyCheck != null) return false;
        if (gradeCheck != null ? !gradeCheck.equals(that.gradeCheck) : that.gradeCheck != null) return false;
        if (rankShow != null ? !rankShow.equals(that.rankShow) : that.rankShow != null) return false;
        if (rule != null ? !rule.equals(that.rule) : that.rule != null) return false;
        if (applyNum != null ? !applyNum.equals(that.applyNum) : that.applyNum != null) return false;
        if (passNum != null ? !passNum.equals(that.passNum) : that.passNum != null) return false;
        if (refuseNum != null ? !refuseNum.equals(that.refuseNum) : that.refuseNum != null) return false;
        return notAuditNum != null ? notAuditNum.equals(that.notAuditNum) : that.notAuditNum == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (publisher != null ? publisher.hashCode() : 0);
        result = 31 * result + (publisherLogo != null ? publisherLogo.hashCode() : 0);
        result = 31 * result + (projectLogo != null ? projectLogo.hashCode() : 0);
        result = 31 * result + (signLogo != null ? signLogo.hashCode() : 0);
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
        result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
        result = 31 * result + (price != +0.0f ? Float.floatToIntBits(price) : 0);
        result = 31 * result + (applyCheck != null ? applyCheck.hashCode() : 0);
        result = 31 * result + (gradeCheck != null ? gradeCheck.hashCode() : 0);
        result = 31 * result + (rankShow != null ? rankShow.hashCode() : 0);
        result = 31 * result + (rule != null ? rule.hashCode() : 0);
        result = 31 * result + (applyNum != null ? applyNum.hashCode() : 0);
        result = 31 * result + (passNum != null ? passNum.hashCode() : 0);
        result = 31 * result + (refuseNum != null ? refuseNum.hashCode() : 0);
        result = 31 * result + (notAuditNum != null ? notAuditNum.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SignProject{" +
                "title='" + title + '\'' +
                ", publisher='" + publisher + '\'' +
                ", publisherLogo='" + publisherLogo + '\'' +
                ", projectLogo='" + projectLogo + '\'' +
                ", signLogo='" + signLogo + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", price=" + price +
                ", applyCheck=" + applyCheck +
                ", gradeCheck=" + gradeCheck +
                ", rankShow=" + rankShow +
                ", rule='" + rule + '\'' +
                ", applyNum=" + applyNum +
                ", passNum=" + passNum +
                ", refuseNum=" + refuseNum +
                ", notAuditNum=" + notAuditNum +
                '}';
    }
}
