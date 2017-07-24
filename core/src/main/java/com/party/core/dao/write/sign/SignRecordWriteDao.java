package com.party.core.dao.write.sign;

import com.party.core.dao.write.BaseWriteDao;
import com.party.core.model.sign.SignRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 签到记录数据写入
 * Created by wei.li
 *
 * @date 2017/6/5 0005
 * @time 15:48
 */

@Repository
public interface SignRecordWriteDao extends BaseWriteDao<SignRecord> {

    /**
     * 根据报名编号删除
     * @param applyId 报名编号
     * @return 删除结果（true/false）
     */
    boolean deleteByApplyId(@Param(value = "applyId") String applyId);
}
