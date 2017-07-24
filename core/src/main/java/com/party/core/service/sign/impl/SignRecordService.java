package com.party.core.service.sign.impl;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.party.common.paging.Page;
import com.party.common.utils.DateUtils;
import com.party.core.dao.read.sign.SignRecordReadDao;
import com.party.core.dao.write.sign.SignRecordWriteDao;
import com.party.core.exception.BusinessException;
import com.party.core.model.BaseModel;
import com.party.core.model.sign.SignRecord;
import com.party.core.service.sign.ISignRecordService;
import com.sun.istack.NotNull;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 签到记录接口实现
 * Created by wei.li
 *
 * @date 2017/6/5 0005
 * @time 18:45
 */

@Service
public class SignRecordService implements ISignRecordService {

    @Autowired
    private SignRecordWriteDao signRecordWriteDao;

    @Autowired
    private SignRecordReadDao signRecordReadDao;

    /**
     * 签到记录插入
     * @param signRecord 签到记录
     * @return 编号
     */
    @Override
    public String insert(SignRecord signRecord) {

        //是否已经签到
        SignRecord s = getToday(signRecord.getApplyId());
        if (null != s){
            throw new BusinessException("该报名今天已经签到");
        }
        BaseModel.preInsert(signRecord);
        boolean result = signRecordWriteDao.insert(signRecord);
        if (result){
            return signRecord.getId();
        }
        return null;
    }

    /**
     * 更新签到记录
     * @param signRecord 签到记录
     * @return 更新结果（true/false）
     */
    @Override
    public boolean update(SignRecord signRecord) {
        return signRecordWriteDao.update(signRecord);
    }

    /**
     * 逻辑删除签到记录
     * @param id 实体主键
     * @return 删除结果（true/false）
     */
    @Override
    public boolean deleteLogic(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return signRecordWriteDao.deleteLogic(id);
    }

    /**
     * 物理删除签到记录
     * @param id 实体主键
     * @return 删除结果（true/false）
     */
    @Override
    public boolean delete(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return signRecordWriteDao.delete(id);
    }

    /**
     * 批量逻辑删除签到记录
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    @Override
    public boolean batchDeleteLogic(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return signRecordWriteDao.batchDeleteLogic(ids);
    }

    /**
     * 批量物理删除签到记录
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    @Override
    public boolean batchDelete(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return signRecordWriteDao.batchDelete(ids);
    }

    /**
     * 获取签到记录
     * @param id 主键
     * @return 签到记录
     */
    @Override
    public SignRecord get(String id) {
        return signRecordReadDao.get(id);
    }


    /**
     * 获取今天签到
     * @param applyId 报名编号
     * @return 签到信息
     */
    @Override
    public SignRecord getToday(String applyId) {
        String date = DateUtils.getTodayStr();
        return signRecordReadDao.getByDate(applyId, date);
    }

    /**
     * 分页查询签到记录
     * @param signRecord
     * @param page 分页信息
     * @return 签到记录列表
     */
    @Override
    public List<SignRecord> listPage(SignRecord signRecord, Page page) {
        return signRecordReadDao.listPage(signRecord, page);
    }

    /**
     * 查询所有签到记录
     * @param signRecord 签到记录
     * @return 签到记录列表
     */
    @Override
    public List<SignRecord> list(SignRecord signRecord) {
        return signRecordReadDao.listPage(signRecord, null);
    }

    @Override
    public List<SignRecord> batchList(@NotNull Set<String> ids, SignRecord signRecord, Page page) {
        return null;
    }

    /**
     * 今天步数
     * @param applyId 报名编号
     * @return 步数
     */
    @Override
    public Long todayStep(String applyId) {
        String today = DateUtils.getTodayStr();
        return this.stepNum(applyId, today, today);
    }

    /**
     * 本月的步数
     * @param applyId 报名编号
     * @return 步数
     */
    @Override
    public Long monthStep(String applyId) {
        String startTime = DateUtils.formatDateTime(DateUtils.getFirstDayOfMonth(0));
        String endTime = DateUtils.getTodayStr();
        return this.stepNum(applyId, startTime, endTime);
    }

    /**
     * 获取时间段步数
     * @param applyId 报名编号
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 步数
     */
    @Override
    public Long stepNum(String applyId, String startTime, String endTime) {
        Map<String, Object> param = Maps.newHashMap();
        param.put("applyId", applyId);
        param.put("startTime", startTime);
        param.put("endTime", endTime);
        Long stepNum = signRecordReadDao.getStepNum(param);
        stepNum = stepNum == null ? 0 : stepNum;
        return stepNum;
    }

    /**
     * 总步数
     * @param applyId 报名编号
     * @return 步数
     */
    @Override
    public Long countStep(String applyId) {
        return this.stepNum(applyId, null, null);
    }

    /**
     * 删除签到记录
     * @param applyId 报名编号
     * @return 删除结果（true/false）
     */
    @Override
    public boolean deleteByApplyId(String applyId) {
        return signRecordWriteDao.deleteByApplyId(applyId);
    }


    /**
     * 获取报名的签到数
     * @param applyId 报名编号
     * @return 签到数
     */
    @Override
    public Integer getCount(String applyId) {
        Map<String, Object> param = Maps.newHashMap();
        param.put("applyId", applyId);
        Integer stepNum = signRecordReadDao.getCount(param);
        stepNum = stepNum == null ? 0 : stepNum;
        return stepNum;
    }
}
