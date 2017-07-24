package com.party.core.service.competition;

import java.util.List;
import java.util.Map;

import com.party.common.paging.Page;
import com.party.core.model.competition.CompetitionGroup;
import com.party.core.service.IBaseService;

/**
 * 赛事小组
 * 
 * @author Administrator
 *
 */
public interface ICompetitionGroupService extends IBaseService<CompetitionGroup> {

	List<CompetitionGroup> webListPage(CompetitionGroup group, Map<String, Object> params, Page page);

	CompetitionGroup getUnique(CompetitionGroup t);

	List<CompetitionGroup> checkGroupName(String groupId, String groupName, String projectId);

}
