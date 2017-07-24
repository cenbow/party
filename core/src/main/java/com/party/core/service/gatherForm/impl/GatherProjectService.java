package com.party.core.service.gatherForm.impl;

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
import com.party.core.dao.read.gatherForm.GatherProjectReadDao;
import com.party.core.dao.write.gatherForm.GatherProjectWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.gatherForm.GatherProject;
import com.party.core.service.gatherForm.IGatherProjectService;

/**
 * 信息采集
 * 
 * @author Administrator
 *
 */
@Service
public class GatherProjectService implements IGatherProjectService {

	@Autowired
	private GatherProjectReadDao gatherProjectReadDao;

	@Autowired
	private GatherProjectWriteDao gatherProjectWriteDao;

	@Override
	public String insert(GatherProject t) {
		BaseModel.preInsert(t);
		boolean result = gatherProjectWriteDao.insert(t);
		if (result) {
			return t.getId();
		}
		return null;
	}

	@Override
	public boolean update(GatherProject t) {
		t.setUpdateDate(new Date());
		return gatherProjectWriteDao.update(t);
	}

	@Override
	public boolean deleteLogic(String id) {
		if (Strings.isNullOrEmpty(id)) {
			return false;
		}
		return gatherProjectWriteDao.deleteLogic(id);
	}

	@Override
	public boolean delete(String id) {
		if (Strings.isNullOrEmpty(id)) {
			return false;
		}
		return gatherProjectWriteDao.delete(id);
	}

	@Override
	public boolean batchDeleteLogic(Set<String> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			return false;
		}
		return gatherProjectWriteDao.batchDeleteLogic(ids);
	}

	@Override
	public boolean batchDelete(Set<String> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			return false;
		}
		return gatherProjectWriteDao.batchDelete(ids);
	}

	@Override
	public GatherProject get(String id) {
		return gatherProjectReadDao.get(id);
	}

	@Override
	public List<GatherProject> listPage(GatherProject t, Page page) {
		return gatherProjectReadDao.listPage(t, page);
	}

	@Override
	public List<GatherProject> list(GatherProject t) {
		return gatherProjectReadDao.listPage(t, null);
	}

	@Override
	public List<GatherProject> batchList(Set<String> ids, GatherProject t, Page page) {
		if (CollectionUtils.isEmpty(ids)) {
			return Collections.EMPTY_LIST;
		}
		return gatherProjectReadDao.batchList(ids, new HashedMap(), page);
	}

	@Override
	public List<GatherProject> webListPage(GatherProject gatherProject, Map<String, Object> params, Page page) {
		return gatherProjectReadDao.webListPage(gatherProject, params, page);
	}
}
