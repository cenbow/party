package com.party.mobile.web.controller.member;

import com.google.common.base.Strings;
import com.party.authorization.annotation.Authorization;
import com.party.common.paging.Page;
import com.party.core.exception.BusinessException;
import com.party.core.model.message.Message;
import com.party.core.model.message.MessageType;
import com.party.mobile.biz.member.MessageBizService;
import com.party.mobile.web.dto.AjaxResult;
import com.party.mobile.web.dto.member.output.GoodsMessageOut;
import com.party.mobile.web.dto.member.output.MessageOut;
import com.party.mobile.web.dto.member.output.NewMessageOut;
import com.party.common.utils.PartyCode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

/**
 * 会员消息控制层
 *
 * @Author: Wesley
 * @Description:
 * @Date: Created in 16:52 16/11/6
 * @Modified by:
 */
@Controller
@RequestMapping(value = "member/message")
public class MessageController {

    @Autowired
    MessageBizService messageBizService;

    /**
     * 获取用户最新消息
     * @param request
     * @return 该用户每种消息的最新条数及系统，活动，预定三类消息的最新一条消息
     */
    @ResponseBody
    @Authorization
    @RequestMapping(value = "/getAllNewMsg")
    public AjaxResult getAllNewMsg(HttpServletRequest request)
    {
        NewMessageOut newMessageOut = messageBizService.getAllNewMsg(request);

        AjaxResult ajaxResult = AjaxResult.success();
        ajaxResult.setData(newMessageOut);
        return ajaxResult;
    }

    /**
     * 获取点赞消息列表
     * @param page
     * @param request
     * @return
     */
    @ResponseBody
    @Authorization
    @RequestMapping(value = "/listLoveMsg")
    public AjaxResult listLoveMsg(Page page, HttpServletRequest request)
    {
        List<MessageOut> messageOutList = messageBizService.listMessage(MessageType.LOVE.getCode(), page, request);

        return AjaxResult.success(messageOutList, page);
    }

    /**
     * 获取评论消息列表
     * @param page
     * @param request
     * @return
     */
    @ResponseBody
    @Authorization
    @RequestMapping(value = "/listCommentMsg")
    public AjaxResult listCommentMsg(Page page, HttpServletRequest request)
    {
        List<MessageOut> messageOutList = messageBizService.listMessage(MessageType.COMMENT.getCode(), page, request);

        return AjaxResult.success(messageOutList, page);
    }

    /**
     * 获取关注消息列表
     * @param page
     * @param request
     * @return
     */
    @ResponseBody
    @Authorization
    @RequestMapping(value = "/listFocusMsg")
    public AjaxResult listFocusMsg(Page page, HttpServletRequest request)
    {
        List<MessageOut> messageOutList = messageBizService.listMessage(MessageType.FOCUS.getCode(), page, request);

        return AjaxResult.success(messageOutList, page);
    }

    /**
     * 获取系统消息列表
     * @param page
     * @param request
     * @return
     */
    @ResponseBody
    @Authorization
    @RequestMapping(value = "/listSysMsg")
    public AjaxResult listSysMsg(Page page, HttpServletRequest request)
    {
        List<MessageOut> messageOutList = messageBizService.listMessage(MessageType.SYS.getCode(), page, request);

        return AjaxResult.success(messageOutList, page);
    }

    /**
     * 获取活动消息列表
     * @param page
     * @param request
     * @retur
     */
    @ResponseBody
    @Authorization
    @RequestMapping(value = "/listActMsg")
    public AjaxResult listActMsg(Page page, HttpServletRequest request)
    {
        List<GoodsMessageOut> messageOutList = messageBizService.listGoodsMessage(MessageType.ACT.getCode(), page, request);

        return AjaxResult.success(messageOutList, page);
    }

    /**
     * 获取商品消息列表
     * @param page
     * @param request
     * @return
     */
    @ResponseBody
    @Authorization
    @RequestMapping(value = "/listGoodsMsg")
    public AjaxResult listGoodsMsg(Page page, HttpServletRequest request)
    {
        List<GoodsMessageOut> messageOutList = messageBizService.listGoodsMessage(MessageType.GOODS.getCode(), page, request);

        return AjaxResult.success(messageOutList, page);
    }

    /**
     * 设置所有消息为已读状态
     * @param page
     * @param request
     * @return
     */
    @ResponseBody
    @Authorization
    @RequestMapping(value = "/setTypeIsReaded")
    public AjaxResult setTypeIsReaded(Message message, HttpServletRequest request)
    {
        messageBizService.setTypeIsReaded(message);
        return AjaxResult.success();
    }
    /**
     * 设置ids消息为已读状态
     * @return
     */
    @ResponseBody
    @Authorization
    @RequestMapping(value = "/setIsReaded")
    public AjaxResult setIsReaded(String ids, HttpServletRequest request)
    {
        messageBizService.setIsReaded(ids);
        return AjaxResult.success();
    }
    /**
     * 删除一条消息
     * @param id
     * @param request
     * @return
     */
    @ResponseBody
    @Authorization
    @RequestMapping(value = "/delMsg")
    public AjaxResult delMsg(String id, HttpServletRequest request)
    {
        //数据验证
        if (Strings.isNullOrEmpty(id)){
            return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, "消息主键不能为空");
        }

        boolean bResult = true;

        try {
            bResult = messageBizService.delMsg(id, request);
            if (bResult) {
                return AjaxResult.success();
            }
            else
            {
                return AjaxResult.error(PartyCode.DEL_MSG_FAIL, "删除消息失败");
            }
        } catch (BusinessException be) {
            return AjaxResult.error(be.getCode(), be.getMessage());
        }

    }
}
