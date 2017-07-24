package com.party.core.dao.write.crowdfund;

import com.party.core.dao.read.BaseReadDao;
import com.party.core.dao.write.BaseWriteDao;
import com.party.core.model.crowdfund.CrowdfundEvent;
import org.springframework.stereotype.Repository;

/**
 * 众筹事件数据写入
 * Created by wei.li
 *
 * @date 2017/4/25 0025
 * @time 16:19
 */
@Repository
public interface CrowdfundEventWriteDao extends BaseWriteDao<CrowdfundEvent> {
}
