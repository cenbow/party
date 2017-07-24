package com.party.core.service.notify.impl;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.party.common.paging.Page;
import com.party.common.utils.LangUtils;
import com.party.core.dao.read.notify.EventChannelReadDao;
import com.party.core.dao.read.notify.MsgChannelReadDao;
import com.party.core.dao.write.notify.EventChannelWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.notify.EventChannel;
import com.party.core.model.notify.MsgChannel;
import com.party.core.model.notify.RelationWithChannel;
import com.party.core.service.notify.IEventChannelService;
import com.sun.istack.NotNull;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;

/**
 * 消息事件通道服务接口实现
 * Created by wei.li
 *
 * @date 2017/4/5 0005
 * @time 19:21
 */

@Service
public class EventChannelService implements IEventChannelService {


    @Autowired
    private EventChannelReadDao eventChannelReadDao;

    @Autowired
    private EventChannelWriteDao eventChannelWriteDao;

    @Autowired
    private MsgChannelReadDao msgChannelReadDao;

    /**
     * 消息关联插入
     * @param eventChannel 消息关联
     * @return 编号
     */
    @Override
    public String insert(EventChannel eventChannel) {
        BaseModel.preInsert(eventChannel);
        boolean result = eventChannelWriteDao.insert(eventChannel);
        if (result){
            return eventChannel.getId();
        }
        return null;
    }

    /**
     * 消息关联更新
     * @param eventChannel 消息关联
     * @return 更新结果(true/false)
     */
    @Override
    public boolean update(EventChannel eventChannel) {
        return eventChannelWriteDao.update(eventChannel);
    }

    /**
     * 逻辑删除
     * @param id 实体主键
     * @return 删除结果(true/false)
     */
    @Override
    public boolean deleteLogic(@NotNull String id) {
        return eventChannelWriteDao.deleteLogic(id);
    }

    /**
     * 物理删除
     * @param id 实体主键
     * @return 删除结果（true/false）
     */
    @Override
    public boolean delete(@NotNull String id) {
        return eventChannelWriteDao.delete(id);
    }

    /**
     * 删除事项通道
     * @param eventId 事件编号
     */
    @Override
    public void deleteByEventId(String eventId) {
        eventChannelWriteDao.deleteByEventId(eventId);
    }

    /**
     * 批量逻辑删除
     * @param ids 主键集合
     * @return 删除结果(true/false)
     */
    @Override
    public boolean batchDeleteLogic(@NotNull Set<String> ids) {
        return eventChannelWriteDao.batchDeleteLogic(ids);
    }

    /**
     * 批量物理删除
     * @param ids 主键集合
     * @return 删除结果(true/false)
     */
    @Override
    public boolean batchDelete(@NotNull Set<String> ids) {
        return eventChannelWriteDao.batchDelete(ids);
    }

    /**
     * 获取
     * @param id 主键
     * @return 消息关联
     */
    @Override
    public EventChannel get(String id) {
        return eventChannelReadDao.get(id);
    }

    /**
     * 获取事件通道
     * @param channelId 通道编号
     * @param eventId 事件编号
     * @return 事件通道
     */
    @Override
    public EventChannel get(String channelId, String eventId) {
        EventChannel eventChannel = new EventChannel();
        eventChannel.setChannelId(channelId);
        eventChannel.setEventId(eventId);
        List<EventChannel> eventChannelList = this.list(eventChannel);
        if (!CollectionUtils.isEmpty(eventChannelList)){
            return eventChannelList.get(0);
        }
        return null;
    }

    /**
     * 获取事件通道
     * @param code 通道编码
     * @param eventId 事件编号
     * @return 时间通道
     */
    @Override
    public EventChannel findByChannelCodeAndEventId(String code, String eventId) {
        MsgChannel msgChannel = msgChannelReadDao.findByCode(code);
        if (null != msgChannel){
            return this.get(msgChannel.getId(), eventId);
        }
        return null;
    }

    /**
     * 分页查询消息关系
     * @param eventChannel 消息关系
     * @param page 分页信息
     * @return 消息关联列表
     */
    @Override
    public List<EventChannel> listPage(EventChannel eventChannel, Page page) {
        return eventChannelReadDao.listPage(eventChannel, page);
    }

    /**
     * 查询所有消息关系
     * @param eventChannel 消息关系
     * @return 消息关系列表
     */
    @Override
    public List<EventChannel> list(EventChannel eventChannel) {
        return eventChannelReadDao.listPage(eventChannel, null);
    }

    /**
     * 包含渠道关系列表
     * @param relationWithChannel 包含渠道关系
     * @param page 分页参数
     * @return 列表
     */
    @Override
    public List<RelationWithChannel> withChannelList(RelationWithChannel relationWithChannel, Page page) {
        return eventChannelReadDao.withChannelList(relationWithChannel, page);
    }

    /**
     * 分配通道
     * @param eventId 事件编号
     * @param channelIds 通道编号集合
     */
    @Override
    @Transactional
    public void distributionChannels(String eventId, Set<String> channelIds) {

        //查询event 的通道
        EventChannel eventChannel = new EventChannel();
        eventChannel.setEventId(eventId);
        List<EventChannel> hasList = this.list(eventChannel);

        List<String> hasIdList = LangUtils.transform(hasList, new Function<EventChannel, String>() {
            @Override
            public String apply(EventChannel eventChannel) {
                return eventChannel.getChannelId();
            }
        });

        Set<String> addList = Sets.newHashSet();
        addList.addAll(channelIds);
        addList.removeAll(hasIdList);

        List<String> removeList = Lists.newArrayList();
        removeList.addAll(hasIdList);
        removeList.removeAll(channelIds);

        //新增
        for (String id : addList){
            EventChannel addEvent = new EventChannel();
            addEvent.setChannelId(id);
            addEvent.setEventId(eventId);
            this.insert(addEvent);
        }

        //删除
        for (EventChannel he : hasList){
            for (String id : removeList){
                if (he.getChannelId().equals(id)){
                    this.delete(he.getId());
                }
            }
        }

    }

    @Override
    public List<EventChannel> batchList(@NotNull Set<String> ids, EventChannel eventChannel, Page page) {
        return eventChannelReadDao.batchList(ids, Maps.newHashMap(), page);
    }

    /**
     * 微信事件列表
     * @return 事件列表
     */
    @Override
    public List<RelationWithChannel> wechatList() {
        RelationWithChannel relationWithChannel = new RelationWithChannel();
        relationWithChannel.setChannelId("5");//微信通道编号
        return this.withChannelList(relationWithChannel, null);
    }
}
