package com.party.admin.web.controller.notify;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Sets;
import com.party.admin.biz.notify.EventBizService;
import com.party.admin.web.dto.AjaxResult;
import com.party.common.constant.Constant;
import com.party.common.paging.Page;
import com.party.core.model.message.MessageType;
import com.party.core.model.notify.Event;
import com.party.core.model.notify.MsgChannel;
import com.party.core.service.notify.IEventChannelService;
import com.party.core.service.notify.IEventService;
import com.party.core.service.notify.IMsgChannelService;
import com.party.admin.biz.quartz.IScheduleJobService;
import com.party.notify.notifyPush.servce.INotifySendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by wei.li
 *
 * @date 2017/4/7 0007
 * @time 16:24
 */

@Controller
@RequestMapping(value = "/notify/event")
public class EventController {

    @Autowired
    private IEventService eventService;

    @Autowired
    private IMsgChannelService msgChannelService;

    @Autowired
    private IEventChannelService eventChannelService;

    @Autowired
    private EventBizService eventBizService;

    @Autowired
    private IScheduleJobService scheduleJobService;

    @Autowired
    private INotifySendService notifySendService;


    private static final String ALL_CROWDFUND = "allCrowdfundPush";

    private static final String SUCCESS_CROWDFUND = "successCrowdfundPush";

    private static final String UNDERWAY_CORDFUND = "underwayCordfundPush";
    /**
     * 事件列表
     * @param event 事件
     * @param page 分页
     * @return
     */
    @RequestMapping(value = "list")
    public ModelAndView list(Event event, Page page){
        page.setLimit(20);
        List<Event> eventList = eventService.listPage(event, page);
        ModelAndView modelAndView = new ModelAndView("/notify/eventList");
        Map<String, String> type = MessageType.convertMap();
        modelAndView.addObject("types", type);
        modelAndView.addObject("list", eventList);
        modelAndView.addObject("page", page);
        return modelAndView;
    }

    /**
     * 时间查看
     * @param id 编号
     * @return 视图
     */
    @RequestMapping(value = "view")
    public ModelAndView view(String id){
        ModelAndView modelAndView = new ModelAndView("/notify/eventForm");
        Event event;
        if (Strings.isNullOrEmpty(id)){
            event = new Event(0, Constant.EVENT_HAND, 0);
        }
        else {
            event = eventService.get(id);
        }
        Map<String, String> types = MessageType.convertMap();
        modelAndView.addObject("event", event);
        modelAndView.addObject("types", types);
        return modelAndView;
    }

    /**
     * 保存事件
     * @param event 事件信息
     * @return 事件
     */
    @ResponseBody
    @RequestMapping(value = "save")
    public AjaxResult save(Event event){
        AjaxResult ajaxResult = new AjaxResult();
        try {
            eventBizService.save(event);
        } catch (RuntimeException be) {
            ajaxResult.setDescription(be.getMessage());
            return ajaxResult;
        }
        ajaxResult.setSuccess(true);
        return ajaxResult;
    }

    /**
     * 删除
     * @param id 编号
     * @return 交互数据
     */
    @ResponseBody
    @RequestMapping(value = "delete")
    public AjaxResult delete(String id){
        Event event = eventService.get(id);
        if (Constant.EVENT_AUTO.equals(event.getWay())){
            scheduleJobService.delete(id);
        }
        else {
            eventService.delete(id);
        }
        return AjaxResult.success();
    }

    /**
     * 通道列表
     * @param eventId 事件编号
     * @return 通道列表页
     */
    @RequestMapping(value = "channelList")
    public ModelAndView channelList(String eventId){
        ModelAndView modelAndView = new ModelAndView("notify/distributionChannel");
        List<MsgChannel> myChannelList = msgChannelService.findByEventId(eventId);
        List<MsgChannel> channelList = msgChannelService.list(new MsgChannel());
        modelAndView.addObject("myChannelList", myChannelList);
        modelAndView.addObject("channelList", channelList);
        modelAndView.addObject("eventId", eventId);
        return modelAndView;
    }


    /**
     * 分配通道
     * @param eventId 事件编号
     * @param channelIds 通道编号
     * @return 交互数据
     */
    @ResponseBody
    @RequestMapping(value = "distribution")
    public AjaxResult distributionChannels(String eventId, String channelIds){
        AjaxResult ajaxResult = new AjaxResult(true);
        Set<String> channelSet = Sets.newHashSet(Splitter.on(",").split(channelIds));
        eventChannelService.distributionChannels(eventId, channelSet);
        return ajaxResult;
    }

    /**
     * 开关
     * @param id 编号
     * @param msgSwitch 开关
     * @return 交互数据
     */
    @ResponseBody
    @RequestMapping(value = "eventSwitch")
    public AjaxResult eventSwitch(String id, Integer msgSwitch){
        AjaxResult ajaxResult = new AjaxResult();
        try {
            eventBizService.eventSwitch(id, msgSwitch);
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setDescription(e.getMessage());
            return ajaxResult;
        }
        ajaxResult.setSuccess(true);
        return ajaxResult;
    }


    /**
     * 推送消息
     * @param id 事件编号
     * @return 交互数据
     */
    @ResponseBody
    @RequestMapping(value = "push")
    public AjaxResult push(String id){
        AjaxResult ajaxResult = new AjaxResult();
        Event event = eventService.get(id);
        if (event.getCode().equals(ALL_CROWDFUND)){
            notifySendService.sendAllCrowdfund();
        }
        else if (event.getCode().equals(SUCCESS_CROWDFUND)){
            notifySendService.sendSuccessCrowdfund();
        }
        else if (event.getCode().equals(UNDERWAY_CORDFUND)){
            notifySendService.sendUnderwayCordfund();
        }
        ajaxResult.setSuccess(true);
        return ajaxResult;
    }
}
