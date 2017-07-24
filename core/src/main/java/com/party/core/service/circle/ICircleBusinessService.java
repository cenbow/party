package com.party.core.service.circle;

import com.party.core.model.circle.CircleBusiness;
import com.party.core.service.IBaseService;

/**
 * ICircleBusinessService
 * 
 * @author Juliana
 *
 */
public interface ICircleBusinessService extends IBaseService<CircleBusiness> {
	CircleBusiness findByBusinessId(String businessId, String type);

	boolean delByCircle(String id);
}
