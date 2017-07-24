package com.party.mobile.biz.article;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.party.core.exception.BusinessException;
import com.party.core.model.BaseModel;
import com.party.core.model.article.Article;
import com.party.core.model.article.ArticleGroup;
import com.party.core.service.article.IArticleGroupService;
import com.party.core.service.article.IArticleService;
import com.party.mobile.web.dto.article.output.ArticleGroupOutput;
import com.party.common.utils.PartyCode;

@Service
public class ArticleGroupBizService {

	@Autowired
	IArticleGroupService articleGroupService;

	@Autowired
	IArticleService articleService;

	public ArticleGroupOutput getDetails(String id, HttpServletRequest request) {
		ArticleGroup channel = articleGroupService.get(id);
		if (channel == null) {
			throw new BusinessException(PartyCode.DYNAMIC_NOT_EXIT, "动态的主键id错误，数据不存在");
		}

		ArticleGroupOutput output = ArticleGroupOutput.transform(channel);
		Article article = new Article();
		article.setArticleGroupId(id);
		article.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
		List<Article> articles = articleService.list(article);
		output.setArticleNum(articles.size());
		return output;
	}

}
