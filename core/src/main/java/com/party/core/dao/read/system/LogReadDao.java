package com.party.core.dao.read.system;

import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.system.Log;
import org.springframework.stereotype.Repository;

/**
 * 系统消息数据读取
 * party
 * Created by wei.li
 * on 2016/9/14 0014.
 */
@Repository
public interface LogReadDao extends BaseReadDao<Log> {
}
