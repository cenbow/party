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
import com.party.core.dao.read.competition.CompetitionProjectReadDao;
import com.party.core.dao.write.competition.CompetitionProjectWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.competition.CompetitionProject;
import com.party.core.service.competition.ICompetitionProjectService;

/**
 * 赛事项目
 * 
 * @author Administrator
 *
 */
@Service
public class CompetitionProjectService implements ICompetitionProjectService {

	@Autowired
	private CompetitionProjectReadDao competitionProjectReadDao;

	@Autowired
	private CompetitionProjectWriteDao competitionProjectWriteDao;

	@Override
	public String insert(CompetitionProject t) {
		BaseModel.preInsert(t);
		boolean result = competitionProjectWriteDao.insert(t);
		if (result) {
			return t.getId();
		}
		return null;
	}

	@Override
	public boolean update(CompetitionProject t) {
		t.setUpdateDate(new Date());
		return competitionProjectWriteDao.update(t);
	}

	@Override
	public boolean deleteLogic(String id) {
		if (Strings.isNullOrEmpty(id)) {
			return false;
		}
		return competitionProjectWriteDao.deleteLogic(id);
	}

	@Override
	public boolean delete(String id) {
		if (Strings.isNullOrEmpty(id)) {
			return false;
		}
		return competitionProjectWriteDao.delete(id);
	}

	@Override
	public boolean batchDeleteLogic(Set<String> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			return false;
		}
		return competitionProjectWriteDao.batchDeleteLogic(ids);
	}

	@Override
	public boolean batchDelete(Set<String> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			return false;
		}
		return competitionProjectWriteDao.batchDelete(ids);
	}

	@Override
	public CompetitionProject get(String id) {
		return competitionProjectReadDao.get(id);
	}

	@Override
	public List<CompetitionProject> listPage(CompetitionProject t, Page page) {
		return competitionProjectReadDao.listPage(t, page);
	}

	@Override
	public List<CompetitionProject> list(CompetitionProject t) {
		return competitionProjectReadDao.listPage(t, null);
	}

	@Override
	public List<CompetitionProject> batchList(Set<String> ids, CompetitionProject t, Page page) {
		if (CollectionUtils.isEmpty(ids)) {
			return Collections.EMPTY_LIST;
		}
		return competitionProjectReadDao.batchList(ids, new HashedMap(), page);
	}

	@Override
	public List<CompetitionProject> webListPage(CompetitionProject project, Map<String, Object> params, Page page) {
		return competitionProjectReadDao.webListPage(project, params, page);
	}
}
