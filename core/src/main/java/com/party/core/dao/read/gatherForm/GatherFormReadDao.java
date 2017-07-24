package com.party.core.dao.read.gatherForm;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.gatherForm.GatherForm;

@Repository
public interface GatherFormReadDao extends BaseReadDao<GatherForm> {

	String getTitle(@Param("projectId") String projectId);

	Integer getCount(@Param("projectId") String projectId);
}
