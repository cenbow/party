package com.party.mobile.biz.wechat;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Charsets;
import com.party.common.redis.StringJedis;
import com.party.core.model.YesNoStatus;
import com.party.core.model.member.MemberMerchant;
import com.party.mobile.web.dto.wechat.input.AccessTokenResponse;
import com.party.mobile.web.dto.wechat.input.GetTicketResponse;
import com.party.mobile.web.dto.wechat.input.ShortUrlResponse;
import com.party.mobile.web.dto.wechat.input.UserInfoResponse;
import com.party.mobile.web.dto.wechat.output.ShortUrlRequest;
import com.party.common.constant.WechatConstant;
import com.party.common.utils.wechat.WechatUtils;

/**
 * 微信服务接口
 * party
 * Created by wei.li
 * on 2016/9/24 0024.
 */
@Service
public class WechatBizService {

    //公众号编号
	@Value("#{wechat['wechat.wwz.appid']}")
    private String appid;

    //微信密钥
    @Value("#{wechat['wechat.wwz.secret']}")
    private String secret;
    
    //小程序编号
    @Value("#{wechat['wechat.xcx.appid']}")
    private String xcxappid;

    //小程序密钥
    @Value("#{wechat['wechat.xcx.secret']}")
    private String xcxsecret;

    protected static Logger logger = LoggerFactory.getLogger(WechatBizService.class);

    //appid 请求参数
    public static final String APPID = "APPID";

    //重定向路径请求参数
    public static final String REDIRECT_URI = "REDIRECT_URI";

    //应用授权作用域
    public static final String SCOPE = "SCOPE";

    //微信公众平台秘钥
    public static final String SECRET = "SECRET";

    //微信授权会话码
    public static final String CODE = "CODE";

    //微信接口凭证
    public static final String ACCESS_TOKEN = "ACCESS_TOKEN";

    //微信公众号编号
    public static final String OPENID = "OPENID";

    @Autowired
    StringJedis stringJedis;

    /**
     * 生成微信授权url
     * @param redirectUri 重定向url
     * @return 授权url
     * @throws UnsupportedEncodingException
     */
    public String getOathorizeUrl(String redirectUri) throws UnsupportedEncodingException {
        redirectUri = URLEncoder.encode(redirectUri, Charsets.UTF_8.name());
        String url = WechatConstant.OATHORIZE_URL.replace(APPID, appid)
                .replace(REDIRECT_URI, redirectUri)
                .replace(SCOPE, WechatConstant.SCOPE_USERINFO);

        return url;
    }
    
    /**
     * 生成微信授权url（普通授权）
     * @param redirectUri 重定向url
     * @param memberMerchant 
     * @return 授权url
     * @throws UnsupportedEncodingException
     */
    public String getBaseOathorizeUrl(String redirectUri, MemberMerchant memberMerchant) throws UnsupportedEncodingException {
        redirectUri = URLEncoder.encode(redirectUri, Charsets.UTF_8.name());
        String url = "";
        // 获取绑定商户
        if (memberMerchant != null && memberMerchant.getOpenStatus().equals(YesNoStatus.YES.getCode())) {
        	String appId = memberMerchant.getOfficialAccountId();
        	url = WechatConstant.OATHORIZE_URL.replace(APPID, appId)
                    .replace(REDIRECT_URI, redirectUri)
                    .replace(SCOPE, WechatConstant.SCOPE_USERINFO);
		} else {
			url = WechatConstant.OATHORIZE_URL.replace(APPID, appid)
					.replace(REDIRECT_URI, redirectUri)
					.replace(SCOPE, WechatConstant.SCOPE_USERINFO);
		}
        return url;
    }


    /**
     * 获取交互凭证
     * @param code 授权code
     * @param memberMerchant 业务发布者关联的商户信息
     * @return 交互数据
     */
    public AccessTokenResponse acccessToken(String code, MemberMerchant memberMerchant){
        String url = "";
        // 获取绑定商户
        if (memberMerchant != null && memberMerchant.getOpenStatus().equals(YesNoStatus.YES.getCode())) {
			String appId = memberMerchant.getOfficialAccountId();
			String appSecret = memberMerchant.getOfficialAccountSecret();
			url = WechatConstant.ACCESS_TOKEN_URL.replace(APPID, appId)
	                .replace(SECRET, appSecret)
	                .replace(CODE, code);
		} else {
			url = WechatConstant.ACCESS_TOKEN_URL.replace(APPID, appid)
	                .replace(SECRET, secret)
	                .replace(CODE, code);
		}

        logger.info("获取微信网页授权交互凭证请求报文{}", url);
        JSONObject response = WechatUtils.httpRequest(url, WechatUtils.GET, null);

        logger.info("获取微信网页收取交互凭证响应报文{}", response);
        if (null == response){
            return null;
        }

        return JSONObject.toJavaObject(response, AccessTokenResponse.class);
    }
    
    /**
     * 获取小程序交互凭证
     * @param code 授权code
     * @return 交互数据
     */
    public JSONObject xcxAccessToken(String code){
        String url = WechatConstant.XCX_ACCESS_TOKEN_URL.replace(APPID, xcxappid)
                .replace(SECRET, xcxsecret)
                .replace(CODE, code);

        logger.info("获取小程序授权交互凭证请求报文{}", url);
        JSONObject response = WechatUtils.httpRequest(url, WechatUtils.GET, null);

        logger.info("获取小程序收取交互凭证响应报文{}", response);
        if (null == response){
            return null;
        }

        return response;
    }


    /**
     * 获取微信用户信息
     * @param openid 用户编号
     * @return 交互数据
     */
    public UserInfoResponse userInfo(String openid, String accessToken){
        String url = WechatConstant.USERINFO_URL.replace(ACCESS_TOKEN, accessToken)
                .replace(OPENID, openid);

        logger.info("获取微信用户信息请求报文{}", url);
        JSONObject response = WechatUtils.httpRequest(url, WechatUtils.GET, null);

        logger.info("获取微信用户信息响应报文{}", response);
        if (null == response){
            return null;
        }
        return JSONObject.toJavaObject(response, UserInfoResponse.class);
    }

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
     * @param longUrl 长连接
     * @param accessToken 交互令牌
     * @return 返回连接
     */
    public String longToShort(String longUrl, String accessToken){
        String url = WechatConstant.LONG_TO_SHORT_URL.replace(ACCESS_TOKEN, accessToken);
        ShortUrlRequest shorturlRequest = new ShortUrlRequest( longUrl);
        logger.info("微信长连接转短链接请求报文{}", shorturlRequest.toString());
        JSONObject jsonObject = WechatUtils.httpRequest(url, WechatUtils.POST, JSONObject.toJSONString(shorturlRequest));
        logger.info("微信长连接转短连接响应报文{}", jsonObject);

        ShortUrlResponse shortUrlResponse = jsonObject.toJavaObject(jsonObject, ShortUrlResponse.class);
        return  shortUrlResponse.getShortUrl();
    }
}
