package com.party.admin.biz.openPlatform;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.party.admin.web.dto.output.openPlatform.WechatAuthorizerInfo;
import com.party.admin.web.dto.output.openPlatform.WechatAuthorizerUserInfo;
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
        String requestUrlFormat = "https://api.weixin.qq.com/cgi-bin/component/api_component_token";
        Map<String, Object> requestMap = Maps.newHashMap();
        requestMap.put("component_appid", appid); // 第三方平台appid
        requestMap.put("component_appsecret", appsecret); //第三方平台appsecret
        requestMap.put("component_verify_ticket", verifyTicket); // 微信后台推送的ticket
        String requestJson = JSONObject.toJSONString(requestMap);
        logger.info("获取第三方平台component_access_token 请求参数{}", requestJson);
        String responseData = WechatPayUtils.httpsPost(requestUrlFormat, requestJson);
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
        String requestUrlFormat = "https://api.weixin.qq.com/cgi-bin/component/api_create_preauthcode?component_access_token={0}";
        Map<String, Object> requestMap = Maps.newHashMap();
        requestMap.put("component_appid", appid);
        String requestJson = JSONObject.toJSONString(requestMap);
        logger.info("获取获取预授权码pre_auth_code 请求参数{}", requestJson);
        String requestUrl = requestUrlFormat.replace("{0}", accessToken);
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
     * 获取授权URL
     *
     * @param redirectUrl    重定向地址
     * @param componentAppid 第三方平台appid
     * @param authCode       预授权码
     * @return
     */
    public String getAuthorizeUrl(String redirectUrl, String componentAppid, String authCode) throws UnsupportedEncodingException {
        String requestUrlFormat = "https://mp.weixin.qq.com/cgi-bin/componentloginpage?component_appid=APPID&pre_auth_code=AUTH_CODE&redirect_uri=REDIRECT_URI";
        redirectUrl = URLEncoder.encode(redirectUrl, Constant.UTF_8);
        authCode = URLEncoder.encode(authCode, Constant.UTF_8);
        String authorizationUrl = requestUrlFormat.replace("APPID", componentAppid)
                .replace("AUTH_CODE", authCode)
                .replace("REDIRECT_URI", redirectUrl);
        return authorizationUrl;
    }

    /**
     * 获取授权信息
     *
     * @param componentAppid       第三方平台appid
     * @param componentAccessToken 第三方平台access_token
     * @param authorizationCode    授权code,会在授权成功时返回给第三方平台
     * @return
     */
    public WechatAuthorizerInfo getAuthorizationInfo(String componentAppid, String componentAccessToken, String authorizationCode) {
//        String requestUrlFormat = "https://api.weixin.qq.com/cgi-bin/component/api_query_auth?component_access_token={0}";
//        Map<String, Object> requestMap = Maps.newHashMap();
//        requestMap.put("component_appid", componentAppid);
//        requestMap.put("authorization_code", authorizationCode);
//        String requestJson = JSONObject.toJSONString(requestMap);
//        logger.info("授权 请求参数{}", requestJson);
//        String requestUrl = requestUrlFormat.replace("{0}", componentAccessToken);
//        String responseData = WechatPayUtils.httpsPost(requestUrl, requestJson);
//        logger.info("授权 响应参数{}", responseData);
        String responseData = "{ \n" +
                "\t\"authorization_info\": {\n" +
                "\t\t\"authorizer_appid\": \"wxf8b4f85f3a794e77\", \n" +
                "\t\t\"authorizer_access_token\": \"QXjUqNqfYVH0yBE1iI_7vuN_9gQbpjfK7hYwJ3P7xOa88a89-Aga5x1NMYJyB8G2yKt1KCl0nPC3W9GJzw0Zzq_dBxc8pxIGUNi_bFes0qM\", \n" +
                "\t\t\"expires_in\": 7200, \n" +
                "\t\t\"authorizer_refresh_token\": \"dTo-YCXPL4llX-u1W1pPpnp8Hgm4wpJtlR6iV0doKdY\", \n" +
                "\t\t\"func_info\": [\n" +
                "\t\t\t{\"funcscope_category\": {\"id\": 1}}, \n" +
                "\t\t\t{\"funcscope_category\": {\"id\": 2}}, \n" +
                "\t\t\t{\"funcscope_category\": {\"id\": 3}}\n" +
                "\t\t]\n" +
                "\t}\n" +
                "}";
        if (StringUtils.isNotEmpty(responseData)) {
            WechatAuthorizerInfo authorizationInfo = JSONObject.parseObject(responseData, WechatAuthorizerInfo.class);
            System.out.println(JSONObject.toJSON(authorizationInfo));
            return authorizationInfo;
        }
        return null;
    }

    /**
     * 获取授权方的帐号基本信息
     *
     * @param componentAppid       服务appid
     * @param authorizerAppid      授权方appid
     * @param componentAccessToken 第三方平台access_token
     */
    public WechatAuthorizerUserInfo getAuthorizerInfo(String componentAppid, String authorizerAppid, String componentAccessToken) {
//        String requestUrlFormat = "https://api.weixin.qq.com/cgi-bin/component/api_get_authorizer_info?component_access_token={0}";
//        Map<String, Object> params = Maps.newHashMap();
//        params.put("component_appid", componentAppid);
//        params.put("authorizer_appid", authorizerAppid);
//        String requestData = JSONObject.toJSONString(params);
//        String requestUrl = requestUrlFormat.replace("{0}", componentAccessToken);
//        logger.info("获取授权方的帐号基本信息 请求参数{}", requestData);
//        String responseData = WechatPayUtils.httpsPost(requestUrl, requestData);
//        logger.info("获取授权方的帐号基本信息 响应参数{}", responseData);
        String responseData = "{\n" +
                "    \"authorizer_info\":{\n" +
                "        \"nick_name\":\"微信SDK Demo Special\",\n" +
                "        \"head_img\":\"http://wx.qlogo.cn/mmopen/GPy\",\n" +
                "        \"service_type_info\":{\n" +
                "            \"id\":2\n" +
                "        },\n" +
                "        \"verify_type_info\":{\n" +
                "            \"id\":0\n" +
                "        },\n" +
                "        \"user_name\":\"gh_eb5e3a772040\",\n" +
                "        \"principal_name\":\"腾讯计算机系统有限公司\",\n" +
                "        \"business_info\":{\n" +
                "            \"open_store\":0,\n" +
                "            \"open_scan\":0,\n" +
                "            \"open_pay\":0,\n" +
                "            \"open_card\":0,\n" +
                "            \"open_shake\":0\n" +
                "        },\n" +
                "        \"alias\":\"paytest01\",\n" +
                "        \"qrcode_url\":\"URL\"\n" +
                "    },\n" +
                "    \"authorization_info\":{\n" +
                "        \"appid\":\"wxf8b4f85f3a794e77\",\n" +
                "        \"func_info\":[\n" +
                "            {\n" +
                "                \"funcscope_category\":{\n" +
                "                    \"id\":1\n" +
                "                }\n" +
                "            },\n" +
                "            {\n" +
                "                \"funcscope_category\":{\n" +
                "                    \"id\":2\n" +
                "                }\n" +
                "            },\n" +
                "            {\n" +
                "                \"funcscope_category\":{\n" +
                "                    \"id\":3\n" +
                "                }\n" +
                "            }\n" +
                "        ]\n" +
                "    }\n" +
                "}";

        responseData = "{\n" +
                "    \"authorizer_info\":{\n" +
                "        \"nick_name\":\"微信SDK Demo Special\",\n" +
                "        \"head_img\":\"http://wx.qlogo.cn/mmopen/GPy\",\n" +
                "        \"service_type_info\":{\n" +
                "            \"id\":2\n" +
                "        },\n" +
                "        \"verify_type_info\":{\n" +
                "            \"id\":0\n" +
                "        },\n" +
                "        \"user_name\":\"gh_eb5e3a772040\",\n" +
                "        \"principal_name\":\"腾讯计算机系统有限公司\",\n" +
                "        \"business_info\":{\n" +
                "            \"open_store\":0,\n" +
                "            \"open_scan\":0,\n" +
                "            \"open_pay\":0,\n" +
                "            \"open_card\":0,\n" +
                "            \"open_shake\":0\n" +
                "        },\n" +
                "        \"qrcode_url\":\"URL\",\n" +
                "        \"signature\":\"时间的水缓缓流去\",\n" +
                "        \"MiniProgramInfo\":{\n" +
                "            \"network\":{\n" +
                "                \"RequestDomain\":[\n" +
                "                    \"https://www.qq.com\",\n" +
                "                    \"https://www.qq.com\"\n" +
                "                ],\n" +
                "                \"WsRequestDomain\":[\n" +
                "                    \"wss://www.qq.com\",\n" +
                "                    \"wss://www.qq.com\"\n" +
                "                ],\n" +
                "                \"UploadDomain\":[\n" +
                "                    \"https://www.qq.com\",\n" +
                "                    \"https://www.qq.com\"\n" +
                "                ],\n" +
                "                \"DownloadDomain\":[\n" +
                "                    \"https://www.qq.com\",\n" +
                "                    \"https://www.qq.com\"\n" +
                "                ]\n" +
                "            },\n" +
                "            \"categories\":[\n" +
                "                {\n" +
                "                    \"first\":\"资讯\",\n" +
                "                    \"second\":\"文娱\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"first\":\"工具\",\n" +
                "                    \"second\":\"天气\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"visit_status\":0\n" +
                "        }\n" +
                "    },\n" +
                "    \"authorization_info\":{\n" +
                "        \"appid\":\"wxf8b4f85f3a794e77\",\n" +
                "        \"func_info\":[\n" +
                "            {\n" +
                "                \"funcscope_category\":{\n" +
                "                    \"id\":17\n" +
                "                }\n" +
                "            },\n" +
                "            {\n" +
                "                \"funcscope_category\":{\n" +
                "                    \"id\":18\n" +
                "                }\n" +
                "            },\n" +
                "            {\n" +
                "                \"funcscope_category\":{\n" +
                "                    \"id\":19\n" +
                "                }\n" +
                "            }\n" +
                "        ]\n" +
                "    }\n" +
                "}";
        if (StringUtils.isNotEmpty(responseData)) {
            WechatAuthorizerUserInfo authorizerUserInfo = JSONObject.parseObject(responseData, WechatAuthorizerUserInfo.class);
            System.out.print(JSONObject.toJSONString(authorizerUserInfo));
            return authorizerUserInfo;
        }
        return null;
    }
}
