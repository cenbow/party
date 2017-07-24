package com.party.core.model.store;

import java.io.Serializable;
import java.util.Date;

/**
 * 商铺统计实体
 * party
 * Created by wei.li
 * on 2016/10/11 0011.
 */
public class StoreCount implements Serializable {

    private static final long serialVersionUID = -7105950696298896739L;
    //编号
    private String id;

    //商户商品编号
    private String storeGoodsId;

    //商品编号
    private String goodsId;

    //会员编号
    private String memberId;

    //商品类型（0：商品，1：活动）
    private Integer goodsType;

    //创建时间
    private Date createDate;

    //商品销售量
    private Integer salesNum;

    //商品分享量
    private Integer shareNum;

    //商品浏览量
    private Integer viewNum;

    //商品销售金额
    private Float salesAmount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStoreGoodsId() {
        return storeGoodsId;
    }

    public void setStoreGoodsId(String storeGoodsId) {
        this.storeGoodsId = storeGoodsId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StoreCount that = (StoreCount) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (storeGoodsId != null ? !storeGoodsId.equals(that.storeGoodsId) : that.storeGoodsId != null) return false;
        if (goodsId != null ? !goodsId.equals(that.goodsId) : that.goodsId != null) return false;
        if (memberId != null ? !memberId.equals(that.memberId) : that.memberId != null) return false;
        if (goodsType != null ? !goodsType.equals(that.goodsType) : that.goodsType != null) return false;
        if (createDate != null ? !createDate.equals(that.createDate) : that.createDate != null) return false;
        if (salesNum != null ? !salesNum.equals(that.salesNum) : that.salesNum != null) return false;
        if (shareNum != null ? !shareNum.equals(that.shareNum) : that.shareNum != null) return false;
        if (viewNum != null ? !viewNum.equals(that.viewNum) : that.viewNum != null) return false;
        return salesAmount != null ? salesAmount.equals(that.salesAmount) : that.salesAmount == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (storeGoodsId != null ? storeGoodsId.hashCode() : 0);
        result = 31 * result + (goodsId != null ? goodsId.hashCode() : 0);
        result = 31 * result + (memberId != null ? memberId.hashCode() : 0);
        result = 31 * result + (goodsType != null ? goodsType.hashCode() : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (salesNum != null ? salesNum.hashCode() : 0);
        result = 31 * result + (shareNum != null ? shareNum.hashCode() : 0);
        result = 31 * result + (viewNum != null ? viewNum.hashCode() : 0);
        result = 31 * result + (salesAmount != null ? salesAmount.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "StoreCount{" +
                "id='" + id + '\'' +
                ", storeGoodsId='" + storeGoodsId + '\'' +
                ", goodsId='" + goodsId + '\'' +
                ", memberId='" + memberId + '\'' +
                ", goodsType=" + goodsType +
                ", createDate=" + createDate +
                ", salesNum=" + salesNum +
                ", shareNum=" + shareNum +
                ", viewNum=" + viewNum +
                ", salesAmount=" + salesAmount +
                '}';
    }
}
