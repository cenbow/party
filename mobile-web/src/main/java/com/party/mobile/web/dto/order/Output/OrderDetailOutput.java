package com.party.mobile.web.dto.order.Output;

import com.party.core.model.order.OrderForm;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * 订单详情输出视图
 *
 * @Author: Wesley
 * @Description:
 * @Date: Created in 8:59 16/11/11
 * @Modified by:
 */
public class OrderDetailOutput extends OrderOutput {
    //活动城市名
    private String cityName;

    //活动区县名
    private String areaName;

    //活动地点
    private String place;

    //活动核销码
    private List<String> consumerCode;

    //活动提供方名字
    private String thirdPartyName;

    //活动提供方电话
    private String telephone;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public List<String> getConsumerCode() {
        return consumerCode;
    }

    public void setConsumerCode(List<String> consumerCode) {
        this.consumerCode = consumerCode;
    }

    public String getThirdPartyName() {
        return thirdPartyName;
    }

    public void setThirdPartyName(String thirdPartyName) {
        this.thirdPartyName = thirdPartyName;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     * 商品实体转输出视图
     * @param orderForm 商品实体
     * @return 输出视图
     */
    public static OrderDetailOutput transform(OrderForm orderForm){
        OrderDetailOutput orderDetailOutput = new OrderDetailOutput();
        BeanUtils.copyProperties(orderForm, orderDetailOutput);
        return orderDetailOutput;
    }
}
