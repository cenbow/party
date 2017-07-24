package com.party.core.dao.write.gatherForm;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.party.core.dao.write.BaseWriteDao;
import com.party.core.model.gatherForm.GatherFormInfo;

@Repository
public interface GatherFormInfoWriteDao extends BaseWriteDao<GatherFormInfo> {

	boolean deleteByProjectId(@Param(value = "projectId") String projectId, @Param(value = "maxIndex") String maxIndex);

}