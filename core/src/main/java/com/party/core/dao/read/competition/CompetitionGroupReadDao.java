package com.party.core.dao.read.competition;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.party.common.paging.Page;
import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.competition.CompetitionGroup;

/**
 * 赛事小组
 * 
 * @author Administrator
 *
 */
@Repository
public interface CompetitionGroupReadDao extends BaseReadDao<CompetitionGroup> {

	List<CompetitionGroup> webListPage(@Param(value = "group") CompetitionGroup group, @Param(value = "params") Map<String, Object> params, Page page);

	List<CompetitionGroup> checkGroupName(@Param(value = "groupId") String groupId, @Param(value = "groupName") String groupName,
			@Param(value = "projectId") String projectId);

}
