package com.party.mobile.web.dto.activity.output;

import com.party.core.model.activity.Activity;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * 活动管理输出视图
 * Created by wei.li
 *
 * @date 2017/3/28 0028
 * @time 17:50
 */
public class ManageDetailOutput {

    private String id;

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

    // 名称
    private String title;

    // 封面图
    private String pic;

    // 人数上限
    private Integer limitNum;

    //城市名
    private String cityName;

    // 开始时间
    private Date startTime;

    // 结束时间
    private Date endTime;

    // 区,字符串，由后台录入
    private String area;

    // 活动场所
    private String place;

    // 二维码
    private String qrCodeUrl;

    // 报名登记
    private String signCodeUrl;

    // 分销二维码
    private String disQrCode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
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

    public String getQrCodeUrl() {
        return qrCodeUrl;
    }

    public void setQrCodeUrl(String qrCodeUrl) {
        this.qrCodeUrl = qrCodeUrl;
    }


    public String getSignCodeUrl() {
        return signCodeUrl;
    }

    public void setSignCodeUrl(String signCodeUrl) {
        this.signCodeUrl = signCodeUrl;
    }

    public String getDisQrCode() {
        return disQrCode;
    }

    public void setDisQrCode(String disQrCode) {
        this.disQrCode = disQrCode;
    }

    public Integer getLimitNum() {
        return limitNum;
    }

    public void setLimitNum(Integer limitNum) {
        this.limitNum = limitNum;
    }

    public static ManageDetailOutput transform(Activity activity){
        ManageDetailOutput manageDetailOutput = new ManageDetailOutput();
        BeanUtils.copyProperties(activity, manageDetailOutput);
        return manageDetailOutput;
    }
}
