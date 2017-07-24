package com.party.mobile.web.dto.goods.input;

/**
 * 商品列表输入视图
 * party
 * Created by wei.li
 * on 2016/9/28 0028.
 */
public class ListInput {

    //城市编号
    private String cityId;

    //商品类型
    private Integer type;

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
