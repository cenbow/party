package com.party.mobile.web.dto.store.input;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * 添加到店铺输入视图
 * party
 * Created by wei.li
 * on 2016/10/10 0010.
 */
public class AddToStoreInput {

    //商品类型（0：商品，1：活动，2:文章）
    @NotNull(message = "商品类型不能为空")
    private Integer type;

    //商品（活动）编号
    @NotBlank(message = "商品编号不能为空")
    private String goodsId;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }
}
