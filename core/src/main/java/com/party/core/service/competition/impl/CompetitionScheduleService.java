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
import com.party.core.dao.read.competition.CompetitionScheduleReadDao;
import com.party.core.dao.write.competition.CompetitionScheduleWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.competition.CompetitionSchedule;
import com.party.core.service.competition.ICompetitionScheduleService;

/**
 * 赛事日程
 * 
 * @author Administrator
 *
 */
@Service
public class CompetitionScheduleService implements ICompetitionScheduleService {

	@Autowired
	private CompetitionScheduleReadDao competitionScheduleReadDao;

	@Autowired
	private CompetitionScheduleWriteDao competitionScheduleWriteDao;

	@Override
	public String insert(CompetitionSchedule t) {
		BaseModel.preInsert(t);
		boolean result = competitionScheduleWriteDao.insert(t);
		if (result) {
			return t.getId();
		}
		return null;
	}

	@Override
	public boolean update(CompetitionSchedule t) {
		t.setUpdateDate(new Date());
		return competitionScheduleWriteDao.update(t);
	}

	@Override
	public boolean deleteLogic(String id) {
		if (Strings.isNullOrEmpty(id)) {
			return false;
		}
		return competitionScheduleWriteDao.deleteLogic(id);
	}

	@Override
	public boolean delete(String id) {
		if (Strings.isNullOrEmpty(id)) {
			return false;
		}
		return competitionScheduleWriteDao.delete(id);
	}

	@Override
	public boolean batchDeleteLogic(Set<String> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			return false;
		}
		return competitionScheduleWriteDao.batchDeleteLogic(ids);
	}

	@Override
	public boolean batchDelete(Set<String> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			return false;
		}
		return competitionScheduleWriteDao.batchDelete(ids);
	}

	@Override
	public CompetitionSchedule get(String id) {
		return competitionScheduleReadDao.get(id);
	}

	@Override
	public List<CompetitionSchedule> listPage(CompetitionSchedule t, Page page) {
		return competitionScheduleReadDao.listPage(t, page);
	}

	@Override
	public List<CompetitionSchedule> list(CompetitionSchedule t) {
		return competitionScheduleReadDao.listPage(t, null);
	}

	@Override
	public List<CompetitionSchedule> batchList(Set<String> ids, CompetitionSchedule t, Page page) {
		if (CollectionUtils.isEmpty(ids)) {
			return Collections.EMPTY_LIST;
		}
		return competitionScheduleReadDao.batchList(ids, new HashedMap(), page);
	}

	@Override
	public List<CompetitionSchedule> webListPage(CompetitionSchedule project, Map<String, Object> params, Page page) {
		return competitionScheduleReadDao.webListPage(project, params, page);
	}

	@Override
	public List<CompetitionSchedule> findByProject(String id) {
		CompetitionSchedule t = new CompetitionSchedule();
		t.setProjectId(id);
		return competitionScheduleReadDao.listPage(t, null);
	}

	@Override
	public List<CompetitionSchedule> checkPlayDay(String playDayStr, String projectId, String id) {
		return competitionScheduleReadDao.checkPlayDay(playDayStr, projectId, id);
	}

	@Override
	public List<CompetitionSchedule> scheduleResultAll(CompetitionSchedule schedule, Map<String, Object> params, Page page) {
		return competitionScheduleReadDao.scheduleResultAll(schedule, params, page);
	}

	@Override
	public Double getSumDistance(String projectId) {
		return competitionScheduleReadDao.getSumDistance(projectId);
	}
}
