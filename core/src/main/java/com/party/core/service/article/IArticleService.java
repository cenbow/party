package com.party.core.service.article;

import java.util.List;
import java.util.Map;

import com.party.common.paging.Page;
import com.party.core.model.article.Article;
import com.party.core.service.IBaseService;

/**
 * 文章服务接口
 * party
 * Created by wei.li
 * on 2016/9/20 0020.
 */

public interface IArticleService extends IBaseService<Article>{

	/**
	 * 根据频道获取文章
	 * @param id
	 * @return
	 */
	List<Article> getByChannelId(String id);

	List<Article> webListPage(Article article, Map<String, Object> params, Page page);
}
