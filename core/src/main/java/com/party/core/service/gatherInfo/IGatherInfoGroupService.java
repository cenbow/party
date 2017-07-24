package com.party.core.service.gatherInfo;

import java.util.List;
import java.util.Map;

import com.party.common.paging.Page;
import com.party.core.model.gatherInfo.GatherInfoGroup;
import com.party.core.service.IBaseService;

/**
 * 人员小组
 * 
 * @author Administrator
 *
 */
public interface IGatherInfoGroupService extends IBaseService<GatherInfoGroup> {

	List<GatherInfoGroup> webListPage(GatherInfoGroup group, Map<String, Object> params, Page page);

}
