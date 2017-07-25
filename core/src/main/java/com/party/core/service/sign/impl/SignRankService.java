package com.party.core.service.sign.impl;

import com.google.common.collect.Maps;
import com.party.core.dao.read.sign.SignApplyReadDao;
import com.party.core.model.sign.SignApplyStatus;
import com.party.core.model.sign.SignGradeStatus;
import com.party.core.service.sign.ISignApplyService;
import com.party.core.service.sign.ISignRankService;
import com.party.core.service.sign.ISignRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 签到排名接口实现
 * Created by wei.li
 *
 * @date 2017/6/19 0019
 * @time 17:05
 */

@Service
public class SignRankService implements ISignRankService{

    @Autowired
    private SignApplyReadDao signApplyReadDao;

    /**
     * 报名在项目中排行
     * @param projectId 项目编号
     * @param applyId 步数
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 排名
     */
    @Override
    public Integer projectRankRecord(String projectId, String applyId, String startTime, String endTime) {
        Map<String, Object> param = Maps.newHashMap();
        param.put("projectId", projectId);
        param.put("startTime", startTime);
        param.put("endTime", endTime);
        param.put("applyId", applyId);
        param.put("status", SignApplyStatus.PASS_STATUS.getCode());
        param.put("gradeStatus", SignGradeStatus.EFFECTIVE.getCode());
        Integer num = signApplyReadDao.rankRecord( param);
        return num;
    }

    /**
     * 报名在小组排行
     * @param groupId 小组编号
     * @param applyId 步数
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 排名
     */
    @Override
    public Integer groupRankRecord(String groupId,  String applyId, String startTime, String endTime) {
        Map<String, Object> param = Maps.newHashMap();
        param.put("groupId", groupId);
        param.put("startTime", startTime);
        param.put("endTime", endTime);
        param.put("applyId", applyId);
        param.put("status", SignApplyStatus.PASS_STATUS.getCode());
        Integer num = signApplyReadDao.rankRecord( param);
        return num;
    }
}
