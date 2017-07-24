package com.party.notify.wechatNotify.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.party.common.constant.Constant;
import com.party.common.constant.WechatConstant;
import com.party.common.utils.wechat.WechatUtils;
import com.party.core.exception.BusinessException;
import com.party.core.model.member.ThirdPartyUser;
import com.party.core.model.wechat.WechatAccount;
import com.party.core.service.notify.IInstanceService;
import com.party.core.service.wechat.IWechatAccountService;
import com.party.notify.jms.publisher.service.INotifyPublisherService;
import com.party.notify.wechatNotify.model.TemplateMessage;
import com.party.notify.wechatNotify.model.TemplateParameter;
import com.party.notify.wechatNotify.service.IWechatNotifyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * 微信模板消息推送接口实现
 * Created by wei.li
 *
 * @date 2017/5/17 0017
 * @time 13:52
 */

@Service
public class WechatNotifyService implements IWechatNotifyService {

    protected static Logger logger = LoggerFactory.getLogger(WechatNotifyService.class);

    private static final String ACCESS_TOKEN = "ACCESS_TOKEN";

    @Autowired
    private IWechatAccountService wechatAccountService;

    @Autowired
    private INotifyPublisherService notifyPublisherService;

    @Autowired
    private IInstanceService instanceService;

    /**
     * 发送微信模板消息
     * @param url 连接
     * @param templateMessage 模板消息实体
     * @return 发送结果(true/false)
     */
    public boolean send(String url, TemplateMessage templateMessage) {

        logger.info("微信发送模板消息, 请求报文{}", JSONObject.toJSONString(templateMessage));

        JSONObject response = WechatUtils.httpRequest(url, WechatUtils.POST, JSONObject.toJSONString(templateMessage));

        logger.info("微信发送模板消息, 返回报文{}", response.toJSONString());

        if (Constant.WECHAT_CODE_SUCCESS.equals(response.get(Constant.WECHAT_ERR_CODE))){
            return true;
        }
        return false;
    }




    /**
     * 微信消息推送
     * @param isWrite 是否写入
     * @param content 消息内容
     */
    public void push(boolean isWrite,HashMap<String, Object> content){

        // 找到接受者的 openId
        List<String> openIdList = (List<String>) content.get("openIdList");
        String accessToken = (String) content.get("accessToken");
        String templateUrl = (String) content.get("templateUrl");
        String templateId = (String) content.get("templateId");

        // 提取发送的参数
        JSONObject jsonData = (JSONObject) content.get(Constant.WECHAT_NOTIFY_KEY);
        if (null == jsonData){
            throw new BusinessException("微信消息模板配置信息不存在");
        }
        String url = WechatConstant.POST_TEMPLATE_URL.replace(ACCESS_TOKEN, accessToken);
        TemplateMessage templateMessage = new TemplateMessage();
        templateMessage.setData(jsonData);
        templateMessage.setTemplateId(templateId);
        templateMessage.setUrl(templateUrl);
        TemplateParameter templateParameter = new TemplateParameter();
        templateParameter.setTemplateMessage(templateMessage);
        templateParameter.setUrl(url);
        for (String openId : openIdList){
            templateMessage.setTouser(openId);
            notifyPublisherService.send(templateParameter, Constant.MESSAGE_CHANNEL_WECHAT, isWrite);
        }
    }

    public void send(String openId){

        JSONObject jsonFirst = new JSONObject();
        jsonFirst.put("value", "您收到一笔巨款");
        jsonFirst.put("color", "#173177");

        JSONObject keynote1 = new JSONObject();
        keynote1.put("value", "众筹一次说走就走的旅行");
        keynote1.put("color", "#173177");

        JSONObject keynote2 = new JSONObject();
        keynote2.put("value", "微笑");
        keynote2.put("color", "#173177");

        JSONObject keynote3 = new JSONObject();
        keynote3.put("value", "1000万");
        keynote3.put("color", "#173177");

        JSONObject keynote4 = new JSONObject();
        keynote4.put("value", "2015年10月1日 20:30");
        keynote4.put("color", "#173177");

        JSONObject jsonRemark = new JSONObject();
        jsonRemark.put("value", "没条件支持你");
        jsonRemark.put("color", "#173177");

        JSONObject jsonData = new JSONObject();
        jsonData.put("first",jsonFirst);
        jsonData.put("keyword1",keynote1);
        jsonData.put("keyword2",keynote2);
        jsonData.put("keyword3",keynote3);
        jsonData.put("keyword4",keynote4);
        jsonData.put("remark",jsonRemark);

        String token = wechatAccountService.getSystem().getToken();
        String url = WechatConstant.POST_TEMPLATE_URL.replace(ACCESS_TOKEN, token);

        TemplateMessage templateMessage = new TemplateMessage();
        templateMessage.setTouser(openId);
        templateMessage.setUrl("4g.tongxingzhe.cn/micWeb");
        templateMessage.setTemplateId("IG2N0yWfkSRNba2DHjjHcr72DXOTUixCvOJ0anuhUto");
        templateMessage.setData(jsonData);
        this.send(url, templateMessage);
    }
}
