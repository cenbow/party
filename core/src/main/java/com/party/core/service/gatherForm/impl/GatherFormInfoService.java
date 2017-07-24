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
import com.party.core.dao.read.gatherForm.GatherFormInfoReadDao;
import com.party.core.dao.write.gatherForm.GatherFormInfoWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.gatherForm.GatherFormInfo;
import com.party.core.service.gatherForm.IGatherFormInfoService;

/**
 * 信息采集
 * @author Administrator
 *
 */
@Service
public class GatherFormInfoService implements IGatherFormInfoService {

	@Autowired
	private GatherFormInfoReadDao gatherFormInfoReadDao;

	@Autowired
	private GatherFormInfoWriteDao gatherFormInfoWriteDao;

	@Override
	public String insert(GatherFormInfo t) {
		BaseModel.preInsert(t);
		boolean result = gatherFormInfoWriteDao.insert(t);
		if (result) {
			return t.getId();
		}
		return null;
	}

	@Override
	public boolean update(GatherFormInfo t) {
		t.setUpdateDate(new Date());
		return gatherFormInfoWriteDao.update(t);
	}

	@Override
	public boolean deleteLogic(String id) {
		if (Strings.isNullOrEmpty(id)) {
			return false;
		}
		return gatherFormInfoWriteDao.deleteLogic(id);
	}

	@Override
	public boolean delete(String id) {
		if (Strings.isNullOrEmpty(id)) {
			return false;
		}
		return gatherFormInfoWriteDao.delete(id);
	}

	@Override
	public boolean batchDeleteLogic(Set<String> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			return false;
		}
		return gatherFormInfoWriteDao.batchDeleteLogic(ids);
	}

	@Override
	public boolean batchDelete(Set<String> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			return false;
		}
		return gatherFormInfoWriteDao.batchDelete(ids);
	}

	@Override
	public GatherFormInfo get(String id) {
		return gatherFormInfoReadDao.get(id);
	}

	@Override
	public List<GatherFormInfo> listPage(GatherFormInfo t, Page page) {
		return gatherFormInfoReadDao.listPage(t, page);
	}

	@Override
	public List<GatherFormInfo> list(GatherFormInfo t) {
		return gatherFormInfoReadDao.listPage(t, null);
	}

	@Override
	public List<GatherFormInfo> batchList(Set<String> ids, GatherFormInfo t, Page page) {
		if (CollectionUtils.isEmpty(ids)) {
			return Collections.EMPTY_LIST;
		}
		return gatherFormInfoReadDao.batchList(ids, new HashedMap(), page);
	}

	@Override
	public Integer getMaxIndex(String projectId) {
		return gatherFormInfoReadDao.getMaxIndex(projectId);
	}

	@Override
	public List<Map<String, Object>> webListPage(Map<String, Object> params, Page page) {
		return gatherFormInfoReadDao.webListPage(params, page);
	}

	@Override
	public boolean deleteByProjectId(String projectId, String maxIndex) {
		return gatherFormInfoWriteDao.deleteByProjectId(projectId, maxIndex);
	}
}
