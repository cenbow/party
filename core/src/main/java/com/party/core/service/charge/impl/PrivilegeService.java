package com.party.core.service.charge.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.party.common.paging.Page;
import com.party.common.utils.StringUtils;
import com.party.core.dao.read.charge.PrivilegeReadDao;
import com.party.core.dao.write.charge.PrivilegeWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.charge.ProductPrivilege;
import com.party.core.service.charge.IPrivilegeService;


/**
 * 等级实现
 *
 * @author Administrator
 */
@Service
public class PrivilegeService implements IPrivilegeService {

    @Autowired
    private PrivilegeReadDao privilegeReadDao;
    @Autowired
    private PrivilegeWriteDao privilegeWriteDao;

    @Override
    public String insert(ProductPrivilege t) {
        BaseModel.preInsert(t);
        boolean result = privilegeWriteDao.insert(t);
        if (result) {
            return t.getId();
        }
        return null;
    }

    @Override
    public boolean update(ProductPrivilege t) {
        if (null == t)
            return false;
        t.setUpdateDate(new Date());
        return privilegeWriteDao.update(t);
    }

    @Override
    public boolean deleteLogic(String id) {
        if (StringUtils.isBlank(id))
            return false;

        return privilegeWriteDao.deleteLogic(id);
    }

    @Override
    public boolean delete(String id) {
        if (StringUtils.isBlank(id))
            return false;

        return privilegeWriteDao.delete(id);
    }

    @Override
    public boolean batchDeleteLogic(Set<String> ids) {
        if (CollectionUtils.isEmpty(ids))
            return false;

        return privilegeWriteDao.batchDeleteLogic(ids);
    }

    @Override
    public boolean batchDelete(Set<String> ids) {
        if (CollectionUtils.isEmpty(ids))
            return false;

        return privilegeWriteDao.batchDelete(ids);
    }

    @Override
    public ProductPrivilege get(String id) {
        return privilegeReadDao.get(id);
    }

    @Override
    public List<ProductPrivilege> listPage(ProductPrivilege t, Page page) {
        return privilegeReadDao.listPage(t, page);
    }

    @Override
    public List<ProductPrivilege> list(ProductPrivilege t) {
        return privilegeReadDao.listPage(t, null);
    }

    @Override
    public List<ProductPrivilege> batchList(Set<String> ids, ProductPrivilege t, Page page) {
        if (CollectionUtils.isEmpty(ids))
            return Collections.EMPTY_LIST;

        return privilegeReadDao.batchList(ids, new HashedMap(), page);
    }

	@Override
	public List<ProductPrivilege> webListPage(ProductPrivilege privilege,
			Map<String, Object> params, Page page) {
		return privilegeReadDao.webListPage(privilege, params, page);
	}
}
