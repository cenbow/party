package com.party.mobile.web.dto.wechat.output;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 微信长连接转短链接
 * Created by wei.li
 *
 * @date 2016/12/16 0016
 * @time 16:41
 */
public class ShortUrlRequest {

    //此处填long2short
    private String action;

    //需要转换的长链接
    @JSONField(name = "long_url")
    private String longUrl;

    private static final String ACTION_VALUE = "long2short";

    public ShortUrlRequest(String longUrl) {
        this.longUrl = longUrl;
        this.action = ACTION_VALUE;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

    @Override
    public String toString() {
        return "{" +
                "\"action\":" +"\""+ action + "\"," +
                "\"long_url\":" + "\""+longUrl + "\"," +
                '}';
    }
}
