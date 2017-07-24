package com.party.admin.biz.quartz;

import com.party.core.model.notify.Event;

import java.util.List;


/**
 * 定时任务接口
 * Created by wei.li
 *
 * @date 2017/4/12 0012
 * @time 11:49
 */
public interface IScheduleJobService {

    /**
     * 新增定时任务
     *
     * @param event
     * @return
     */
    String insert(Event event);


    /**
     * 直接修改 只能修改运行的时间，参数、同异步等无法修改
     *
     * @param event
     */
    void update(Event event);

    /**
     * 删除重新创建方式
     *
     * @param event
     */
    void delUpdate(Event event);

    /**
     * 删除
     *
     * @param eventId
     */
    void delete(String eventId);

    /**
     * 运行一次任务
     *
     * @param eventId 定时任务编号
     * @return
     */
    void runOnce(String eventId);

    /**
     * 暂停任务
     *
     * @param eventId 定时任务编号
     * @return
     */
    void pauseJob(String eventId);

    /**
     * 恢复任务
     *
     * @param eventId 定时任务编号
     * @return
     */
    void resumeJob(String eventId);


    /**
     * 获取任务对象
     *
     * @param eventId 定时任务编号
     * @return
     */
    Event get(String eventId);

    /**
     * 查询任务列表
     *
     * @param event
     * @return
     */
    List<Event> queryList(Event event);

    /**
     * 获取运行中的任务列表
     *
     * @return
     */
    List<Event> queryExecutingJobList();
}
