package com.party.core.service.gatherInfo.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.party.common.paging.Page;
import com.party.core.dao.read.gatherInfo.GatherInfoGroupReadDao;
import com.party.core.dao.write.gatherInfo.GatherInfoGroupWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.gatherInfo.GatherInfoGroup;
import com.party.core.service.gatherInfo.IGatherInfoGroupService;

/**
 * 人员小组
 * 
 * @author Administrator
 *
 */
@Service
public class GatherInfoGroupService implements IGatherInfoGroupService {

	@Autowired
	private GatherInfoGroupReadDao gatherInfoGroupReadDao;

	@Autowired
	private GatherInfoGroupWriteDao gatherInfoGroupWriteDao;

	@Override
	public String insert(GatherInfoGroup t) {
		BaseModel.preInsert(t);
		boolean result = gatherInfoGroupWriteDao.insert(t);
		if (result) {
			return t.getId();
		}
		return null;
	}

	@Override
	public boolean update(GatherInfoGroup t) {
		t.setUpdateDate(new Date());
		return gatherInfoGroupWriteDao.update(t);
	}

	@Override
	public boolean deleteLogic(String id) {
		if (Strings.isNullOrEmpty(id)) {
			return false;
		}
		return gatherInfoGroupWriteDao.deleteLogic(id);
	}

	@Override
	public boolean delete(String id) {
		if (Strings.isNullOrEmpty(id)) {
			return false;
		}
		return gatherInfoGroupWriteDao.delete(id);
	}

	@Override
	public boolean batchDeleteLogic(Set<String> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			return false;
		}
		return gatherInfoGroupWriteDao.batchDeleteLogic(ids);
	}

	@Override
	public boolean batchDelete(Set<String> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			return false;
		}
		return gatherInfoGroupWriteDao.batchDelete(ids);
	}

	@Override
	public GatherInfoGroup get(String id) {
		return gatherInfoGroupReadDao.get(id);
	}

	@Override
	public List<GatherInfoGroup> listPage(GatherInfoGroup t, Page page) {
		return gatherInfoGroupReadDao.listPage(t, page);
	}

	@Override
	public List<GatherInfoGroup> list(GatherInfoGroup t) {
		return gatherInfoGroupReadDao.listPage(t, null);
	}

	@Override
	public List<GatherInfoGroup> batchList(Set<String> ids, GatherInfoGroup t, Page page) {
		if (CollectionUtils.isEmpty(ids)) {
			return Collections.EMPTY_LIST;
		}
		return gatherInfoGroupReadDao.batchList(ids, new HashedMap(), page);
	}

	@Override
	public List<GatherInfoGroup> webListPage(GatherInfoGroup group, Map<String, Object> params, Page page) {
		return gatherInfoGroupReadDao.webListPage(group, params, page);
	}
}
