package com.party.core.service.gatherForm;

import java.util.List;
import java.util.Map;

import com.party.common.paging.Page;
import com.party.core.model.gatherForm.GatherProject;
import com.party.core.service.IBaseService;

public interface IGatherProjectService extends IBaseService<GatherProject> {

	List<GatherProject> webListPage(GatherProject gatherProject, Map<String, Object> params, Page page);

}
