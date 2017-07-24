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
import com.party.core.dao.read.gatherInfo.GatherInfoMemberReadDao;
import com.party.core.dao.write.gatherInfo.GatherInfoMemberWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.gatherInfo.GatherInfoMember;
import com.party.core.model.gatherInfo.GatherInfoMemberOutput;
import com.party.core.service.gatherInfo.IGatherInfoMemberService;

/**
 * 人员信息
 * 
 * @author Administrator
 *
 */
@Service
public class GatherInfoMemberService implements IGatherInfoMemberService {

	@Autowired
	private GatherInfoMemberReadDao gatherInfoMemberReadDao;

	@Autowired
	private GatherInfoMemberWriteDao gatherInfoMemberWriteDao;

	@Override
	public String insert(GatherInfoMember t) {
		BaseModel.preInsert(t);
		boolean result = gatherInfoMemberWriteDao.insert(t);
		if (result) {
			return t.getId();
		}
		return null;
	}

	@Override
	public boolean update(GatherInfoMember t) {
		t.setUpdateDate(new Date());
		return gatherInfoMemberWriteDao.update(t);
	}

	@Override
	public boolean deleteLogic(String id) {
		if (Strings.isNullOrEmpty(id)) {
			return false;
		}
		return gatherInfoMemberWriteDao.deleteLogic(id);
	}

	@Override
	public boolean delete(String id) {
		if (Strings.isNullOrEmpty(id)) {
			return false;
		}
		return gatherInfoMemberWriteDao.delete(id);
	}

	@Override
	public boolean batchDeleteLogic(Set<String> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			return false;
		}
		return gatherInfoMemberWriteDao.batchDeleteLogic(ids);
	}

	@Override
	public boolean batchDelete(Set<String> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			return false;
		}
		return gatherInfoMemberWriteDao.batchDelete(ids);
	}

	@Override
	public GatherInfoMember get(String id) {
		return gatherInfoMemberReadDao.get(id);
	}

	@Override
	public List<GatherInfoMember> listPage(GatherInfoMember t, Page page) {
		return gatherInfoMemberReadDao.listPage(t, page);
	}

	@Override
	public List<GatherInfoMember> list(GatherInfoMember t) {
		return gatherInfoMemberReadDao.listPage(t, null);
	}

	@Override
	public List<GatherInfoMember> batchList(Set<String> ids, GatherInfoMember t, Page page) {
		if (CollectionUtils.isEmpty(ids)) {
			return Collections.EMPTY_LIST;
		}
		return gatherInfoMemberReadDao.batchList(ids, new HashedMap(), page);
	}

	@Override
	public List<GatherInfoMember> findByProject(String id) {
		GatherInfoMember t = new GatherInfoMember();
		t.setProjectId(id);
		return gatherInfoMemberReadDao.listPage(t, null);
	}

	@Override
	public List<Map<String, Object>> webListPage(GatherInfoMember memberInfo, Map<String, Object> params, Page page) {
		return gatherInfoMemberReadDao.webListPage(memberInfo, params, page);
	}

	@Override
	public int getCountByProject(String id) {
		return gatherInfoMemberReadDao.getCountByProject(id);
	}

	@Override
	public GatherInfoMember findByProjectAndMember(String memberId, String projectId) {
		GatherInfoMember t = new GatherInfoMember();
		t.setMemberId(memberId);
		t.setProjectId(projectId);
		return gatherInfoMemberReadDao.getUnique(t);
	}

	@Override
	public List<GatherInfoMember> findByGroup(String id) {
		GatherInfoMember t = new GatherInfoMember();
		t.setGroupId(id);
		return gatherInfoMemberReadDao.listPage(t, null);
	}

	@Override
	public int getCountByGroup(String id) {
		return gatherInfoMemberReadDao.getCountByGroup(id);
	}

	@Override
	public List<GatherInfoMemberOutput> exportList(GatherInfoMember memberInfo, Map<String, Object> params) {
		return gatherInfoMemberReadDao.exportList(memberInfo, params);
	}

}
