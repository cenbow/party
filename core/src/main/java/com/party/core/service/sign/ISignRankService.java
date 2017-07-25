package com.party.core.service.sign;

/**
 * 签到排名接口
 * Created by wei.li
 *
 * @date 2017/6/19 0019
 * @time 17:04
 */
public interface ISignRankService {

    /**
     * 报名在项目中排行
     * @param projectId 项目编号
     * @param applyId 步数
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 排名
     */
    Integer projectRankRecord(String projectId, String applyId, String startTime, String endTime);

    /**
     * 报名在小组的排行
     * @param groupId 小组编号
     * @param applyId 步数
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 排名
     */
    Integer groupRankRecord(String groupId,  String applyId, String startTime, String endTime);
}
