package com.party.core.dao.write.version;

import com.party.core.dao.write.BaseWriteDao;
import com.party.core.model.version.VersionManager;
import org.springframework.stereotype.Repository;

/**
 * 第三方登录开关设置数据写入
 * party
 * Created by Wesley
 * on 2016/11/23
 */
@Repository
public interface VersionManagerWriteDao extends BaseWriteDao<VersionManager> {
}
