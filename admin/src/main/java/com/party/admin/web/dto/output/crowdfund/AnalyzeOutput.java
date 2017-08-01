package com.party.admin.web.dto.output.crowdfund;

import com.party.core.model.crowdfund.ProjectAnalyze;
import com.party.core.model.label.Label;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 众筹分析数据输出
 * Created by wei.li
 *
 * @date 2017/7/10 0010
 * @time 14:34
 */
public class AnalyzeOutput{

    //众筹编号
    private String id;

    //众筹者编号
    private String authorId;

    //众筹者
    private String authorName;

    //众筹图片
    private String authorLogo;

    //城市
    private String cityName;

    //上级分销
    private String parentName;

    //公司
    private String company;

    //职务
    private String jobTitle;

    //电话
    private String mobile;

    //是否加好友
    private String isFriend;

    //是否加群
    private String isGroup;

    //众筹状态
    private String isSuccess;

    //创建时间
    private Date createDate;

    //支持者数目
    private Integer favorerNum;

    //实际筹集资金
    private Float actualAmount;

    //标签名称
    private String labels;

    //标签号
    private String labelId;

    //样式
    private String style;

    //最近沟通记录
    private String recentlyRecord;

    //金额map
    private Map<String, Float> moneyMap;

    public Map<String, Float> getMoneyMap() {
        return moneyMap;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorLogo() {
        return authorLogo;
    }

    public void setAuthorLogo(String authorLogo) {
        this.authorLogo = authorLogo;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getIsFriend() {
        return isFriend;
    }

    public void setIsFriend(String isFriend) {
        this.isFriend = isFriend;
    }

    public String getIsGroup() {
        return isGroup;
    }

    public void setIsGroup(String isGroup) {
        this.isGroup = isGroup;
    }

    public String getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(String isSuccess) {
        this.isSuccess = isSuccess;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getFavorerNum() {
        return favorerNum;
    }

    public void setFavorerNum(Integer favorerNum) {
        this.favorerNum = favorerNum;
    }

    public Float getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(Float actualAmount) {
        this.actualAmount = actualAmount;
    }

    public String getLabels() {
        return labels;
    }

    public void setLabels(String labels) {
        this.labels = labels;
    }

    public void setMoneyMap(Map<String, Float> moneyMap) {
        this.moneyMap = moneyMap;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getLabelId() {
        return labelId;
    }

    public void setLabelId(String labelId) {
        this.labelId = labelId;
    }

    public String getRecentlyRecord() {
        return recentlyRecord;
    }

    public void setRecentlyRecord(String recentlyRecord) {
        this.recentlyRecord = recentlyRecord;
    }

    public static AnalyzeOutput transform(ProjectAnalyze projectAnalyze){
        AnalyzeOutput analyzeOutput = new AnalyzeOutput();
        BeanUtils.copyProperties(projectAnalyze, analyzeOutput);
        return analyzeOutput;
    }
}
