package com.party.admin.biz.openPlatform;

import com.party.admin.utils.openPlatform.WXBizMsgCrypt;
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
}
