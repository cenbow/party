package com.party.core.model.distributor;

import com.party.core.model.BaseModel;

import java.util.Date;

/**
 * 分销关系实体
 * Created by wei.li
 *
 * @date 2017/3/1 0001
 * @time 12:14
 */
public class DistributorRelation extends BaseModel{
    private static final long serialVersionUID = -3315207788443060077L;

    //分销关系类型(0:标准商品， 1：定制商品， 2：活动， 3：文章， 4： 众筹)
    private Integer type;

    //分销对象编号
    private String targetId;

    //分销者编号
    private String distributorId;

    //被分销者编号
    private String parentId;

    //题目
    private String title;

    //图片
    private String pic;

    //价格
    private Float price;

    //目标结束时间
    private Date targetEndDate;



    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getDistributorId() {
        return distributorId;
    }

    public void setDistributorId(String distributorId) {
        this.distributorId = distributorId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Date getTargetEndDate() {
        return targetEndDate;
    }

    public void setTargetEndDate(Date targetEndDate) {
        this.targetEndDate = targetEndDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        DistributorRelation that = (DistributorRelation) o;

        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (targetId != null ? !targetId.equals(that.targetId) : that.targetId != null) return false;
        if (distributorId != null ? !distributorId.equals(that.distributorId) : that.distributorId != null)
            return false;
        if (parentId != null ? !parentId.equals(that.parentId) : that.parentId != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (pic != null ? !pic.equals(that.pic) : that.pic != null) return false;
        if (price != null ? !price.equals(that.price) : that.price != null) return false;
        return targetEndDate != null ? targetEndDate.equals(that.targetEndDate) : that.targetEndDate == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (targetId != null ? targetId.hashCode() : 0);
        result = 31 * result + (distributorId != null ? distributorId.hashCode() : 0);
        result = 31 * result + (parentId != null ? parentId.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (pic != null ? pic.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (targetEndDate != null ? targetEndDate.hashCode() : 0);
        return result;
    }
}
