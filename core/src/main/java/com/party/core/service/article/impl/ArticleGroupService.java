package com.party.core.service.article.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.party.common.paging.Page;
import com.party.common.utils.StringUtils;
import com.party.core.dao.read.article.ArticleGroupReadDao;
import com.party.core.dao.write.article.ArticleGroupWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.article.ArticleGroup;
import com.party.core.service.article.IArticleGroupService;

/**
 * 文章组
 * 
 * @author Administrator
 *
 */
@Service
public class ArticleGroupService implements IArticleGroupService {

	@Autowired
	private ArticleGroupReadDao articleGroupReadDao;
	@Autowired
	private ArticleGroupWriteDao articleGroupWriteDao;

	@Override
	public String insert(ArticleGroup t) {
		BaseModel.preInsert(t);
		boolean result = articleGroupWriteDao.insert(t);
		if (result) {
			return t.getId();
		}
		return null;
	}

	@Override
	public boolean update(ArticleGroup t) {
		if (null == t)
			return false;
		t.setUpdateDate(new Date());
		return articleGroupWriteDao.update(t);
	}

	@Override
	public boolean deleteLogic(String id) {
		if (StringUtils.isBlank(id))
			return false;

		return articleGroupWriteDao.deleteLogic(id);
	}

	@Override
	public boolean delete(String id) {
		if (StringUtils.isBlank(id))
			return false;

		return articleGroupWriteDao.delete(id);
	}

	@Override
	public boolean batchDeleteLogic(Set<String> ids) {
		if (CollectionUtils.isEmpty(ids))
			return false;

		return articleGroupWriteDao.batchDeleteLogic(ids);
	}

	@Override
	public boolean batchDelete(Set<String> ids) {
		if (CollectionUtils.isEmpty(ids))
			return false;

		return articleGroupWriteDao.batchDelete(ids);
	}

	@Override
	public ArticleGroup get(String id) {
		return articleGroupReadDao.get(id);
	}

	@Override
	public List<ArticleGroup> listPage(ArticleGroup t, Page page) {
		return articleGroupReadDao.listPage(t, page);
	}

	@Override
	public List<ArticleGroup> list(ArticleGroup t) {
		return articleGroupReadDao.listPage(t, null);
	}

	@Override
	public List<ArticleGroup> batchList(Set<String> ids, ArticleGroup t, Page page) {
		if (CollectionUtils.isEmpty(ids))
			return Collections.EMPTY_LIST;

		return articleGroupReadDao.batchList(ids, new HashedMap(), page);
	}

}
