package com.party.core.service.message.impl;

import com.google.common.base.Strings;
import com.party.common.paging.Page;
import com.party.core.dao.read.message.JpushMessageReadDao;
import com.party.core.dao.write.message.JpushMessageWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.message.JpushMessage;
import com.party.core.service.message.IJpushMessageService;
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
 * push 消息服务实现
 * party
 * Created by wei.li
 * on 2016/9/19 0019.
 */

@Service
public class JpushMessageService implements IJpushMessageService {

    @Autowired
    JpushMessageReadDao jpushMessageReadDao;

    @Autowired
    JpushMessageWriteDao jpushMessageWriteDao;

    /**
     * 插入jpush消息
     * @param jpushMessage jpush消息
     * @return 插入结果（true/false）
     */
    @Override
    public String insert(JpushMessage jpushMessage) {
        BaseModel.preInsert(jpushMessage);
        boolean result = jpushMessageWriteDao.insert(jpushMessage);
        if (result){
            return jpushMessage.getId();
        }
        return null;
    }


    /**
     * 更新jpush消息
     * @param jpushMessage jpush消息
     * @return 更新结果（true/false）
     */
    @Override
    public boolean update(JpushMessage jpushMessage) {
        jpushMessage.setUpdateDate(new Date());
        return jpushMessageWriteDao.update(jpushMessage);
    }

    /**
     * 逻辑删除jpush消息
     * @param id 实体主键
     * @return 删除结果（true/false）
     */
    @Override
    public boolean deleteLogic(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return jpushMessageWriteDao.deleteLogic(id);
    }


    /**
     * 物理删除jpush消息
     * @param id 实体主键
     * @return 删除结果（true/false）
     */
    @Override
    public boolean delete(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return jpushMessageWriteDao.delete(id);
    }

    /**
     * 批量逻辑删除jpush消息
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    @Override
    public boolean batchDeleteLogic(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return jpushMessageWriteDao.batchDeleteLogic(ids);
    }

    /**
     * 批量物理删除jpush消息
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    @Override
    public boolean batchDelete(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return jpushMessageWriteDao.batchDelete(ids);
    }

    /**
     * 根据主键获取 jpush消息
     * @param id 主键
     * @return jpush消息
     */
    @Override
    public JpushMessage get(String id) {
        return jpushMessageReadDao.get(id);
    }

    /**
     * 分页查询jpush消息
     * @param jpushMessage jpush消息
     * @param page 分页信息
     * @return jpush消息列表
     */
    @Override
    public List<JpushMessage> listPage(JpushMessage jpushMessage, Page page) {
        return jpushMessageReadDao.listPage(jpushMessage, page);
    }

    /**
     * 查询所有jpush消息
     * @param jpushMessage jpush消息
     * @return jpush消息列表
     */
    @Override
    public List<JpushMessage> list(JpushMessage jpushMessage) {
        return jpushMessageReadDao.listPage(jpushMessage, null);
    }


    /**
     * 批量查询jpush消息
     * @param ids 主键集合
     * @param jpushMessage jpush消息
     * @param page 分页信息
     * @return jpush消息列表
     */
    @Override
    public List<JpushMessage> batchList(@NotNull Set<String> ids, JpushMessage jpushMessage, Page page) {
        if (CollectionUtils.isEmpty(ids)){
            return Collections.EMPTY_LIST;
        }
        return jpushMessageReadDao.batchList(ids, new HashedMap(), page);
    }
}
