package com.party.mobile.web.dto.wechat.input;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 长连接转短链接响应参数
 * Created by wei.li
 *
 * @date 2016/12/16 0016
 * @time 16:46
 */
public class ShortUrlResponse {

    //错误码
    private String errcode;

    //错误信息
    private String errmsg;

    //短链接
    @JSONField(name = "short_url")
    private String shortUrl;

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

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }
}
