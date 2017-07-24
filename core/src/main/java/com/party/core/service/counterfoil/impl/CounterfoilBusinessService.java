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
import com.party.core.dao.read.counterfoil.CounterfoilBusinessReadDao;
import com.party.core.dao.write.counterfoil.CounterfoilBusinessWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.counterfoil.CounterfoilBusiness;
import com.party.core.service.counterfoil.ICounterfoilBusinessService;

@Service
public class CounterfoilBusinessService implements ICounterfoilBusinessService {
	@Autowired
	CounterfoilBusinessReadDao counterfoilBusinessReadDao;

	@Autowired
	CounterfoilBusinessWriteDao counterfoilBusinessWriteDao;

	/**
	 * 票据插入
	 *
	 * @param counterfoil
	 *            票据评论
	 * @return 插入结果（true/false）
	 */
	@Override
	public String insert(CounterfoilBusiness counterfoil) {
		BaseModel.preInsert(counterfoil);
		boolean result = counterfoilBusinessWriteDao.insert(counterfoil);
		if (result) {
			return counterfoil.getId();
		}
		return null;
	}

	/**
	 * 票据更新
	 *
	 * @param counterfoil
	 *            票据
	 * @return 更新结果（true/false）
	 */
	@Override
	public boolean update(CounterfoilBusiness counterfoil) {
		counterfoil.setUpdateDate(new Date());
		return counterfoilBusinessWriteDao.update(counterfoil);
	}

	/**
	 * 逻辑删除票据
	 *
	 * @param id
	 *            实体主键
	 * @return 删除结果（true/false）
	 */
	@Override
	public boolean deleteLogic(String id) {
		if (Strings.isNullOrEmpty(id)) {
			return false;
		}
		return counterfoilBusinessWriteDao.deleteLogic(id);
	}

	/**
	 * 物理删除票据
	 *
	 * @param id
	 *            实体主键
	 * @return 删除结果（true/false）
	 */
	@Override
	public boolean delete(String id) {
		if (Strings.isNullOrEmpty(id)) {
			return false;
		}
		return counterfoilBusinessWriteDao.delete(id);
	}

	/**
	 * 批量逻辑删除票据
	 *
	 * @param ids
	 *            主键集合
	 * @return 删除结果（true/false）
	 */
	@Override
	public boolean batchDeleteLogic(Set<String> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			return false;
		}
		return counterfoilBusinessWriteDao.batchDeleteLogic(ids);
	}

	/**
	 * 批量物理删除票据
	 *
	 * @param ids
	 *            主键集合
	 * @return 删除结果（true/false）
	 */
	@Override
	public boolean batchDelete(Set<String> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			return false;
		}
		return counterfoilBusinessWriteDao.batchDelete(ids);
	}

	/**
	 * 根据主键获取票据
	 *
	 * @param id
	 *            主键
	 * @return 票据
	 */
	@Override
	public CounterfoilBusiness get(String id) {
		return counterfoilBusinessReadDao.get(id);
	}

	/**
	 * 分页查询票据
	 *
	 * @param counterfoil
	 *            票据
	 * @param page
	 *            分页信息
	 * @return 票据列表
	 */
	@Override
	public List<CounterfoilBusiness> listPage(CounterfoilBusiness counterfoil, Page page) {
		return counterfoilBusinessReadDao.listPage(counterfoil, page);
	}

	/**
	 * 查询所有票据
	 *
	 * @param counterfoil
	 *            票据
	 * @return 票据列表
	 */
	@Override
	public List<CounterfoilBusiness> list(CounterfoilBusiness counterfoil) {
		return counterfoilBusinessReadDao.listPage(counterfoil, null);
	}

	/**
	 * 批量查询票据
	 *
	 * @param ids
	 *            主键集合
	 * @param CounterfoilBusiness
	 *            票据
	 * @param page
	 *            分页信息
	 * @return 票据列表
	 */
	@Override
	public List<CounterfoilBusiness> batchList(Set<String> ids, CounterfoilBusiness CounterfoilBusiness, Page page) {
		if (CollectionUtils.isEmpty(ids)) {
			return Collections.EMPTY_LIST;
		}
		return counterfoilBusinessReadDao.batchList(ids, new HashedMap(), page);
	}

	@Override
	public CounterfoilBusiness findByBusinessId(String businessId) {
		CounterfoilBusiness t = new CounterfoilBusiness();
		t.setBusinessId(businessId);
		return counterfoilBusinessReadDao.getUnique(t);
	}

	@Override
	public CounterfoilBusiness getUnique(CounterfoilBusiness counterfoilBusiness) {
		return counterfoilBusinessReadDao.getUnique(counterfoilBusiness);
	}

	@Override
	public List<CounterfoilBusiness> findByCounterfoilId(String counterfoilId) {
		CounterfoilBusiness t = new CounterfoilBusiness();
		t.setCounterfoilId(counterfoilId);
		return counterfoilBusinessReadDao.listPage(t, null);
	}

	@Override
	public void deleteByBusinessId(String businessId) {
		counterfoilBusinessWriteDao.deleteByBusinessId(businessId);
	}

}
