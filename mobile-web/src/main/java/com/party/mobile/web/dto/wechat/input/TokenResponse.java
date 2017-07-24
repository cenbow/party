package com.party.mobile.web.dto.wechat.input;

/**
 * 接口令牌响应实体
 * @author wei.li
 * @version 2016/8/3 0003
 */
public class TokenResponse {


    //获取到的凭证
    private String access_token;

    //凭证有效时间
    private String expires_in;

    //错误码
    private String errcode;

    //错误消息
    private String errmsg;


    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(String expires_in) {
        this.expires_in = expires_in;
    }

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
}
