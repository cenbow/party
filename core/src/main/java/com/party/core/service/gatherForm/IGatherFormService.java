package com.party.core.service.gatherForm;

import com.party.core.model.gatherForm.GatherForm;
import com.party.core.service.IBaseService;

public interface IGatherFormService extends IBaseService<GatherForm> {

	boolean deleteByProject(String projectId);

	Integer getCount(String projectId);

}
