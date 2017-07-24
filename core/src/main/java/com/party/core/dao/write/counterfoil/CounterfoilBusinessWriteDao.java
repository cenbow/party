package com.party.core.dao.write.counterfoil;

import com.party.core.dao.write.BaseWriteDao;
import com.party.core.model.counterfoil.CounterfoilBusiness;

public interface CounterfoilBusinessWriteDao extends BaseWriteDao<CounterfoilBusiness> {

	void deleteByBusinessId(String businessId);

}
