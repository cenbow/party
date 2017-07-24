package com.party.core.model.crowdfund;

import com.party.core.model.BaseModel;

/**
 * 众筹支持实体
 * Created by wei.li
 *
 * @date 2017/2/16 0016
 * @time 16:58
 */
public class Support extends BaseModel {
    private static final long serialVersionUID = 5882820909120417254L;

    //支持评论
    private String comment;

    //支持者编号
    private String favorerId;

    //支付状态（0：待支付，1：支付成功,2.退款中,3:退款成功，4退款失败）
    private Integer payStatus;

    //支持生成的订单号
    private String orderId;

    //众筹项目编号
    private String projectId;

    //排除的状态
    private Integer excludePayStatus;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getFavorerId() {
        return favorerId;
    }

    public void setFavorerId(String favorerId) {
        this.favorerId = favorerId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }


    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getExcludePayStatus() {
        return excludePayStatus;
    }

    public void setExcludePayStatus(Integer excludePayStatus) {
        this.excludePayStatus = excludePayStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Support support = (Support) o;

        if (comment != null ? !comment.equals(support.comment) : support.comment != null) return false;
        if (favorerId != null ? !favorerId.equals(support.favorerId) : support.favorerId != null) return false;
        if (payStatus != null ? !payStatus.equals(support.payStatus) : support.payStatus != null) return false;
        if (orderId != null ? !orderId.equals(support.orderId) : support.orderId != null) return false;
        if (projectId != null ? !projectId.equals(support.projectId) : support.projectId != null) return false;
        return excludePayStatus != null ? excludePayStatus.equals(support.excludePayStatus) : support.excludePayStatus == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        result = 31 * result + (favorerId != null ? favorerId.hashCode() : 0);
        result = 31 * result + (payStatus != null ? payStatus.hashCode() : 0);
        result = 31 * result + (orderId != null ? orderId.hashCode() : 0);
        result = 31 * result + (projectId != null ? projectId.hashCode() : 0);
        result = 31 * result + (excludePayStatus != null ? excludePayStatus.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Support{" +
                "comment='" + comment + '\'' +
                ", favorerId='" + favorerId + '\'' +
                ", payStatus=" + payStatus +
                ", orderId='" + orderId + '\'' +
                ", projectId='" + projectId + '\'' +
                ", excludePayStatus=" + excludePayStatus +
                '}';
    }
}
