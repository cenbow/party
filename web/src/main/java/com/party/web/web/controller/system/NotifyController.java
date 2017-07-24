package com.party.web.web.controller.system;

import com.google.common.collect.Lists;
import com.party.common.paging.Page;
import com.party.common.utils.DateUtils;
import com.party.common.utils.StringUtils;
import com.party.core.model.YesNoStatus;
import com.party.core.model.message.Message;
import com.party.core.model.message.MessageType;
import com.party.core.service.message.IMessageService;
import com.party.core.utils.MyBeanUtils;
import com.party.web.utils.RealmUtils;
import com.party.web.web.dto.AjaxResult;
import com.party.web.web.dto.output.index.MessageOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * 消息管理
 */
@Controller
@RequestMapping("system/notify")
public class NotifyController {

    @Autowired
    private IMessageService messageService;

    /**
     * 获取系统通知数字
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/getNotifyCount")
    public AjaxResult getNotifyCount() {
        String memberId = RealmUtils.getCurrentUser().getId();
        Message message = new Message();
        message.setCreateBy(memberId);
        message.setIsNew(YesNoStatus.YES.getCode());
        message.setMessageType(MessageType.SYS.getCode());
        Integer count = messageService.getCount(message);
        return AjaxResult.success(count);
    }

    /**
     * 系统通知列表
     *
     * @param message
     * @param page
     * @return
     */
    @RequestMapping("/getNotifyList")
    public ModelAndView getNotifyList(Message message, Page page) throws Exception {
        page.setLimit(12);
        String memberId = RealmUtils.getCurrentUser().getId();
        message.setCreateBy(memberId);
        message.setMessageType(MessageType.SYS.getCode());
        List<Message> messages = messageService.listPage(message, page);
        List<MessageOutput> outputs = Lists.newArrayList();
        for (Message m : messages) {
            m.setIsNew(YesNoStatus.NO.getCode());
            messageService.updateRead(m);

            MessageOutput output = new MessageOutput();
            MyBeanUtils.copyBeanNotNull2Bean(m, output);
            String dateDiff = DateUtils.getDateDiff(m.getUpdateDate());
            output.setDateDiff(dateDiff);
            outputs.add(output);
        }
        ModelAndView mv = new ModelAndView("system/notify/systemNotifyList");
        mv.addObject("messages", outputs);
        mv.addObject("page", page);
        return mv;
    }

    /**
     * 删除所有消息
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("deleteAllNotify")
    public AjaxResult deleteAllNotify() {
        try {
            String memberId = RealmUtils.getCurrentUser().getId();
            Message message = new Message();
            message.setCreateBy(memberId);
            message.setMessageType(MessageType.SYS.getCode());
            List<Message> messages = messageService.listPage(message, null);
            for (Message dbMessage : messages) {
                messageService.delete(dbMessage.getId());
            }
            return AjaxResult.success();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 删除消息
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("deleteNotify")
    public AjaxResult deleteNotify(String id) {
        if (StringUtils.isEmpty(id)) {
            return AjaxResult.error("消息id不能为空");
        }

        messageService.delete(id);
        return AjaxResult.success();
    }
}
