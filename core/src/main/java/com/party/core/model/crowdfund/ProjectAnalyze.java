package com.party.core.model.crowdfund;

import com.party.core.model.label.Label;

import java.util.Date;
import java.util.List;

/**
 * 众筹分析
 * Created by wei.li
 *
 * @date 2017/7/10 0010
 * @time 10:01
 */
public class ProjectAnalyze {

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
    private Integer isFriend;

    //是否加群
    private Integer isGroup;

    //众筹状态
    private Integer isSuccess;

    //创建时间
    private Date createDate;

    //支持者数目
    private Integer favorerNum;

    //实际筹集资金
    private Float actualAmount;

    //标签名称
    private List<Label> labelList;

    //标签名称
    private String labelId;

    //排序方式
    private Integer sort;

    //操作金额
    private Integer operator;

    //数据
    private Integer operatorNum;

    //编号集合
    private List<String> ids;

    //目标编号
    private String targetId;

    //事件编号
    private String eventId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Integer getIsFriend() {
        return isFriend;
    }

    public void setIsFriend(Integer isFriend) {
        this.isFriend = isFriend;
    }

    public Integer getIsGroup() {
        return isGroup;
    }

    public void setIsGroup(Integer isGroup) {
        this.isGroup = isGroup;
    }

    public Integer getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(Integer isSuccess) {
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

    public List<Label> getLabelList() {
        return labelList;
    }

    public void setLabelList(List<Label> labelList) {
        this.labelList = labelList;
    }


    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getOperator() {
        return operator;
    }

    public void setOperator(Integer operator) {
        this.operator = operator;
    }

    public Integer getOperatorNum() {
        return operatorNum;
    }

    public void setOperatorNum(Integer operatorNum) {
        this.operatorNum = operatorNum;
    }

    public String getLabelId() {
        return labelId;
    }

    public void setLabelId(String labelId) {
        this.labelId = labelId;
    }

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProjectAnalyze that = (ProjectAnalyze) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (authorId != null ? !authorId.equals(that.authorId) : that.authorId != null) return false;
        if (authorName != null ? !authorName.equals(that.authorName) : that.authorName != null) return false;
        if (authorLogo != null ? !authorLogo.equals(that.authorLogo) : that.authorLogo != null) return false;
        if (cityName != null ? !cityName.equals(that.cityName) : that.cityName != null) return false;
        if (parentName != null ? !parentName.equals(that.parentName) : that.parentName != null) return false;
        if (company != null ? !company.equals(that.company) : that.company != null) return false;
        if (jobTitle != null ? !jobTitle.equals(that.jobTitle) : that.jobTitle != null) return false;
        if (mobile != null ? !mobile.equals(that.mobile) : that.mobile != null) return false;
        if (isFriend != null ? !isFriend.equals(that.isFriend) : that.isFriend != null) return false;
        if (isGroup != null ? !isGroup.equals(that.isGroup) : that.isGroup != null) return false;
        if (isSuccess != null ? !isSuccess.equals(that.isSuccess) : that.isSuccess != null) return false;
        if (createDate != null ? !createDate.equals(that.createDate) : that.createDate != null) return false;
        if (favorerNum != null ? !favorerNum.equals(that.favorerNum) : that.favorerNum != null) return false;
        if (actualAmount != null ? !actualAmount.equals(that.actualAmount) : that.actualAmount != null) return false;
        if (labelList != null ? !labelList.equals(that.labelList) : that.labelList != null) return false;
        if (labelId != null ? !labelId.equals(that.labelId) : that.labelId != null) return false;
        if (sort != null ? !sort.equals(that.sort) : that.sort != null) return false;
        if (operator != null ? !operator.equals(that.operator) : that.operator != null) return false;
        if (operatorNum != null ? !operatorNum.equals(that.operatorNum) : that.operatorNum != null) return false;
        if (ids != null ? !ids.equals(that.ids) : that.ids != null) return false;
        if (targetId != null ? !targetId.equals(that.targetId) : that.targetId != null) return false;
        return eventId != null ? eventId.equals(that.eventId) : that.eventId == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (authorId != null ? authorId.hashCode() : 0);
        result = 31 * result + (authorName != null ? authorName.hashCode() : 0);
        result = 31 * result + (authorLogo != null ? authorLogo.hashCode() : 0);
        result = 31 * result + (cityName != null ? cityName.hashCode() : 0);
        result = 31 * result + (parentName != null ? parentName.hashCode() : 0);
        result = 31 * result + (company != null ? company.hashCode() : 0);
        result = 31 * result + (jobTitle != null ? jobTitle.hashCode() : 0);
        result = 31 * result + (mobile != null ? mobile.hashCode() : 0);
        result = 31 * result + (isFriend != null ? isFriend.hashCode() : 0);
        result = 31 * result + (isGroup != null ? isGroup.hashCode() : 0);
        result = 31 * result + (isSuccess != null ? isSuccess.hashCode() : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (favorerNum != null ? favorerNum.hashCode() : 0);
        result = 31 * result + (actualAmount != null ? actualAmount.hashCode() : 0);
        result = 31 * result + (labelList != null ? labelList.hashCode() : 0);
        result = 31 * result + (labelId != null ? labelId.hashCode() : 0);
        result = 31 * result + (sort != null ? sort.hashCode() : 0);
        result = 31 * result + (operator != null ? operator.hashCode() : 0);
        result = 31 * result + (operatorNum != null ? operatorNum.hashCode() : 0);
        result = 31 * result + (ids != null ? ids.hashCode() : 0);
        result = 31 * result + (targetId != null ? targetId.hashCode() : 0);
        result = 31 * result + (eventId != null ? eventId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ProjectAnalyze{" +
                "id='" + id + '\'' +
                ", authorId='" + authorId + '\'' +
                ", authorName='" + authorName + '\'' +
                ", authorLogo='" + authorLogo + '\'' +
                ", cityName='" + cityName + '\'' +
                ", parentName='" + parentName + '\'' +
                ", company='" + company + '\'' +
                ", jobTitle='" + jobTitle + '\'' +
                ", mobile='" + mobile + '\'' +
                ", isFriend=" + isFriend +
                ", isGroup=" + isGroup +
                ", isSuccess=" + isSuccess +
                ", createDate=" + createDate +
                ", favorerNum=" + favorerNum +
                ", actualAmount=" + actualAmount +
                ", labelList=" + labelList +
                ", labelId='" + labelId + '\'' +
                ", sort=" + sort +
                ", operator=" + operator +
                ", operatorNum=" + operatorNum +
                ", ids=" + ids +
                ", targetId='" + targetId + '\'' +
                ", eventId='" + eventId + '\'' +
                '}';
    }
}
