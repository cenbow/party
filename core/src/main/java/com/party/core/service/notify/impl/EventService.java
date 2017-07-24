package com.party.core.service.notify.impl;

import com.party.common.constant.Constant;
import com.party.common.paging.Page;
import com.party.core.dao.read.notify.EventReadDao;
import com.party.core.dao.write.notify.EventWriteDao;
import com.party.core.exception.BusinessException;
import com.party.core.model.BaseModel;
import com.party.core.model.notify.Event;
import com.party.core.service.notify.IEventService;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;

/**
 * 消息事件服务接口实现
 * Created by wei.li
 *
 * @date 2017/4/5 0005
 * @time 19:11
 */

@Service
public class EventService implements IEventService {

    @Autowired
    private EventReadDao eventReadDao;

    @Autowired
    private EventWriteDao eventWriteDao;

    /**
     * 插入消息事件
     * @param event 消息事件
     * @return 编号
     */
    @Override
    public String insert(Event event) {
        Event dbEvent = this.findByCode(event.getCode());
        if (null != dbEvent){
            throw new BusinessException("时间代码已经存在");
        }
        BaseModel.preInsert(event);
        boolean result = eventWriteDao.insert(event);
        if (result){
            return event.getId();
        }
        return null;
    }

    /**
     * 更新消息事件
     * @param event 消息事件
     * @return 更新结果（true/false）
     */
    @Override
    public boolean update(Event event) {
        Event dbEvent = this.findByCode(event.getCode());
        if (null != dbEvent && !dbEvent.getId().equals(event.getId())){
            throw new BusinessException("时间代码已经存在");
        }
        return eventWriteDao.update(event);
    }

    /**
     * 逻辑删除消息事件
     * @param id 实体主键
     * @return 删除结果（true/false）
     */
    @Override
    public boolean deleteLogic(@NotNull String id) {
        return eventWriteDao.deleteLogic(id);
    }

    /**
     * 物理删除消息事件
     * @param id 实体主键
     * @return 删除结果（true/false）
     */
    @Override
    public boolean delete(@NotNull String id) {
        return eventWriteDao.delete(id);
    }

    /**
     * 批量逻辑删除消息事件
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    @Override
    public boolean batchDeleteLogic(@NotNull Set<String> ids) {
        return eventWriteDao.batchDeleteLogic(ids);
    }

    /**
     * 批量物理删除消息事件
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    @Override
    public boolean batchDelete(@NotNull Set<String> ids) {
        return eventWriteDao.batchDelete(ids);
    }

    /**
     * 获取消息事件
     * @param id 主键
     * @return 消息事件
     */
    @Override
    public Event get(String id) {
        return eventReadDao.get(id);
    }


    /**
     * 根据代码查询时间
     * @param code 代码
     * @return 时间
     */
    @Override
    public Event findByCode(String code) {
        return eventReadDao.findByCode(code);
    }

    /**
     * 分页查询消息事件
     * @param event 消息事件
     * @param page 分页信息
     * @return 消息事件列表
     */
    @Override
    public List<Event> listPage(Event event, Page page) {
        return eventReadDao.listPage(event, page);
    }

    /**
     * 查询所有消息事件
     * @param event 消息事件
     * @return 消息事件列表
     */
    @Override
    public List<Event> list(Event event) {
        return eventReadDao.listPage(event, null);
    }

    /**
     * 查找定时任务列表
     * @return 定时任务列表
     */
    @Override
    public List<Event> jobList() {
        Event event = new Event();
        event.setWay(Constant.EVENT_AUTO);
        return this.list(event);
    }

    @Override
    public List<Event> batchList(@NotNull Set<String> ids, Event event, Page page) {
        return null;
    }
}
