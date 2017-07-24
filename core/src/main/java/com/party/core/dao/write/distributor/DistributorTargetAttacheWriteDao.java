package com.party.core.dao.write.distributor;

import com.party.core.dao.read.BaseReadDao;
import com.party.core.dao.write.BaseWriteDao;
import com.party.core.model.distributor.DistributorTargetAttache;
import org.springframework.stereotype.Repository;

/**
 * 分销对象附属信息写入
 * Created by wei.li
 *
 * @date 2017/3/3 0003
 * @time 18:33
 */
@Repository
public interface DistributorTargetAttacheWriteDao extends BaseWriteDao<DistributorTargetAttache> {
}
