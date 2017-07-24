package com.party.core.service.wechat.impl;

import com.google.common.base.Strings;
import com.party.common.paging.Page;
import com.party.core.dao.read.wechat.WechatMessageReadDao;
import com.party.core.dao.write.wechat.WechatMessageWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.wechat.WechatMessage;
import com.party.core.service.wechat.IWechatMessageService;
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
 * 微信消息服务实现
 * party
 * Created by wei.li
 * on 2016/9/13 0013.
 */

@Service
public class WechatMessageService implements IWechatMessageService {

    @Autowired
    WechatMessageReadDao wechatMessageReadDao;

    @Autowired
    WechatMessageWriteDao wechatMessageWriteDao;

    /**
     * 微信消息插入
     * @param wechatMessage 微信消息
     * @return 插入结果（true/false）
     */
    @Override
    public String insert(WechatMessage wechatMessage) {
        BaseModel.preInsert(wechatMessage);
        boolean result = wechatMessageWriteDao.insert(wechatMessage);
        if (result){
            return wechatMessage.getId();
        }
        return null;
    }


    /**
     * 微信消息更新
     * @param wechatMessage 微信消息
     * @return 更新结果（true/false）
     */
    @Override
    public boolean update(WechatMessage wechatMessage) {
        wechatMessage.setUpdateDate(new Date());
        return wechatMessageWriteDao.update(wechatMessage);
    }


    /**
     * 微信消息逻辑删除
     * @param id 实体主键
     * @return 删除结果（true/false）
     */
    @Override
    public boolean deleteLogic(@NotNull String id) {

        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return wechatMessageWriteDao.deleteLogic(id);
    }


    /**
     * 微信消息物理删除
     * @param id 实体主键
     * @return 删除结果（true/false）
     */
    @Override
    public boolean delete(@NotNull String id) {

        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return wechatMessageWriteDao.delete(id);
    }


    /**
     * 微信消息逻辑批量删除
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    @Override
    public boolean batchDeleteLogic(@NotNull Set<String> ids) {

        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return wechatMessageWriteDao.batchDeleteLogic(ids);
    }

    /**
     * 微信消息物理逻辑删除
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    @Override
    public boolean batchDelete(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return wechatMessageWriteDao.batchDelete(ids);
    }


    /**
     * 根据主键获取微信消息
     * @param id 主键
     * @return 微信消息
     */
    @Override
    public WechatMessage get(String id) {
        return wechatMessageReadDao.get(id);
    }


    /**
     * 分页查询微信消息
     * @param wechatMessage 微信消息
     * @param page 分页信息
     * @return 微信消息列表
     */
    @Override
    public List<WechatMessage> listPage(WechatMessage wechatMessage, Page page) {
        return wechatMessageReadDao.listPage(wechatMessage, page);
    }

    /**
     * 查询所有微信消息
     * @param wechatMessage
     * @return 微信消息列表
     */
    @Override
    public List<WechatMessage> list(WechatMessage wechatMessage) {
        return wechatMessageReadDao.listPage(wechatMessage, null);
    }

    /**
     * 批量查询微信消息
     * @param ids 主键集合
     * @param wechatMessage
     * @param page 分页信息
     * @return 微信消息列表
     */
    @Override
    public List<WechatMessage> batchList(@NotNull Set<String> ids, WechatMessage wechatMessage, Page page) {
        if (CollectionUtils.isEmpty(ids)){
            return Collections.EMPTY_LIST;
        }
        return wechatMessageReadDao.batchList(ids, new HashedMap(), page);
    }
}
