package com.party.core.service.message.impl;

import com.google.common.base.Strings;
import com.party.common.paging.Page;
import com.party.core.dao.read.message.SysMessageReadDao;
import com.party.core.dao.write.message.SysMessageWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.message.SysMessage;
import com.party.core.service.message.ISysMessageService;
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
 * 后台消息实现
 * party
 * Created by wei.li
 * on 2016/8/23 0023.
 */

@Service
public class SysMessageService implements ISysMessageService{

    @Autowired
    private SysMessageReadDao sysMessageReadDao;

    @Autowired
    private SysMessageWriteDao sysMessageWriteDao;

    /**
     * 后台系统消息插入
     * @param sysMessage 后台系统消息
     * @return 插入结果（true/false）
     */
    @Override
    public String insert(SysMessage sysMessage) {
        BaseModel.preInsert(sysMessage);
        boolean result = sysMessageWriteDao.insert(sysMessage);
        if(result){
            return sysMessage.getId();
        }
        return null;
    }


    /**
     * 后台系统消息更新
     * @param sysMessage 后台系统消息
     * @return 更新结果（true/false）
     */
    @Override
    public boolean update(SysMessage sysMessage) {
        sysMessage.setUpdateDate(new Date());
        return sysMessageWriteDao.update(sysMessage);
    }


    /**
     * 后台系统消息逻辑删除
     * @param id 实体主键
     * @return 删除结果(true/false)
     */
    @Override
    public boolean deleteLogic(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return sysMessageWriteDao.deleteLogic(id);
    }


    /**
     * 后台系统消息物理删除
     * @param id 实体主键
     * @return 删除结果（true/false）
     */
    @Override
    public boolean delete(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return sysMessageWriteDao.delete(id);
    }

    /**
     * 后台系统消息批量逻辑删除
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    @Override
    public boolean batchDeleteLogic(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return sysMessageWriteDao.batchDeleteLogic(ids);
    }

    /**
     * 后台系统消息批量物理删除
     * @param ids 主键集合
     * @return 删除结果(true/false)
     */
    @Override
    public boolean batchDelete(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return sysMessageWriteDao.batchDelete(ids);
    }

    /**
     * 根据主键获取系统后台消息
     * @param id 主键
     * @return 系统后台消息
     */
    @Override
    public SysMessage get(String id) {
        return sysMessageReadDao.get(id);
    }

    /**
     * 分页查询系统系统后台消息
     * @param sysMessage 系统后台消息
     * @param page 分页信息
     * @return 后台系统消息列表
     */
    @Override
    public List<SysMessage> listPage(SysMessage sysMessage, Page page) {
        return sysMessageReadDao.listPage(sysMessage, page);
    }


    /**
     * 查询所有系统后台消息
     * @param sysMessage 系统后台消息
     * @return 后台系统消息列表
     */
    @Override
    public List<SysMessage> list(SysMessage sysMessage) {
        return sysMessageReadDao.listPage(sysMessage, null);
    }

    /**
     * 批量查询系统后台消息
     * @param ids 主键集合
     * @param sysMessage 系统后台消息
     * @param page 分页信息
     * @return 系统消息列表
     */
    @Override
    public List<SysMessage> batchList(@NotNull Set<String> ids, SysMessage sysMessage, Page page) {
        if (CollectionUtils.isEmpty(ids)){
            return Collections.EMPTY_LIST;
        }
        return sysMessageReadDao.batchList(ids, new HashedMap(), page);
    }
}
