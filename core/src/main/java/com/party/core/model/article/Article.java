package com.party.core.model.article;

import com.party.core.model.BaseModel;
import com.party.core.model.channel.Channel;

/**
 * 文章实体
 * party
 * Created by wei.li
 * on 2016/9/20 0020.
 */
public class Article extends BaseModel {

    private static final long serialVersionUID = 6417274247527292980L;

    //标题
    private String title;

    //副标题
    private String subTitle;

    //封面图
    private String pic;

    //是否热门(0:否；1：是)
    private Integer isHot;

    //文章类型
    private String articleType;

    //内容
    private String content;

    //排序
    private Integer sort;
    
    private String orderBy;
    
    private Channel channel;
    
    private String articleGroupId;
    
    private Integer readNum; // 阅读量
    
    private String applyUrl; //报名路径
    
    private Integer showApply; //是否显示报名
    
    private String btnName; //按钮名称
    
    private String member; // 前端发布者

    private String memberId;//编号

    private String type; //文章内容来源
    private String url; // 外部链接

    public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

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

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Article article = (Article) o;

        if (title != null ? !title.equals(article.title) : article.title != null) return false;
        if (subTitle != null ? !subTitle.equals(article.subTitle) : article.subTitle != null) return false;
        if (pic != null ? !pic.equals(article.pic) : article.pic != null) return false;
        if (isHot != null ? !isHot.equals(article.isHot) : article.isHot != null) return false;
        if (articleType != null ? !articleType.equals(article.articleType) : article.articleType != null) return false;
        if (content != null ? !content.equals(article.content) : article.content != null) return false;
        return sort != null ? sort.equals(article.sort) : article.sort == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (subTitle != null ? subTitle.hashCode() : 0);
        result = 31 * result + (pic != null ? pic.hashCode() : 0);
        result = 31 * result + (isHot != null ? isHot.hashCode() : 0);
        result = 31 * result + (articleType != null ? articleType.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (sort != null ? sort.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Article{" +
                "title='" + title + '\'' +
                ", subTitle='" + subTitle + '\'' +
                ", pic='" + pic + '\'' +
                ", isHot=" + isHot +
                ", articleType='" + articleType + '\'' +
                ", content='" + content + '\'' +
                ", sort=" + sort +
                '}';
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

	public String getArticleGroupId() {
		return articleGroupId;
	}

	public void setArticleGroupId(String articleGroupId) {
		this.articleGroupId = articleGroupId;
	}
}
