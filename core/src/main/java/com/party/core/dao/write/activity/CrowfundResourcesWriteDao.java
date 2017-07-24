package com.party.core.dao.write.activity;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.party.core.dao.write.BaseWriteDao;
import com.party.core.model.activity.CrowfundResources;

/**
 * 众筹项目资源
 * 
 * @author Administrator
 *
 */
@Repository
public interface CrowfundResourcesWriteDao extends BaseWriteDao<CrowfundResources> {

	boolean deleteByRefId(@Param(value = "refId") String refId, @Param(value = "type") String type);
}
