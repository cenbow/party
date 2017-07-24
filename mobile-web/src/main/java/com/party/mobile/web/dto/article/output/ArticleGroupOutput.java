package com.party.mobile.web.dto.article.output;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.party.core.model.article.ArticleGroup;

/**
 * 文章组输出视图
 * 
 * @author Administrator
 *
 */
public class ArticleGroupOutput {
	// 主键id
	private String id;

	// 名称
	private String name;

	// 创建时间
	private Date createDate;

	// 封面图
	private String picture;

	// 描述
	private String remarks;

	// 文章
	private List<ArticleOutput> articles;

	private Integer articleNum; // 文章数量

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public List<ArticleOutput> getArticles() {
		return articles;
	}

	public void setArticles(List<ArticleOutput> articles) {
		this.articles = articles;
	}

	public static ArticleGroupOutput transform(ArticleGroup articleGroup) {
		ArticleGroupOutput output = new ArticleGroupOutput();
		try {
			BeanUtils.copyProperties(output, articleGroup);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return output;
	}

	public Integer getArticleNum() {
		return articleNum;
	}

	public void setArticleNum(Integer articleNum) {
		this.articleNum = articleNum;
	}

}
