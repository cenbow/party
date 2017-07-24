package com.party.core.service.subject;

import java.util.List;
import java.util.Map;

import com.party.common.paging.Page;
import com.party.core.model.subject.Subject;
import com.party.core.service.IBaseService;

/**
 * 专题接口
 * 
 * @author Administrator
 *
 */
public interface ISubjectService extends IBaseService<Subject> {
	/**
	 * web端
	 * 
	 * @param subject
	 * @param params
	 * @param page
	 * @return
	 */
	List<Subject> webListPage(Subject subject, Map<String, Object> params, Page page);
}
