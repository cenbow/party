package com.party.core.dao.read.competition;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.party.common.paging.Page;
import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.competition.CompetitionSchedule;

/**
 * 赛事日程
 * 
 * @author Administrator
 *
 */
@Repository
public interface CompetitionScheduleReadDao extends BaseReadDao<CompetitionSchedule> {

	/**
	 * web端列表查询
	 * 
	 * @param project
	 * @param params
	 * @param page
	 * @return
	 */
	List<CompetitionSchedule> webListPage(@Param(value = "schedule") CompetitionSchedule schedule,
			@Param(value = "params") Map<String, Object> params, Page page);

	/**
	 * 检查项目中比赛日是否有重复
	 * @param playDayStr
	 * @param projectId
	 * @param id
	 * @return
	 */
	List<CompetitionSchedule> checkPlayDay(@Param(value = "playDayStr") String playDayStr, @Param(value = "projectId") String projectId,
			@Param(value = "id") String id);
	
	List<CompetitionSchedule> scheduleResultAll(@Param(value = "schedule") CompetitionSchedule schedule, @Param(value = "params") Map<String, Object> params, Page page);

	/**
	 * 获取赛程的总里程
	 * @param projectId
	 * @return
	 */
	Double getSumDistance(String projectId);
}
