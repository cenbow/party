package com.party.web.web.controller.article;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringEscapeUtils;
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
import com.party.core.model.article.Article;
import com.party.core.model.channel.Channel;
import com.party.core.model.subject.Subject;
import com.party.core.model.subject.SubjectApply;
import com.party.core.model.system.Dict;
import com.party.core.model.user.User;
import com.party.core.service.article.IArticleService;
import com.party.core.service.channel.IChannelService;
import com.party.core.service.subject.ISubjectApplyService;
import com.party.core.service.subject.ISubjectService;
import com.party.core.service.system.IDictService;
import com.party.core.service.user.IUserService;
import com.party.web.biz.file.FileBizService;
import com.party.web.utils.MyBeanUtils;
import com.party.web.utils.RealmUtils;
import com.party.web.web.dto.AjaxResult;
import com.party.web.web.dto.output.article.ArticleOutput;

/**
 * 文章
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/article/article")
public class ArticleController {

	@Autowired
	private IArticleService articleService;

	@Autowired
	private IDictService dictService;

	@Autowired
	private IUserService userService;

	@Autowired
	private IChannelService channelService;

	@Autowired
	private FileBizService fileBizService;

	@Autowired
	private ISubjectApplyService subjectApplyService;
	
	@Autowired
	private ISubjectService subjectService;

	@Value("#{party['mic.url']}")
	private String micUrl;

	protected static Logger logger = LoggerFactory.getLogger(ArticleController.class);

	/**
	 * 跳转至列表 我发布的文章
	 * 
	 * @return
	 */
	@RequestMapping(value = "articleList")
	public ModelAndView articleList(Article article, Page page, Integer timeType, String c_start, String c_end) {
		ModelAndView mv = new ModelAndView("article/articleList");
		article.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
		Map<String, Object> params = new HashMap<String, Object>();
		// 时间块处理
		if (timeType != null && timeType != 0) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar calendar = Calendar.getInstance();
			Date ed = calendar.getTime(); // 结束时间
			if (timeType == 2) { // 本周内
				int day_of_week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
				if (day_of_week == 0) {
					day_of_week = 7;
				}
				calendar.add(Calendar.DATE, -day_of_week + 1);
			} else if (timeType == 3) { // 本月内
				calendar.set(Calendar.DAY_OF_MONTH, 1);
			}
			Date sd = calendar.getTime(); // 开始时间
			String std = sdf.format(sd) + " 00:00:00";
			params.put("startDate", std);
			String end = sdf.format(ed) + " 23:59:59";
			params.put("endDate", end);

			mv.addObject("timeType", timeType);
		}
		// 时间段处理
		if (StringUtils.isNotEmpty(c_start)) {
			params.put("c_start", c_start);
			mv.addObject("c_start", c_start);
		}
		if (StringUtils.isNotEmpty(c_end)) {
			params.put("c_end", c_end);
			mv.addObject("c_end", c_end);
		}

		List<Article> articles = articleService.webListPage(article, params, page);

		String path = RealmUtils.getCurrentUser().getId() + "/article/";

		List<ArticleOutput> articleOutputs = LangUtils.transform(articles, input -> {
			ArticleOutput articleOutput = ArticleOutput.transform(input);
			String articleType = input.getArticleType();
			Dict dict = new Dict();
			dict.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
			dict.setType("article_type");
			dict.setValue(articleType);
			Dict dictEntity = dictService.getByProperty(dict);
			if (dictEntity != null) {
				articleOutput.setArticleType(dictEntity.getLabel());
			}

			if (input.getChannel() != null) {
				Channel channel = channelService.get(input.getChannel().getId());
				articleOutput.setChannel(channel);
			}

			String content = "article/article_detail.html?id=" + input.getId();
			String qrCodeUrl = fileBizService.getFileEntity(input.getId(), path, content);
			articleOutput.setQrCodeUrl(qrCodeUrl);

			return articleOutput;
		});

		Dict dict = new Dict();
		dict.setType("article_type");
		mv.addObject("articleTypes", dictService.list(dict));

		mv.addObject("articles", articleOutputs);
		mv.addObject("page", page);
		return mv;
	}

	/**
	 * 跳转至发布
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "publishArticle")
	public ModelAndView publishArticle(String id, String applyId, String channelId, String subjectId) {
		ModelAndView mv = new ModelAndView("article/publishArticle");
		if (StringUtils.isNotEmpty(id)) {
			Article article = articleService.get(id);
			String content = StringUtils.stringtohtml(article.getContent());
			article.setContent(content);
			mv.addObject("article", article);
		}

		Dict dict = new Dict();
		dict.setType("article_type");
		mv.addObject("types", dictService.list(dict));
		SubjectApply subjectApply = subjectApplyService.get(applyId);
		mv.addObject("subjectApply", subjectApply);
		Subject subject = subjectService.get(subjectApply.getSubjectId());
		mv.addObject("subject", subject);

		if (StringUtils.isEmpty(channelId)) {
			Channel channel = new Channel();
			channel.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
			List<Channel> channels = channelService.list(channel);
			mv.addObject("channels", channels);
		} else {
			Channel channel = channelService.get(channelId);
			mv.addObject("channel", channel);
		}
		mv.addObject("subjectId", subjectId);
		return mv;
	}

	/**
	 * 跳转至详情
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "articleDetail")
	public ModelAndView articleDetail(String id) {
		ModelAndView mv = new ModelAndView("article/articleDetail");
		Article article = articleService.get(id);
		if (StringUtils.isNotEmpty(article.getContent())) {
			article.setContent(StringUtils.stringtohtml(article.getContent()));
		}
		String articleType = article.getArticleType();
		Dict dict = new Dict();
		dict.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
		dict.setType("article_type");
		dict.setValue(articleType);
		Dict dictEntity = dictService.getByProperty(dict);
		if (dictEntity != null) {
			article.setArticleType(dictEntity.getLabel());
		}

		if (article.getChannel() != null) {
			Channel channel = channelService.get(article.getChannel().getId());
			article.setChannel(channel);
		}

		mv.addObject("article", article);
		return mv;
	}

	/**
	 * 发布保存
	 * 
	 * @param article
	 * @param result
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "save")
	public AjaxResult save(Article article, BindingResult result, String channelId, String applyId, String subjectId) throws Exception {
		AjaxResult ajaxResult = new AjaxResult();
		// 数据验证
		if (result.hasErrors()) {
			List<ObjectError> allErros = result.getAllErrors();
			ajaxResult.setDescription(allErros.get(0).getDefaultMessage());
			return ajaxResult;
		}

		if (StringUtils.isNotEmpty(article.getContent())) {
			String content = StringEscapeUtils.escapeHtml4(article.getContent().trim());
			article.setContent(content);
		}
		SubjectApply apply = null;
		if (StringUtils.isNotEmpty(applyId)) {
			apply = subjectApplyService.get(applyId);
			if (apply.getType().equals("channel")) {
				article.setArticleGroupId(apply.getTargetId());
			}
		}

		if (StringUtils.isNotEmpty(article.getId())) {
			Article t = articleService.get(article.getId());
			MyBeanUtils.copyBeanNotNull2Bean(article, t);// 将编辑表单中的非NULL值覆盖数据库记录中的值
			articleService.update(t);

		} else {
			if (article.getReadNum() == null) {
				article.setReadNum(0);
			}
			User user = userService.findByLoginName("admin");
			article.setCreateBy(user.getId());
			article.setUpdateBy(user.getId());
			article.setMember(RealmUtils.getCurrentUser().getId());
			if (StringUtils.isNotEmpty(channelId)) {
				Channel channel = channelService.get(channelId);
				article.setChannel(channel);
			}

			String articleId = articleService.insert(article);
			if (apply != null && apply.getType().equals("article")) {
				apply.setUrl(micUrl + "article/article_detail.html?id=" + articleId);
				apply.setTargetId(articleId);
				subjectApplyService.update(apply);
			}
		}
		if (apply != null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("applyId", apply.getId());
			map.put("subjectId", apply.getSubjectId());
			ajaxResult.setData(map);
		}
		ajaxResult.setSuccess(true);
		return ajaxResult;
	}

	/**
	 * 删除文章
	 */
	@ResponseBody
	@RequestMapping(value = "delete")
	public AjaxResult delete(String articleId, String subjectId, String applyId) {
		AjaxResult ajaxResult = new AjaxResult();
		try {
			if (StringUtils.isEmpty(articleId)) {
				return new AjaxResult(false);
			}
			if (StringUtils.isNotEmpty(subjectId)) {
				ajaxResult.setData(subjectId);
			}
			articleService.delete(articleId);
			if (StringUtils.isNotEmpty(applyId)) {
				SubjectApply subjectApply = subjectApplyService.get(applyId);
				if (subjectApply.getType().equals("article")) {
					subjectApply.setTargetId("");
					subjectApplyService.update(subjectApply);
				}
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("applyId", applyId);
			map.put("subjectId", subjectId);
			ajaxResult.setData(map);
			ajaxResult.setSuccess(true);
			return ajaxResult;
		} catch (Exception e) {
			logger.info("文章删除异常", e);
		}
		ajaxResult.setSuccess(false);
		return ajaxResult;
	}

	/**
	 * 读取频道下的文章
	 * 
	 * @param page
	 * @param channelId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getArticleByChanne")
	public Map<String, Object> publishListJson(Page page, String channelId) {
		Article article = new Article();
		Channel channel = new Channel();
		channel.setId(channelId);
		article.setChannel(channel);
		article.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
		List<Article> articles = articleService.listPage(article, page);

		for (Article entity : articles) {
			String articleType = entity.getArticleType();
			Dict dict = new Dict();
			dict.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
			dict.setType("article_type");
			dict.setValue(articleType);
			Dict dictEntity = dictService.getByProperty(dict);
			if (dictEntity != null) {
				entity.setArticleType(dictEntity.getLabel());
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("articles", articles);
		map.put("page", page);
		return map;
	}
}
