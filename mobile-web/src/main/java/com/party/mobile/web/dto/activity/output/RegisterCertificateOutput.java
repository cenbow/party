package com.party.mobile.web.dto.activity.output;

import com.alibaba.fastjson.annotation.JSONField;
import com.party.core.model.activity.Activity;
import com.party.mobile.web.dto.member.output.CreateByOutput;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.List;

/**
 * 活动报名凭证输出视图
 * party
 * Created by Wesley
 * on 2016/11/3 .
 */
public class RegisterCertificateOutput extends CreateByOutput {

    //活动核销码
    private List<String> consumerCode;

    // 名称
    private String title;

    // 封面图
    private String pic;


    //城市名称
    private String cityName;

    // 区,字符串，由后台录入
    private String area;


    // 开始时间
    @JSONField(format = "yyyy-MM-dd HH:mm")
    private Date startTime;

    // 结束时间
    @JSONField(format = "yyyy-MM-dd HH:mm")
    private Date endTime;

    // 活动场所
    private String place;

    //报名编号
    private String meberActId;

    //报名费
    private Float price;

    //活动提供方名字
    private String thirdPartyName;

    //活动提供方电话
    private String phone;

    public List<String> getConsumerCode() {
        return consumerCode;
    }

    public void setConsumerCode(List<String> consumerCode) {
        this.consumerCode = consumerCode;
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

    public String getMeberActId() {
        return meberActId;
    }

    public void setMeberActId(String meberActId) {
        this.meberActId = meberActId;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getThirdPartyName() {
        return thirdPartyName;
    }

    public void setThirdPartyName(String thirdPartyName) {
        this.thirdPartyName = thirdPartyName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 活动转活动凭证输出视图
     * @param activity 活动实体
     * @return 活动凭证输出视图
     */
    public static RegisterCertificateOutput transform(Activity activity){
        RegisterCertificateOutput registerCertificateOutput = new RegisterCertificateOutput();
        BeanUtils.copyProperties(activity, registerCertificateOutput);
        return registerCertificateOutput;
    }
}
