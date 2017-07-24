package com.party.core.dao.read.competition;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.party.common.paging.Page;
import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.competition.CompetitionResult;

/**
 * 参赛人员成绩
 * 
 * @author Administrator
 *
 */
@Repository
public interface CompetitionResultReadDao extends BaseReadDao<CompetitionResult> {

	/**
	 * 项目所有人员排名
	 * 
	 * @param params
	 * @param page
	 * @return
	 */
	List<Map<String, Object>> getAllResult(@Param(value = "params") Map<String, Object> params, Page page);

	/**
	 * 项目小组排名
	 * 
	 * @param params
	 * @param page
	 * @return
	 */
	List<Map<String, Object>> getByGroup(@Param(value = "params") Map<String, Object> params, Page page);

	/**
	 * 项目小组所有人员排名
	 * 
	 * @param params
	 * @param page
	 * @return
	 */
	List<Map<String, Object>> getGroupAllPersonResult(@Param(value = "params") Map<String, Object> params, Page page);

	Map<String, Object> getTotalDistanceAndResult(@Param(value = "params") Map<String, Object> params);

	List<Map<String, Object>> getScheduleRank(CompetitionResult cResult);
}
