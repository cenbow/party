package com.party.core.service.notify;

import com.party.common.paging.Page;
import com.party.core.model.notify.MsgChannel;
import com.party.core.service.IBaseService;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 消息通道服务接口
 * Created by wei.li
 *
 * @date 2017/4/5 0005
 * @time 18:58
 */


public interface IMsgChannelService extends IBaseService<MsgChannel> {

    /**
     * 根据编码获取消息通道
     * @param code 编码
     * @return 消息通道
     */
    MsgChannel findByCode(String code);

    /**
     * 查询通道列表
     * @param params 参数
     * @param page 分页参数
     * @return 通道列表
     */
    List<MsgChannel> list(Map<String, Object> params, Page page);

    /**
     * 根据事件查询
     * @param eventId 事件编号
     * @return 消息通道
     */
    List<MsgChannel> findByEventId(String eventId);

}
