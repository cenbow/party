package com.party.core.dao.write.resource;

import org.springframework.stereotype.Repository;

import com.party.core.dao.write.BaseWriteDao;
import com.party.core.model.resource.Resource;

/**
 * 资源数据写入
 * @author Administrator
 *
 */
@Repository
public interface ResourceWriteDao extends BaseWriteDao<Resource> {
}
