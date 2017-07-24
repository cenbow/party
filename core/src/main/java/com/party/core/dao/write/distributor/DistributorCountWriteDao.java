package com.party.core.dao.write.distributor;

import com.party.core.dao.write.BaseWriteDao;
import com.party.core.model.distributor.DistributorCount;
import org.springframework.stereotype.Repository;

/**
 * 分销统计数据写入
 * Created by wei.li
 *
 * @date 2017/3/1 0001
 * @time 14:12
 */
@Repository
public interface DistributorCountWriteDao extends BaseWriteDao<DistributorCount> {
}
