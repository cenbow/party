package com.party.core.dao.read.charge;

import com.party.common.paging.Page;
import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.charge.PackagePrivilege;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 等级
 */
@Repository
public interface PackagePrivilegeReadDao extends BaseReadDao<PackagePrivilege> {

    PackagePrivilege findByPackage(@Param("params") Map<String, Object> params);

	List<Map<String, Object>> webListPage(@Param("params") Map<String, Object> params, Page page);
}
