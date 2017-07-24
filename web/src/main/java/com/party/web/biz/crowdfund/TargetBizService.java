package com.party.web.biz.crowdfund;

import com.google.common.base.Strings;
import com.party.common.constant.Constant;
import com.party.core.exception.BusinessException;
import com.party.core.model.member.Member;
import com.party.core.model.notify.Event;
import com.party.core.model.notify.EventChannel;
import com.party.core.model.notify.TargetTemplate;
import com.party.core.model.notify.TargetTemplateType;
import com.party.core.service.crowdfund.ITargetTemplateService;
import com.party.core.service.notify.IEventChannelService;
import com.party.core.service.notify.IEventService;
import com.party.notify.notifyPush.servce.INotifySendService;
import com.party.web.utils.RealmUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 众筹项目业务接口
 * Created by wei.li
 *
 * @date 2017/7/4 0004
 * @time 11:30
 */

@Service
public class TargetBizService {

    @Autowired
    private ITargetTemplateService targetTemplateService;

    @Autowired
    private INotifySendService notifySendService;

    @Autowired
    private IEventChannelService eventChannelService;

    @Autowired
    private IEventService eventService;

    private final static String ALL_CROWDFUND = "allCrowdfundPush";

    private final static String UNDERWAY_CORDFUND = "underwayCordfundPush";

    private final static String SUCCESS_CROWDFUND = "successCrowdfundPush";



    /**
     * 获取目标模板
     * @param targetId 模板编号
     * @param type 类型
     * @return 模板编号
     */
    public TargetTemplate get(String targetId, Integer type){
        type = null == type ? TargetTemplateType.CROWDFUNF_ALL.getCode() : type;
        TargetTemplate targetTemplate = targetTemplateService.findByTargetId(targetId, type);
        if (null == targetTemplate){
            targetTemplate = new TargetTemplate();
            Event event = new Event();
            if (TargetTemplateType.CROWDFUND_SUCCESS.getCode().equals(type)){
                 event = eventService.findByCode(SUCCESS_CROWDFUND);
            }
            else if (TargetTemplateType.CROWDFUND_UNDERWAY.getCode().equals(type)){
                event = eventService.findByCode(UNDERWAY_CORDFUND);
            }
            else if (TargetTemplateType.CROWDFUNF_ALL.getCode().equals(type)){
                event = eventService.findByCode(ALL_CROWDFUND);
            }
            if (Strings.isNullOrEmpty(event.getId())){
                return targetTemplate;
            }
            EventChannel eventChannel = eventChannelService.findByChannelCodeAndEventId(Constant.MESSAGE_CHANNEL_SMS,event.getId());
            if (null == eventChannel){
                return targetTemplate;
            }

            targetTemplate.setTemplate(eventChannel.getTemplate());
            targetTemplate.setType(type);
            targetTemplate.setTargetId(targetId);
        }
        return targetTemplate;
    }

    /**
     * 发送消息and保存模板
     * @param targetTemplate 目标模板
     */
    public void sendAndSave(TargetTemplate targetTemplate){

        Member member = RealmUtils.getCurrentUser();
        if (null == member){
            throw new BusinessException("获取不到当前用户");
        }
        //发送
        if (TargetTemplateType.CROWDFUNF_ALL.getCode().equals(targetTemplate.getType())){
            notifySendService.sendAllCrowdfund(targetTemplate.getTargetId(),
                    member.getId(), targetTemplate.getTemplate());
        }
        else if (TargetTemplateType.CROWDFUND_UNDERWAY.getCode().equals(targetTemplate.getType())){
            notifySendService.sendUnderwayCordfund(targetTemplate.getTargetId(),
                    member.getId(), targetTemplate.getTemplate());
        }
        else if (TargetTemplateType.CROWDFUND_SUCCESS.getCode().equals(targetTemplate.getType())){
            notifySendService.sendSuccessCrowdfund(targetTemplate.getTargetId(),
                    member.getId(), targetTemplate.getTemplate());
        }

        TargetTemplate dbTemplate = targetTemplateService.findByTargetId(targetTemplate.getTargetId(), targetTemplate.getType());
        //保存
        if (null == dbTemplate){
            targetTemplateService.insert(targetTemplate);
        }
        else {
            targetTemplateService.update(targetTemplate);
        }
    }
}
