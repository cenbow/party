package com.party.core.service.crowdfund;

import com.party.core.model.notify.TargetTemplate;
import com.party.core.service.IBaseService;

/**
 * 目标模板接口
 * Created by wei.li
 *
 * @date 2017/7/4 0004
 * @time 11:19
 */
public interface ITargetTemplateService extends IBaseService<TargetTemplate> {

    /**
     * 根据目标编号查询
     * @param targetId 目标编号
     * @param type 类型
     * @return 模板
     */
    TargetTemplate findByTargetId(String targetId, Integer type);
}
