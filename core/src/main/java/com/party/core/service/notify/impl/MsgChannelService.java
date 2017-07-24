package com.party.core.service.notify.impl;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.party.common.paging.Page;
import com.party.core.dao.read.notify.MsgChannelReadDao;
import com.party.core.dao.write.notify.MsgChannelWriteDao;
import com.party.core.exception.BusinessException;
import com.party.core.model.BaseModel;
import com.party.core.model.notify.MsgChannel;
import com.party.core.service.notify.IMsgChannelService;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 消息通道服务接口实现
 * Created by wei.li
 *
 * @date 2017/4/5 0005
 * @time 19:00
 */

@Service
public class MsgChannelService implements IMsgChannelService {

    @Autowired
    private MsgChannelReadDao msgChannelReadDao;


    @Autowired
    private MsgChannelWriteDao msgChannelWriteDao;


    /**
     * 消息通道插入
     * @param channel 消息通道
     * @return 编号
     */
    @Override
    public String insert(MsgChannel channel) {
        MsgChannel dbChannel = this.findByCode(channel.getCode());
        if (null != dbChannel){
            throw new BusinessException("消息通道代码已经存在");
        }
        BaseModel.preInsert(channel);
        boolean result = msgChannelWriteDao.insert(channel);
        if (result){
            return channel.getId();
        }
        return null;
    }

    /**
     * 消息通达更新
     * @param channel 消息通道
     * @return 更新结果（true/false）
     */
    @Override
    public boolean update(MsgChannel channel) {
        MsgChannel dbChannel = this.findByCode(channel.getCode());
        if (null != dbChannel && !dbChannel.getId().equals(channel.getId())){
            throw new BusinessException("消息通道代码已经存在");
        }
        return msgChannelWriteDao.update(channel);
    }

    /**
     * 逻辑删除消息通道
     * @param id 实体主键
     * @return 删除结果（true/false）
     */
    @Override
    public boolean deleteLogic(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return msgChannelWriteDao.deleteLogic(id);
    }

    /**
     * 物理删除消息通道
     * @param id 实体主键
     * @return 删除结果（true/false）
     */
    @Override
    public boolean delete(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return msgChannelWriteDao.delete(id);
    }

    /**
     * 批量逻辑删除消息通达
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    @Override
    public boolean batchDeleteLogic(@NotNull Set<String> ids) {
        return msgChannelWriteDao.batchDeleteLogic(ids);
    }

    /**
     * 批量物理删除消息通达
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    @Override
    public boolean batchDelete(@NotNull Set<String> ids) {
        return msgChannelWriteDao.batchDelete(ids);
    }

    /**
     * 根据主键获取消息通到
     * @param id 主键
     * @return 消息通道
     */
    @Override
    public MsgChannel get(String id) {
        return msgChannelReadDao.get(id);
    }


    /**
     * 根据编号查找
     * @param code 编码
     * @return 消息通道
     */
    @Override
    public MsgChannel findByCode(String code) {
        return msgChannelReadDao.findByCode(code);
    }

    /**
     * 分页查询
     * @param channel 消息通达
     * @param page 分页信息
     * @return 消息通道列表
     */
    @Override
    public List<MsgChannel> listPage(MsgChannel channel, Page page) {
        return msgChannelReadDao.listPage(channel, page);
    }

    /**
     * 查询所有
     * @param channel 消息通达
     * @return 消息通道列表
     */
    @Override
    public List<MsgChannel> list(MsgChannel channel) {
        return msgChannelReadDao.listPage(channel, null);
    }


    /**
     * 查询渠道列表
     * @param params 参数
     * @param page 分页参数
     * @return 渠道列表
     */
    @Override
    public List<MsgChannel> list(Map<String, Object> params, Page page) {
        return msgChannelReadDao.list(params, page);
    }


    /**
     * 根据事件编号查询通道
     * @param eventId 事件编号
     * @return 通道列表
     */
    @Override
    public List<MsgChannel> findByEventId(String eventId) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("eventId", eventId);
        return this.list(params, null);
    }

    @Override
    public List<MsgChannel> batchList(@NotNull Set<String> ids, MsgChannel channel, Page page) {
        return null;
    }
}
