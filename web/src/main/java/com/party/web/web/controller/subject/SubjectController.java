package com.party.web.web.controller.subject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.party.common.paging.Page;
import com.party.common.utils.LangUtils;
import com.party.common.utils.StringUtils;
import com.party.core.model.BaseModel;
import com.party.core.model.member.Member;
import com.party.core.model.subject.Subject;
import com.party.core.model.subject.SubjectApply;
import com.party.core.model.user.User;
import com.party.core.service.subject.ISubjectApplyService;
import com.party.core.service.subject.ISubjectService;
import com.party.core.service.user.IUserService;
import com.party.web.biz.file.FileBizService;
import com.party.web.utils.RealmUtils;
import com.party.web.web.dto.AjaxResult;
import com.party.web.web.dto.input.common.CommonInput;
import com.party.web.web.dto.output.subject.SubjectOutput;

/**
 * 专题
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/subject/subject")
public class SubjectController {
	
	@Value("#{party['mic.url']}")
	private String micUrl;

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
	 * 跳转至列表 我发布的专题
	 * 
	 * @return
	 */
	@RequestMapping(value = "subjectList")
	@RequiresPermissions("subject:subject:list")
	public ModelAndView subjectList(Subject subject, Page page, CommonInput commonInput) {
		ModelAndView mv = new ModelAndView("subject/subjectList");
		Member user = RealmUtils.getCurrentUser();
		subject.setMemberId(user.getId());
		subject.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
		Map<String, Object> params = CommonInput.appendParams(commonInput);
		mv.addObject("input", commonInput);
		List<Subject> subjects = subjectService.webListPage(subject, params, page);
		String path = user.getId() + "/subject/";

		List<SubjectOutput> subjectOutputs = LangUtils.transform(subjects, input -> {
			Map<String, String> qrCodeMap = new HashMap<String, String>();
			SubjectOutput subjectOutput = SubjectOutput.transform(input);
			List<SubjectApply> applies = subjectApplyService.getBySubjectId(input.getId());
			subjectOutput.setSubjectApplies(applies);

			String content = "subject/subject_detail.html?id=" + input.getId();
			String qrCodeUrl = fileBizService.getFileEntity(input.getId(), path, content);
			qrCodeMap.put("qrCode", qrCodeUrl);
			qrCodeMap.put("qrCodeContent", micUrl + content);
			subjectOutput.setQrCodeMap(qrCodeMap);

			return subjectOutput;
		});
		mv.addObject("subjects", subjectOutputs);
		mv.addObject("page", page);
		return mv;
	}

	/**
	 * 跳转至发布
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "publishSubject")
	public ModelAndView publishChannel(String id) {
		ModelAndView mv = new ModelAndView("subject/publishSubject");
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
	public ModelAndView subjectDetail(String id) {
		ModelAndView mv = new ModelAndView("subject/subjectDetail");
		Subject subject = subjectService.get(id);
		mv.addObject("subject", subject);
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
