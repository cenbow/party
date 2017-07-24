package com.party.core.model.crowdfund;


import com.party.common.annotation.ExcelField;

/**
 * 众筹支持带支持者信息
 * Created by wei.li
 *
 * @date 2017/2/17 0017
 * @time 10:37
 */
public class SupportWithMember extends Support{
    private static final long serialVersionUID = 6136593330887274455L;

    //支持者名称
    private String favorerName;

    //支持者图像
    private String favorerLogo;

    //支持金额
    private Float payment;

    //分销关系编号
    private String relationId;

    //支持者公司
    private String favorerCompany;

    //支持者工作
    private String favorerJobTitle;

    //支持者电话
    private String favorerMobile;

    //支持订单状态
    private Integer status;

    //开始时间
    private String startTime;

    @ExcelField(title = "支持者", align = 2, sort = 1)
    public String getFavorerName() {
        return favorerName;
    }

    public void setFavorerName(String favorerName) {
        this.favorerName = favorerName;
    }

    public String getFavorerLogo() {
        return favorerLogo;
    }

    public void setFavorerLogo(String favorerLogo) {
        this.favorerLogo = favorerLogo;
    }

    @ExcelField(title = "支持金额", align = 2, sort = 5)
    public Float getPayment() {
        return payment;
    }

    public void setPayment(Float payment) {
        this.payment = payment;
    }


    public String getRelationId() {
        return relationId;
    }

    public void setRelationId(String relationId) {
        this.relationId = relationId;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    @ExcelField(title = "公司", align = 2, sort = 2)
    public String getFavorerCompany() {
        return favorerCompany;
    }

    public void setFavorerCompany(String favorerCompany) {
        this.favorerCompany = favorerCompany;
    }

    @ExcelField(title = "职位", align = 2, sort = 3)
    public String getFavorerJobTitle() {
        return favorerJobTitle;
    }

    public void setFavorerJobTitle(String favorerJobTitle) {
        this.favorerJobTitle = favorerJobTitle;
    }

    @ExcelField(title = "联系电话", align = 2, sort = 4)
    public String getFavorerMobile() {
        return favorerMobile;
    }

    public void setFavorerMobile(String favorerMobile) {
        this.favorerMobile = favorerMobile;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        SupportWithMember that = (SupportWithMember) o;

        if (favorerName != null ? !favorerName.equals(that.favorerName) : that.favorerName != null) return false;
        if (favorerLogo != null ? !favorerLogo.equals(that.favorerLogo) : that.favorerLogo != null) return false;
        if (payment != null ? !payment.equals(that.payment) : that.payment != null) return false;
        if (relationId != null ? !relationId.equals(that.relationId) : that.relationId != null) return false;
        if (favorerCompany != null ? !favorerCompany.equals(that.favorerCompany) : that.favorerCompany != null)
            return false;
        if (favorerJobTitle != null ? !favorerJobTitle.equals(that.favorerJobTitle) : that.favorerJobTitle != null)
            return false;
        if (favorerMobile != null ? !favorerMobile.equals(that.favorerMobile) : that.favorerMobile != null)
            return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        return startTime != null ? startTime.equals(that.startTime) : that.startTime == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (favorerName != null ? favorerName.hashCode() : 0);
        result = 31 * result + (favorerLogo != null ? favorerLogo.hashCode() : 0);
        result = 31 * result + (payment != null ? payment.hashCode() : 0);
        result = 31 * result + (relationId != null ? relationId.hashCode() : 0);
        result = 31 * result + (favorerCompany != null ? favorerCompany.hashCode() : 0);
        result = 31 * result + (favorerJobTitle != null ? favorerJobTitle.hashCode() : 0);
        result = 31 * result + (favorerMobile != null ? favorerMobile.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SupportWithMember{" +
                "favorerName='" + favorerName + '\'' +
                ", favorerLogo='" + favorerLogo + '\'' +
                ", payment=" + payment +
                ", relationId='" + relationId + '\'' +
                ", favorerCompany='" + favorerCompany + '\'' +
                ", favorerJobTitle='" + favorerJobTitle + '\'' +
                ", favorerMobile='" + favorerMobile + '\'' +
                ", status=" + status +
                ", startTime='" + startTime + '\'' +
                '}';
    }
}
