package com.party.core.model.crowdfund;

import java.util.Set;

/**
 * 众筹项目和创建者集合
 * Created by wei.li
 *
 * @date 2017/2/22 0022
 * @time 11:12
 */
public class ProjectWithAuthor extends Project{

    //创建者编号
    private String authorId;

    //创建者用户状态
    private Integer authorStatus;

    //创建者是否达人(0：不是 1：是)
    private Integer authorIsExpert;

    //创建者名称
    private String authorName;

    //创建者图片
    private String authorLogo;

    //创建者公司
    private String authorCompany;

    //创建者职务
    private String authorJobTitle;

    //创建者区域
    private String cityName;

    //创建者电话
    private String authorMobile;

    //目标编号
    private String targetId;

    //支持者编号
    private String favorerId;

    //订单状态
    private Integer orderStatus;

    //分销关系编号
    private String relationId;

    //编号集合
    private Set<String> authorIds;

    //众筹事件编号
    private String eventId;

    //排除目标编号
    private String excludeTargetId;

    //浏览量
    private Integer viewNum;

    //排序方式
    private int sort;

    //运算符号
    private Integer operator;

    //运算数据
    private Float operatorNum;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    @Override
    public String getAuthorId() {
        return authorId;
    }

    @Override
    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public Integer getAuthorStatus() {
        return authorStatus;
    }

    public void setAuthorStatus(Integer authorStatus) {
        this.authorStatus = authorStatus;
    }

    public Integer getAuthorIsExpert() {
        return authorIsExpert;
    }

    public void setAuthorIsExpert(Integer authorIsExpert) {
        this.authorIsExpert = authorIsExpert;
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

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getFavorerId() {
        return favorerId;
    }

    public void setFavorerId(String favorerId) {
        this.favorerId = favorerId;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getRelationId() {
        return relationId;
    }

    public void setRelationId(String relationId) {
        this.relationId = relationId;
    }

    public Set<String> getAuthorIds() {
        return authorIds;
    }

    public void setAuthorIds(Set<String> authorIds) {
        this.authorIds = authorIds;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getExcludeTargetId() {
        return excludeTargetId;
    }

    public void setExcludeTargetId(String excludeTargetId) {
        this.excludeTargetId = excludeTargetId;
    }


    public String getAuthorCompany() {
        return authorCompany;
    }

    public void setAuthorCompany(String authorCompany) {
        this.authorCompany = authorCompany;
    }

    public String getAuthorJobTitle() {
        return authorJobTitle;
    }

    public void setAuthorJobTitle(String authorJobTitle) {
        this.authorJobTitle = authorJobTitle;
    }

    public String getAuthorMobile() {
        return authorMobile;
    }

    public void setAuthorMobile(String authorMobile) {
        this.authorMobile = authorMobile;
    }

    public Integer getViewNum() {
        return viewNum;
    }

    public void setViewNum(Integer viewNum) {
        this.viewNum = viewNum;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public Integer getOperator() {
        return operator;
    }

    public void setOperator(Integer operator) {
        this.operator = operator;
    }

    public Float getOperatorNum() {
        return operatorNum;
    }

    public void setOperatorNum(Float operatorNum) {
        this.operatorNum = operatorNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ProjectWithAuthor that = (ProjectWithAuthor) o;

        if (sort != that.sort) return false;
        if (authorId != null ? !authorId.equals(that.authorId) : that.authorId != null) return false;
        if (authorStatus != null ? !authorStatus.equals(that.authorStatus) : that.authorStatus != null) return false;
        if (authorIsExpert != null ? !authorIsExpert.equals(that.authorIsExpert) : that.authorIsExpert != null)
            return false;
        if (authorName != null ? !authorName.equals(that.authorName) : that.authorName != null) return false;
        if (authorLogo != null ? !authorLogo.equals(that.authorLogo) : that.authorLogo != null) return false;
        if (authorCompany != null ? !authorCompany.equals(that.authorCompany) : that.authorCompany != null)
            return false;
        if (authorJobTitle != null ? !authorJobTitle.equals(that.authorJobTitle) : that.authorJobTitle != null)
            return false;
        if (authorMobile != null ? !authorMobile.equals(that.authorMobile) : that.authorMobile != null) return false;
        if (targetId != null ? !targetId.equals(that.targetId) : that.targetId != null) return false;
        if (favorerId != null ? !favorerId.equals(that.favorerId) : that.favorerId != null) return false;
        if (orderStatus != null ? !orderStatus.equals(that.orderStatus) : that.orderStatus != null) return false;
        if (relationId != null ? !relationId.equals(that.relationId) : that.relationId != null) return false;
        if (authorIds != null ? !authorIds.equals(that.authorIds) : that.authorIds != null) return false;
        if (eventId != null ? !eventId.equals(that.eventId) : that.eventId != null) return false;
        if (excludeTargetId != null ? !excludeTargetId.equals(that.excludeTargetId) : that.excludeTargetId != null)
            return false;
        if (viewNum != null ? !viewNum.equals(that.viewNum) : that.viewNum != null) return false;
        if (operator != null ? !operator.equals(that.operator) : that.operator != null) return false;
        return operatorNum != null ? operatorNum.equals(that.operatorNum) : that.operatorNum == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (authorId != null ? authorId.hashCode() : 0);
        result = 31 * result + (authorStatus != null ? authorStatus.hashCode() : 0);
        result = 31 * result + (authorIsExpert != null ? authorIsExpert.hashCode() : 0);
        result = 31 * result + (authorName != null ? authorName.hashCode() : 0);
        result = 31 * result + (authorLogo != null ? authorLogo.hashCode() : 0);
        result = 31 * result + (authorCompany != null ? authorCompany.hashCode() : 0);
        result = 31 * result + (authorJobTitle != null ? authorJobTitle.hashCode() : 0);
        result = 31 * result + (authorMobile != null ? authorMobile.hashCode() : 0);
        result = 31 * result + (targetId != null ? targetId.hashCode() : 0);
        result = 31 * result + (favorerId != null ? favorerId.hashCode() : 0);
        result = 31 * result + (orderStatus != null ? orderStatus.hashCode() : 0);
        result = 31 * result + (relationId != null ? relationId.hashCode() : 0);
        result = 31 * result + (authorIds != null ? authorIds.hashCode() : 0);
        result = 31 * result + (eventId != null ? eventId.hashCode() : 0);
        result = 31 * result + (excludeTargetId != null ? excludeTargetId.hashCode() : 0);
        result = 31 * result + (viewNum != null ? viewNum.hashCode() : 0);
        result = 31 * result + sort;
        result = 31 * result + (operator != null ? operator.hashCode() : 0);
        result = 31 * result + (operatorNum != null ? operatorNum.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ProjectWithAuthor{" +
                "authorId='" + authorId + '\'' +
                ", authorStatus=" + authorStatus +
                ", authorIsExpert=" + authorIsExpert +
                ", authorName='" + authorName + '\'' +
                ", authorLogo='" + authorLogo + '\'' +
                ", authorCompany='" + authorCompany + '\'' +
                ", authorJobTitle='" + authorJobTitle + '\'' +
                ", authorMobile='" + authorMobile + '\'' +
                ", targetId='" + targetId + '\'' +
                ", favorerId='" + favorerId + '\'' +
                ", orderStatus=" + orderStatus +
                ", relationId='" + relationId + '\'' +
                ", authorIds=" + authorIds +
                ", eventId='" + eventId + '\'' +
                ", excludeTargetId='" + excludeTargetId + '\'' +
                ", viewNum=" + viewNum +
                ", sort=" + sort +
                ", operator=" + operator +
                ", operatorNum=" + operatorNum +
                '}';
    }
}
