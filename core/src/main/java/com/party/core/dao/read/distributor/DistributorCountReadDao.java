package com.party.core.dao.read.distributor;

import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.distributor.DistributorCount;
import org.springframework.stereotype.Repository;

/**
 * 分销统计数据读取
 * Created by wei.li
 *
 * @date 2017/3/1 0001
 * @time 14:17
 */

@Repository
public interface DistributorCountReadDao extends BaseReadDao<DistributorCount>{

    /**
     * 根据分销关系编号查找分销统计
     * @param relationId 分销关系编号
     * @return 分销统计
     */
    DistributorCount findByRelationId(String relationId);
}
