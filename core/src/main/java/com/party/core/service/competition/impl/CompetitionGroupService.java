package com.party.core.service.competition.impl;

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
import com.party.core.dao.read.competition.CompetitionGroupReadDao;
import com.party.core.dao.write.competition.CompetitionGroupWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.competition.CompetitionGroup;
import com.party.core.service.competition.ICompetitionGroupService;

/**
 * 人员小组
 * 
 * @author Administrator
 *
 */
@Service
public class CompetitionGroupService implements ICompetitionGroupService {

	@Autowired
	private CompetitionGroupReadDao competitionGroupReadDao;

	@Autowired
	private CompetitionGroupWriteDao competitionGroupWriteDao;

	@Override
	public String insert(CompetitionGroup t) {
		BaseModel.preInsert(t);
		boolean result = competitionGroupWriteDao.insert(t);
		if (result) {
			return t.getId();
		}
		return null;
	}

	@Override
	public boolean update(CompetitionGroup t) {
		t.setUpdateDate(new Date());
		return competitionGroupWriteDao.update(t);
	}

	@Override
	public boolean deleteLogic(String id) {
		if (Strings.isNullOrEmpty(id)) {
			return false;
		}
		return competitionGroupWriteDao.deleteLogic(id);
	}

	@Override
	public boolean delete(String id) {
		if (Strings.isNullOrEmpty(id)) {
			return false;
		}
		return competitionGroupWriteDao.delete(id);
	}

	@Override
	public boolean batchDeleteLogic(Set<String> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			return false;
		}
		return competitionGroupWriteDao.batchDeleteLogic(ids);
	}

	@Override
	public boolean batchDelete(Set<String> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			return false;
		}
		return competitionGroupWriteDao.batchDelete(ids);
	}

	@Override
	public CompetitionGroup get(String id) {
		return competitionGroupReadDao.get(id);
	}

	@Override
	public List<CompetitionGroup> listPage(CompetitionGroup t, Page page) {
		return competitionGroupReadDao.listPage(t, page);
	}

	@Override
	public List<CompetitionGroup> list(CompetitionGroup t) {
		return competitionGroupReadDao.listPage(t, null);
	}

	@Override
	public List<CompetitionGroup> batchList(Set<String> ids, CompetitionGroup t, Page page) {
		if (CollectionUtils.isEmpty(ids)) {
			return Collections.EMPTY_LIST;
		}
		return competitionGroupReadDao.batchList(ids, new HashedMap(), page);
	}

	@Override
	public List<CompetitionGroup> webListPage(CompetitionGroup group, Map<String, Object> params, Page page) {
		return competitionGroupReadDao.webListPage(group, params, page);
	}

	@Override
	public CompetitionGroup getUnique(CompetitionGroup t) {
		return competitionGroupReadDao.getUnique(t);
	}

	@Override
	public List<CompetitionGroup> checkGroupName(String groupId, String groupName, String projectId) {
		return competitionGroupReadDao.checkGroupName(groupId, groupName, projectId);
	}
}
