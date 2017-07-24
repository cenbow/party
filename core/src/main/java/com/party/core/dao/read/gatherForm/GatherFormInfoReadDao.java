package com.party.core.dao.read.gatherForm;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.party.common.paging.Page;
import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.gatherForm.GatherFormInfo;

@Repository
public interface GatherFormInfoReadDao extends BaseReadDao<GatherFormInfo> {

	Integer getMaxIndex(String projectId);
	
	List<Map<String, Object>> webListPage(@Param(value = "params") Map<String, Object> params, Page page);

}
