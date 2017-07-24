package com.party.mobile.web.dto.partner.output;

import com.alibaba.fastjson.annotation.JSONField;
import com.party.core.model.activity.Activity;
import com.party.mobile.web.dto.activity.output.ActivityOutput;
import com.party.mobile.web.dto.member.output.CreateByOutput;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * 合作商获取商品详情
 * party
 * Created by wei.li
 * on 2016/10/24 0024.
 */
public class GetDetailsOutput extends CreateByOutput {

    //主键
    private String id;

    // 活动类型
    private Integer activityType;

    // 名称
    private String title;

    // 封面图
    private String pic;

    // 城市ID
    private String cityId;

    //城市名称
    private String cityName;

    // 区,字符串，由后台录入
    private String area;

    // 人数上限
    private Integer limitNum;

    // 开始时间
    @JSONField(format = "yyyy-MM-dd HH:mm")
    private Date startTime;

    // 结束时间
    @JSONField(format = "yyyy-MM-dd HH:mm")
    private Date endTime;

    //创建时间
    @JSONField(format = "yyyy-MM-dd HH:mm")
    private Date createDate;

    // 活动场所
    private String place;

    // 经度
    private String lng;

    // 纬度
    private String lat;

    // 报名开启
    private Integer isOpen;

    // 持邀请码参与
    private Integer inviteOnly;

    // 邀请码
    private String inviteCode;

    // 隐藏报名人员
    private Integer joinHidden;

    // 其他要填的选项
    private String extra;

    // 会员ID
    private String member;

    //活动详情
    private String content;

    //商品浏览量
    private Integer viewNum;

    //价格
    private Float price;

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getViewNum() {
        return viewNum;
    }

    public void setViewNum(Integer viewNum) {
        this.viewNum = viewNum;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getActivityType() {
        return activityType;
    }

    public void setActivityType(Integer activityType) {
        this.activityType = activityType;
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

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
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

    public Integer getLimitNum() {
        return limitNum;
    }

    public void setLimitNum(Integer limitNum) {
        this.limitNum = limitNum;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
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

    public Integer getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Integer isOpen) {
        this.isOpen = isOpen;
    }

    public Integer getInviteOnly() {
        return inviteOnly;
    }

    public void setInviteOnly(Integer inviteOnly) {
        this.inviteOnly = inviteOnly;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public Integer getJoinHidden() {
        return joinHidden;
    }

    public void setJoinHidden(Integer joinHidden) {
        this.joinHidden = joinHidden;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 活动转输出视图
     * @param activity 活动实体
     * @return 活动输出视图
     */
    public static GetDetailsOutput transform(Activity activity){
        GetDetailsOutput getDetailsOutput = new GetDetailsOutput();
        BeanUtils.copyProperties(activity, getDetailsOutput);
        return getDetailsOutput;
    }
}
