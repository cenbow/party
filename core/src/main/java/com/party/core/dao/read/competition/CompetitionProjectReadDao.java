package com.party.core.dao.read.competition;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.party.common.paging.Page;
import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.competition.CompetitionProject;

/**
 * 赛事项目
 * 
 * @author Administrator
 *
 */
@Repository
public interface CompetitionProjectReadDao extends BaseReadDao<CompetitionProject> {
	/**
	 * web端列表查询
	 * 
	 * @param project
	 * @param params
	 * @param page
	 * @return
	 */
	List<CompetitionProject> webListPage(@Param(value = "project") CompetitionProject project, @Param(value = "params") Map<String, Object> params,
			Page page);
}
