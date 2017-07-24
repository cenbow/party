package com.party.core.model.store;

import com.party.core.model.BaseModel;

/**
 * 商铺商品实体
 * party
 * Created by wei.li
 * on 2016/10/10 0010.
 */
public class StoreGoods extends BaseModel{


    private static final long serialVersionUID = -8866122522805752842L;

    //会员编号
    private String memberId;

    //用户编号
    private String userId;

    //商品编号
    private String goodsId;

    //商品类型（0：商品，1：活动, 2:文章）
    private Integer goodsType;

    //商品销售量
    private Integer salesNum;

    //商品分享量
    private Integer shareNum;

    //商品浏览量
    private Integer viewNum;

    //报名数
    private Integer applyNum;

    //商品销售金额
    private Float salesAmount;

    //是否是私有产品（0：否，1是）
    private Integer isPrivate;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(Integer isPrivate) {
        this.isPrivate = isPrivate;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public Integer getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(Integer goodsType) {
        this.goodsType = goodsType;
    }

    public Integer getSalesNum() {
        return salesNum;
    }

    public void setSalesNum(Integer salesNum) {
        this.salesNum = salesNum;
    }

    public Integer getShareNum() {
        return shareNum;
    }

    public void setShareNum(Integer shareNum) {
        this.shareNum = shareNum;
    }

    public Integer getViewNum() {
        return viewNum;
    }

    public void setViewNum(Integer viewNum) {
        this.viewNum = viewNum;
    }

    public Float getSalesAmount() {
        return salesAmount;
    }

    public void setSalesAmount(Float salesAmount) {
        this.salesAmount = salesAmount;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getApplyNum() {
        return applyNum;
    }

    public void setApplyNum(Integer applyNum) {
        this.applyNum = applyNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        StoreGoods that = (StoreGoods) o;

        if (memberId != null ? !memberId.equals(that.memberId) : that.memberId != null) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (goodsId != null ? !goodsId.equals(that.goodsId) : that.goodsId != null) return false;
        if (goodsType != null ? !goodsType.equals(that.goodsType) : that.goodsType != null) return false;
        if (salesNum != null ? !salesNum.equals(that.salesNum) : that.salesNum != null) return false;
        if (shareNum != null ? !shareNum.equals(that.shareNum) : that.shareNum != null) return false;
        if (viewNum != null ? !viewNum.equals(that.viewNum) : that.viewNum != null) return false;
        if (applyNum != null ? !applyNum.equals(that.applyNum) : that.applyNum != null) return false;
        if (salesAmount != null ? !salesAmount.equals(that.salesAmount) : that.salesAmount != null) return false;
        return isPrivate != null ? isPrivate.equals(that.isPrivate) : that.isPrivate == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (memberId != null ? memberId.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (goodsId != null ? goodsId.hashCode() : 0);
        result = 31 * result + (goodsType != null ? goodsType.hashCode() : 0);
        result = 31 * result + (salesNum != null ? salesNum.hashCode() : 0);
        result = 31 * result + (shareNum != null ? shareNum.hashCode() : 0);
        result = 31 * result + (viewNum != null ? viewNum.hashCode() : 0);
        result = 31 * result + (applyNum != null ? applyNum.hashCode() : 0);
        result = 31 * result + (salesAmount != null ? salesAmount.hashCode() : 0);
        result = 31 * result + (isPrivate != null ? isPrivate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "StoreGoods{" +
                "memberId='" + memberId + '\'' +
                ", userId='" + userId + '\'' +
                ", goodsId='" + goodsId + '\'' +
                ", goodsType=" + goodsType +
                ", salesNum=" + salesNum +
                ", shareNum=" + shareNum +
                ", viewNum=" + viewNum +
                ", applyNum=" + applyNum +
                ", salesAmount=" + salesAmount +
                ", isPrivate=" + isPrivate +
                '}';
    }
}
