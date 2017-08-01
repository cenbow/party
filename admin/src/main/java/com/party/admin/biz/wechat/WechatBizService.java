package com.party.admin.biz.wechat;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.party.admin.web.dto.input.wechat.GetTicketResponse;
import com.party.common.constant.WechatConstant;
import com.party.common.utils.StringUtils;
import com.party.common.utils.refund.WechatPayUtils;
import com.party.common.utils.wechat.WechatUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.party.core.model.wechat.WechatAccount;
import com.party.core.service.wechat.impl.WechatAccountService;
/**
 * 微信服务接口
 * Created by wei.li
 *
 * @date 2017/5/19 0019
 * @time 11:31
 */

@Service
public class WechatBizService {

    //微信接口凭证
    public static final String ACCESS_TOKEN = "ACCESS_TOKEN";

    @Autowired
    private WechatAccountService wechatAccountService;

    protected static Logger logger = LoggerFactory.getLogger(WechatBizService.class);

    /**
     * 获取微信 jsapi ticket
     * @param accessToken 接口交互令牌
     * @return 交互数据
     */
    public GetTicketResponse getTicket(String accessToken){
        String url = WechatConstant.GET_TICKET_URL.replace(ACCESS_TOKEN, accessToken);
        logger.info("获取 js api ticket 请求报文{}", url);
        JSONObject response = WechatUtils.httpRequest(url, WechatUtils.GET, null);

        logger.info("获取微信 js api ticket 响应报文{}", response);
        if (null == response){
            return null;
        }
        return JSONObject.toJavaObject(response, GetTicketResponse.class);
    }

    /**
     * 微信长连接转短连接
     *
     * @param longUrl     长连接
     * @param accessToken 交互令牌
     * @return 返回连接
     */
    public String longToShort(String longUrl, String accessToken) {
        String url = WechatConstant.LONG_TO_SHORT_URL.replace(ACCESS_TOKEN, accessToken);
        Map<String, Object> requestMap = Maps.newHashMap();
        requestMap.put("access_token", accessToken);
        requestMap.put("action", "long2short");
        requestMap.put("long_url", longUrl);
        String requestData = JSONObject.toJSONString(requestMap);
        logger.info("微信长连接转短链接请求报文{}", requestData);
        String responseData = WechatPayUtils.httpsPost(url, requestData);
        logger.info("微信长连接转短连接响应报文{}", responseData);

        if (StringUtils.isNotEmpty(responseData)) {
            Map<String, String> responseMap = JSONObject.parseObject(responseData, Map.class);
            return responseMap.get("short_url");
        }
        return null;
    }

    /**
     * 长连接转短连接
     *
     * @param longUrl
     * @return
     */
    public String longToShort(String longUrl) {
        WechatAccount wechatAccount = wechatAccountService.getSystem();
        return longToShort(longUrl, wechatAccount.getToken());
    }
}
