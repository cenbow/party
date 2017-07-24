package com.party.mobile.web.dto.wechat.input;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 网页授权access_token 响应参数
 * party
 * Created by wei.li
 * on 2016/9/24 0024.
 */
public class AccessTokenResponse {

    //网页授权接口调用凭证
    @JSONField(name = "access_token")
    private String accessToken;

    //接口调用凭证超时时间
    @JSONField(name = "expires_in")
    private String expiresIn;

    //用户刷新access_token
    @JSONField(name = "refresh_token")
    private String refreshToken;

    //用户唯一标识
    private String openid;

    //用户授权的作用域
    private String scope;


    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(String expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
