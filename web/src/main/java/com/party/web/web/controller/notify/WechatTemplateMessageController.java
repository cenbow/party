package com.party.web.web.controller.notify;

import com.google.common.base.Strings;
import com.party.common.constant.Constant;
import com.party.common.paging.Page;
import com.party.core.model.member.Member;
import com.party.core.model.notify.RelationWithChannel;
import com.party.core.model.wechat.WechatTemplateMassage;
import com.party.core.model.wechat.WechatTemplateMessageWithEvent;
import com.party.core.service.wechat.IWechatTemplateMassageService;
import com.party.web.biz.notify.WechatTemplateMessageBizService;
import com.party.web.utils.RealmUtils;
import com.party.web.web.dto.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * 微信模板消息控制器
 * Created by wei.li
 *
 * @date 2017/5/23 0023
 * @time 15:03
 */

@Controller
@RequestMapping(value = "/notify/wechat/")
public class WechatTemplateMessageController {

    @Autowired
    private IWechatTemplateMassageService wechatTemplateMassageService;

    @Autowired
    private WechatTemplateMessageBizService wechatTemplateMessageBizService;


    /**
     * 微信模板消息列表
     * @param wechatTemplateMessageWithEvent 微信模板消息
     * @param page 分页参数
     * @return 交互数据
     */
    @RequestMapping(value = "list")
    public ModelAndView list(WechatTemplateMessageWithEvent wechatTemplateMessageWithEvent, Page page){
        page.setLimit(10);
        ModelAndView modelAndView = new ModelAndView("notify/wechatList");
        Member member = RealmUtils.getCurrentUser();
        wechatTemplateMessageWithEvent.setType(Constant.WECHAT_ACCOUNT_TYPE_PARTNER);
        wechatTemplateMessageWithEvent.setMemberId(member.getId());
        List<WechatTemplateMessageWithEvent> list
                = wechatTemplateMassageService.withEventList(wechatTemplateMessageWithEvent, page);
        modelAndView.addObject("list", list);
        modelAndView.addObject("page", page);
        return modelAndView;
    }

    /**
     * 查看微信模板消息
     * @param id 编号
     * @return 模板消息
     */
    @RequestMapping(value = "view")
    public ModelAndView view(String id){
        ModelAndView modelAndView = new ModelAndView("notify/wechatView");
        WechatTemplateMassage wechatTemplateMassage;
        List<RelationWithChannel> channelList;
        if (Strings.isNullOrEmpty(id)){
            wechatTemplateMassage = new WechatTemplateMassage();
            channelList = wechatTemplateMessageBizService.myEventList();
        }
        else {
            wechatTemplateMassage = wechatTemplateMassageService.get(id);
            channelList = wechatTemplateMessageBizService.myEventList(id);
        }
        modelAndView.addObject("message", wechatTemplateMassage);
        modelAndView.addObject("channelList", channelList);
        return modelAndView;
    }


    /**
     * 保存微信模板消息
     * @param wechatTemplateMassage 微信模板消息
     * @return 交互数据
     */
    @ResponseBody
    @RequestMapping(value = "save")
    public AjaxResult save(WechatTemplateMassage wechatTemplateMassage){
        AjaxResult ajaxResult = new AjaxResult();
        if (Strings.isNullOrEmpty(wechatTemplateMassage.getId())){
            Member member = RealmUtils.getCurrentUser();
            wechatTemplateMassage.setCreateBy(member.getId());
            wechatTemplateMassage.setUpdateBy(member.getId());
            wechatTemplateMassage.setMemberId(member.getId());
            wechatTemplateMassage.setType(Constant.WECHAT_ACCOUNT_TYPE_PARTNER);
            wechatTemplateMassageService.insert(wechatTemplateMassage);
        }
        else {
            wechatTemplateMassageService.update(wechatTemplateMassage);
        }
        ajaxResult.setSuccess(true);
        return ajaxResult;
    }

    /**
     * 删除微信模板消息
     * @param id 消息编号
     * @return 交互数据
     */
    @ResponseBody
    @RequestMapping(value = "delete")
    public AjaxResult delete(String id){
        AjaxResult ajaxResult = new AjaxResult();
        wechatTemplateMassageService.delete(id);
        ajaxResult.setSuccess(true);
        return ajaxResult;
    }
}
