package com.party.admin.web.controller.notify;

import com.party.admin.web.dto.AjaxResult;
import com.party.core.model.channel.Channel;
import com.party.core.model.notify.Event;
import com.party.core.model.notify.EventChannel;
import com.party.core.model.notify.MsgChannel;
import com.party.core.service.channel.IChannelService;
import com.party.core.service.notify.IEventChannelService;
import com.party.core.service.notify.IEventService;
import com.party.core.service.notify.IMsgChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 事件通道管理控制器
 * Created by wei.li
 *
 * @date 2017/4/11 0011
 * @time 15:43
 */

@Controller
@RequestMapping(value = "/notify/eventChannel")
public class EventChannelController {

    @Autowired
    private IEventChannelService eventChannelService;

    @Autowired
    private IMsgChannelService msgChannelService;

    /**
     * 保存配置信息
     * @param eventChannel 配置信息
     * @return 交互数据
     */
    @ResponseBody
    @RequestMapping(value = "save")
    public AjaxResult save(EventChannel eventChannel){
        AjaxResult ajaxResult = new AjaxResult();
        eventChannelService.update(eventChannel);
        ajaxResult.setSuccess(true);
        return ajaxResult;
    }


    /**
     * 配置信息查看
     * @param id 编号
     * @return 配置信息
     */
    @RequestMapping(value = "view")
    public ModelAndView view(String id){
        ModelAndView modelAndView = new ModelAndView("notify/eventChannelForm");
        EventChannel eventChannel = eventChannelService.get(id);
        MsgChannel msgChannel = msgChannelService.get(eventChannel.getChannelId());
        modelAndView.addObject("eventChannel", eventChannel);
        modelAndView.addObject("channel", msgChannel);
        return modelAndView;
    }
}
