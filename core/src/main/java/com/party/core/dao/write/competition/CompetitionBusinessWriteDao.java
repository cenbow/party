package com.party.core.dao.write.competition;

import org.springframework.stereotype.Repository;

import com.party.core.dao.write.BaseWriteDao;
import com.party.core.model.competition.CompetitionBusiness;

/**
 * 赛事项目与业务表关系
 * @author Administrator
 *
 */
@Repository
public interface CompetitionBusinessWriteDao extends BaseWriteDao<CompetitionBusiness> {
}
