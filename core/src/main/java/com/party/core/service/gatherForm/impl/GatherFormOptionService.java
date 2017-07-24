package com.party.core.service.gatherForm.impl;

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
import com.party.core.dao.read.gatherForm.GatherFormOptionReadDao;
import com.party.core.dao.write.gatherForm.GatherFormOptionWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.gatherForm.GatherFormOption;
import com.party.core.service.gatherForm.IGatherFormOptionService;

/**
 * 信息采集
 * @author Administrator
 *
 */
@Service
public class GatherFormOptionService implements IGatherFormOptionService {

	@Autowired
	private GatherFormOptionReadDao gatherFormOptionReadDao;

	@Autowired
	private GatherFormOptionWriteDao gatherFormOptionWriteDao;

	@Override
	public String insert(GatherFormOption t) {
		BaseModel.preInsert(t);
		boolean result = gatherFormOptionWriteDao.insert(t);
		if (result) {
			return t.getId();
		}
		return null;
	}

	@Override
	public boolean update(GatherFormOption t) {
		t.setUpdateDate(new Date());
		return gatherFormOptionWriteDao.update(t);
	}

	@Override
	public boolean deleteLogic(String id) {
		if (Strings.isNullOrEmpty(id)) {
			return false;
		}
		return gatherFormOptionWriteDao.deleteLogic(id);
	}

	@Override
	public boolean delete(String id) {
		if (Strings.isNullOrEmpty(id)) {
			return false;
		}
		return gatherFormOptionWriteDao.delete(id);
	}

	@Override
	public boolean batchDeleteLogic(Set<String> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			return false;
		}
		return gatherFormOptionWriteDao.batchDeleteLogic(ids);
	}

	@Override
	public boolean batchDelete(Set<String> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			return false;
		}
		return gatherFormOptionWriteDao.batchDelete(ids);
	}

	@Override
	public GatherFormOption get(String id) {
		return gatherFormOptionReadDao.get(id);
	}

	@Override
	public List<GatherFormOption> listPage(GatherFormOption t, Page page) {
		return gatherFormOptionReadDao.listPage(t, page);
	}

	@Override
	public List<GatherFormOption> list(GatherFormOption t) {
		return gatherFormOptionReadDao.listPage(t, null);
	}

	@Override
	public List<GatherFormOption> batchList(Set<String> ids, GatherFormOption t, Page page) {
		if (CollectionUtils.isEmpty(ids)) {
			return Collections.EMPTY_LIST;
		}
		return gatherFormOptionReadDao.batchList(ids, new HashedMap(), page);
	}

	@Override
	public boolean deleteByField(String fieldId) {
		return gatherFormOptionWriteDao.deleteByField(fieldId);
	}
}
