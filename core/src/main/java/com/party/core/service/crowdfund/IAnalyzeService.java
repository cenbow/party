package com.party.core.service.crowdfund;

import com.party.core.model.crowdfund.Analyze;
import com.party.core.service.IBaseService;

/**
 * 众筹分析接口
 * Created by wei.li
 *
 * @date 2017/7/7 0007
 * @time 15:17
 */

public interface IAnalyzeService extends IBaseService<Analyze> {

    /**
     * 根据目标编号查询
     * @param targetId 目标编号
     * @return 分析实体
     */
    Analyze findByTargetId(String targetId);
}
