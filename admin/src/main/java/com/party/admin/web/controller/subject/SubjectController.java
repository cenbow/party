package com.party.admin.web.controller.subject;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.party.admin.biz.file.FileBizService;
import com.party.admin.utils.RealmUtils;
import com.party.admin.web.dto.AjaxResult;
import com.party.admin.web.dto.input.common.CommonInput;
import com.party.common.paging.Page;
import com.party.common.utils.StringUtils;
import com.party.core.model.BaseModel;
import com.party.core.model.subject.Subject;
import com.party.core.model.subject.SubjectApply;
import com.party.core.model.user.User;
import com.party.core.service.subject.ISubjectApplyService;
import com.party.core.service.subject.ISubjectService;
import com.party.core.service.user.IUserService;

/**
 * 专题
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/subject/subject")
public class SubjectController {

	@Autowired
	private ISubjectService subjectService;

	@Autowired
	private ISubjectApplyService subjectApplyService;

	@Autowired
	private IUserService userService;

	@Autowired
	private FileBizService fileBizService;

	protected static Logger logger = LoggerFactory.getLogger(SubjectController.class);

	/**
	 * 专题管理
	 * 
	 * @param subject
	 * @param page
	 * @param timeType
	 * @param c_start
	 * @param c_end
	 * @param memberName
	 * @return
	 */
	@RequestMapping(value = "subjectList")
	public ModelAndView subjectList(Subject subject, Page page, CommonInput commonInput) {
		ModelAndView mv = new ModelAndView("subject/subjectList");
		subject.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
		Map<String, Object> params = CommonInput.appendParams(commonInput);
		mv.addObject("input", commonInput);
		List<Subject> subjects = subjectService.webListPage(subject, params, page);
		String path = RealmUtils.getCurrentUser().getId() + "/subject/";
		for (Subject entity : subjects) {
			List<SubjectApply> applies = subjectApplyService.getBySubjectId(entity.getId());
			entity.setSubjectApplies(applies);

			String content = "subject/subject_detail.html?id=" + entity.getId();
			String fileEntity = fileBizService.getFileEntity(entity.getId(), path, content);
			entity.setQrCodeUrl(fileEntity);
		}
		mv.addObject("subjects", subjects);
		mv.addObject("page", page);
		return mv;
	}

	/**
	 * 跳转至发布
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "subjectForm")
	public ModelAndView subjectForm(String id) {
		ModelAndView mv = new ModelAndView("subject/subjectForm");
		if (StringUtils.isNotEmpty(id)) {
			Subject subject = subjectService.get(id);
			mv.addObject("subject", subject);
		}
		return mv;
	}

	/**
	 * 跳转至详情
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "subjectDetail")
	public ModelAndView subjectDetail(String id, Page page) {
		ModelAndView mv = new ModelAndView("subject/subjectDetail");
		if (StringUtils.isNotEmpty(id)) {
			Subject subject = subjectService.get(id);
			mv.addObject("subject", subject);

			page.setLimit(10);
			SubjectApply subjectApply = new SubjectApply();
			subjectApply.setSubjectId(id);
			subjectApply.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
			List<SubjectApply> applies = subjectApplyService.listPage(subjectApply, page);
			for (SubjectApply apply : applies) {
				apply.setSubjectName(subject.getName());
			}
			mv.addObject("page", page);
			mv.addObject("applies", applies);
		}
		return mv;
	}

	/**
	 * 发布保存
	 * 
	 * @param subject
	 * @param result
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "save")
	public AjaxResult save(Subject subject, BindingResult result) {
		AjaxResult ajaxResult = new AjaxResult();
		// 数据验证
		if (result.hasErrors()) {
			List<ObjectError> allErros = result.getAllErrors();
			ajaxResult.setDescription(allErros.get(0).getDefaultMessage());
			return ajaxResult;
		}

		if (StringUtils.isNotEmpty(subject.getId())) {
			subjectService.update(subject);
		} else {
			User user = userService.findByLoginName("admin");
			subject.setCreateBy(user.getId());
			subject.setUpdateBy(user.getId());
			subject.setMember(RealmUtils.getCurrentUser().getId());
			subjectService.insert(subject);
		}
		ajaxResult.setSuccess(true);
		return ajaxResult;
	}

	/**
	 * 删除专题
	 */
	@ResponseBody
	@RequestMapping(value = "delete")
	public AjaxResult delete(String id) {
		AjaxResult ajaxResult = new AjaxResult();
		try {
			if (StringUtils.isEmpty(id)) {
				return new AjaxResult(false);
			}

			List<SubjectApply> applies = subjectApplyService.getBySubjectId(id);
			if (applies.size() > 0) {
				return new AjaxResult(false, "该专题下已有栏目");
			}

			subjectService.delete(id);
			ajaxResult.setSuccess(true);
			return ajaxResult;
		} catch (Exception e) {
			logger.info("专题删除异常", e);
		}
		ajaxResult.setSuccess(false);
		return ajaxResult;
	}
}
