package com.party.core.service.article.impl;

import com.google.common.base.Strings;
import com.party.common.paging.Page;
import com.party.core.dao.read.article.ArticleReadDao;
import com.party.core.dao.write.article.ArticleWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.article.Article;
import com.party.core.service.article.IArticleService;
import com.sun.istack.NotNull;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 文章服务实现
 * party
 * Created by wei.li
 * on 2016/9/20 0020.
 */
@Service
public class ArticleService implements IArticleService{

    @Autowired
    ArticleWriteDao articleWriteDao;

    @Autowired
    ArticleReadDao articleReadDao;

    /**
     * 文章插入
     * @param article 文章
     * @return 插入结果（true/false）
     */
    @Override
    public String insert(Article article) {
        BaseModel.preInsert(article);
        boolean result = articleWriteDao.insert(article);
        if (result){
            return article.getId();
        }
        return null;
    }

    /**
     * 文章更新
     * @param article 文章
     * @return 更新结果（true/false）
     */
    @Override
    public boolean update(Article article) {
        article.setUpdateDate(new Date());
        return articleWriteDao.update(article);
    }

    /**
     * 逻辑删除文章
     * @param id 实体主键
     * @return 删除结果（true/false）
     */
    @Override
    public boolean deleteLogic(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return articleWriteDao.deleteLogic(id);
    }


    /**
     * 物理删除文章
     * @param id 实体主键
     * @return 删除结果（true/false）
     */
    @Override
    public boolean delete(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return articleWriteDao.delete(id);
    }

    /**
     * 批量逻辑删除文章
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    @Override
    public boolean batchDeleteLogic(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return articleWriteDao.batchDeleteLogic(ids);
    }

    /**
     * 批量物理删除文章
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    @Override
    public boolean batchDelete(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return articleWriteDao.batchDelete(ids);
    }

    /**
     * 根据主键查询文章
     * @param id 主键
     * @return 文章
     */
    @Override
    public Article get(String id) {
        return articleReadDao.get(id);
    }

    /**
     * 分页查询文章
     * @param article 文章
     * @param page 分页信息
     * @return 文章列表
     */
    @Override
    public List<Article> listPage(Article article, Page page) {
        return articleReadDao.listPage(article, page);
    }

    /**
     * 查询所有文章
     * @param article 文章
     * @return 文章列表
     */
    @Override
    public List<Article> list(Article article) {
        return articleReadDao.listPage(article, null);
    }

    /**
     * 批量查询文章
     * @param ids 主键集合
     * @param article 文章
     * @param page 分页信息
     * @return 文章列表
     */
    @Override
    public List<Article> batchList(@NotNull Set<String> ids, Article article, Page page) {
        if (CollectionUtils.isEmpty(ids)){
            return Collections.EMPTY_LIST;
        }
        return articleReadDao.batchList(ids, new HashedMap(), page);
    }

	@Override
	public List<Article> getByChannelId(String id) {
		return articleReadDao.getByChannelId(id);
	}

	@Override
	public List<Article> webListPage(Article article, Map<String, Object> params, Page page) {
		return articleReadDao.webListPage(article, params, page);
	}
}
