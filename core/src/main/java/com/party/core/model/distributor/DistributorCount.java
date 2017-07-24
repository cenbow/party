package com.party.core.model.distributor;

import com.party.core.model.BaseModel;

/**
 * 分销统计实体
 * Created by wei.li
 *
 * @date 2017/3/1 0001
 * @time 13:49
 */
public class DistributorCount extends BaseModel {
    private static final long serialVersionUID = 1851159930084336478L;

    //分销关系编号
    private String distributorRalationId;

    //浏览量
    private Integer viewNum;

    //分享量
    private Integer shareNum;

    //报名量
    private Integer applyNum;

    //销售量
    private Integer salesNum;

    //众筹数
    private Integer crowdfundNum;

    //支持人数
    private Integer favorerNum;

    //支持金额
    private Float favorerAmount;

    public DistributorCount() {
    }

    public DistributorCount(String distributorRalationId) {
        this.distributorRalationId = distributorRalationId;
    }

    public Integer getViewNum() {
        return viewNum;
    }

    public void setViewNum(Integer viewNum) {
        this.viewNum = viewNum;
    }

    public Integer getShareNum() {
        return shareNum;
    }

    public void setShareNum(Integer shareNum) {
        this.shareNum = shareNum;
    }

    public Integer getApplyNum() {
        return applyNum;
    }

    public void setApplyNum(Integer applyNum) {
        this.applyNum = applyNum;
    }

    public Integer getSalesNum() {
        return salesNum;
    }

    public void setSalesNum(Integer salesNum) {
        this.salesNum = salesNum;
    }

    public String getDistributorRalationId() {
        return distributorRalationId;
    }

    public void setDistributorRalationId(String distributorRalationId) {
        this.distributorRalationId = distributorRalationId;
    }

    public Integer getCrowdfundNum() {
        return crowdfundNum;
    }

    public void setCrowdfundNum(Integer crowdfundNum) {
        this.crowdfundNum = crowdfundNum;
    }

    public Integer getFavorerNum() {
        return favorerNum;
    }

    public void setFavorerNum(Integer favorerNum) {
        this.favorerNum = favorerNum;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Float getFavorerAmount() {
        return favorerAmount;
    }

    public void setFavorerAmount(Float favorerAmount) {
        this.favorerAmount = favorerAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        DistributorCount that = (DistributorCount) o;

        if (distributorRalationId != null ? !distributorRalationId.equals(that.distributorRalationId) : that.distributorRalationId != null)
            return false;
        if (viewNum != null ? !viewNum.equals(that.viewNum) : that.viewNum != null) return false;
        if (shareNum != null ? !shareNum.equals(that.shareNum) : that.shareNum != null) return false;
        if (applyNum != null ? !applyNum.equals(that.applyNum) : that.applyNum != null) return false;
        return salesNum != null ? salesNum.equals(that.salesNum) : that.salesNum == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (distributorRalationId != null ? distributorRalationId.hashCode() : 0);
        result = 31 * result + (viewNum != null ? viewNum.hashCode() : 0);
        result = 31 * result + (shareNum != null ? shareNum.hashCode() : 0);
        result = 31 * result + (applyNum != null ? applyNum.hashCode() : 0);
        result = 31 * result + (salesNum != null ? salesNum.hashCode() : 0);
        return result;
    }
}
