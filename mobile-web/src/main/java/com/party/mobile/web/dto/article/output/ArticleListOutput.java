package com.party.mobile.web.dto.article.output;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.party.core.model.article.Article;

/**
 * 文章列表输出视图
 *
 * @Author: Wesley
 * @Description:
 * @Date: Created in 18:35 16/11/21
 * @Modified by:
 */
public class ArticleListOutput {
	
	
    //主键id
    private String id;

    //封面图
    private String pic;

    //标题
    private String title;

    //文章类型(1：室外活动，2：室内活动，3：讲座)
    private String articleType;

    //分享链接
    private String shareLink;
    
    private String createDate;
    
    private Integer readNum;

    private String applyUrl;
    
    private Integer showApply;
    
    private String btnName;    
    
    private String subTitle;

    private String channelId;
    
    private String channelName;
    
    
    public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public String getBtnName() {
		return btnName;
	}

	public void setBtnName(String btnName) {
		this.btnName = btnName;
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

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArticleType() {
        return articleType;
    }

    public void setArticleType(String articleType) {
        this.articleType = articleType;
    }

    public String getShareLink() {
        return shareLink;
    }

    public void setShareLink(String shareLink) {
        this.shareLink = shareLink;
    }


	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		this.createDate = format.format(createDate);
	}

	public Integer getReadNum() {
		return readNum;
	}

	public void setReadNum(Integer readNum) {
		this.readNum = readNum;
	}

    /**
     * 文章转输出视图
     * @param article 活动实体
     * @return 文章输出视图
     */
    public static ArticleListOutput transform(Article article){
        ArticleListOutput articleOutput = new ArticleListOutput();
        BeanUtils.copyProperties(article, articleOutput);
        return articleOutput;
    }
}
