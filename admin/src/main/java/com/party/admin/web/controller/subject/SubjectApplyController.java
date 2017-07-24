package com.party.admin.web.controller.subject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.party.admin.biz.file.FileBizService;
import com.party.admin.utils.MyBeanUtils;
import com.party.admin.utils.RealmUtils;
import com.party.admin.web.dto.AjaxResult;
import com.party.admin.web.dto.output.article.ArticleOutput;
import com.party.common.paging.Page;
import com.party.common.utils.LangUtils;
import com.party.common.utils.StringUtils;
import com.party.core.model.BaseModel;
import com.party.core.model.article.Article;
import com.party.core.model.article.ArticleGroup;
import com.party.core.model.subject.Subject;
import com.party.core.model.subject.SubjectApply;
import com.party.core.model.user.User;
import com.party.core.service.article.IArticleGroupService;
import com.party.core.service.article.IArticleService;
import com.party.core.service.subject.ISubjectApplyService;
import com.party.core.service.subject.ISubjectService;
import com.party.core.service.user.IUserService;

/**
 * 专题应用
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/subject/apply")
public class SubjectApplyController {

	@Autowired
	private ISubjectService subjectService;

	@Autowired
	private ISubjectApplyService subjectApplyService;

	@Autowired
	private IUserService userService;

	@Autowired
	private IArticleService articleService;

	@Autowired
	private IArticleGroupService articleGroupService;

	@Value("#{party['mic.url']}")
	private String micUrl;

	@Autowired
	private FileBizService fileBizService;

	protected static Logger logger = LoggerFactory.getLogger(SubjectApplyController.class);

	/**
	 * 跳转至列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "applyList")
	public ModelAndView publishList(SubjectApply subjectApply, String applyId) {
		ModelAndView mv = new ModelAndView("subject/applyList");
		Subject subject = subjectService.get(subjectApply.getSubjectId());
		mv.addObject("subject", subject);

		subjectApply.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
		List<SubjectApply> applies = subjectApplyService.listPage(subjectApply, null);
		mv.addObject("applies", applies);
		if (StringUtils.isNotEmpty(applyId)) {
			mv.addObject("firstId", applyId);
		} else {
			if (applies.size() > 0) {
				mv.addObject("firstId", applies.get(0).getId());
			}
		}
		if (StringUtils.isNotEmpty(subjectApply.getId())) {
			subjectApply = subjectApplyService.get(subjectApply.getId());
			mv.addObject("subjectApply", subjectApply);
		}
		return mv;
	}

	@ResponseBody
	@RequestMapping(value = "loadJsonData")
	public Map<String, Object> loadJsonData(String applyId, String title, Page page) {
		Map<String, Object> map = new HashMap<String, Object>();
		SubjectApply subjectApply = subjectApplyService.get(applyId);
		map.put("subjectApply", subjectApply);
		List<Article> articles = new ArrayList<Article>();
		if (subjectApply.getType().equals("article")) {
			Article article = articleService.get(subjectApply.getTargetId());
			if (article != null) {
				articles.add(article);
			}
		} else if (subjectApply.getType().equals("channel")) {
			Article t = new Article();
			t.setTitle(title);
			t.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
			t.setArticleGroupId(subjectApply.getTargetId());
			page.setLimit(10);
			articles = articleService.webListPage(t, null, page);
		}
		String path = RealmUtils.getCurrentUser().getId() + "/article/";

		List<ArticleOutput> articleOutputs = LangUtils.transform(articles, input -> {
			ArticleOutput articleOutput = ArticleOutput.transform(input);
			String content = "article/article_detail.html?id=" + input.getId();
			articleOutput.setPic(content);
			String fileEntity = fileBizService.getFileEntity(input.getId(), path, content);
			articleOutput.setQrCodeUrl(fileEntity);
			articleOutput.setApplyId(applyId);
			return articleOutput;
		});

		map.put("articles", articleOutputs);
		map.put("page", page);
		return map;
	}

	/**
	 * 读取列表数据
	 * 
	 * @param page
	 * @param subjectId
	 *            专题id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "publishListJson")
	public Map<String, Object> publishListJson(Page page, String subjectId) {
		page.setLimit(9);
		SubjectApply subjectApply = new SubjectApply();
		subjectApply.setSubjectId(subjectId);
		subjectApply.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
		List<SubjectApply> applies = subjectApplyService.listPage(subjectApply, page);
		for (SubjectApply apply : applies) {
			Subject subject = subjectService.get(apply.getSubjectId());
			apply.setSubjectName(subject.getName());
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("datas", applies);
		map.put("page", page);
		return map;
	}

	/**
	 * 跳转至发布
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "applyForm")
	public ModelAndView applyForm(String id, String subjectId) {
		ModelAndView mv = new ModelAndView("subject/applyForm");
		if (StringUtils.isNotEmpty(id)) {
			SubjectApply apply = subjectApplyService.get(id);
			mv.addObject("apply", apply);
			if (apply.getType().equals("channel")) {
				ArticleGroup articleGroup = articleGroupService.get(apply.getTargetId());
				mv.addObject("articleGroup", articleGroup);
			}
		}
		Subject subject = subjectService.get(subjectId);
		mv.addObject("subject", subject);
		return mv;
	}

	/**
	 * 跳转至详情
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "applyDetail")
	public ModelAndView applyDetail(String id) {
		ModelAndView mv = new ModelAndView("subject/applyInfo");
		SubjectApply apply = subjectApplyService.get(id);
		Subject subject = subjectService.get(apply.getSubjectId());
		mv.addObject("subject", subject);
		mv.addObject("apply", apply);
		return mv;
	}

	/**
	 * 发布保存
	 * 
	 * @param subject
	 * @param result
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "save")
	public AjaxResult save(SubjectApply apply, BindingResult result, String channelPicture) throws Exception {
		AjaxResult ajaxResult = new AjaxResult();
		// 数据验证
		if (result.hasErrors()) {
			List<ObjectError> allErros = result.getAllErrors();
			ajaxResult.setDescription(allErros.get(0).getDefaultMessage());
			return ajaxResult;
		}

		User user = userService.findByLoginName("admin");

		if (StringUtils.isNotEmpty(apply.getId())) {
			SubjectApply t = subjectApplyService.get(apply.getId());
			MyBeanUtils.copyBeanNotNull2Bean(apply, t);// 将编辑表单中的非NULL值覆盖数据库记录中的值
			if (t.getType().equals("url")) {
				t.setTargetId(null);
			}

			if (t.getType().equals("channel")) {
				ArticleGroup articleGroup = articleGroupService.get(t.getTargetId());
				if (articleGroup == null) {
					articleGroup = new ArticleGroup();
					articleGroup.setName(apply.getName());
					articleGroup.setPicture(channelPicture);
					articleGroup.setCreateBy(user.getId());
					articleGroup.setUpdateBy(user.getId());
					articleGroup.setMember(RealmUtils.getCurrentUser().getId());
					articleGroup.setIsShow(1);
					String channelId = articleGroupService.insert(articleGroup);
					t.setTargetId(channelId);
					t.setUrl(micUrl + "article/article_group_detail.html?id=" + channelId);
				} else {
					articleGroup.setPicture(channelPicture);
					articleGroup.setName(apply.getName());
					articleGroupService.update(articleGroup);
					t.setUrl(micUrl + "article/article_group_detail.html?id=" + articleGroup.getId());
				}
			}

			subjectApplyService.update(t);
		} else {
			apply.setCreateBy(user.getId());
			apply.setUpdateBy(user.getId());
			apply.setMember(RealmUtils.getCurrentUser().getId());

			if (apply.getType().equals("channel")) {
				ArticleGroup articleGroup = new ArticleGroup();
				articleGroup.setName(apply.getName());
				articleGroup.setPicture(channelPicture);
				articleGroup.setCreateBy(user.getId());
				articleGroup.setUpdateBy(user.getId());
				articleGroup.setMember(RealmUtils.getCurrentUser().getId());
				articleGroup.setIsShow(1);
				String channelId = articleGroupService.insert(articleGroup);
				apply.setTargetId(channelId);
				apply.setUrl(micUrl + "article/article_group_detail.html?id=" + channelId);
			}

			subjectApplyService.insert(apply);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("subjectId", apply.getSubjectId());
		map.put("applyId", apply.getId());
		ajaxResult.setData(map);
		ajaxResult.setSuccess(true);
		return ajaxResult;
	}

	/**
	 * 删除栏目
	 */
	@ResponseBody
	@RequestMapping(value = "delete")
	public AjaxResult delete(String applyId, String subjectId) {
		AjaxResult ajaxResult = new AjaxResult();
		try {
			if (StringUtils.isEmpty(applyId)) {
				return new AjaxResult(false);
			}
			if (StringUtils.isNotEmpty(subjectId)) {
				ajaxResult.setData(subjectId);
			}
			boolean flag = true;
			SubjectApply subjectApply = subjectApplyService.get(applyId);
			if (subjectApply.getType().equals("channel")) {
				Article t = new Article();
				t.setArticleGroupId(subjectApply.getTargetId());
				List<Article> articles = articleService.list(t);
				if (articles.size() > 0) {
					flag = false;
				}
			} else if (subjectApply.getType().equals("article") && StringUtils.isNotEmpty(subjectApply.getTargetId())) {
				flag = false;
			}

			if (flag) {
				subjectApplyService.delete(applyId);
				ajaxResult.setSuccess(true);
			} else {
				ajaxResult.setSuccess(false);
				ajaxResult.setDescription("已有文章不能删除");
			}
		} catch (Exception e) {
			logger.info("文章删除异常", e);
		}
		return ajaxResult;
	}
}
