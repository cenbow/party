package com.party.admin.web.controller.openPlatform;

import com.party.admin.biz.openPlatform.AuthorizationBizService;
import com.party.admin.web.dto.output.openPlatform.WechatAuthorizerInfo;
import com.party.admin.web.dto.output.openPlatform.WechatAuthorizerUserInfo;
import com.party.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.UnsupportedEncodingException;

/**
 * 授权
 */
@Controller
@RequestMapping("/openPlatform/authorization")
public class AuthorizationController {

    private String componentAppid;
    private String componentAppsecret;
    private String redirectUrl; // 授权回调地址

    @Autowired
    private AuthorizationBizService authorizationBizService;

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 获取授权链接
     *
     * @return
     */
    @RequestMapping("getAuthorizeUrl")
    public ModelAndView getAuthorizeUrl() {
        ModelAndView mv = new ModelAndView("authorForward");
        try {
            // 获取accessToken
            String accessToken = authorizationBizService.getAccessToken(componentAppid, componentAppsecret, "");
            if (StringUtils.isNotEmpty(accessToken)) {
                // 获取preAuthCode
                String authCode = authorizationBizService.getPreAuthCode(componentAppid, accessToken);
                if (StringUtils.isNotEmpty(authCode)) {
                    String authorizeUrl = authorizationBizService.getAuthorizeUrl(redirectUrl, componentAppid, authCode);
                    mv.addObject("authorizeUrl", authorizeUrl);
                }
            }
        } catch (UnsupportedEncodingException e) {
            logger.error("编码错误{}", e);
        } catch (Exception e) {
            logger.error("获取授权URL异常{}", e);
        }
        return mv;
    }

    /**
     * 授权回调
     *
     * @param auth_code  授权码
     * @param expires_in 过期时间
     * @return
     */
    @RequestMapping("callBack")
    public ModelAndView callBack(String auth_code, String expires_in) {
        ModelAndView mv = new ModelAndView("");
        // 获取授权信息
        WechatAuthorizerInfo authorizationInfo = authorizationBizService.getAuthorizationInfo(componentAppid, "", auth_code);
        // String authorizerAppid = authorizationInfo.getAuthorization_info().getAuthorizer_appid();
        String authorizerAppid = "";
        // 获取授权者信息
        WechatAuthorizerUserInfo authorizerInfo = authorizationBizService.getAuthorizerInfo(componentAppid, authorizerAppid, "");

        return mv;
    }

}
