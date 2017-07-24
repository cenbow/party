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
import com.party.core.dao.read.subject.SubjectReadDao;
import com.party.core.dao.write.subject.SubjectWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.subject.Subject;
import com.party.core.service.subject.ISubjectService;

@Service
public class SubjectService implements ISubjectService {

	@Autowired
	private SubjectReadDao subjectReadDao;

	@Autowired
	private SubjectWriteDao subjectWriteDao;

	@Override
	public String insert(Subject t) {
		BaseModel.preInsert(t);
		boolean result = subjectWriteDao.insert(t);
		if (result) {
			return t.getId();
		}
		return null;
	}

	@Override
	public boolean update(Subject t) {
		if (null == t)
			return false;
		t.setUpdateDate(new Date());
		return subjectWriteDao.update(t);
	}

	@Override
	public boolean deleteLogic(String id) {
		if (StringUtils.isBlank(id))
			return false;

		return subjectWriteDao.deleteLogic(id);
	}

	@Override
	public boolean delete(String id) {
		if (StringUtils.isBlank(id))
			return false;

		return subjectWriteDao.delete(id);
	}

	@Override
	public boolean batchDeleteLogic(Set<String> ids) {
		if (CollectionUtils.isEmpty(ids))
			return false;

		return subjectWriteDao.batchDeleteLogic(ids);
	}

	@Override
	public boolean batchDelete(Set<String> ids) {
		if (CollectionUtils.isEmpty(ids))
			return false;

		return subjectWriteDao.batchDelete(ids);
	}

	@Override
	public Subject get(String id) {
		return subjectReadDao.get(id);
	}

	@Override
	public List<Subject> listPage(Subject t, Page page) {
		return subjectReadDao.listPage(t, page);
	}

	@Override
	public List<Subject> list(Subject t) {
		return subjectReadDao.listPage(t, null);
	}

	@Override
	public List<Subject> batchList(Set<String> ids, Subject t, Page page) {
		if (CollectionUtils.isEmpty(ids))
			return Collections.EMPTY_LIST;

		return subjectReadDao.batchList(ids, new HashedMap(), page);
	}

	@Override
	public List<Subject> webListPage(Subject subject, Map<String, Object> params, Page page) {
		return subjectReadDao.webListPage(subject, params, page);
	}

}
