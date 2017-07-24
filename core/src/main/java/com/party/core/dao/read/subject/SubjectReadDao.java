package com.party.core.dao.read.subject;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.party.common.paging.Page;
import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.subject.Subject;

@Repository
public interface SubjectReadDao extends BaseReadDao<Subject> {
	List<Subject> webListPage(@Param(value = "subject") Subject subject, @Param(value = "params") Map<String, Object> params, Page page);
}
