package com.party.core.service.charge;

import com.party.common.paging.Page;
import com.party.core.model.charge.ProductPackage;
import com.party.core.service.IBaseService;

import java.util.List;
import java.util.Map;

/**
 * 等级接口
 */
public interface IPackageService extends IBaseService<ProductPackage> {

    List<ProductPackage> webListPage(ProductPackage productPackage, Map<String, Object> params, Page page);
}
