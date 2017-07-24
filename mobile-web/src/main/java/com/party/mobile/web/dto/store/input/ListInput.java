package com.party.mobile.web.dto.store.input;

/**
 * 店铺商品列表输入视图
 * party
 * Created by wei.li
 * on 2016/10/10 0010.
 */
public class ListInput {

    //商品类型（0：商品，1：活动，2:文章，不传默认为全部）
    private Integer goodsType;

    //排序方式（0：时间，1，销量，2：销售额）
    private Integer sort;

    public Integer getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(Integer goodsType) {
        this.goodsType = goodsType;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
