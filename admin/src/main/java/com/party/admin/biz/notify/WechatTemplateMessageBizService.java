package com.party.admin.biz.notify;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.party.common.constant.Constant;
import com.party.common.utils.LangUtils;
import com.party.core.model.notify.RelationWithChannel;
import com.party.core.model.wechat.WechatTemplateMassage;
import com.party.core.model.wechat.WechatTemplateMessageWithEvent;
import com.party.core.service.notify.IEventChannelService;
import com.party.core.service.wechat.IWechatTemplateMassageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 微信模板消息
 * Created by wei.li
 *
 * @date 2017/5/19 0019
 * @time 17:08
 */

@Service
public class WechatTemplateMessageBizService {

    @Autowired
    private IEventChannelService eventChannelService;

    @Autowired
    private IWechatTemplateMassageService wechatTemplateMassageService;

    /**
     * 我可以选择的事件
     * @return 事件列表
     */
    public List<RelationWithChannel> myEventList(){

        //所有的微信通道事件
        List<RelationWithChannel> list = eventChannelService.wechatList();

        //排除已经添加的
        WechatTemplateMessageWithEvent wechatTemplateMessageWithEvent = new WechatTemplateMessageWithEvent();
        wechatTemplateMessageWithEvent.setType(Constant.WECHAT_ACCOUNT_TYPE_SYSTEM);
        List<WechatTemplateMessageWithEvent> wechatTemplateMassages
                = wechatTemplateMassageService.withEventList(wechatTemplateMessageWithEvent, null);

        List<RelationWithChannel> removeList = Lists.newArrayList();
       for (RelationWithChannel relationWithChannel : list){
            for (WechatTemplateMessageWithEvent w : wechatTemplateMassages){
                if (relationWithChannel.getId().equals(w.getEventChannelId())){
                    removeList.add(relationWithChannel);
                }
            }
        }

        list.removeAll(removeList);
        return list;
    }

    /**
     * 我可以选择的事件
     * @param id 消息编号
     * @return 事件列表
     */
    public List<RelationWithChannel> myEventList(String id){
        //所有的微信通道事件
        List<RelationWithChannel> list = eventChannelService.wechatList();

        //排除已经添加的
        WechatTemplateMessageWithEvent wechatTemplateMessageWithEvent = new WechatTemplateMessageWithEvent();
        wechatTemplateMessageWithEvent.setType(Constant.WECHAT_ACCOUNT_TYPE_SYSTEM);
        List<WechatTemplateMessageWithEvent> wechatTemplateMassages
                = wechatTemplateMassageService.withEventList(wechatTemplateMessageWithEvent, null);

        List<RelationWithChannel> removeList = Lists.newArrayList();
        for (RelationWithChannel relationWithChannel : list){
            for (WechatTemplateMessageWithEvent w : wechatTemplateMassages){
                if (relationWithChannel.getId().equals(w.getEventChannelId()) && !w.getId().equals(id)){
                    removeList.add(relationWithChannel);
                }
            }
        }

        list.removeAll(removeList);
        return list;
    }
}
