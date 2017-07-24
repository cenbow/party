package com.party.core.service.message.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.party.common.paging.Page;
import com.party.core.dao.read.message.MessageSetReadDao;
import com.party.core.dao.write.message.MessageSetWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.member.Member;
import com.party.core.model.message.MessageSet;
import com.party.core.service.message.IMessageSetService;
import com.sun.istack.NotNull;

/**
 * 消息设置服务实现
 * party
 * Created by wei.li
 * on 2016/8/12 0012.
 */

@Service
public class MessageSetService implements IMessageSetService{

    @Autowired
    private MessageSetReadDao messageSetReadDao;

    @Autowired
    private MessageSetWriteDao messageSetWriteDao;

    /**
     * 消息设置插入
     * @param messageSet 消息设置信息
     * @return 插入结果（true/false）
     */
    public String insert(MessageSet messageSet) {
        BaseModel.preInsert(messageSet);
        boolean result = messageSetWriteDao.insert(messageSet);
        if (result){
            return messageSet.getId();
        }
        return null;
    }


    /**
     * 消息设置更新
     * @param messageSet 消息设置信息
     * @return 更新结果（true/false）
     */
    public boolean update(MessageSet messageSet) {
        messageSet.setUpdateDate(new Date());
        return messageSetWriteDao.update(messageSet);
    }

    /**
     * 逻辑删除消息设置
     * @param id 消息设置主键
     * @return 删除结果（true/false）
     */
    public boolean deleteLogic(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return messageSetWriteDao.deleteLogic(id);
    }

    /**
     * 物理删除消息设置
     * @param id 消息设置主键
     * @return 删除结果（true/false）
     */
    public boolean delete(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return messageSetWriteDao.delete(id);
    }

    /**
     * 批量逻辑删除消息设置
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    public boolean batchDeleteLogic(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return messageSetWriteDao.batchDeleteLogic(ids);
    }

    /**
     * 批量物理删除消息设置
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    public boolean batchDelete(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return messageSetWriteDao.batchDelete(ids);
    }

    /**
     * 根据主键获取消息设置信息
     * @param id 主键
     * @return 消息设置信息
     */
    public MessageSet get(String id) {
        return messageSetReadDao.get(id);
    }

    /**
     * 分页查询消息设置
     * @param messageSet 消息设置信息
     * @param page 分页信息
     * @return 消息设置列表
     */
    public List<MessageSet> listPage(MessageSet messageSet, Page page) {
        return messageSetReadDao.listPage(messageSet, page);
    }

    /**
     * 查询所以消息设置信息
     * @param messageSet 消息设置信息
     * @return 消息设置列表
     */
    public List<MessageSet> list(MessageSet messageSet) {
        return messageSetReadDao.listPage(messageSet, null);
    }



    /**
     * 批量查询消息设置信息
     * @param ids 主键集合
     * @param messageSet 消息设置信息
     * @param page 分页信息
     * @return 消息设置列表
     */
    public List<MessageSet> batchList(@NotNull Set<String> ids, MessageSet messageSet, Page page) {
        if (CollectionUtils.isEmpty(ids)){
            return Collections.EMPTY_LIST;
        }
        return messageSetReadDao.batchList(ids, new HashedMap(), page);
    }

    @Override
    public MessageSet findByMemberId(@NotNull String memberId) {
        MessageSet messageSet = new MessageSet();
        messageSet.setMemberId(memberId);
        return messageSetReadDao.findByMemberId(messageSet);
    }


	@Override
	public void initMessageSet(Member member) {
		MessageSet messageSet = this.findByMemberId(member.getId());
		if (null == messageSet)
		{
			messageSet = new MessageSet();
			messageSet.setLoveTip(1);
			messageSet.setCommentTip(1);
			messageSet.setFocusTip(1);
			messageSet.setSysTip(1);
			messageSet.setActTip(1);
			messageSet.setGoodsTip(1);
			messageSet.setMemberId(member.getId());
			this.insert(messageSet);//保存push消息提示
		}
	}
}
