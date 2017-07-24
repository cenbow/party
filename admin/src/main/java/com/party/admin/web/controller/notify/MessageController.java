package com.party.admin.web.controller.notify;

import com.party.admin.utils.RealmUtils;
import com.party.common.constant.Constant;
import com.party.common.paging.Page;
import com.party.core.model.member.Member;
import com.party.core.model.notify.Instance;
import com.party.core.model.notify.InstanceWithMember;
import com.party.core.service.notify.IInstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * 短信控制器
 * Created by wei.li
 *
 * @date 2017/7/19 0019
 * @time 16:23
 */

@Controller
@RequestMapping(value = "notify/message")
public class MessageController {

    @Autowired
    private IInstanceService instanceService;

    /**
     * 消息列表
     * @param instance 实例
     * @param page 分页参数
     * @return 交互数据
     */
    @RequestMapping(value = "list")
    public ModelAndView list(Instance instance, Page page){
        page.setLimit(20);
        ModelAndView modelAndView = new ModelAndView("/crowdfund/messageList");
        instance.setChannelType(Constant.MESSAGE_CHANNEL_SMS);
        List<Instance> list = instanceService.listPage(instance, page);
        modelAndView.addObject("list", list);
        modelAndView.addObject("page", page);
        return modelAndView;
    }


    /**
     * 消息详情
     * @param id 编号
     * @return 交互数据
     */
    @RequestMapping(value = "view")
    public ModelAndView view(String id, String targetId){
        ModelAndView modelAndView = new ModelAndView("/crowdfund/messageConstant");
        Instance instance =instanceService.get(id);
        modelAndView.addObject("message", instance);
        modelAndView.addObject("targetId", targetId);
        return modelAndView;
    }


    /**
     * 查询所有消息列表
     * @param instanceWithMember 消息
     * @param page 分页参数
     * @return 消息列表
     */
    @RequestMapping(value = "listAll")
    public ModelAndView listAll(InstanceWithMember instanceWithMember, Page page){
        page.setLimit(20);
        ModelAndView modelAndView = new ModelAndView("notify/messageList");
        instanceWithMember.setChannelType(Constant.MESSAGE_CHANNEL_SMS);
        List<InstanceWithMember> list = instanceService.listWithMemberPage(instanceWithMember, page);
        modelAndView.addObject("list", list);
        modelAndView.addObject("page", page);
        return modelAndView;
    }


    /**
     * 消息详情
     * @param id 编号
     * @return 加护数据
     */
    @RequestMapping(value = "viewAll")
    public ModelAndView viewAll(String id){
        ModelAndView modelAndView = new ModelAndView("notify/messageConstant");
        Instance instance =instanceService.get(id);
        modelAndView.addObject("message", instance);
        return modelAndView;
    }
}
