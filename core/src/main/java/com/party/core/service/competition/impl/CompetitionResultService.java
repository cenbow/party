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
import com.party.core.dao.read.competition.CompetitionResultReadDao;
import com.party.core.dao.write.competition.CompetitionResultWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.competition.CompetitionResult;
import com.party.core.service.competition.ICompetitionResultService;

/**
 * 参赛人员成绩
 * 
 * @author Administrator
 *
 */
@Service
public class CompetitionResultService implements ICompetitionResultService {

	@Autowired
	private CompetitionResultReadDao competitionResultReadDao;

	@Autowired
	private CompetitionResultWriteDao competitionResultWriteDao;

	@Override
	public String insert(CompetitionResult t) {
		BaseModel.preInsert(t);
		boolean result = competitionResultWriteDao.insert(t);
		if (result) {
			return t.getId();
		}
		return null;
	}

	@Override
	public boolean update(CompetitionResult t) {
		t.setUpdateDate(new Date());
		return competitionResultWriteDao.update(t);
	}

	@Override
	public boolean deleteLogic(String id) {
		if (Strings.isNullOrEmpty(id)) {
			return false;
		}
		return competitionResultWriteDao.deleteLogic(id);
	}

	@Override
	public boolean delete(String id) {
		if (Strings.isNullOrEmpty(id)) {
			return false;
		}
		return competitionResultWriteDao.delete(id);
	}

	@Override
	public boolean batchDeleteLogic(Set<String> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			return false;
		}
		return competitionResultWriteDao.batchDeleteLogic(ids);
	}

	@Override
	public boolean batchDelete(Set<String> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			return false;
		}
		return competitionResultWriteDao.batchDelete(ids);
	}

	@Override
	public CompetitionResult get(String id) {
		return competitionResultReadDao.get(id);
	}

	@Override
	public List<CompetitionResult> listPage(CompetitionResult t, Page page) {
		return competitionResultReadDao.listPage(t, page);
	}

	@Override
	public List<CompetitionResult> list(CompetitionResult t) {
		return competitionResultReadDao.listPage(t, null);
	}

	@Override
	public List<CompetitionResult> batchList(Set<String> ids, CompetitionResult t, Page page) {
		if (CollectionUtils.isEmpty(ids)) {
			return Collections.EMPTY_LIST;
		}
		return competitionResultReadDao.batchList(ids, new HashedMap(), page);
	}

	@Override
	public List<Map<String, Object>> getAllResult(Map<String, Object> params, Page page) {
		return competitionResultReadDao.getAllResult(params, page);
	}

	@Override
	public List<Map<String, Object>> getByGroup(Map<String, Object> params, Page page) {
		return competitionResultReadDao.getByGroup(params, page);
	}

	@Override
	public List<Map<String, Object>> getGroupAllPersonResult(Map<String, Object> params, Page page) {
		return competitionResultReadDao.getGroupAllPersonResult(params, page);
	}

	@Override
	public Map<String, Object> getTotalDistanceAndResult(Map<String, Object> params) {
		return competitionResultReadDao.getTotalDistanceAndResult(params);
	}

	@Override
	public List<Map<String, Object>> getScheduleRank(CompetitionResult cResult) {
		return competitionResultReadDao.getScheduleRank(cResult);
	}
}
