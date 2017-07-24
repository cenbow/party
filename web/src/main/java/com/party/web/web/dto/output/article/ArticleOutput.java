package com.party.web.web.dto.output.article;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

import com.party.core.model.BaseModel;
import com.party.core.model.article.Article;
import com.party.core.model.channel.Channel;
import com.party.web.web.dto.output.channel.ChannelOutput;

/**
 * 文章实体 party Created by wei.li on 2016/9/20 0020.
 */
public class ArticleOutput extends BaseModel {

	private static final long serialVersionUID = 6417274247527292980L;

	// 标题
	private String title;

	// 副标题
	private String subTitle;

	// 封面图
	private String pic;

	// 是否热门(0:否；1：是)
	private Integer isHot;

	// 文章类型
	private String articleType;

	// 内容
	private String content;

	// 排序
	private Integer sort;

	private String orderBy;

	private Channel channel;

	private Integer readNum; // 阅读量

	private String applyUrl; // 报名路径

	private Integer showApply; // 是否显示报名

	private String btnName; // 按钮名称

	private String member; // 前端发布者

	private String qrCodeUrl;

	private String applyId; // 欄目id

	public String getBtnName() {
		return btnName;
	}

	public void setBtnName(String btnName) {
		this.btnName = btnName;
	}

	public String getTitle() {
		return title;
	}

	public String getApplyUrl() {
		return applyUrl;
	}

	public void setApplyUrl(String applyUrl) {
		this.applyUrl = applyUrl;
	}

	public Integer getShowApply() {
		return showApply;
	}

	public void setShowApply(Integer showApply) {
		this.showApply = showApply;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public Integer getIsHot() {
		return isHot;
	}

	public void setIsHot(Integer isHot) {
		this.isHot = isHot;
	}

	public String getArticleType() {
		return articleType;
	}

	public void setArticleType(String articleType) {
		this.articleType = articleType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public Integer getReadNum() {
		return readNum;
	}

	public void setReadNum(Integer readNum) {
		this.readNum = readNum;
	}

	public String getMember() {
		return member;
	}

	public void setMember(String member) {
		this.member = member;
	}

	public String getQrCodeUrl() {
		return qrCodeUrl;
	}

	public void setQrCodeUrl(String qrCodeUrl) {
		this.qrCodeUrl = qrCodeUrl;
	}

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public static ArticleOutput transform(Article article) {
		ArticleOutput output = new ArticleOutput();
		try {
			BeanUtils.copyProperties(output, article);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return output;
	}
}
