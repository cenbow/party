package com.party.core.dao.read.notify;

import com.party.common.paging.Page;
import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.notify.EventChannel;
import com.party.core.model.notify.RelationWithChannel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 消息事件通道关系读取
 * Created by wei.li
 *
 * @date 2017/4/5 0005
 * @time 11:43
 */
@Repository
public interface EventChannelReadDao extends BaseReadDao<EventChannel> {

    /**
     * 包含渠道关系列表
     * @param relationWithChannel 包含渠道关系
     * @param page 分页参数
     * @return 列表
     */
    List<RelationWithChannel> withChannelList(@Param(value = "relationWithChannel") RelationWithChannel relationWithChannel, Page page);
}
