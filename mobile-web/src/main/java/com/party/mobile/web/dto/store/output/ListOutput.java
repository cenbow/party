package com.party.mobile.web.dto.store.output;

import com.party.core.model.store.StoreGoods;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * 店铺商品输出视图
 * party
 * Created by wei.li
 * on 2016/10/10 0010.
 */
public class ListOutput {

    //商铺商品编号
    private String id;

    //商品图片
    private String pic;

    //商品标题
    private String title;

    //创建时间
    private Date createDate;

    //价格
    private Float price;

    //商品类型（0：商品，1：活动）
    private Integer type;

    //销售量
    private Integer salesNum;

    //分享量
    private Integer shareNum;

    //浏览量
    private Integer viewNum;

    //销售额
    private Float salesAmount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public static ListOutput transform(StoreGoods storeGoods){
        ListOutput listOutput = new ListOutput();
        BeanUtils.copyProperties(storeGoods, listOutput);
        return listOutput;
    }
}
