package com.party.core.service.gatherForm;

import com.party.core.model.gatherForm.GatherFormOption;
import com.party.core.service.IBaseService;

public interface IGatherFormOptionService extends IBaseService<GatherFormOption> {

	boolean deleteByField(String id);

}
