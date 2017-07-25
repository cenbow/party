package com.party.admin.biz.openPlatform;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.party.admin.utils.openPlatform.WXBizMsgCrypt;
import com.party.common.utils.StringUtils;
import com.party.common.utils.refund.WechatPayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 开放平台的第三方平台消息业务
 */
@Service
public class NotifyBizService {
    // 获取第三方平台component_access_token
    private String GET_COMPONENT_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/component/api_component_token";
    // 获取预授权码pre_auth_code
    private String GET_PRE_AUTH_CODE_URL = "https://api.weixin.qq.com/cgi-bin/component/api_create_preauthcode?component_access_token=ACCESS_TOKEN";
    // 授权
    private String AUTH_URL = "https://api.weixin.qq.com/cgi-bin/component/api_query_auth?component_access_token=ACCESS_TOKEN";

    protected static Logger logger = LoggerFactory.getLogger(NotifyBizService.class);

    /**
     * 1.获取推送component_verify_ticket协议
     *
     * @param responseData
     * @return
     * @throws Exception
     */
    public String resolveTicket(String token, String encodingAesKey, String responseData) throws Exception {
        Map<String, String> responseMap = WechatPayUtils.xmlToMap(responseData);
        String appId = responseMap.get("AppId");
        String encrypt = responseMap.get("Encrypt");
        logger.info("开始解密");
        WXBizMsgCrypt crypt = new WXBizMsgCrypt(token, encodingAesKey, appId);
        String decrypt = crypt.decrypt(encrypt);
        logger.info("解密结果{}", decrypt);
        Map<String, String> decryptMap = WechatPayUtils.xmlToMap(decrypt);
        String componentVerifyTicket = decryptMap.get("ComponentVerifyTicket");
        return componentVerifyTicket;
    }

    /**
     * 2、获取第三方平台component_access_token
     *
     * @param appid        第三方平台appid
     * @param appsecret    第三方平台appsecret
     * @param verifyTicket 微信后台推送的ticket，此ticket会定时推送
     * @return 第三方平台access_token
     */
    public String getComponentToken(String appid, String appsecret, String verifyTicket) {
        Map<String, Object> requestMap = Maps.newHashMap();
        requestMap.put("component_appid", appid); // 第三方平台appid
        requestMap.put("component_appsecret", appsecret); //第三方平台appsecret
        requestMap.put("component_verify_ticket", verifyTicket); // 微信后台推送的ticket
        String requestJson = JSONObject.toJSONString(requestMap);
        logger.info("获取第三方平台component_access_token 请求参数{}", requestJson);
        String responseData = WechatPayUtils.httpsPost(GET_COMPONENT_TOKEN_URL, requestJson);
        logger.info("获取第三方平台component_access_token 响应参数{}", responseData);
        if (StringUtils.isNotEmpty(responseData)) {
            Map responseMap = JSONObject.parseObject(responseData, Map.class);
            String accessToken = (String) responseMap.get("component_access_token"); // 第三方平台access_token
            return accessToken;
        }
        return null;
    }

    /**
     * 3、获取预授权码pre_auth_code
     *
     * @param appid       第三方平台方appid
     * @param accessToken 第三方平台access_token
     * @return
     */
    public String getPreAuthCode(String appid, String accessToken) {
        Map<String, Object> requestMap = Maps.newHashMap();
        requestMap.put("component_appid", appid);
        String requestJson = JSONObject.toJSONString(requestMap);
        logger.info("获取获取预授权码pre_auth_code 请求参数{}", requestJson);
        String requestUrl = GET_PRE_AUTH_CODE_URL.replace("ACCESS_TOKEN", accessToken);
        String responseData = WechatPayUtils.httpsPost(requestUrl, requestJson);
        logger.info("获取获取预授权码pre_auth_code 响应参数{}", responseData);
        if (StringUtils.isNotEmpty(responseData)) {
            Map responseMap = JSONObject.parseObject(responseData, Map.class);
            String preAuthCode = (String) responseMap.get("pre_auth_code");
            return preAuthCode;
        }
        return null;
    }

    /**
     * 授权
     *
     * @param appid       第三方平台appid
     * @param accessToken 第三方平台access_token
     * @param authCode    预授权码
     * @return
     */
    public String doAuth(String appid, String accessToken, String authCode) {
        Map<String, Object> requestMap = Maps.newHashMap();
        requestMap.put("component_appid", appid);
        requestMap.put("authorization_code", authCode);
        String requestJson = JSONObject.toJSONString(requestMap);
        logger.info("授权 请求参数{}", requestJson);
        String requestUrl = AUTH_URL.replace("ACCESS_TOKEN", accessToken);
        String responseData = WechatPayUtils.httpsPost(requestUrl, requestJson);
        logger.info("授权 响应参数{}", responseData);
        if (StringUtils.isNotEmpty(responseData)) {
            Map responseMap = JSONObject.parseObject(responseData, Map.class);
        }
        return null;
    }

}
