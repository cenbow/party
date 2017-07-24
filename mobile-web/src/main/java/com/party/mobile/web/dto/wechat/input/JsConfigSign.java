package com.party.mobile.web.dto.wechat.input;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 微信 js 签名参数
 * party
 * Created by wei.li
 * on 2016/10/13 0013.
 */
public class JsConfigSign {

    //随机数
    private String noncestr;

    //时间戳
    private String timestamp;

    //当前网页的URL
    private String url;

    //交互 ticket
    @XStreamAlias(value = "jsapi_ticket")
    private String ticket;

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    @Override
    public String toString() {
        return "JsConfigSign{" +
                "noncestr='" + noncestr + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", url='" + url + '\'' +
                ", ticket='" + ticket + '\'' +
                '}';
    }
}
