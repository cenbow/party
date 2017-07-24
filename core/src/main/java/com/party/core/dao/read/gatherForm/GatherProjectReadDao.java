package com.party.core.dao.read.gatherForm;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.party.common.paging.Page;
import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.gatherForm.GatherProject;

@Repository
public interface GatherProjectReadDao extends BaseReadDao<GatherProject> {

	List<GatherProject> webListPage(@Param("project") GatherProject gatherProject, @Param("params") Map<String, Object> params, Page page);

}
