package com.party.mobile.web.dto.sign.output;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * 签到记录输出视图
 * Created by wei.li
 *
 * @date 2017/6/12 0012
 * @time 10:42
 */
public class SignRecodListOutput {

    //编号
    private String id;

    //时间
    private Date date;

    //是否签到
    @JSONField(name = "isSign")
    private Boolean isSign;


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Boolean getSign() {
        return isSign;
    }

    public void setSign(Boolean sign) {
        isSign = sign;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
