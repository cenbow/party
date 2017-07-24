package com.party.core.service.competition;

import java.util.List;
import java.util.Map;

import com.party.common.paging.Page;
import com.party.core.model.competition.CompetitionResult;
import com.party.core.service.IBaseService;

/**
 * 参赛人员成绩
 * 
 * @author Administrator
 *
 */
public interface ICompetitionResultService extends IBaseService<CompetitionResult> {

	/**
	 * 项目所有人员排名
	 * 
	 * @param params
	 * @param page
	 * @return
	 */
	List<Map<String, Object>> getAllResult(Map<String, Object> params, Page page);

	/**
	 * 项目小组排名
	 * 
	 * @param params
	 * @param page
	 * @return
	 */
	List<Map<String, Object>> getByGroup(Map<String, Object> params, Page page);

	List<Map<String, Object>> getGroupAllPersonResult(Map<String, Object> params, Page page);
	
	Map<String, Object> getTotalDistanceAndResult(Map<String, Object> params);

	List<Map<String, Object>> getScheduleRank(CompetitionResult cResult);
}
