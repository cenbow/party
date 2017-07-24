package com.party.notify.wechatNotify.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Function;
import com.google.common.base.Strings;
import com.party.common.constant.Constant;
import com.party.common.utils.LangUtils;
import com.party.core.exception.BusinessException;
import com.party.core.model.YesNoStatus;
import com.party.core.model.member.Member;
import com.party.core.model.member.MemberMerchant;
import com.party.core.model.member.ThirdPartyUser;
import com.party.core.model.notify.Event;
import com.party.core.model.notify.EventChannel;
import com.party.core.model.wechat.WechatAccount;
import com.party.core.model.wechat.WechatTemplateMassage;
import com.party.core.service.member.IMemberMerchantService;
import com.party.core.service.member.IThirdPartyUserService;
import com.party.core.service.notify.IEventChannelService;
import com.party.core.service.notify.IEventService;
import com.party.core.service.wechat.IWechatAccountService;
import com.party.core.service.wechat.IWechatTemplateMassageService;
import com.party.notify.template.service.ITemplateService;
import com.party.notify.wechatNotify.service.IWechatKeynotService;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * 微信消息内容接口实现
 * Created by wei.li
 *
 * @date 2017/5/17 0017
 * @time 18:19
 */

@Service
public class WechatKeynotService implements IWechatKeynotService {



    //公众号编号
    @Value("${wechat.appid}")
    private String appid;

    @Autowired
    private IMemberMerchantService memberMerchantService;

    @Autowired
    private IWechatAccountService wechatAccountService;

    @Autowired
    private IThirdPartyUserService thirdPartyUserService;

    @Autowired
    private IWechatTemplateMassageService wechatTemplateMassageService;

    @Autowired
    private ITemplateService templateService;


    /**
     * 获取微信消息内容值
     * @param value 值
     * @return 消息内容
     */
    public JSONObject get(String value) {
        JSONObject object = new JSONObject();
        object.put("value", value);
        object.put("color", Constant.WECHAT_MESSAGE_COLOR);
        return object;
    }

    /**
     * 封装标题和尾部
     * @param wechatTemplateMassage 模板消息
     * @return 消息内容
     */
    public JSONObject appendFirstAndLast(WechatTemplateMassage wechatTemplateMassage, JSONObject jsonObject) {
        if (null == wechatTemplateMassage){
            throw new BusinessException("消息模板配置不存在");
        }
        if (!Strings.isNullOrEmpty(wechatTemplateMassage.getFirst())){
            JSONObject jsonFirst = this.get(wechatTemplateMassage.getFirst());
            jsonObject.put("first", jsonFirst);
        }

        if (!Strings.isNullOrEmpty(wechatTemplateMassage.getRemark())){
            JSONObject jsonRemark = this.get(wechatTemplateMassage.getRemark());
            jsonObject.put("remark", jsonRemark);
        }
        return jsonObject;
    }


    /**
     * 封装微信模板消息配置
     * @param memberMerchant 商户信息
     * @param notifyCode 消息事件编码
     * @param authorId 接受者
     * @param constant 消息内容
     * @param jsonData 微信消息内容
     * @return 消息内容
     */
    public HashMap<String, Object> getWechatConstant(MemberMerchant memberMerchant, String notifyCode,
                                                     String authorId, HashMap<String, Object> constant,
                                                     JSONObject jsonData, HashMap<String, Object> urlConstant){
        //是否存在商户配置
        List<ThirdPartyUser> thirdPartyUserList;
        WechatTemplateMassage wechatTemplateMassage = null;
        String accessToken;

        // 拥有商户信息
        if (null != memberMerchant && YesNoStatus.YES.getCode().equals(memberMerchant.getOpenStatus())){
            //发送者
            WechatAccount wechatAccount = wechatAccountService.getPartner(memberMerchant.getMemberId());
            accessToken = wechatAccount.getToken();
            thirdPartyUserList = thirdPartyUserService.get(3, authorId, memberMerchant.getOfficialAccountId());
            wechatTemplateMassage = wechatTemplateMassageService.getPartner(memberMerchant.getMemberId(), notifyCode);
        }
        // 走系统配置
        else {
            WechatAccount wechatAccount = wechatAccountService.getSystem();
            accessToken = wechatAccount.getToken();
            thirdPartyUserList = thirdPartyUserService.get(3, authorId, appid);
            wechatTemplateMassage = wechatTemplateMassageService.getSystem(notifyCode);
        }

        //微信账户
        List<String> openIdList = LangUtils.transform(thirdPartyUserList, new Function<ThirdPartyUser, String>() {
            public String apply(ThirdPartyUser thirdPartyUser) {
                return thirdPartyUser.getThirdPartyId();
            }
        });

        //如果存在消息模板
        if (null != wechatTemplateMassage){
            jsonData = this.appendFirstAndLast(wechatTemplateMassage, jsonData);
            constant.put(Constant.WECHAT_NOTIFY_KEY, jsonData);
            String url = templateService.replaceUrl(wechatTemplateMassage.getUrl(), urlConstant);
            //替换url
            constant.put("templateUrl", url);
            constant.put("templateId", wechatTemplateMassage.getTemplateId());
        }

        constant.put("openIdList", openIdList);
        constant.put("accessToken", accessToken);
        return constant;
    }


    /**
     * 封装微信模板消息配置
     * @param targetMemberId 目标商户
     * @param notifyCode 消息事件编码
     * @param authorId 接受者
     * @param constant 消息内容
     * @param jsonData 微信消息内容
     * @return
     */
    public HashMap<String, Object> getWechatConstant(String targetMemberId, String notifyCode,
                                                     String authorId, HashMap<String, Object> constant,
                                                     JSONObject jsonData, HashMap<String, Object> urlConstant){
        MemberMerchant memberMerchant = memberMerchantService.findByMemberId(targetMemberId);
        return this.getWechatConstant(memberMerchant, notifyCode, authorId, constant, jsonData, urlConstant);
    }


    /**
     * 封装微信模板消息配置
     * @param notifyCode 消息事件编码
     * @param authorId 接受者
     * @param constant 消息内容
     * @param jsonData 微信消息内容
     * @return
     */
    public HashMap<String, Object> getWechatConstant( String notifyCode, String authorId,
                                                      HashMap<String, Object> constant, JSONObject jsonData, HashMap<String, Object> urlConstant){
        MemberMerchant memberMerchant = null;
        return this.getWechatConstant(memberMerchant, notifyCode, authorId, constant, jsonData, urlConstant);
    }
}
