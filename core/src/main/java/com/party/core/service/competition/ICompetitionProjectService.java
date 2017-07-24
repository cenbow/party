package com.party.core.service.competition;

import java.util.List;
import java.util.Map;

import com.party.common.paging.Page;
import com.party.core.model.competition.CompetitionProject;
import com.party.core.service.IBaseService;

/**
 * 赛事项目
 * 
 * @author Administrator
 *
 */
public interface ICompetitionProjectService extends IBaseService<CompetitionProject> {
	/**
	 * web端列表查询
	 * 
	 * @param project
	 * @param params
	 * @param page
	 * @return
	 */
	List<CompetitionProject> webListPage(CompetitionProject project, Map<String, Object> params, Page page);

}
