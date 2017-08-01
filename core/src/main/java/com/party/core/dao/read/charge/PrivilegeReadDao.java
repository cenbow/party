package com.party.core.dao.read.charge;

import java.util.List;
import java.util.Map;

import com.party.common.paging.Page;
import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.charge.ProductPrivilege;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 产品权限
 */
@Repository
public interface PrivilegeReadDao extends BaseReadDao<ProductPrivilege> {

	List<ProductPrivilege> webListPage(@Param("privilege") ProductPrivilege privilege, @Param("params") Map<String, Object> params, Page page);

}
