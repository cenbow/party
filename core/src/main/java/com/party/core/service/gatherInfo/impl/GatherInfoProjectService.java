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
import com.party.core.dao.read.gatherInfo.GatherInfoProjectReadDao;
import com.party.core.dao.write.gatherInfo.GatherInfoProjectWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.gatherInfo.GatherInfoProject;
import com.party.core.service.gatherInfo.IGatherInfoProjectService;

/**
 * 人员信息采集项目
 * 
 * @author Administrator
 *
 */
@Service
public class GatherInfoProjectService implements IGatherInfoProjectService {

	@Autowired
	private GatherInfoProjectReadDao gatherInfoProjectReadDao;

	@Autowired
	private GatherInfoProjectWriteDao gatherInfoProjectWriteDao;

	@Override
	public String insert(GatherInfoProject t) {
		BaseModel.preInsert(t);
		boolean result = gatherInfoProjectWriteDao.insert(t);
		if (result) {
			return t.getId();
		}
		return null;
	}

	@Override
	public boolean update(GatherInfoProject t) {
		t.setUpdateDate(new Date());
		return gatherInfoProjectWriteDao.update(t);
	}

	@Override
	public boolean deleteLogic(String id) {
		if (Strings.isNullOrEmpty(id)) {
			return false;
		}
		return gatherInfoProjectWriteDao.deleteLogic(id);
	}

	@Override
	public boolean delete(String id) {
		if (Strings.isNullOrEmpty(id)) {
			return false;
		}
		return gatherInfoProjectWriteDao.delete(id);
	}

	@Override
	public boolean batchDeleteLogic(Set<String> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			return false;
		}
		return gatherInfoProjectWriteDao.batchDeleteLogic(ids);
	}

	@Override
	public boolean batchDelete(Set<String> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			return false;
		}
		return gatherInfoProjectWriteDao.batchDelete(ids);
	}

	@Override
	public GatherInfoProject get(String id) {
		return gatherInfoProjectReadDao.get(id);
	}

	@Override
	public List<GatherInfoProject> listPage(GatherInfoProject t, Page page) {
		return gatherInfoProjectReadDao.listPage(t, page);
	}

	@Override
	public List<GatherInfoProject> list(GatherInfoProject t) {
		return gatherInfoProjectReadDao.listPage(t, null);
	}

	@Override
	public List<GatherInfoProject> batchList(Set<String> ids, GatherInfoProject t, Page page) {
		if (CollectionUtils.isEmpty(ids)) {
			return Collections.EMPTY_LIST;
		}
		return gatherInfoProjectReadDao.batchList(ids, new HashedMap(), page);
	}

	@Override
	public List<GatherInfoProject> webListPage(GatherInfoProject project, Map<String, Object> params, Page page) {
		return gatherInfoProjectReadDao.webListPage(project, params, page);
	}

}
