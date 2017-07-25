package com.party.core.service.charge.impl;

import com.party.common.paging.Page;
import com.party.common.utils.StringUtils;
import com.party.core.dao.read.charge.PackageMemberReadDao;
import com.party.core.dao.write.charge.PackageMemberWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.charge.PackageMember;
import com.party.core.service.charge.IPackageMemberService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 等级实现
 *
 * @author Administrator
 */
@Service
public class PackageMemberService implements IPackageMemberService {

    @Autowired
    private PackageMemberReadDao packageMemberReadDao;
    @Autowired
    private PackageMemberWriteDao packageMemberWriteDao;

    @Override
    public String insert(PackageMember t) {
        BaseModel.preInsert(t);
        boolean result = packageMemberWriteDao.insert(t);
        if (result) {
            return t.getId();
        }
        return null;
    }

    @Override
    public boolean update(PackageMember t) {
        if (null == t)
            return false;
        t.setUpdateDate(new Date());
        return packageMemberWriteDao.update(t);
    }

    @Override
    public boolean deleteLogic(String id) {
        if (StringUtils.isBlank(id))
            return false;

        return packageMemberWriteDao.deleteLogic(id);
    }

    @Override
    public boolean delete(String id) {
        if (StringUtils.isBlank(id))
            return false;

        return packageMemberWriteDao.delete(id);
    }

    @Override
    public boolean batchDeleteLogic(Set<String> ids) {
        if (CollectionUtils.isEmpty(ids))
            return false;

        return packageMemberWriteDao.batchDeleteLogic(ids);
    }

    @Override
    public boolean batchDelete(Set<String> ids) {
        if (CollectionUtils.isEmpty(ids))
            return false;

        return packageMemberWriteDao.batchDelete(ids);
    }

    @Override
    public PackageMember get(String id) {
        return packageMemberReadDao.get(id);
    }

    @Override
    public List<PackageMember> listPage(PackageMember t, Page page) {
        return packageMemberReadDao.listPage(t, page);
    }

    @Override
    public List<PackageMember> list(PackageMember t) {
        return packageMemberReadDao.listPage(t, null);
    }

    @Override
    public List<PackageMember> batchList(Set<String> ids, PackageMember t, Page page) {
        if (CollectionUtils.isEmpty(ids))
            return Collections.EMPTY_LIST;

        return packageMemberReadDao.batchList(ids, new HashedMap(), page);
    }

    @Override
    public PackageMember findByMemberId(PackageMember packageMember) {
        return packageMemberReadDao.findByMemberId(packageMember);
    }
}
