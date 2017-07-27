package com.party.admin.biz.openPlatform;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.party.common.constant.Constant;
import com.party.common.utils.StringUtils;
import com.party.common.utils.refund.WechatPayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * 开放平台的第三方平台授权业务
 */
@Service
public class AuthorizationBizService {

    // 获取第三方平台component_access_token
    private String GET_COMPONENT_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/component/api_component_token";
    // 获取预授权码pre_auth_code
    private String GET_PRE_AUTH_CODE_URL = "https://api.weixin.qq.com/cgi-bin/component/api_create_preauthcode?component_access_token=ACCESS_TOKEN";
    // 授权
    private String AUTH_URL = "https://api.weixin.qq.com/cgi-bin/component/api_query_auth?component_access_token=ACCESS_TOKEN";
    // 授权页面
    private static final String AUTORIZATION_URL = "https://mp.weixin.qq.com/cgi-bin/componentloginpage?component_appid=APPID&pre_auth_code=AUTH_CODE&redirect_uri=REDIRECT_URI";

    protected static Logger logger = LoggerFactory.getLogger(AuthorizationBizService.class);

    /**
     * 2、获取第三方平台component_access_token
     *
     * @param appid        第三方平台appid
     * @param appsecret    第三方平台appsecret
     * @param verifyTicket 微信后台推送的ticket，此ticket会定时推送
     * @return 第三方平台access_token
     */
    public String getAccessToken(String appid, String appsecret, String verifyTicket) {
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
     * 获取授权信息
     *
     * @param appid       第三方平台appid
     * @param accessToken 第三方平台access_token
     * @param authCode    预授权码
     * @return
     */
    public Map<String, String> getAuthorizationInfo(String appid, String accessToken, String authCode) {
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
            Map<String, String> infos = (Map<String, String>) responseMap.get("authorization_info");
            return infos;
        }
        return null;
    }

    /**
     * 获取授权URL
     *
     * @param redirectUrl    重定向地址
     * @param componentAppid 第三方平台appid
     * @param authCode       预授权码
     * @return
     */
    public String getAuthorizeUrl(String redirectUrl, String componentAppid, String authCode) throws UnsupportedEncodingException {
        redirectUrl = URLEncoder.encode(redirectUrl, Constant.UTF_8);
        authCode = URLEncoder.encode(authCode, Constant.UTF_8);
        String authorizationUrl = AUTORIZATION_URL.replace("APPID", componentAppid)
                .replace("AUTH_CODE", authCode)
                .replace("REDIRECT_URI", redirectUrl);
        return authorizationUrl;
    }
}
