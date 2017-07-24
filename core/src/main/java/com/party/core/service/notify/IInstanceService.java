package com.party.core.service.notify;

import com.party.common.paging.Page;
import com.party.core.model.notify.Instance;
import com.party.core.model.notify.InstanceWithMember;
import com.party.core.service.IBaseService;

import java.util.List;
import java.util.Map;

/**
 * 消息实例服务接口
 * Created by wei.li
 *
 * @date 2017/4/5 0005
 * @time 19:29
 */
public interface IInstanceService extends IBaseService<Instance> {

    /**
     * 插入消息实例
     * @param title 题目
     * @param channelType 通道类型
     * @param targetId 目标编号
     * @param memberId 会员编号
     * @return 编号
     */
    String insert(String title, String channelType, String targetId, String memberId);

    /**
     * 插入消息实例
     * @param title 题目
     * @param channelType 类型
     * @param receiver 接受者
     * @param result 结果
     * @return 编号
     */
    String insert(String title, String channelType, String receiver, String result, Integer isSueccess);
    /**
     * 获取历史数据
     * @param title 内容
     * @param receiver 接受者
     * @param hour 多少小时内
     * @return 历史数据
     */
    List<Instance> getHistory(String title, String receiver, Integer hour);

    /**
     * 是否有历史数据
     * @param title 内容
     * @param receiver 接受者
     * @param hour 多少小时内
     * @return 是否有（true/false）
     */
    boolean hasHistory(String title, String receiver, Integer hour);

    /**
     * 短信列表
     * @param instanceWithMember 消息
     * @param page 分页参数
     * @return 消息列表
     */
    List<InstanceWithMember> listWithMemberPage(InstanceWithMember instanceWithMember, Page page);
}
