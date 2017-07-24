package com.party.core.service.resource;

import java.util.List;
import java.util.Map;

import com.party.common.paging.Page;
import com.party.core.model.resource.Resource;
import com.party.core.service.IBaseService;

/**
 * 资源服务接口
 * 
 * @author Administrator
 *
 */
public interface IResourceService extends IBaseService<Resource> {
	List<Resource> findByType(String type);

	List<Resource> webListPage(Resource resource, Map<String, Object> params, Page page);
}
