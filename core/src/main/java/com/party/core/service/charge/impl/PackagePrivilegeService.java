package com.party.core.service.charge.impl;

import com.party.common.paging.Page;
import com.party.common.utils.StringUtils;
import com.party.core.dao.read.charge.PackagePrivilegeReadDao;
import com.party.core.dao.write.charge.PackagePrivilegeWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.charge.PackagePrivilege;
import com.party.core.service.charge.IPackagePrivilegeService;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 等级实现
 *
 * @author Administrator
 */
@Service
public class PackagePrivilegeService implements IPackagePrivilegeService {

    @Autowired
    private PackagePrivilegeReadDao packagePrivilegeReadDao;
    @Autowired
    private PackagePrivilegeWriteDao packagePrivilegeWriteDao;

    @Override
    public String insert(PackagePrivilege t) {
        BaseModel.preInsert(t);
        boolean result = packagePrivilegeWriteDao.insert(t);
        if (result) {
            return t.getId();
        }
        return null;
    }

    @Override
    public boolean update(PackagePrivilege t) {
        if (null == t)
            return false;
        t.setUpdateDate(new Date());
        return packagePrivilegeWriteDao.update(t);
    }

    @Override
    public boolean deleteLogic(String id) {
        if (StringUtils.isBlank(id))
            return false;

        return packagePrivilegeWriteDao.deleteLogic(id);
    }

    @Override
    public boolean delete(String id) {
        if (StringUtils.isBlank(id))
            return false;

        return packagePrivilegeWriteDao.delete(id);
    }

    @Override
    public boolean batchDeleteLogic(Set<String> ids) {
        if (CollectionUtils.isEmpty(ids))
            return false;

        return packagePrivilegeWriteDao.batchDeleteLogic(ids);
    }

    @Override
    public boolean batchDelete(Set<String> ids) {
        if (CollectionUtils.isEmpty(ids))
            return false;

        return packagePrivilegeWriteDao.batchDelete(ids);
    }

    @Override
    public PackagePrivilege get(String id) {
        return packagePrivilegeReadDao.get(id);
    }

    @Override
    public List<PackagePrivilege> listPage(PackagePrivilege t, Page page) {
        return packagePrivilegeReadDao.listPage(t, page);
    }

    @Override
    public List<PackagePrivilege> list(PackagePrivilege t) {
        return packagePrivilegeReadDao.listPage(t, null);
    }

    @Override
    public List<PackagePrivilege> batchList(Set<String> ids,
                                            PackagePrivilege t, Page page) {
        if (CollectionUtils.isEmpty(ids))
            return Collections.EMPTY_LIST;

        return packagePrivilegeReadDao.batchList(ids, new HashedMap(), page);
    }

    @Override
    public PackagePrivilege getUnique(PackagePrivilege packagePrivilege) {
        return packagePrivilegeReadDao.getUnique(packagePrivilege);
    }

    @Override
    public PackagePrivilege findByPackage(Map<String, Object> params) {
        return packagePrivilegeReadDao.findByPackage(params);
    }

	@Override
	public List<Map<String, Object>> webListPage(Map<String, Object> params,
			Page page) {
		return packagePrivilegeReadDao.webListPage(params, page);
	}
}
