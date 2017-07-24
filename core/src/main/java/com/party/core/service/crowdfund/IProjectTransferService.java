package com.party.core.service.crowdfund;

/**
 * 众筹数据转换接口
 * Created by wei.li
 *
 * @date 2017/7/13 0013
 * @time 16:23
 */
public interface IProjectTransferService {

    /**
     * 转移支持数据
     * @param sourceId 资源编号
     * @param targetId 目标编号
     */
    void transferSupport(String sourceId, String targetId);
}
