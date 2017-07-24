package com.party.core.service.charge.impl;

import com.party.common.paging.Page;
import com.party.common.utils.StringUtils;
import com.party.core.dao.read.charge.LevelMemberReadDao;
import com.party.core.dao.write.charge.LevelMemberWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.charge.LevelMember;
import com.party.core.service.charge.ILevelMemberService;
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
public class LevelMemberService implements ILevelMemberService {

    @Autowired
    private LevelMemberReadDao levelMemberReadDao;
    @Autowired
    private LevelMemberWriteDao levelMemberWriteDao;

    @Override
    public String insert(LevelMember t) {
        BaseModel.preInsert(t);
        boolean result = levelMemberWriteDao.insert(t);
        if (result) {
            return t.getId();
        }
        return null;
    }

    @Override
    public boolean update(LevelMember t) {
        if (null == t)
            return false;
        t.setUpdateDate(new Date());
        return levelMemberWriteDao.update(t);
    }

    @Override
    public boolean deleteLogic(String id) {
        if (StringUtils.isBlank(id))
            return false;

        return levelMemberWriteDao.deleteLogic(id);
    }

    @Override
    public boolean delete(String id) {
        if (StringUtils.isBlank(id))
            return false;

        return levelMemberWriteDao.delete(id);
    }

    @Override
    public boolean batchDeleteLogic(Set<String> ids) {
        if (CollectionUtils.isEmpty(ids))
            return false;

        return levelMemberWriteDao.batchDeleteLogic(ids);
    }

    @Override
    public boolean batchDelete(Set<String> ids) {
        if (CollectionUtils.isEmpty(ids))
            return false;

        return levelMemberWriteDao.batchDelete(ids);
    }

    @Override
    public LevelMember get(String id) {
        return levelMemberReadDao.get(id);
    }

    @Override
    public List<LevelMember> listPage(LevelMember t, Page page) {
        return levelMemberReadDao.listPage(t, page);
    }

    @Override
    public List<LevelMember> list(LevelMember t) {
        return levelMemberReadDao.listPage(t, null);
    }

    @Override
    public List<LevelMember> batchList(Set<String> ids, LevelMember t, Page page) {
        if (CollectionUtils.isEmpty(ids))
            return Collections.EMPTY_LIST;

        return levelMemberReadDao.batchList(ids, new HashedMap(), page);
    }

}
