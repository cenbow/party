package com.party.core.dao.write.circle;

import org.springframework.stereotype.Repository;

import com.party.core.dao.write.BaseWriteDao;
import com.party.core.model.circle.CircleBusiness;

/**
 * CircleBusinessWriteDao
 *
 * @author Juliana
 * @data 2016-12-14
 */
@Repository
public interface CircleBusinessWriteDao extends BaseWriteDao<CircleBusiness> {

	boolean delByCircle(String id);
}
