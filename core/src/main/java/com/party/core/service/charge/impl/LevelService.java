package com.party.core.service.charge.impl;

import com.party.common.paging.Page;
import com.party.common.utils.StringUtils;
import com.party.core.dao.read.charge.LevelReadDao;
import com.party.core.dao.write.charge.LevelWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.charge.Level;
import com.party.core.service.charge.ILevelService;
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
public class LevelService implements ILevelService {

    @Autowired
    private LevelReadDao levelReadDao;
    @Autowired
    private LevelWriteDao levelWriteDao;

    @Override
    public String insert(Level t) {
        BaseModel.preInsert(t);
        boolean result = levelWriteDao.insert(t);
        if (result) {
            return t.getId();
        }
        return null;
    }

    @Override
    public boolean update(Level t) {
        if (null == t)
            return false;
        t.setUpdateDate(new Date());
        return levelWriteDao.update(t);
    }

    @Override
    public boolean deleteLogic(String id) {
        if (StringUtils.isBlank(id))
            return false;

        return levelWriteDao.deleteLogic(id);
    }

    @Override
    public boolean delete(String id) {
        if (StringUtils.isBlank(id))
            return false;

        return levelWriteDao.delete(id);
    }

    @Override
    public boolean batchDeleteLogic(Set<String> ids) {
        if (CollectionUtils.isEmpty(ids))
            return false;

        return levelWriteDao.batchDeleteLogic(ids);
    }

    @Override
    public boolean batchDelete(Set<String> ids) {
        if (CollectionUtils.isEmpty(ids))
            return false;

        return levelWriteDao.batchDelete(ids);
    }

    @Override
    public Level get(String id) {
        return levelReadDao.get(id);
    }

    @Override
    public List<Level> listPage(Level t, Page page) {
        return levelReadDao.listPage(t, page);
    }

    @Override
    public List<Level> list(Level t) {
        return levelReadDao.listPage(t, null);
    }

    @Override
    public List<Level> batchList(Set<String> ids, Level t, Page page) {
        if (CollectionUtils.isEmpty(ids))
            return Collections.EMPTY_LIST;

        return levelReadDao.batchList(ids, new HashedMap(), page);
    }

    @Override
    public List<Level> webListPage(Level level, Map<String, Object> params, Page page) {
        return levelReadDao.webListPage(level, params, page);
    }

}
