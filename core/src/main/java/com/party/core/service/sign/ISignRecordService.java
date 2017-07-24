package com.party.core.service.sign;

import com.party.core.model.sign.SignRecord;
import com.party.core.service.IBaseService;

import java.util.Date;

/**
 * 签到记录接口
 * Created by wei.li
 *
 * @date 2017/6/5 0005
 * @time 18:45
 */
public interface ISignRecordService extends IBaseService<SignRecord> {

    /**
     * 今天步数
     * @param applyId 报名编号
     * @return 步数
     */
    Long todayStep(String applyId);

    /**
     * 本月步数
     * @param applyId 报名编号
     * @return 步数
     */
    Long monthStep(String applyId);

    /**
     * 时间段步数
     * @param applyId 报名编号
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 步数
     */
    Long stepNum(String applyId, String startTime, String endTime);

    /**
     * 总步数
     * @param applyId 报名编号
     * @return 步数
     */
    Long countStep(String applyId);

    /**
     * 获取今天的签到
     * @param applyId 报名编号
     * @return 签到信息
     */
    SignRecord getToday(String applyId);

    /**
     * 删除签到记录
     * @param applyId 报名编号
     * @return 删除结果（true/false）
     */
    boolean deleteByApplyId(String applyId);

    /**
     * 获取报名的签到次数
     * @param applyId 报名编号
     * @return 签到次数
     */
    Integer getCount(String applyId);
}
