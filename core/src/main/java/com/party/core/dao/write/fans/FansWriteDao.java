package com.party.core.dao.write.fans;

import com.party.core.dao.write.BaseWriteDao;
import com.party.core.model.fans.Fans;
import org.springframework.stereotype.Repository;

/**
 * 粉丝数据写入
 * party
 * Created by wei.li
 * on 2016/9/19 0019.
 */

@Repository
public interface FansWriteDao extends BaseWriteDao<Fans> {
}
