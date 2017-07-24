package com.party.core.service.distributor;

import com.party.core.model.distributor.DistributorTargetAttache;
import com.party.core.service.IBaseService;

/**
 * 附属信息服务接口
 * Created by wei.li
 *
 * @date 2017/3/3 0003
 * @time 18:52
 */
public interface IDistributorTargetAttacheService extends IBaseService<DistributorTargetAttache> {

    /**
     * 根据众筹关系查询附属表
     * @param relationId 关系编号
     * @return 关系附属信息
     */
    DistributorTargetAttache findByRelationId(String relationId);
}
