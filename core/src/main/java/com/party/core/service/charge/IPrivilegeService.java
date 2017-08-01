package com.party.core.service.charge;

import java.util.List;
import java.util.Map;

import com.party.common.paging.Page;
import com.party.core.model.charge.ProductPrivilege;
import com.party.core.service.IBaseService;

/**
 * 产品权限
 */
public interface IPrivilegeService extends IBaseService<ProductPrivilege> {

	List<ProductPrivilege> webListPage(ProductPrivilege privilege,
			Map<String, Object> params, Page page);

}
