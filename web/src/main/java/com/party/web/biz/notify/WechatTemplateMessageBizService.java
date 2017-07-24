package com.party.web.biz.notify;

import com.google.common.collect.Lists;
import com.party.common.constant.Constant;
import com.party.core.model.member.Member;
import com.party.core.model.notify.RelationWithChannel;
import com.party.core.model.wechat.WechatTemplateMessageWithEvent;
import com.party.core.service.notify.IEventChannelService;
import com.party.core.service.wechat.IWechatTemplateMassageService;
import com.party.web.utils.RealmUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 微信模板消息服务接口
 * Created by wei.li
 *
 * @date 2017/5/23 0023
 * @time 15:19
 */

@Service
public class WechatTemplateMessageBizService {

    @Autowired
    private IEventChannelService eventChannelService;

    @Autowired
    private IWechatTemplateMassageService wechatTemplateMassageService;


    /**
     * 我能添加的事件
     * @return 事件列表
     */
    public List<RelationWithChannel> myEventList(){
        Member member = RealmUtils.getCurrentUser();
        //所有的微信通道事件
        List<RelationWithChannel> list = eventChannelService.wechatList();
        //排除已经添加的
        WechatTemplateMessageWithEvent wechatTemplateMessageWithEvent = new WechatTemplateMessageWithEvent();
        wechatTemplateMessageWithEvent.setType(Constant.WECHAT_ACCOUNT_TYPE_PARTNER);
        wechatTemplateMessageWithEvent.setMemberId(member.getId());
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
     * 我能添加的事件
     * @param id 编号
     * @return 时间列表
     */
    public List<RelationWithChannel> myEventList(String id){
        Member member = RealmUtils.getCurrentUser();
        //所有的微信通道事件
        List<RelationWithChannel> list = eventChannelService.wechatList();
        //排除已经添加的
        WechatTemplateMessageWithEvent wechatTemplateMessageWithEvent = new WechatTemplateMessageWithEvent();
        wechatTemplateMessageWithEvent.setType(Constant.WECHAT_ACCOUNT_TYPE_PARTNER);
        wechatTemplateMessageWithEvent.setMemberId(member.getId());
        List<WechatTemplateMessageWithEvent> wechatTemplateMassages
                = wechatTemplateMassageService.withEventList(wechatTemplateMessageWithEvent, null);
        List<RelationWithChannel> removeList = Lists.newArrayList();
        for (RelationWithChannel relationWithChannel : list){
            for (WechatTemplateMessageWithEvent w : wechatTemplateMassages ){
                if (relationWithChannel.getId().equals(w.getEventChannelId()) && !w.getId().equals(id)){
                    removeList.add(relationWithChannel);
                }
            }
        }
        list.removeAll(removeList);
        return list;
    }

}
