package com.party.admin.web.controller.article;

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

import com.party.admin.biz.article.ArticleBizService;
import com.party.admin.biz.file.FileBizService;
import com.party.admin.utils.MyBeanUtils;
import com.party.admin.utils.RealmUtils;
import com.party.admin.web.dto.AjaxResult;
import com.party.admin.web.dto.input.common.CommonInput;
import com.party.admin.web.dto.output.article.ArticleOutput;
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
	private ArticleBizService articleBizService;

	@Autowired
	private ISubjectService subjectService;

	@Value("#{party['mic.url']}")
	private String micUrl;

	protected static Logger logger = LoggerFactory.getLogger(ArticleController.class);

	/**
	 * 文章管理
	 * 
	 * @param article
	 * @param page
	 * @param timeType
	 * @param c_start
	 * @param c_end
	 * @param memberName
	 * @return
	 */
	@RequestMapping(value = "articleList")
	public ModelAndView articleList(Article article, Page page, CommonInput commonInput) {
		ModelAndView mv = new ModelAndView("article/articleList");

		if (article.getChannel() != null && StringUtils.isNotEmpty(article.getChannel().getId())) {
			mv.addObject("channelId", article.getChannel().getId());
		}

		article.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
		Map<String, Object> params = CommonInput.appendParams(commonInput);
		mv.addObject("input", commonInput);

		List<Article> articles = articleService.webListPage(article, params, page);

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

			String path = input.getMemberId() + "/article/";
			String content = "article/article_detail.html?id=" + input.getId();
			String fileEntity = fileBizService.getFileEntity(input.getId(), path, content);
			articleOutput.setQrCodeUrl(fileEntity);

			// 活动二维码
				String disQr = articleBizService.getQrCode(input.getId(), input.getMemberId());
				articleOutput.setDisQrCode(disQr);

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
	 * 从专题到发文章
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "articleForm")
	public ModelAndView publishArticle(String id, String applyId, String subjectId) {
		ModelAndView mv = new ModelAndView("article/articleForm");
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
		if (subjectApply != null) {
			Subject subject = subjectService.get(subjectApply.getSubjectId());
			mv.addObject("subject", subject);
		}
		mv.addObject("subjectId", subjectId);
		return mv;
	}

	/**
	 * 直接发文章
	 * 
	 * @param id
	 * @param channelId
	 * @return
	 */
	@RequestMapping(value = "articleFormNormal")
	public ModelAndView articleFormNormal(String id, String channelId) {
		ModelAndView mv = new ModelAndView("article/articleForm");
		// 文章类型
		Dict dict = new Dict();
		dict.setType("article_type");
		mv.addObject("types", dictService.list(dict));
		// 频道
		if (StringUtils.isEmpty(channelId)) {
			Channel channel = new Channel();
			channel.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
			List<Channel> channels = channelService.list(channel);
			mv.addObject("channels", channels);
		} else {
			Channel channel = channelService.get(channelId);
			mv.addObject("channel", channel);
		}
		// 文章
		if (StringUtils.isNotEmpty(id)) {
			Article article = articleService.get(id);
			String content = StringUtils.stringtohtml(article.getContent());
			article.setContent(content);
			mv.addObject("article", article);
		}
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
	 * 三个入口：1直接发布文章 2从频道里面发布文章 3从文章组发布文章
	 * 
	 * @param article
	 * @param result
	 * @param channelId
	 *            不为空 表示从频道进来
	 * @param applyId
	 * @param subjectId
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

		if (article.getReadNum() == null) {
			article.setReadNum(0);
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
			if (StringUtils.isNotEmpty(article.getType())) {
				if (article.getType().equals("out")) {
					t.setContent("");
				} else if (article.getType().equals("local")) {
					t.setUrl("");
				}
			}
			articleService.update(t);

		} else {
			User user = userService.findByLoginName("admin");
			article.setCreateBy(user.getId());
			article.setUpdateBy(user.getId());
			article.setMember(RealmUtils.getCurrentUser().getId());
			if (StringUtils.isNotEmpty(channelId)) {
				Channel channel = channelService.get(channelId);
				article.setChannel(channel);
			}

			if (StringUtils.isNotEmpty(article.getType())) {
				article.setType("local");
			}
			
			String articleId = articleService.insert(article);
			if (apply != null && apply.getType().equals("article")) {
				apply.setUrl(micUrl + "article/article_detail.html?id=" + articleId);
				apply.setTargetId(articleId);
				subjectApplyService.update(apply);
			}
		}

		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(channelId)) {
			map.put("channelId", channelId);
			ajaxResult.setData(map);
		}

		if (apply != null) {
			map.put("applyId", apply.getId());
			map.put("subjectId", apply.getSubjectId());
			ajaxResult.setData(map);
		}
		ajaxResult.setSuccess(true);
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

	/**
	 * 删除文章
	 */
	@ResponseBody
	@RequestMapping(value = "delete")
	public AjaxResult delete(String id, String subjectId, String applyId) {
		AjaxResult ajaxResult = new AjaxResult();
		try {
			if (StringUtils.isEmpty(id)) {
				return new AjaxResult(false);
			}

			if (StringUtils.isNotEmpty(subjectId)) {
				ajaxResult.setData(subjectId);
			}

			articleService.delete(id);
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
}
