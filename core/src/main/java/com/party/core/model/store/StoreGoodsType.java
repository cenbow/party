package com.party.core.model.store;

/**
 * 商铺商品类型枚举
 * party
 * Created by wei.li
 * on 2016/10/11 0011.
 */
public enum StoreGoodsType {

    GOODS(0, "商品"),

    ACTIVITY(1, "活动"),

    ARTICLE(2, "文章");

    //状态码
    private Integer code;

    //状态值
    private String value;

    StoreGoodsType(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
