package com.party.core.service.subject.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.party.common.paging.Page;
import com.party.common.utils.StringUtils;
import com.party.core.dao.read.subject.SubjectApplyReadDao;
import com.party.core.dao.write.subject.SubjectApplyWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.subject.SubjectApply;
import com.party.core.service.subject.ISubjectApplyService;

/**
 * 专题应用接口实现
 * 
 * @author Administrator
 *
 */
@Service
public class SubjectApplyService implements ISubjectApplyService {

	@Autowired
	private SubjectApplyReadDao subjectApplyDao;

	@Autowired
	private SubjectApplyWriteDao subjectApplyWriteDao;

	@Override
	public String insert(SubjectApply t) {
		BaseModel.preInsert(t);
		boolean result = subjectApplyWriteDao.insert(t);
		if (result) {
			return t.getId();
		}
		return null;
	}

	@Override
	public boolean update(SubjectApply t) {
		if (null == t)
			return false;
		t.setUpdateDate(new Date());
		return subjectApplyWriteDao.update(t);
	}

	@Override
	public boolean deleteLogic(String id) {
		if (StringUtils.isBlank(id))
			return false;

		return subjectApplyWriteDao.deleteLogic(id);
	}

	@Override
	public boolean delete(String id) {
		if (StringUtils.isBlank(id))
			return false;

		return subjectApplyWriteDao.delete(id);
	}

	@Override
	public boolean batchDeleteLogic(Set<String> ids) {
		if (CollectionUtils.isEmpty(ids))
			return false;

		return subjectApplyWriteDao.batchDeleteLogic(ids);
	}

	@Override
	public boolean batchDelete(Set<String> ids) {
		if (CollectionUtils.isEmpty(ids))
			return false;

		return subjectApplyWriteDao.batchDelete(ids);
	}

	@Override
	public SubjectApply get(String id) {
		return subjectApplyDao.get(id);
	}

	@Override
	public List<SubjectApply> listPage(SubjectApply t, Page page) {
		return subjectApplyDao.listPage(t, page);
	}

	@Override
	public List<SubjectApply> list(SubjectApply t) {
		return subjectApplyDao.listPage(t, null);
	}

	@Override
	public List<SubjectApply> batchList(Set<String> ids, SubjectApply t, Page page) {
		if (CollectionUtils.isEmpty(ids))
			return Collections.EMPTY_LIST;

		return subjectApplyDao.batchList(ids, new HashedMap(), page);
	}

	@Override
	public List<SubjectApply> getBySubjectId(String subjectid) {
		return subjectApplyDao.getBySubjectId(subjectid);
	}

	@Override
	public List<SubjectApply> webListPage(SubjectApply subjectApply, Map<String, Object> params, Page page) {
		return subjectApplyDao.webListPage(subjectApply, params, page);
	}

}
