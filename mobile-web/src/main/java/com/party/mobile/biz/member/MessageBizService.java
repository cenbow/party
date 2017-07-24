package com.party.mobile.biz.member;

import com.party.common.paging.Page;
import com.party.common.utils.LangUtils;
import com.party.core.exception.BusinessException;
import com.party.core.model.message.Message;
import com.party.core.model.message.MessageStatus;
import com.party.core.model.message.MessageType;
import com.party.core.service.activity.IActivityService;
import com.party.core.service.goods.IGoodsService;
import com.party.core.service.member.IMemberService;
import com.party.core.service.message.IMessageService;
import com.party.core.service.message.IMessageSetService;
import com.party.mobile.biz.currentUser.CurrentUserBizService;
import com.party.mobile.biz.dynamic.DynamicBizService;
import com.party.mobile.web.dto.login.output.CurrentUser;
import com.party.mobile.web.dto.member.output.GoodsMessageOut;
import com.party.mobile.web.dto.member.output.MessageOut;
import com.party.mobile.web.dto.member.output.NewMessageOut;
import com.party.common.utils.PartyCode;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

import java.util.Collections;
import java.util.List;

/**
 * 会员消息业务service
 *
 * @Author: Wesley
 * @Description:
 * @Date: Created in 16:51 16/11/6
 * @Modified by:
 */
@Service
public class MessageBizService {
	@Autowired
	IMessageSetService messageSetService;

	@Autowired
	IMessageService messageService;

	@Autowired
	CurrentUserBizService currentUserBizService;

	@Autowired
	IMemberService memberService;

	@Autowired
	IActivityService activityService;

	@Autowired
	IGoodsService goodsService;

	@Autowired
	DynamicBizService dynamicBizService;

	/**
	 * 根据消息类型查找相应的消息列表业务
	 * 
	 * @param type
	 * @param page
	 * @param request
	 * @return
	 */
	@Transactional
	public List<MessageOut> listMessage(String type, Page page,
			HttpServletRequest request) {
		CurrentUser currentUser = currentUserBizService.getCurrentUser(request);

		Message message = new Message();
		message.setMessageType(type);
		message.setCreateBy(currentUser.getId());

		List<Message> messageList = messageService.listPage(message, page);

		if (!CollectionUtils.isEmpty(messageList)) {
			List<MessageOut> messageOutList = LangUtils
					.transform(
							messageList,
							input -> {
								MessageOut messageOut = MessageOut
										.transform(input);
								if (input.getMessageType().equals(
										MessageType.COMMENT.getCode())
										|| input.getMessageType().equals(
												MessageType.LOVE.getCode())
										|| input.getMessageType().equals(
												MessageType.FOCUS.getCode())) {
									// 修改消息状态为已读
									// TODO 循环io，需优化
									if (input.getIsNew() == MessageStatus.MESSAGE_STATUS_NEW
											.getCode()) {
										input.setIsNew(MessageStatus.MESSAGE_STATUS_READ
												.getCode());
										messageService.updateRead(input);
									}
									messageOut.setMember(dynamicBizService
											.getDyMember(input.getMemberId(),
													currentUser.getId()));
								}
								return messageOut;
							});
			return messageOutList;
		}
		return Collections.EMPTY_LIST;
	}

	/**
	 * 根据消息类型查找相应的消息列表业务(商品，活动消息)
	 * 
	 * @param type
	 * @param page
	 * @param request
	 * @return
	 */
	@Transactional
	public List<GoodsMessageOut> listGoodsMessage(String type, Page page,
			HttpServletRequest request) {
		CurrentUser currentUser = currentUserBizService.getCurrentUser(request);

		Message message = new Message();
		message.setMessageType(type);
		message.setCreateBy(currentUser.getId());

		List<Message> messageList = messageService.listPage(message, page);

		if (!CollectionUtils.isEmpty(messageList)) {
			List<GoodsMessageOut> messageOutList = LangUtils
					.transform(
							messageList,
							input -> {
								GoodsMessageOut messageOut = GoodsMessageOut
										.transform(input);
								return messageOut;
							});
			return messageOutList;
		}
		return Collections.EMPTY_LIST;
	}

	/**
	 * 获取用户新消息条数业务
	 * 
	 * @param request
	 * @return
	 */
	@Transactional
	public NewMessageOut getAllNewMsg(HttpServletRequest request) {
		CurrentUser currentUser = currentUserBizService.getCurrentUser(request);
		Message message = new Message();		
		message.setCreateBy(currentUser.getId());
		NewMessageOut newMessageOut = new NewMessageOut();
		for (MessageType type : MessageType.values()) {
			message.setMessageType(type.getCode());
			message.setIsNew(MessageStatus.MESSAGE_STATUS_NEW.getCode());
			Integer count = messageService.getCount(message);
			if (MessageType.GOODS.getCode().equals(type.getCode())) {
				newMessageOut.setGoodsMsgNum(count);
				message.setIsNew(null);
				Message messagedb = messageService.find(message);
				if(null == messagedb)
					messagedb = new Message();
				newMessageOut.setGoodsMsg(MessageOut.transform(messagedb));
			} else if (MessageType.ACT.getCode().equals(type.getCode())) {				
				newMessageOut.setActMsgNum(count);
				message.setIsNew(null);
				Message messagedb = messageService.find(message);
				if(null == messagedb)
					messagedb = new Message();
				newMessageOut.setActMsg(MessageOut.transform(messagedb));
			} else if (MessageType.SYS.getCode().equals(type.getCode())) {
				newMessageOut.setSysMsgNum(count);
				message.setIsNew(null);
				Message messagedb = messageService.find(message);
				if(null == messagedb)
					messagedb = new Message();
				newMessageOut.setSysMsg(MessageOut.transform(messagedb));
			} else if (MessageType.LOVE.getCode().equals(type.getCode())) {
				newMessageOut.setLoveMsgNum(count);
			} else if (MessageType.COMMENT.getCode().equals(type.getCode())) {
				newMessageOut.setCommentMsgNum(count);
			} else if (MessageType.FOCUS.getCode().equals(type.getCode())) {
				newMessageOut.setFocusMsgNum(count);
			}
		}
		return newMessageOut;
	}

	/**
	 * 逻辑删除消息业务
	 * 
	 * @param id
	 * @return
	 */
	@Transactional
	public boolean delMsg(String id, HttpServletRequest request)
			throws BusinessException {
		CurrentUser currentUser = currentUserBizService.getCurrentUser(request);
		Message dbMessage = messageService.get(id);

		if (null == dbMessage) {
			throw new BusinessException(PartyCode.MSG_NON_EXISTENTT, "消息不存在");
		}

		if (!currentUser.getId().equals(dbMessage.getCreateBy())) {
			throw new BusinessException(PartyCode.DEL_MSG_ERROR,
					"删除消息错误，消息主键id错误");
		}

		boolean bResult = messageService.deleteLogic(id);
		return bResult;
	}
	/**
	 * 设置已读
	 */
	@Transactional
	public boolean setTypeIsReaded(Message message) {
		message.setIsNew(MessageStatus.MESSAGE_STATUS_NEW.getCode());		
		List<Message> messageList = messageService.list(message);
		for(Message m:messageList){
			m.setIsNew(MessageStatus.MESSAGE_STATUS_READ.getCode());
			messageService.update(m);
		}
		return true;
	}
	
	@Transactional
	public boolean setIsReaded(String ids) {
		String[] idArray = ids.split(",");
		for(String id : idArray){
			Message dbMessage = messageService.get(id);
			if (null == dbMessage) {
				throw new BusinessException(PartyCode.MSG_NON_EXISTENTT, "消息不存在");
			}else{
				dbMessage.setIsNew(MessageStatus.MESSAGE_STATUS_READ.getCode());
				messageService.update(dbMessage);
			}
		}
		return true;
	}

}
