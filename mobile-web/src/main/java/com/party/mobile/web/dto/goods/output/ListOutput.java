package com.party.mobile.web.dto.goods.output;

import com.party.core.model.goods.Goods;
import org.springframework.beans.BeanUtils;

/**
 * 标准商品列表输出视图
 * party
 * Created by wei.li
 * on 2016/9/28 0028.
 */
public class ListOutput {

    //商品编号
    private String id;

    //标题
    private String title;

    // 封面图片
    private String picsURL;

    // 城市名
    private String cityName;

    // 区
    private String area;

    // 集合地点
    private String place;

    // 价格
    private Float price;

    //商品状态：是否正在进行
    private Integer isInProgress;

    //分享链接
    private String shareLink;

    //商品分类字段
    private String category;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPicsURL() {
        return picsURL;
    }

    public void setPicsURL(String picsURL) {
        this.picsURL = picsURL;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Integer getIsInProgress() {
        return isInProgress;
    }

    public void setIsInProgress(Integer isInProgress) {
        this.isInProgress = isInProgress;
    }

    public String getShareLink() {
        return shareLink;
    }

    public void setShareLink(String shareLink) {
        this.shareLink = shareLink;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * 转换为标准商品输出视图
     * @param goods 商品实体
     * @return 商品输出视图
     */
    public static ListOutput transform(Goods goods){
        ListOutput listOutput = new ListOutput();
        BeanUtils.copyProperties(goods, listOutput);
        return listOutput;
    }
}
