package com.party.core.service.notify.impl;

import com.google.common.collect.Maps;
import com.party.common.constant.Constant;
import com.party.common.paging.Page;
import com.party.core.dao.read.notify.InstanceReadDao;
import com.party.core.dao.write.notify.InstanceWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.notify.Instance;
import com.party.core.model.notify.InstanceWithMember;
import com.party.core.service.notify.IInstanceService;
import com.sun.istack.NotNull;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 消息服务接口实现
 * Created by wei.li
 *
 * @date 2017/4/5 0005
 * @time 19:29
 */

@Service
public class InstanceService implements IInstanceService {

    @Autowired
    private InstanceReadDao instanceReadDao;

    @Autowired
    private InstanceWriteDao instanceWriteDao;

    /**
     * 插入消息实例
     * @param instance 消息实例
     * @return 编号
     */
    @Override
    public String insert(Instance instance) {
        BaseModel.preInsert(instance);
        boolean result = instanceWriteDao.insert(instance);
        if (result){
            return instance.getId();
        }
        return null;
    }

    /**
     * 插入消息实例
     * @param title 题目
     * @param channelType 通道类型
     * @param targetId 目标编号
     * @param memberId 会员编号
     * @return 编号
     */
    @Override
    public String insert(String title, String channelType, String targetId, String memberId) {
        Instance instance = new Instance();
        instance.setTitle(title);
        instance.setChannelType(channelType);
        instance.setMemberId(memberId);
        instance.setTargetId(targetId);
        return this.insert(instance);
    }

    /**
     * 插入消息实例
     * @param title 题目
     * @param channelType 类型
     * @param receiver 接受者
     * @param result 结果
     * @return 编号
     */
    @Override
    public String insert(String title, String channelType, String receiver, String result, Integer isSueccess) {
        Instance instance = new Instance();
        instance.setTitle(title);
        instance.setChannelType(channelType);
        instance.setReceiver(receiver);
        instance.setResult(result);
        instance.setTime(1);
        instance.setIsSuccess(isSueccess);
        return this.insert(instance);
    }

    /**
     * 更新消息实例
     * @param instance 消息实例
     * @return 更新结果（true/false）
     */
    @Override
    public boolean update(Instance instance) {
        return instanceWriteDao.update(instance);
    }

    /**
     * 逻辑删除
     * @param id 实体主键
     * @return 删除结果（true/false）
     */
    @Override
    public boolean deleteLogic(@NotNull String id) {
        return instanceWriteDao.deleteLogic(id);
    }

    /**
     * 物理删除
     * @param id 实体主键
     * @return 删除结果（true/false）
     */
    @Override
    public boolean delete(@NotNull String id) {
        return instanceWriteDao.delete(id);
    }

    /**
     * 批量逻辑删除
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    @Override
    public boolean batchDeleteLogic(@NotNull Set<String> ids) {
        return instanceWriteDao.batchDeleteLogic(ids);
    }

    /**
     * 批量物理删除
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    @Override
    public boolean batchDelete(@NotNull Set<String> ids) {
        return instanceWriteDao.batchDelete(ids);
    }

    /**
     * 获取
     * @param id 主键
     * @return 消息实体
     */
    @Override
    public Instance get(String id) {
        return instanceReadDao.get(id);
    }

    /**
     * 分页查询
     * @param instance 消息实体
     * @param page 分页信息
     * @return
     */
    @Override
    public List<Instance> listPage(Instance instance, Page page) {
        return instanceReadDao.listPage(instance, page);
    }

    /**
     * 查询所有
     * @param instance 消息实体
     * @return 列表
     */
    @Override
    public List<Instance> list(Instance instance) {
        return instanceReadDao.listPage(instance, null);
    }

    /**
     * 获取历史数据
     * @param title 内容
     * @param receiver 接受者
     * @param hour 多少小时内
     * @return 历史数据
     */
    @Override
    public List<Instance> getHistory(String title, String receiver, Integer hour) {
        Map<String, Object> param = Maps.newHashMap();
        param.put("title", title);
        param.put("receiver", receiver);
        param.put("hour", hour);
        return instanceReadDao.getHistory(param);
    }

    /**
     * 是否存在历史数据
     * @param title 内容
     * @param receiver 接受者
     * @param hour 多少小时内
     * @return 是否存在历史数据（true/false）
     */
    @Override
    public boolean hasHistory(String title, String receiver, Integer hour) {
        List<Instance> list = getHistory(title, receiver, hour);
        if (!CollectionUtils.isEmpty(list)){
            return true;
        }
        return false;
    }

    @Override
    public List<Instance> batchList(@NotNull Set<String> ids, Instance instance, Page page) {
        return null;
    }

    /**
     * 消息列表
     * @param instanceWithMember 消息
     * @param page 分页参数
     * @return 消息列表
     */
    @Override
    public List<InstanceWithMember> listWithMemberPage(InstanceWithMember instanceWithMember, Page page) {
        return instanceReadDao.listWithMemberPage(instanceWithMember, page);
    }
}
