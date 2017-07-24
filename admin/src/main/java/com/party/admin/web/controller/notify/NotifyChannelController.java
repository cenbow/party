package com.party.admin.web.controller.notify;

import com.google.common.base.Strings;
import com.party.admin.web.dto.AjaxResult;
import com.party.common.paging.Page;
import com.party.core.exception.BusinessException;
import com.party.core.model.notify.MsgChannel;
import com.party.core.model.notify.RelationWithChannel;
import com.party.core.service.notify.IEventChannelService;
import com.party.core.service.notify.IMsgChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * 消息通道控制器
 * Created by wei.li
 *
 * @date 2017/4/11 0011
 * @time 11:36
 */
@Controller
@RequestMapping(value = "/notify/channel")
public class NotifyChannelController {

    @Autowired
    private IMsgChannelService msgChannelService;

    @Autowired
    private IEventChannelService eventChannelService;

    /**
     * 通道列表
     * @param channel 通道
     * @param page 分页参数
     * @return 视图
     */
    @RequestMapping(value = "list")
    public ModelAndView list(MsgChannel channel, Page page){
        ModelAndView modelAndView = new ModelAndView("notify/channelList");
        List<MsgChannel> channelList = msgChannelService.listPage(channel, page);
        modelAndView.addObject("list", channelList);
        modelAndView.addObject("page", page);
        return modelAndView;
    }

    /**
     * 通道查看
     * @param id 编号
     * @return 视图
     */
    @RequestMapping(value = "view")
    public ModelAndView view(String id){
        ModelAndView modelAndView = new ModelAndView("/notify/channelForm");
        MsgChannel msgChannel;
        if (Strings.isNullOrEmpty(id)){
            msgChannel = new MsgChannel();
        }
        else {
            msgChannel = msgChannelService.get(id);
        }
        modelAndView.addObject("channel", msgChannel);
        return modelAndView;
    }

    /**
     * 通道保存
     * @param channel 通道
     * @return 视图
     */
    @ResponseBody
    @RequestMapping(value = "save")
    public AjaxResult save(MsgChannel channel){
        AjaxResult ajaxResult = new AjaxResult();
        try {
            if (Strings.isNullOrEmpty(channel.getId())){
                msgChannelService.insert(channel);
            }
            else {
                msgChannelService.update(channel);
            }
        } catch (BusinessException be) {
            be.printStackTrace();
            ajaxResult.setDescription(be.getMessage());
            return ajaxResult;
        }
        ajaxResult.setSuccess(true);
        return ajaxResult;
    }

    /**
     * 查询事件下关系
     * @param eventId 事件编号
     * @param page 分页参数
     * @return 交互数据
     */
    @RequestMapping(value = "listForEven")
    public ModelAndView listForEven(String eventId, Page page){
        ModelAndView modelAndView = new ModelAndView("notify/distributionList");
        RelationWithChannel relationWithChannel = new RelationWithChannel();
        relationWithChannel.setEventId(eventId);
        List<RelationWithChannel> list = eventChannelService.withChannelList(relationWithChannel, page);
        modelAndView.addObject("list", list);
        modelAndView.addObject("page", page);
        return modelAndView;
    }
}
