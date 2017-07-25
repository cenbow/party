package com.party.admin.web.controller.openPlatform;

import com.party.admin.biz.openPlatform.NotifyBizService;
import com.party.admin.web.dto.AjaxResult;
import com.party.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 微信开放平台的第三方平台消息
 */
@Controller
@RequestMapping("/openPlatform/notify")
public class NotifyController {

    private String componentAppid;
    private String componentAppsecret;

    // 第三方平台申请时填写的接收消息的校验token
    String token = "yilutongxing2016";
    // 第三方平台申请时填写的接收消息的加密解密key
    String encodingAesKey = "66HoTKSy371Asryx6k8iVzuMWMFRZ6es9ghKR20x6BX";

    @Autowired
    private NotifyBizService notifyBizService;

    protected static Logger logger = LoggerFactory.getLogger(NotifyController.class);

    /**
     * 接收微信推送过来的消息
     *
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("receive")
    public AjaxResult receiveNotify(HttpServletRequest request) throws Exception {
        logger.info("微信第三方平台---------微信推送Ticket消息10分钟一次-----------");
        // String responseData = WechatPayUtils.getNotifyXml(request);
        String responseData = "<xml>    <AppId><![CDATA[wx952ca9980421cb8f]]></AppId>    <Encrypt><![CDATA[ZGEJIamiVi34nnbHZh9Dcq3/AayVrzWCO4jjvDt7DrxdYcWWAUObDL/cbvkW/0K9o7/bIw9rd8GR3JtaYJviPQ2dh1uV4BrjypvUhHM2Z30An6bvRvn9bJVwx+0zIOkDUpvWHUfHAzDCPwv4Gt67NBrnqNDmrUxCtmhaj2mcwS0xotOVLzAWT2gO/8YAEDm6VwE6fsWrvWUPnowMDmnyM2lSxjqq7uOKZjhjciF71nqBRRJDqpwQL4sGKT9U04MX//+EhQLlmPyxEPHtvCxheygj/Q9uOl+82cCicNeAxvmKzmEU4kZO679u3EvTJ+uVeUDXH+VSXJZjap43zQExyPMcVjhFhqfVlfSwe+0zjINt/nLfPaEOlA/FEpwQWJGhlMawgOgDKVNCKXf3wR1N46YMDjExcdrHHrRbljnFAbbduqNkegJB2UnAN3vxMPJ774CDrEYc62/ecltW8NBrYA==]]></Encrypt></xml>";
        logger.info("接收Ticket响应数据：{}", responseData);
        // 1.获取component_verify_ticket
        String componentVerifyTicket = notifyBizService.resolveTicket(token, encodingAesKey, responseData);
        // 2.获取第三方平台component_access_token
        String accessToken = notifyBizService.getComponentToken(componentAppid, componentAppsecret, componentVerifyTicket);
        if (StringUtils.isNotEmpty(accessToken)) {
            // 3.获取预授权码pre_auth_code
            String preAuthCode = notifyBizService.getPreAuthCode(componentAppid, accessToken);
            if (StringUtils.isNotEmpty(preAuthCode)) {

            }
        }
        return AjaxResult.success();
    }
}
