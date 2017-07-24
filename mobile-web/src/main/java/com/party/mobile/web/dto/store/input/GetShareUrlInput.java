package com.party.mobile.web.dto.store.input;


import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * 获取分享连接输入视图
 * party
 * Created by wei.li
 * on 2016/10/10 0010.
 */
public class GetShareUrlInput {

    //商品类型（0：商品，1：活动）
    @NotNull(message = "商品类型不能为空")
    private Integer type;

    //商品（活动）编号
    @NotBlank(message = "商户商品编号不能为空")
    private String id;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
