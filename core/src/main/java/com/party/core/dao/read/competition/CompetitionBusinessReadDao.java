package com.party.core.dao.read.competition;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.competition.CompetitionBusiness;

@Repository
public interface CompetitionBusinessReadDao extends BaseReadDao<CompetitionBusiness> {
	CompetitionBusiness findByBusinessId(@Param("businessId") String businessId, @Param("type") String type);
}
