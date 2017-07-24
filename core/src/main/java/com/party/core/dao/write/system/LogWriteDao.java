package com.party.core.dao.write.system;

import com.party.core.dao.write.BaseWriteDao;
import com.party.core.model.system.Log;
import org.springframework.stereotype.Repository;

/**
 * 系统消息数据写入
 * party
 * Created by wei.li
 * on 2016/9/14 0014.
 */
@Repository
public interface LogWriteDao extends BaseWriteDao<Log> {
}
