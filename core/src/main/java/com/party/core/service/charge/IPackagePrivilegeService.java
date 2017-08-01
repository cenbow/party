package com.party.core.service.charge;

import com.party.common.paging.Page;
import com.party.core.model.charge.PackagePrivilege;
import com.party.core.service.IBaseService;

import java.util.List;
import java.util.Map;

/**
 * 产品权限
 */
public interface IPackagePrivilegeService extends IBaseService<PackagePrivilege> {

    PackagePrivilege getUnique(PackagePrivilege packagePrivilege);

    PackagePrivilege findByPackage(Map<String, Object> params);

	List<Map<String, Object>> webListPage(Map<String, Object> params, Page page);
}
