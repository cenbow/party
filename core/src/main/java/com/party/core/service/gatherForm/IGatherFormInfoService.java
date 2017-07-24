package com.party.core.service.gatherForm;

import java.util.List;
import java.util.Map;

import com.party.common.paging.Page;
import com.party.core.model.gatherForm.GatherFormInfo;
import com.party.core.service.IBaseService;

public interface IGatherFormInfoService extends IBaseService<GatherFormInfo> {

	Integer getMaxIndex(String id);

	List<Map<String, Object>> webListPage(Map<String, Object> params, Page page);

	boolean deleteByProjectId(String projectId, String maxIndex);

}
