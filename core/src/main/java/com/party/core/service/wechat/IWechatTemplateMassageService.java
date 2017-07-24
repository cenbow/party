package com.party.core.service.wechat;

import com.party.common.paging.Page;
import com.party.core.model.wechat.WechatTemplateMassage;
import com.party.core.model.wechat.WechatTemplateMessageWithEvent;
import com.party.core.service.IBaseService;

import java.util.List;

/**
 * 微信模板消息服务接口
 * Created by wei.li
 *
 * @date 2017/5/19 0019
 * @time 15:47
 */


public interface IWechatTemplateMassageService extends IBaseService<WechatTemplateMassage> {

    /**
     * 微信模板消息列表
     * @param wechatTemplateMessageWithEvent 微信模板消息
     * @param page 分页参数
     * @return 模板消息列表
     */
    List<WechatTemplateMessageWithEvent> withEventList(WechatTemplateMessageWithEvent wechatTemplateMessageWithEvent, Page page);

    /**
     * 获取系统的模板配置
     * @param eventCode 消息事件编号
     * @return 微信模板
     */
    WechatTemplateMassage getSystem(String eventCode);

    /**
     * 获取合作商模板配置
     * @param memberId 会员编号
     * @param eventCode 消息事件编号
     * @return 微信模板
     */
    WechatTemplateMassage getPartner(String memberId, String eventCode);
}
