package com.party.core.model.count;

import com.party.core.model.BaseModel;

/**
 * 数据统计实体
 * Created by wei.li
 *
 * @date 2017/3/28 0028
 * @time 17:36
 */
public class DataCount extends BaseModel {

    //目标编号
    private String targetId;

    //阅读数
    private Integer viewNum;

    //分销数
    private Integer shareNum;

    //报名数
    private Integer applyNum;

    //销售量
    private Integer salesNum;

    //销售金额
    private Float salesAmount;


    public DataCount() {
        this.viewNum = 0;
        this.shareNum = 0;
        this.applyNum = 0;
        this.salesNum = 0;
        this.salesAmount = 0f;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
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

    public Float getSalesAmount() {
        return salesAmount;
    }

    public void setSalesAmount(Float salesAmount) {
        this.salesAmount = salesAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        DataCount dataCount = (DataCount) o;

        if (targetId != null ? !targetId.equals(dataCount.targetId) : dataCount.targetId != null) return false;
        if (viewNum != null ? !viewNum.equals(dataCount.viewNum) : dataCount.viewNum != null) return false;
        if (shareNum != null ? !shareNum.equals(dataCount.shareNum) : dataCount.shareNum != null) return false;
        if (applyNum != null ? !applyNum.equals(dataCount.applyNum) : dataCount.applyNum != null) return false;
        if (salesNum != null ? !salesNum.equals(dataCount.salesNum) : dataCount.salesNum != null) return false;
        return salesAmount != null ? salesAmount.equals(dataCount.salesAmount) : dataCount.salesAmount == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (targetId != null ? targetId.hashCode() : 0);
        result = 31 * result + (viewNum != null ? viewNum.hashCode() : 0);
        result = 31 * result + (shareNum != null ? shareNum.hashCode() : 0);
        result = 31 * result + (applyNum != null ? applyNum.hashCode() : 0);
        result = 31 * result + (salesNum != null ? salesNum.hashCode() : 0);
        result = 31 * result + (salesAmount != null ? salesAmount.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DataCount{" +
                "targetId='" + targetId + '\'' +
                ", viewNum=" + viewNum +
                ", shareNum=" + shareNum +
                ", applyNum=" + applyNum +
                ", salesNum=" + salesNum +
                ", salesAmount=" + salesAmount +
                '}';
    }
}
