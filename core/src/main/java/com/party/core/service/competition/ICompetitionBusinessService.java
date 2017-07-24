package com.party.core.service.competition;

import com.party.core.model.competition.CompetitionBusiness;
import com.party.core.service.IBaseService;

/**
 * 赛事项目与业务表关系
 * @author Administrator
 *
 */
public interface ICompetitionBusinessService extends IBaseService<CompetitionBusiness> {
	CompetitionBusiness findByBusinessId(String businessId, String type);
}
