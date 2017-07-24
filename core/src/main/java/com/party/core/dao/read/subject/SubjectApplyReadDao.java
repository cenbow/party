package com.party.core.dao.read.subject;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.party.common.paging.Page;
import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.subject.SubjectApply;

/**
 * 专题应用数据读取
 * 
 * @author Administrator
 *
 */
@Repository
public interface SubjectApplyReadDao extends BaseReadDao<SubjectApply> {
	/**
	 * 获取该专题下的所有应用
	 * 
	 * @param subjectid
	 * @return
	 */
	List<SubjectApply> getBySubjectId(String subjectid);

	/**
	 * web端
	 * 
	 * @param subjectApply
	 * @param params
	 * @param page
	 * @return
	 */
	List<SubjectApply> webListPage(@Param(value = "apply") SubjectApply subjectApply, @Param(value = "params") Map<String, Object> params, Page page);
}
