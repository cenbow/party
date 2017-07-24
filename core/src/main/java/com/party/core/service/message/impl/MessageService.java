package com.party.core.service.message.impl;

import com.google.common.base.Strings;
import com.party.common.paging.Page;
import com.party.core.dao.read.message.MessageReadDao;
import com.party.core.dao.write.message.MessageWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.member.Member;
import com.party.core.model.message.Message;
import com.party.core.model.message.MessageStatus;
import com.party.core.model.message.MessageType;
import com.party.core.model.message.SysMessageType;
import com.party.core.service.message.IMessageService;
import com.sun.istack.NotNull;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 会员消息服务实现
 * party
 * Created by wei.li
 * on 2016/9/18 0018.
 */

@Service
public class MessageService implements IMessageService {

    @Autowired
    MessageReadDao messageReadDao;

    @Autowired
    MessageWriteDao messageWriteDao;

    /**
     * 插入会员消息
     * @param message 会员消息
     * @return 插入结果（true/false）
     */
    @Override
    public String insert(Message message) {
        BaseModel.preInsert(message);
        boolean result = messageWriteDao.insert(message);
        if (result){
            return message.getId();
        }
        return null;
    }

    /**
     * 更新会员消息
     * @param message 会员消息
     * @return 更新结果（true/false）
     */
    @Override
    public boolean update(Message message) {
        message.setUpdateDate(new Date());
        return messageWriteDao.update(message);
    }

    /**
     * 逻辑删除会员消息
     * @param id 实体主键
     * @return 删除结果（true/false）
     */
    @Override
    public boolean deleteLogic(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return messageWriteDao.deleteLogic(id);
    }

    /**
     * 物理删除会员消息
     * @param id 实体主键
     * @return 删除结果（true/false）
     */
    @Override
    public boolean delete(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return messageWriteDao.delete(id);
    }

    /**
     * 批量逻辑删除会员消息
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    @Override
    public boolean batchDeleteLogic(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return messageWriteDao.batchDeleteLogic(ids);
    }

    /**
     * 批量物理删除会员消息
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    @Override
    public boolean batchDelete(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return messageWriteDao.batchDelete(ids);
    }

    /**
     * 根据主键获取会员消息
     * @param id 主键
     * @return 会员消息
     */
    @Override
    public Message get(String id) {
        return messageReadDao.get(id);
    }

    /**
     * 选择性查询消息
     * @param message 消息
     * @return 消息信息
     */
    @Override
    public Message find(Message message) {
        return messageReadDao.find(message);
    }

    @Override
    public boolean updateRead(Message message) {
        return messageWriteDao.updateRead(message);
    }

    /**
     * 分页查询会员消息
     * @param message 会员消息
     * @param page 分页信息
     * @return 会员消息列表
     */
    @Override
    public List<Message> listPage(Message message, Page page) {
        return messageReadDao.listPage(message, page);
    }

    /**
     * 查询所有的会员消息
     * @param message 会员消息
     * @return 会员消息列表
     */
    @Override
    public List<Message> list(Message message) {
        return messageReadDao.listPage(message, null);
    }

    /**
     * 批量查询会员消息
     * @param ids 主键集合
     * @param message 会员消息
     * @param page 分页信息
     * @return 会员消息列表
     */
    @Override
    public List<Message> batchList(@NotNull Set<String> ids, Message message, Page page) {
        if (CollectionUtils.isEmpty(ids)){
            return Collections.EMPTY_LIST;
        }
        return messageReadDao.batchList(ids, new HashedMap(), page);
    }

    /**
     * 存储消息
     * @param title，消息title
     * @param logo，	 消息图标
     * @param picUrl，封面图片
     * @param content，内容
     * @param messageType，消息类型
     * @param relId，消息关联id
     * @param author, 消息作者
     * @param member，推送用户
     * @param tag，标签
     */
    @Override
    public void saveAppMessage(String title, String logo, String picUrl, String content, String messageType,
                               String relId, Member author, Member member, String tag) {
        Message message = new Message();
        message.setTitle(title);
        message.setTag(tag);
        message.setMessageType(messageType);
        message.setLogo(logo);
        message.setPicUrl(picUrl);
        message.setContent(content);
        message.setRelId(relId);
        message.setCreateBy(author.getId());
        message.setUpdateBy(author.getId());
        message.setMemberId(member.getId());
        message.setIsNew(MessageStatus.MESSAGE_STATUS_NEW.getCode());

        //关注消息只生成一次入库
        if (MessageType.FOCUS.getCode().equals(messageType)  && null != find(message)) {
            return;
        }
        if (MessageType.LOVE.getCode().equals(messageType)  && null != find(message))
        {
            return;
        }
        insert(message);
    }

    /**
     * 存储商品或者活动消息
     * @param title，消息title
     * @param logo，	 消息图标
     * @param picUrl，封面图片
     * @param content，内容
     * @param messageType，消息类型
     * @param relId，消息关联id
     * @param author, 消息作者
     * @param member，推送用户
     * @param tag，标签
     * @param orderId，商品或者活动的订单id
     * @param orderStatus ，商品或者活动的订单状态
     */
    @Override
    public void saveAppMessage(String title, String logo, String picUrl, String content, String messageType, String relId, Member author, Member member, String tag, String orderId, Integer orderStatus) {
        Message message = new Message();
        message.setTitle(title);
        message.setTag(tag);
        message.setMessageType(messageType);
        message.setLogo(logo);
        message.setPicUrl(picUrl);
        message.setContent(content);
        message.setRelId(relId);
        message.setCreateBy(author.getId());
        message.setUpdateBy(author.getId());
        message.setMemberId(member.getId());
        message.setIsNew(MessageStatus.MESSAGE_STATUS_NEW.getCode());

        message.setOrderId(orderId);
        message.setOrderStatus(orderStatus);

        //关注消息只生成一次入库
        if (MessageType.FOCUS.getCode().equals(messageType)  && null != find(message)) {
            return;
        }
        if (MessageType.LOVE.getCode().equals(messageType)  && null != find(message))
        {
            return;
        }
        insert(message);
    }

	@Override
	public Integer getCount(Message message) {
		return messageReadDao.getCount(message);
	}
}
