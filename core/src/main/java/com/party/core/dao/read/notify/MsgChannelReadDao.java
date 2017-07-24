package com.party.core.dao.read.notify;

import com.party.common.paging.Page;
import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.notify.MsgChannel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 消息通道数据读取
 * Created by wei.li
 *
 * @date 2017/4/5 0005
 * @time 11:32
 */
@Repository
public interface MsgChannelReadDao extends BaseReadDao<MsgChannel>{

    /**
     * 根据编码查找
     * @param code 编号
     * @return 消息通道
     */
    MsgChannel findByCode(String code);

    /**
     * 查询通道列表
     * @param params 参数
     * @param page 分页参数
     * @return 通道列表
     */
    List<MsgChannel> list(@Param("params") Map<String, Object> params, Page page);
}
