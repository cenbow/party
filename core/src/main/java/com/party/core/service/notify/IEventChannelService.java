package com.party.core.service.notify;

import com.party.common.paging.Page;
import com.party.core.model.notify.EventChannel;
import com.party.core.model.notify.RelationWithChannel;
import com.party.core.service.IBaseService;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * 消息事件通道关联服务接口
 * Created by wei.li
 *
 * @date 2017/4/5 0005
 * @time 19:20
 */
public interface IEventChannelService extends IBaseService<EventChannel> {

    /**
     * 获取事件通道
     * @param channelId 通道编号
     * @param eventId 事件编号
     * @return 事件通道
     */
    EventChannel get(String channelId, String eventId);

    /**
     * 获取事件通道
     * @param code 通道编码
     * @param eventId 事件编号
     * @return 事件通道
     */
    EventChannel findByChannelCodeAndEventId(String code, String eventId);


    /**
     * 包含渠道关系列表
     * @param relationWithChannel 包含渠道关系
     * @param page 分页参数
     * @return 列表
     */
    List<RelationWithChannel> withChannelList( RelationWithChannel relationWithChannel, Page page);


    /**
     * 分配渠道
     * @param eventId 事件编号
     * @param channelIds
     */
    void distributionChannels(String eventId, Set<String> channelIds);

    /**
     * 根据事件编号删除通道
     * @param eventId 事件编号
     */
    void deleteByEventId(String eventId);

    /**
     * 微信事件列表
     * @return 列表信息
     */
    List<RelationWithChannel> wechatList();
}
