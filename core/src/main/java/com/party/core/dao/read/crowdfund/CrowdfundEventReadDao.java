package com.party.core.dao.read.crowdfund;

import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.crowdfund.CrowdfundEvent;
import org.springframework.stereotype.Repository;

/**
 * 众筹事件数据写入
 * Created by wei.li
 *
 * @date 2017/4/25 0025
 * @time 16:22
 */

@Repository
public interface CrowdfundEventReadDao extends BaseReadDao<CrowdfundEvent> {
}
