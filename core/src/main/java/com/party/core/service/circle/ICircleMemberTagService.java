package com.party.core.service.circle;

import java.util.List;

import com.party.core.model.circle.CircleMemberTag;
import com.party.core.service.IBaseService;

/**
 * ICircleMemberTagService
 * @author Juliana
 *
 */
public interface ICircleMemberTagService extends IBaseService<CircleMemberTag> {

	List<String> getByTagsId(String tags);

}
