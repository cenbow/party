package com.party.core.dao.read.distributor;

import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.distributor.DistributorTargetAttache;
import org.springframework.stereotype.Repository;

/**
 * 分销对象附属读取
 * Created by wei.li
 *
 * @date 2017/3/3 0003
 * @time 18:31
 */

@Repository
public interface DistributorTargetAttacheReadDao extends BaseReadDao<DistributorTargetAttache> {

    /**
     * 根据众筹关系查询附属表
     * @param relationId 关系编号
     * @return 关系附属信息
     */
    DistributorTargetAttache findByRelationId(String relationId);
}
