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
import com.party.core.dao.read.competition.CompetitionMemberReadDao;
import com.party.core.dao.write.competition.CompetitionMemberWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.competition.CompetitionMember;
import com.party.core.model.member.Member;
import com.party.core.service.competition.ICompetitionMemberService;

/**
 * 参赛人员
 * 
 * @author Administrator
 *
 */
@Service
public class CompetitionMemberService implements ICompetitionMemberService {

	@Autowired
	private CompetitionMemberReadDao competitionMemberReadDao;

	@Autowired
	private CompetitionMemberWriteDao competitionMemberWriteDao;

	@Override
	public String insert(CompetitionMember t) {
		BaseModel.preInsert(t);
		boolean result = competitionMemberWriteDao.insert(t);
		if (result) {
			return t.getId();
		}
		return null;
	}

	@Override
	public boolean update(CompetitionMember t) {
		t.setUpdateDate(new Date());
		return competitionMemberWriteDao.update(t);
	}

	@Override
	public boolean deleteLogic(String id) {
		if (Strings.isNullOrEmpty(id)) {
			return false;
		}
		return competitionMemberWriteDao.deleteLogic(id);
	}

	@Override
	public boolean delete(String id) {
		if (Strings.isNullOrEmpty(id)) {
			return false;
		}
		return competitionMemberWriteDao.delete(id);
	}

	@Override
	public boolean batchDeleteLogic(Set<String> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			return false;
		}
		return competitionMemberWriteDao.batchDeleteLogic(ids);
	}

	@Override
	public boolean batchDelete(Set<String> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			return false;
		}
		return competitionMemberWriteDao.batchDelete(ids);
	}

	@Override
	public CompetitionMember get(String id) {
		return competitionMemberReadDao.get(id);
	}

	@Override
	public List<CompetitionMember> listPage(CompetitionMember t, Page page) {
		return competitionMemberReadDao.listPage(t, page);
	}

	@Override
	public List<CompetitionMember> list(CompetitionMember t) {
		return competitionMemberReadDao.listPage(t, null);
	}

	@Override
	public List<CompetitionMember> batchList(Set<String> ids, CompetitionMember t, Page page) {
		if (CollectionUtils.isEmpty(ids)) {
			return Collections.EMPTY_LIST;
		}
		return competitionMemberReadDao.batchList(ids, new HashedMap(), page);
	}

	@Override
	public CompetitionMember findByProjectAndMember(String memberId, String projectId) {
		CompetitionMember member = new CompetitionMember();
		member.setMemberId(memberId);
		member.setProjectId(projectId);
		return competitionMemberReadDao.getUnique(member);
	}

	@Override
	public List<CompetitionMember> webListPage(CompetitionMember competitionMember, Member member, Map<String, Object> params, Page page) {
		return competitionMemberReadDao.webListPage(competitionMember, member, params, page);
	}

	@Override
	public int getCount(CompetitionMember t) {
		return competitionMemberReadDao.getCount(t);
	}

	@Override
	public List<CompetitionMember> checkNumberPai(String cMemberId, String numberPai, String projectId) {
		return competitionMemberReadDao.checkNumberPai(numberPai, projectId, cMemberId);
	}
}
