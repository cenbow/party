package com.party.mobile.web.controller.subject;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Strings;
import com.party.common.paging.Page;
import com.party.core.exception.BusinessException;
import com.party.core.model.subject.Subject;
import com.party.mobile.biz.subject.SubjectBizService;
import com.party.mobile.web.dto.AjaxResult;
import com.party.mobile.web.dto.subject.output.SubjectOutput;
import com.party.common.utils.PartyCode;

/**
 * 专题控制层
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/subject/subject")
public class SubjectController {

	@Autowired
	private SubjectBizService subjectBizService;

	/**
	 * 获取专题详情
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getDetails")
	public AjaxResult getDetails(String id, HttpServletRequest request) {
		if (Strings.isNullOrEmpty(id)) {
			return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, "参数不合法");
		}

		try {
			SubjectOutput output = subjectBizService.getDetails(id, request);
			return AjaxResult.success(output);
		} catch (BusinessException be) {
			return AjaxResult.error(be.getCode(), be.getMessage());
		}
	}

	@ResponseBody
	@RequestMapping(value = "/list")
	public AjaxResult getDetails(Subject subject,Page page) {
		List<SubjectOutput> outputs = subjectBizService.getList(subject,page);
		return AjaxResult.success(outputs, page);
	}
}
