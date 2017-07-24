package com.party.core.service.gatherInfo;

import java.util.List;
import java.util.Map;

import com.party.common.paging.Page;
import com.party.core.model.gatherInfo.GatherInfoProject;
import com.party.core.service.IBaseService;

/**
 * 人员信息采集项目
 * 
 * @author Administrator
 *
 */
public interface IGatherInfoProjectService extends IBaseService<GatherInfoProject> {

	List<GatherInfoProject> webListPage(GatherInfoProject project, Map<String, Object> params, Page page);

}
