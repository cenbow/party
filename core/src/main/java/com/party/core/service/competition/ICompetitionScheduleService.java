package com.party.core.service.competition;

import java.util.List;
import java.util.Map;

import com.party.common.paging.Page;
import com.party.core.model.competition.CompetitionProject;
import com.party.core.model.competition.CompetitionSchedule;
import com.party.core.service.IBaseService;

/**
 * 赛事日程
 * 
 * @author Administrator
 *
 */
public interface ICompetitionScheduleService extends IBaseService<CompetitionSchedule> {
	/**
	 * web端列表查询
	 * 
	 * @param project
	 * @param params
	 * @param page
	 * @return
	 */
	List<CompetitionSchedule> webListPage(CompetitionSchedule schedule, Map<String, Object> params, Page page);

	List<CompetitionSchedule> findByProject(String id);

	List<CompetitionSchedule> checkPlayDay(String playDayStr, String projectId, String id);

	List<CompetitionSchedule> scheduleResultAll(CompetitionSchedule competitionSchedule, Map<String, Object> params, Page page);

	Double getSumDistance(String projectId);

}
