package com.party.admin.web.controller.openPlatform;

import com.party.admin.biz.openPlatform.AuthorizationBizService;
import com.party.admin.web.dto.AjaxResult;
import com.party.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;

/**
 * 授权
 */
@Controller
@RequestMapping("/openPlatform/authorization")
public class AuthorizationController {

    private String componentAppid;
    private String componentAppsecret;

    @Autowired
    private AuthorizationBizService authorizationBizService;

    protected static Logger logger = LoggerFactory.getLogger(AuthorizationController.class);

    @ResponseBody
    @RequestMapping("getAuthorizeUrl")
    public AjaxResult getAuthorizeUrl(String redirectUrl) {
        if (StringUtils.isEmpty(redirectUrl)) {
            return AjaxResult.error("重定向地址不能为空");
        }
        try {
            // 获取accessToken
            String accessToken = authorizationBizService.getAccessToken(componentAppid, componentAppsecret, "");
            if (StringUtils.isNotEmpty(accessToken)) {
                // 获取preAuthCode
                String authCode = authorizationBizService.getPreAuthCode(componentAppid, accessToken);
                if (StringUtils.isNotEmpty(authCode)) {
                    String authorizeUrl = authorizationBizService.getAuthorizeUrl(redirectUrl, componentAppid, authCode);
                    return AjaxResult.success((Object) authorizeUrl);
                }
            }
        } catch (UnsupportedEncodingException e) {
            logger.error("编码错误{}", e);
        } catch (Exception e) {
            logger.error("获取授权URL异常{}", e);
        }
        return AjaxResult.error("错误");
    }
}
