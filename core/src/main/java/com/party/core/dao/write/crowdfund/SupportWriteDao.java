package com.party.core.dao.write.crowdfund;

import com.party.core.dao.write.BaseWriteDao;
import com.party.core.model.crowdfund.Support;
import org.springframework.stereotype.Repository;

/**
 * 众筹支持数据写入
 * Created by wei.li
 *
 * @date 2017/2/16 0016
 * @time 17:08
 */
@Repository
public interface SupportWriteDao extends BaseWriteDao<Support> {
}
