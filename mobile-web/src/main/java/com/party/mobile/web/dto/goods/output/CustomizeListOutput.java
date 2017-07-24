package com.party.mobile.web.dto.goods.output;

import com.party.core.model.goods.Goods;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * 定制商品列表输出视图
 * party
 * Created by Wesley
 * on 2016/11/1
 */
public class CustomizeListOutput {

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

    //最小成团人数
    private Integer minnum;

    //最大成团人数
    private Integer maxnum;

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

    public Integer getMinnum() {
        return minnum;
    }

    public void setMinnum(Integer minnum) {
        this.minnum = minnum;
    }

    public Integer getMaxnum() {
        return maxnum;
    }

    public void setMaxnum(Integer maxnum) {
        this.maxnum = maxnum;
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
     * 转换为商品输出视图
     * @param goods 商品实体
     * @return 商品输出视图
     */
    public static CustomizeListOutput transform(Goods goods){
        CustomizeListOutput listOutput = new CustomizeListOutput();
        BeanUtils.copyProperties(goods, listOutput);
        return listOutput;
    }
}
