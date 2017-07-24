package com.party.mobile.web.dto.wechat.input;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 获取js ticket 请求参数
 * party
 * Created by wei.li
 * on 2016/10/13 0013.
 */
public class GetTicketResponse {

    //错误码
    private String errcode;

    //返回消息
    private String errmsg;

    //交互数据
    private String ticket;

    //过期时间
    @JSONField(name = "expires_in")
    private String expiresIn;

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(String expiresIn) {
        this.expiresIn = expiresIn;
    }
}
