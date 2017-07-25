package com.party.core.service.charge.impl;

import com.party.common.paging.Page;
import com.party.common.utils.StringUtils;
import com.party.core.dao.read.charge.PackageReadDao;
import com.party.core.dao.write.charge.PackageWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.charge.ProductPackage;
import com.party.core.service.charge.IPackageService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * 等级实现
 *
 * @author Administrator
 */
@Service
public class PackageService implements IPackageService {

    @Autowired
    private PackageReadDao packageReadDao;
    @Autowired
    private PackageWriteDao packageWriteDao;

    @Override
    public String insert(ProductPackage t) {
        BaseModel.preInsert(t);
        boolean result = packageWriteDao.insert(t);
        if (result) {
            return t.getId();
        }
        return null;
    }

    @Override
    public boolean update(ProductPackage t) {
        if (null == t)
            return false;
        t.setUpdateDate(new Date());
        return packageWriteDao.update(t);
    }

    @Override
    public boolean deleteLogic(String id) {
        if (StringUtils.isBlank(id))
            return false;

        return packageWriteDao.deleteLogic(id);
    }

    @Override
    public boolean delete(String id) {
        if (StringUtils.isBlank(id))
            return false;

        return packageWriteDao.delete(id);
    }

    @Override
    public boolean batchDeleteLogic(Set<String> ids) {
        if (CollectionUtils.isEmpty(ids))
            return false;

        return packageWriteDao.batchDeleteLogic(ids);
    }

    @Override
    public boolean batchDelete(Set<String> ids) {
        if (CollectionUtils.isEmpty(ids))
            return false;

        return packageWriteDao.batchDelete(ids);
    }

    @Override
    public ProductPackage get(String id) {
        return packageReadDao.get(id);
    }

    @Override
    public List<ProductPackage> listPage(ProductPackage t, Page page) {
        return packageReadDao.listPage(t, page);
    }

    @Override
    public List<ProductPackage> list(ProductPackage t) {
        return packageReadDao.listPage(t, null);
    }

    @Override
    public List<ProductPackage> batchList(Set<String> ids, ProductPackage t, Page page) {
        if (CollectionUtils.isEmpty(ids))
            return Collections.EMPTY_LIST;

        return packageReadDao.batchList(ids, new HashedMap(), page);
    }

    @Override
    public List<ProductPackage> webListPage(ProductPackage productPackage, Map<String, Object> params, Page page) {
        return packageReadDao.webListPage(productPackage, params, page);
    }

}
