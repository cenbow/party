package com.party.core.service.distributor;

import com.party.core.model.distributor.DistributorCount;
import com.party.core.service.IBaseService;

/**
 * 分销统计服务接口
 * Created by wei.li
 *
 * @date 2017/3/1 0001
 * @time 15:36
 */


public interface IDistributorCountService extends IBaseService<DistributorCount>{

    /**
     * 根据分销关系编号查找分销统计
     * @param relationId 分销关系编号
     * @return 分销统计
     */
    DistributorCount findByRelationId(String relationId);

}
