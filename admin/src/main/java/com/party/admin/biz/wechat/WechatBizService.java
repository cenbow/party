package com.party.admin.biz.wechat;

import com.alibaba.fastjson.JSONObject;
import com.party.admin.web.dto.input.wechat.GetTicketResponse;
import com.party.common.constant.WechatConstant;
import com.party.common.utils.wechat.WechatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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
}
