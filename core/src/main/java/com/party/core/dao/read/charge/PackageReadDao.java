package com.party.core.dao.read.charge;

import com.party.common.paging.Page;
import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.charge.ProductPackage;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 等级
 */
@Repository
public interface PackageReadDao extends BaseReadDao<ProductPackage> {

    List<ProductPackage> webListPage(@Param("level") ProductPackage productPackage, @Param("params") Map<String, Object> params, Page page);
}
