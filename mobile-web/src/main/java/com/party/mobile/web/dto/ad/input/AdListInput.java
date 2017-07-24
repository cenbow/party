package com.party.mobile.web.dto.ad.input;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 广告列表输入视图
 * party
 * Created by Wesley
 * on 2016/11/2.
 */
public class AdListInput {

    //广告位置
    @NotBlank(message = "广告位置不能为空")
    private String adPos;

    public String getAdPos() {
        return adPos;
    }

    public void setAdPos(String adPos) {
        this.adPos = adPos;
    }
}
