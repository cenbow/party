package com.party.mobile.web.dto.goods;

import com.alibaba.fastjson.annotation.JSONField;
import com.party.core.model.goods.Goods;
import com.party.mobile.web.dto.goods.output.ThirdPartyOutput;
import com.party.mobile.web.dto.member.output.CreateByOutput;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * 定制商品输出视图
 * party
 * Created by Wesley
 * on 2016/11/1 .
 */
public class CustomizeOutput extends CreateByOutput {

    //商品主键
    private String id;

    //标题
    private String title;

    //城市名
    private String cityName;

    //区
    private String area;

    // 开始时间
    @JSONField(format = "yyyy-MM-dd HH:mm")
    private Date startTime;

    // 结束时间
    @JSONField(format = "yyyy-MM-dd HH:mm")
    private Date endTime;

    // 集合地点
    private String place;

    // 封面图片
    private String picsURL;

    // 经度
    private String lng;

    // 纬度
    private String lat;

    // 推荐理由
    private String recommend;

    //注意事项
    private String notice;

    //商品详情
    private String content;

    //第三方公司
    private ThirdPartyOutput thirdParty;

    //当前用户是否允许分销（0:：不是 1：是）
    private Integer isDistributor;

    //商品类型（0：标准，1：定制）
    private Integer type;

    //最小人数
    private Integer minnum;

    //最大人数
    private Integer maxnum;

    //商品状态：是否正在进行
    private Integer isInProgress;

    //分享链接
    private String shareLink;


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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getPicsURL() {
        return picsURL;
    }

    public void setPicsURL(String picsURL) {
        this.picsURL = picsURL;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ThirdPartyOutput getThirdParty() {
        return thirdParty;
    }

    public void setThirdParty(ThirdPartyOutput thirdParty) {
        this.thirdParty = thirdParty;
    }

    public Integer getIsDistributor() {
        return isDistributor;
    }

    public void setIsDistributor(Integer isDistributor) {
        this.isDistributor = isDistributor;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    /**
     * 商品实体转输出视图
     * @param goods 商品实体
     * @return 输出视图
     */
    public static CustomizeOutput transform(Goods goods){
        CustomizeOutput goodsOutput = new CustomizeOutput();
        BeanUtils.copyProperties(goods, goodsOutput);
        return goodsOutput;
    }
}
