package com.party.core.service.counterfoil;

import com.party.core.model.counterfoil.Counterfoil;
import com.party.core.service.IBaseService;

/**
 * 票据服务接口
 *
 * @author Administrator
 */
public interface ICounterfoilService extends IBaseService<Counterfoil> {

	Counterfoil getUnique(Counterfoil t);

}
