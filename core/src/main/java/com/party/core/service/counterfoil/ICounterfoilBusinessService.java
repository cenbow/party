package com.party.core.service.counterfoil;

import java.util.List;

import com.party.core.model.counterfoil.CounterfoilBusiness;
import com.party.core.service.IBaseService;

/**
 * 票据业务中间表
 * 
 * @author Administrator
 *
 */
public interface ICounterfoilBusinessService extends IBaseService<CounterfoilBusiness> {
	CounterfoilBusiness findByBusinessId(String businessId);

	CounterfoilBusiness getUnique(CounterfoilBusiness counterfoilBusiness);

	List<CounterfoilBusiness> findByCounterfoilId(String counterfoilId);

	void deleteByBusinessId(String businessId);
}
