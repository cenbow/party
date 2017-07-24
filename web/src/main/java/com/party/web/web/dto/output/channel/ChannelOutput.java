package com.party.web.web.dto.output.channel;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.party.core.model.article.Article;
import com.party.core.model.channel.Channel;

/**
 * 频道输出视图
 * 
 * @author Administrator
 *
 */
public class ChannelOutput {
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
	private List<Article> articles;

	private Integer articleNum; // 文章数量
	
    private String qrCodeUrl;

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

	public List<Article> getArticles() {
		return articles;
	}

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}

	public static ChannelOutput transform(Channel channel) {
		ChannelOutput output = new ChannelOutput();
		try {
			BeanUtils.copyProperties(output, channel);
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

	public String getQrCodeUrl() {
		return qrCodeUrl;
	}

	public void setQrCodeUrl(String qrCodeUrl) {
		this.qrCodeUrl = qrCodeUrl;
	}

}
