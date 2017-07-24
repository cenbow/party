package com.party.core.dao.read.article;

import java.util.List;
import java.util.Map;

import com.party.common.paging.Page;
import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.article.Article;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 文章数据读取 party Created by wei.li on 2016/9/20 0020.
 */
@Repository
public interface ArticleReadDao extends BaseReadDao<Article> {

	/**
	 * 根据频道获取文章
	 * 
	 * @param id
	 * @return
	 */
	List<Article> getByChannelId(String id);

	/**
	 * web端
	 * 
	 * @param article
	 * @param params
	 * @param page
	 * @return
	 */
	List<Article> webListPage(@Param(value = "article") Article article, @Param(value = "params") Map<String, Object> params, Page page);
}
