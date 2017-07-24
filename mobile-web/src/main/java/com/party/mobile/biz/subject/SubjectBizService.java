package com.party.mobile.biz.subject;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.party.common.paging.Page;
import com.party.common.utils.LangUtils;
import com.party.core.exception.BusinessException;
import com.party.core.model.subject.Subject;
import com.party.core.model.subject.SubjectApply;
import com.party.core.service.subject.ISubjectApplyService;
import com.party.core.service.subject.ISubjectService;
import com.party.mobile.web.dto.subject.output.SubjectApplyOutput;
import com.party.mobile.web.dto.subject.output.SubjectOutput;
import com.party.common.utils.PartyCode;

@Service
public class SubjectBizService {

	@Autowired
	private ISubjectService subjectService;

	@Autowired
	private ISubjectApplyService subjectApplyService;

	/**
	 * 获取专题详情
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	public SubjectOutput getDetails(String id, HttpServletRequest request) {

		Subject subject = subjectService.get(id);
		if (subject == null) {
			throw new BusinessException(PartyCode.DYNAMIC_NOT_EXIT, "动态的主键id错误，数据不存在");
		}

		SubjectOutput output = SubjectOutput.transform(subject);

		List<SubjectApplyOutput> applyOutputs = Lists.newArrayList();
		List<SubjectApply> applies = subjectApplyService.getBySubjectId(id);
		for (SubjectApply entity : applies) {
			SubjectApplyOutput applyOutput = SubjectApplyOutput.transform(entity);
			applyOutputs.add(applyOutput);
		}

		output.setSubjectApplies(applyOutputs);
		return output;
	}

	/**
	 * 列表
	 * 
	 * @param subject
	 * @param page
	 * @return
	 */
	public List<SubjectOutput> getList(Subject subject, Page page) {
		List<Subject> subjects = subjectService.listPage(subject, page);

		if (!CollectionUtils.isEmpty(subjects)) {
			List<SubjectOutput> subjectOutputs = LangUtils.transform(subjects, input -> {
				SubjectOutput output = SubjectOutput.transform(input);
				return output;
			});
			return subjectOutputs;
		}
		return Collections.EMPTY_LIST;
	}

}
