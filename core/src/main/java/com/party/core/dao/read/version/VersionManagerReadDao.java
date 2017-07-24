package com.party.core.dao.read.version;

import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.version.VersionManager;
import org.springframework.stereotype.Repository;

/**
 * 第三方登录开关设置数据读取
 * party
 * Created by Wesley
 * on 2016/11/23.
 */
@Repository
public interface VersionManagerReadDao extends BaseReadDao<VersionManager> {

}
