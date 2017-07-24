package com.party.core.service.subject;

import java.util.List;
import java.util.Map;

import com.party.common.paging.Page;
import com.party.core.model.subject.SubjectApply;
import com.party.core.service.IBaseService;

/**
 * 专题应用接口
 * 
 * @author Administrator
 *
 */
public interface ISubjectApplyService extends IBaseService<SubjectApply> {

	List<SubjectApply> getBySubjectId(String subjectid);

	/**
	 * web端
	 * 
	 * @param subjectApply
	 * @param params
	 * @param page
	 * @return
	 */
	List<SubjectApply> webListPage(SubjectApply subjectApply, Map<String, Object> params, Page page);
}
