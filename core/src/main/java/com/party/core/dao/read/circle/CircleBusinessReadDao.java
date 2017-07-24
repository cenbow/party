package com.party.core.dao.read.circle;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.circle.CircleBusiness;

@Repository
public interface CircleBusinessReadDao extends BaseReadDao<CircleBusiness> {
	CircleBusiness findByBusinessId(@Param("businessId") String businessId, @Param("type") String type);
}
