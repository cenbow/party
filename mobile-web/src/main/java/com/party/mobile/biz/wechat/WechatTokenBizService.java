package com.party.mobile.biz.wechat;


import com.alibaba.fastjson.JSONObject;
import com.party.mobile.web.dto.wechat.input.TokenResponse;
import com.party.common.utils.wechat.WechatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 微信接口令牌service
 * @author wei.li
 * @version 2016/8/3 0003
 */

@Service
public class WechatTokenBizService {

    //微信唯一标识
	@Value("#{wechat['wechat.wwz.appid']}")
    private String appid;

	@Value("#{wechat['wechat.wwz.secret']}")
    private String appsecret;

    //获取接口交互令牌接口地址
    private static String TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

    //appid 替换参数
    private static String APPID = "APPID";

    //密钥参数
    private static String APPSECRET = "APPSECRET";

    protected static Logger logger = LoggerFactory.getLogger(WechatTokenBizService.class);


    /**
     * 获取微信接口交互令牌
     * @return 令牌
     */
    public String getToken(){
       return this.getToken(appid, appsecret);
    }

    /**
     * 获取商户交互令牌
     * @param appid appid
     * @param appsecret appsecret
     * @return 令牌
     */
    public String getToken(String appid, String appsecret){
        String url = TOKEN_URL.replace(APPID,appid).replace(APPSECRET, appsecret);
        logger.info("微信获取接口交互令牌请求数据:{}",url);
        JSONObject response = WechatUtils.httpRequest(url, WechatUtils.GET, null);
        logger.info("微信获取接口交互令牌返回数据:{}", response);
        TokenResponse tokenResponse = JSONObject.toJavaObject(response, TokenResponse.class);
        return tokenResponse.getAccess_token();
    }
}
