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
import com.party.core.dao.read.gatherForm.GatherFormReadDao;
import com.party.core.dao.write.gatherForm.GatherFormWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.gatherForm.GatherForm;
import com.party.core.service.gatherForm.IGatherFormService;

/**
 * 信息采集
 * @author Administrator
 *
 */
@Service
public class GatherFormService implements IGatherFormService {

	@Autowired
	private GatherFormReadDao gatherFormReadDao;

	@Autowired
	private GatherFormWriteDao gatherFormWriteDao;

	@Override
	public String insert(GatherForm t) {
		BaseModel.preInsert(t);
		boolean result = gatherFormWriteDao.insert(t);
		if (result) {
			return t.getId();
		}
		return null;
	}

	@Override
	public boolean update(GatherForm t) {
		t.setUpdateDate(new Date());
		return gatherFormWriteDao.update(t);
	}

	@Override
	public boolean deleteLogic(String id) {
		if (Strings.isNullOrEmpty(id)) {
			return false;
		}
		return gatherFormWriteDao.deleteLogic(id);
	}

	@Override
	public boolean delete(String id) {
		if (Strings.isNullOrEmpty(id)) {
			return false;
		}
		return gatherFormWriteDao.delete(id);
	}

	@Override
	public boolean batchDeleteLogic(Set<String> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			return false;
		}
		return gatherFormWriteDao.batchDeleteLogic(ids);
	}

	@Override
	public boolean batchDelete(Set<String> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			return false;
		}
		return gatherFormWriteDao.batchDelete(ids);
	}

	@Override
	public GatherForm get(String id) {
		return gatherFormReadDao.get(id);
	}

	@Override
	public List<GatherForm> listPage(GatherForm t, Page page) {
		return gatherFormReadDao.listPage(t, page);
	}

	@Override
	public List<GatherForm> list(GatherForm t) {
		return gatherFormReadDao.listPage(t, null);
	}

	@Override
	public List<GatherForm> batchList(Set<String> ids, GatherForm t, Page page) {
		if (CollectionUtils.isEmpty(ids)) {
			return Collections.EMPTY_LIST;
		}
		return gatherFormReadDao.batchList(ids, new HashedMap(), page);
	}

	@Override
	public boolean deleteByProject(String projectId) {
		return gatherFormWriteDao.deleteByProject(projectId);
	}

	@Override
	public Integer getCount(String projectId) {
		return gatherFormReadDao.getCount(projectId);
	}
}
