package com.party.core.service.counterfoil.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.party.common.paging.Page;
import com.party.core.dao.read.counterfoil.CounterfoilReadDao;
import com.party.core.dao.write.counterfoil.CounterfoilWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.counterfoil.Counterfoil;
import com.party.core.service.counterfoil.ICounterfoilService;

/**
 * 票据服务实现
 */
@Service
public class CounterfoilService implements ICounterfoilService {

    @Autowired
    CounterfoilReadDao counterfoilReadDao;

    @Autowired
    CounterfoilWriteDao counterfoilWriteDao;

    /**
     * 票据插入
     *
     * @param counterfoil 票据评论
     * @return 插入结果（true/false）
     */
    @Override
    public String insert(Counterfoil counterfoil) {
        BaseModel.preInsert(counterfoil);
        boolean result = counterfoilWriteDao.insert(counterfoil);
        if (result) {
            return counterfoil.getId();
        }
        return null;
    }

    /**
     * 票据更新
     *
     * @param counterfoil 票据
     * @return 更新结果（true/false）
     */
    @Override
    public boolean update(Counterfoil counterfoil) {
        counterfoil.setUpdateDate(new Date());
        return counterfoilWriteDao.update(counterfoil);
    }

    /**
     * 逻辑删除票据
     *
     * @param id 实体主键
     * @return 删除结果（true/false）
     */
    @Override
    public boolean deleteLogic(String id) {
        if (Strings.isNullOrEmpty(id)) {
            return false;
        }
        return counterfoilWriteDao.deleteLogic(id);
    }

    /**
     * 物理删除票据
     *
     * @param id 实体主键
     * @return 删除结果（true/false）
     */
    @Override
    public boolean delete(String id) {
        if (Strings.isNullOrEmpty(id)) {
            return false;
        }
        return counterfoilWriteDao.delete(id);
    }

    /**
     * 批量逻辑删除票据
     *
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    @Override
    public boolean batchDeleteLogic(Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return false;
        }
        return counterfoilWriteDao.batchDeleteLogic(ids);
    }

    /**
     * 批量物理删除票据
     *
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    @Override
    public boolean batchDelete(Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return false;
        }
        return counterfoilWriteDao.batchDelete(ids);
    }

    /**
     * 根据主键获取票据
     *
     * @param id 主键
     * @return 票据
     */
    @Override
    public Counterfoil get(String id) {
        return counterfoilReadDao.get(id);
    }

    /**
     * 分页查询票据
     *
     * @param counterfoil 票据
     * @param page        分页信息
     * @return 票据列表
     */
    @Override
    public List<Counterfoil> listPage(Counterfoil counterfoil, Page page) {
        return counterfoilReadDao.listPage(counterfoil, page);
    }

    /**
     * 查询所有票据
     *
     * @param counterfoil 票据
     * @return 票据列表
     */
    @Override
    public List<Counterfoil> list(Counterfoil counterfoil) {
        return counterfoilReadDao.listPage(counterfoil, null);
    }

    /**
     * 批量查询票据
     *
     * @param ids         主键集合
     * @param Counterfoil 票据
     * @param page        分页信息
     * @return 票据列表
     */
    @Override
    public List<Counterfoil> batchList(Set<String> ids, Counterfoil Counterfoil, Page page) {
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.EMPTY_LIST;
        }
        return counterfoilReadDao.batchList(ids, new HashedMap(), page);
    }

	@Override
	public Counterfoil getUnique(Counterfoil t) {
		return counterfoilReadDao.getUnique(t);
	}

}
