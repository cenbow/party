package com.party.notify.wechatNotify.service;

import com.alibaba.fastjson.JSONObject;
import com.party.core.model.member.Member;
import com.party.core.model.wechat.WechatTemplateMassage;

import java.util.HashMap;

/**
 * 获取微信消息内容服务接口
 * Created by wei.li
 *
 * @date 2017/5/17 0017
 * @time 18:14
 */


public interface IWechatKeynotService {

    /**
     * 获取微信消息内容值
     * @param value 值
     * @return 消息内容
     */
    JSONObject get(String value);


    /**
     * 封装标题和尾部
     * @param wechatTemplateMassage 模板消息
     * @return 消息内容
     */
    JSONObject appendFirstAndLast(WechatTemplateMassage wechatTemplateMassage, JSONObject jsonObject);

    /**
     * 封装微信模板消息配置
     * @param targetMemberId 目标商户
     * @param notifyCode 消息事件编码
     * @param authorId 接受者
     * @param constant 消息内容
     * @param jsonData 微信消息内容
     * @return 消息内容
     */
    HashMap<String, Object> getWechatConstant(String targetMemberId, String notifyCode, String authorId,
                                              HashMap<String, Object> constant, JSONObject jsonData, HashMap<String, Object> urlConstant);


    /**
     * 封装微信模板消息配置
     * @param notifyCode 消息事件编码
     * @param authorId 接受者
     * @param constant 消息内容
     * @param jsonData 微信消息内容
     * @return 消息内容
     */
    HashMap<String, Object> getWechatConstant( String notifyCode, String authorId,
                                                      HashMap<String, Object> constant, JSONObject jsonData, HashMap<String, Object> urlConstant);
}
