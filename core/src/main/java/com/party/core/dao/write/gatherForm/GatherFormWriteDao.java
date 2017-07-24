package com.party.core.dao.write.gatherForm;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.party.core.dao.write.BaseWriteDao;
import com.party.core.model.gatherForm.GatherForm;

@Repository
public interface GatherFormWriteDao extends BaseWriteDao<GatherForm> {

	boolean deleteByProject(@Param(value = "projectId") String projectId);

}
