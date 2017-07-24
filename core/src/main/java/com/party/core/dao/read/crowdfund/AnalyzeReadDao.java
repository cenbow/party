package com.party.core.dao.read.crowdfund;

import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.crowdfund.Analyze;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 众筹分析数据写入
 * Created by wei.li
 *
 * @date 2017/7/7 0007
 * @time 14:53
 */

@Repository
public interface AnalyzeReadDao extends BaseReadDao<Analyze> {

    /**
     * 根据目标编号
     * @param targetId 目标编号
     * @return 分析数据
     */
    Analyze findByTargetId(@Param(value = "targetId") String targetId);
}
