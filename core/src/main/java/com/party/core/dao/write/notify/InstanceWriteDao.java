package com.party.core.dao.write.notify;

import com.party.core.dao.write.BaseWriteDao;
import com.party.core.model.notify.Instance;
import org.springframework.stereotype.Repository;

/**
 * 消息实例数据写入
 * Created by wei.li
 *
 * @date 2017/4/5 0005
 * @time 11:46
 */
@Repository
public interface InstanceWriteDao extends BaseWriteDao<Instance> {
}
