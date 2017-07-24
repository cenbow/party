package com.party.mobile.web.controller.wechat;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.party.common.redis.StringJedis;
import com.party.core.model.YesNoStatus;
import com.party.core.model.member.MemberMerchant;
import com.party.core.model.wechat.WechatAccount;
import com.party.core.service.member.IMemberMerchantService;
import com.party.core.service.wechat.impl.WechatAccountService;
import com.party.mobile.biz.currentUser.CurrentUserBizService;
import com.party.mobile.biz.member.MemberBizService;
import com.party.mobile.biz.wechat.WechatBizService;
import com.party.mobile.web.dto.AjaxResult;
import com.party.mobile.web.dto.wechat.input.AccessTokenResponse;
import com.party.mobile.web.dto.wechat.input.JsConfigSign;
import com.party.mobile.web.dto.wechat.input.UserInfoResponse;
import com.party.mobile.web.dto.wechat.output.OathorizeUrlOutput;
import com.party.mobile.web.dto.wechat.output.WechatJsConfg;
import com.party.common.utils.PartyCode;
import com.party.mobile.web.utils.VerifyCodeUtils;
import com.party.common.constant.WechatConstant;
import com.party.mobile.web.utils.WechatJsUtils;
import com.party.mobile.web.utils.aes.AES;

/**
 * 微信控制层
 * party
 * Created by wei.li
 * on 2016/9/24 0024.
 */
@Controller
@RequestMapping("/wechat/wechat")
public class WechatController {

    @Autowired
    WechatBizService wechatBizService;

    @Autowired
    StringJedis stringJedis;

    @Autowired
    MemberBizService memberBizService;

    @Autowired
    CurrentUserBizService currentUserBizService;

    @Autowired
    WechatAccountService wechatAccountService;
    
    @Autowired
    IMemberMerchantService memberMerchantService;

    //公众号编号
    @Value("#{wechat['wechat.wwz.appid']}")
    private String appid;


    /**
     * 获取微信授权地址
     * @param redirectUri 重定向url
     * @return 微信授权url
     */
    @ResponseBody
    @RequestMapping("/getOathorizeUrl")
    public AjaxResult getOathorizeUrl(String redirectUri){

        //数据验证
        if (Strings.isNullOrEmpty(redirectUri)){
            return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, "重定向地址不能为空");
        }

        String oathorizeUrl;
        try {
            oathorizeUrl = wechatBizService.getOathorizeUrl(redirectUri);
        } catch (UnsupportedEncodingException e) {
            return AjaxResult.error(PartyCode.OATHORIZEURL_ERROR, "生成授权url错误");
        }

        OathorizeUrlOutput oathorizeUrlOutput = new OathorizeUrlOutput();
        oathorizeUrlOutput.setUrl(oathorizeUrl);
        return AjaxResult.success(oathorizeUrlOutput);
    }

    /**
     * 获取微信授权地址（普通授权）
     * @param redirectUri 重定向url
     * @return 微信授权url
     */
    @ResponseBody
    @RequestMapping("/getBaseOathorizeUrl")
    public AjaxResult getBaseOathorizeUrl(String redirectUri, String memberId){

        //数据验证
        if (Strings.isNullOrEmpty(redirectUri)){
            return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, "重定向地址不能为空");
        }
        
//        if (Strings.isNullOrEmpty(memberId)){
//            return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, "业务发布者memberId不能为空");
//        }
        
        MemberMerchant memberMerchant = memberMerchantService.findByMemberId(memberId);

        String oathorizeUrl;
        try {
            oathorizeUrl = wechatBizService.getBaseOathorizeUrl(redirectUri, memberMerchant);
        } catch (UnsupportedEncodingException e) {
            return AjaxResult.error(PartyCode.OATHORIZEURL_ERROR, "生成授权url错误");
        }

        OathorizeUrlOutput oathorizeUrlOutput = new OathorizeUrlOutput();
        oathorizeUrlOutput.setUrl(oathorizeUrl);
        return AjaxResult.success(oathorizeUrlOutput);
    }

    /**
     * 获取微信用户信息
     * @param code 会话码
     * @param request 请求参数
     * @return 交互数据
     */
    @ResponseBody
    @RequestMapping(value = "/getOpenId")
    public AjaxResult getOpenId(String code, HttpServletRequest request){
        //数据验证
        if (Strings.isNullOrEmpty(code)){
            return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, "会话码code不能为空");
        }
        AccessTokenResponse accessTokenResponse = wechatBizService.acccessToken(code, null);
        if (null == accessTokenResponse){
            return AjaxResult.error(PartyCode.OPEN_ID_ERROR, "获取微信openId信息错误");
        }

        //缓存openId
        stringJedis.setValue(code, accessTokenResponse.getOpenid());
        return AjaxResult.success(accessTokenResponse);
    }
    
    /**
     * 获取微信用户信息
     * @param code 会话码
     * @param request 请求参数
     * @param memberId 业务发布者
     * @return 交互数据
     */
    @ResponseBody
    @RequestMapping(value = "/getOpenId2")
    public AjaxResult getOpenId2(String code, HttpServletRequest request, String memberId){
        //数据验证
        if (Strings.isNullOrEmpty(code)){
            return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, "会话码code不能为空");
        }
//        if (Strings.isNullOrEmpty(memberId)){
//            return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, "业务发布者memberId不能为空");
//        }
        MemberMerchant memberMerchant = memberMerchantService.findByMemberId(memberId);
        AccessTokenResponse accessTokenResponse = wechatBizService.acccessToken(code, memberMerchant);
        if (null == accessTokenResponse){
            return AjaxResult.error(PartyCode.OPEN_ID_ERROR, "获取微信openId信息错误");
        }

        //缓存openId
        stringJedis.setValue(code, accessTokenResponse.getOpenid());
        return AjaxResult.success(accessTokenResponse);
    }


    /**
     * 获取微信小程序openid
     * @param code 会话码
     * @param request 请求参数
     * @return 交互数据
     */
    @ResponseBody
    @RequestMapping(value = "/getXcxOpenId")
    public AjaxResult getXcxOpenId(String code, HttpServletRequest request){
        //数据验证
        if (Strings.isNullOrEmpty(code)){
            return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, "会话码code不能为空");
        }
        JSONObject accessTokenResponse = wechatBizService.xcxAccessToken(code);
        if (null == accessTokenResponse){
            return AjaxResult.error(PartyCode.OPEN_ID_ERROR, "获取微信openId信息错误");
        }
        return AjaxResult.success(accessTokenResponse);
    }
    /**
     * 小程序
	 * 获取用户openId和unionId数据(如果没绑定微信开放平台，解密数据中不包含unionId)
	 * @param encryptedData 加密数据
	 * @param iv			加密算法的初始向量	
	 * @param sessionId		会话ID
	 * @return
	 */
    @ResponseBody
	@RequestMapping(value = "/decodeUserInfo")
	public AjaxResult decodeUserInfo(String encryptedData, String iv, String sessionKey){
		System.out.println(encryptedData);
		System.out.println(iv);	
		System.out.println(sessionKey);
		try {
			AES aes = new AES();
			byte[] resultByte = aes.decrypt(Base64.decodeBase64(encryptedData), Base64.decodeBase64(sessionKey), Base64.decodeBase64(iv));
			if(null != resultByte && resultByte.length > 0){
				String userInfo = new String(resultByte, "UTF-8");
				JSONObject jsonObject = JSONObject.parseObject(userInfo);
				return AjaxResult.success(jsonObject);
			}
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return AjaxResult.error();
	}
    /**
     * 获取js 接口初始化函数
     * @param url 当前页面url
     * @return 交互数据
     */
    @ResponseBody
    @RequestMapping("/getJsConfig")
    public AjaxResult getJsConfig(String url){

        WechatJsConfg wechatJsConfg = new WechatJsConfg();
        String nonceStr = VerifyCodeUtils.RandomString(WechatConstant.RANDOM_LENGTH);//获取随机数
        String timeStamp = Long.toString(System.currentTimeMillis() / 1000);
        wechatJsConfg.setAppId(appid);
        wechatJsConfg.setNonceStr(nonceStr);
        wechatJsConfg.setTimestamp(timeStamp);

        //签名
        String ticket = wechatAccountService.getSystem().getTicket();
        JsConfigSign jsConfigSign = new JsConfigSign();
        jsConfigSign.setTimestamp(timeStamp);
        jsConfigSign.setUrl(url);
        jsConfigSign.setNoncestr(nonceStr);
        jsConfigSign.setTicket(ticket);

        String sign = WechatJsUtils.sign(jsConfigSign);
        wechatJsConfg.setSignature(sign);
        System.out.println(jsConfigSign);
        System.out.println(sign);
        return AjaxResult.success(wechatJsConfg);
    }
    
    /**
     * 获取js 接口初始化函数
     * @param url 当前页面url
     * @param 业务发起者
     * @return 交互数据
     */
    @ResponseBody
    @RequestMapping("/getJsConfig2")
    public AjaxResult getJsConfig2(String url, String memberId){
    	
    	if (Strings.isNullOrEmpty(memberId)){
            return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, "业务发布者memberId不能为空");
        }
    	
    	MemberMerchant memberMerchant = memberMerchantService.findByMemberId(memberId);

        WechatJsConfg wechatJsConfg = new WechatJsConfg();
        String nonceStr = VerifyCodeUtils.RandomString(WechatConstant.RANDOM_LENGTH);//获取随机数
        String timeStamp = Long.toString(System.currentTimeMillis() / 1000);
        // 获取绑定商户
        if (memberMerchant != null && memberMerchant.getOpenStatus().equals(YesNoStatus.YES.getCode())) {
			wechatJsConfg.setAppId(memberMerchant.getOfficialAccountId());
		} else {
			wechatJsConfg.setAppId(appid);
		}
        wechatJsConfg.setNonceStr(nonceStr);
        wechatJsConfg.setTimestamp(timeStamp);

        //签名
        String ticket = wechatAccountService.getSystem().getTicket();
        JsConfigSign jsConfigSign = new JsConfigSign();
        jsConfigSign.setTimestamp(timeStamp);
        jsConfigSign.setUrl(url);
        jsConfigSign.setNoncestr(nonceStr);
        jsConfigSign.setTicket(ticket);

        String sign = WechatJsUtils.sign(jsConfigSign);
        wechatJsConfg.setSignature(sign);
        System.out.println(jsConfigSign);
        System.out.println(sign);
        return AjaxResult.success(wechatJsConfg);
    }

    /**
     * 微信长连接转短连接
     * @param url 请求参数
     * @return 返回连接
     */
    @ResponseBody
    @RequestMapping("/longToShort")
    public AjaxResult longToShort(String url){
        WechatAccount wechatAccount = wechatAccountService.getSystem();
        String resultUrl = wechatBizService.longToShort(url, wechatAccount.getToken());
        return AjaxResult.success(resultUrl);
    }

    /**
     * 根据openid获取用户信息
     * @param openId 用户唯一标识
     * @return 交互数据
     */
    @ResponseBody
    @RequestMapping(value = "/userInfo")
    public AjaxResult userInfo(String openId, String accessToken){
        UserInfoResponse userInfoResponse = wechatBizService.userInfo(openId, accessToken);
        return AjaxResult.success(userInfoResponse);
    }
}
