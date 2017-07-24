package com.party.mobile.web.dto.article.output;

import com.alibaba.fastjson.annotation.JSONField;
import com.party.core.model.article.Article;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * 文章输出视图
 *
 * @Author: Wesley
 * @Description:
 * @Date: Created in 18:35 16/11/21
 * @Modified by:
 */
public class ArticleOutput extends ArticleListOutput
{
    //内容
    private String content;

    private String member;
    
    private String type; // local:本地  out:外部链接
    
    private String url;
    
    //是否为合作商
    private Integer isPartner; //创建者是否为合作商

    //是我的分销
    @JSONField(name = "isMyDistribution")
    private boolean isMyDistribution;

    //当前用户是否允许分销（0:：不是 1：是）
    private Integer isDistributor;

    //分销者名称
    private String distributorName;

    //分销者图像
    private String distributorLogo;

    //分销时间
    private Date distributorTime;

    //风格
    private String style;

    //宣言
    private String declaration;

    
    public Integer getIsPartner() {
		return isPartner;
	}

	public void setIsPartner(Integer isPartner) {
		this.isPartner = isPartner;
	}

	public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public boolean isMyDistribution() {
        return isMyDistribution;
    }

    public void setMyDistribution(boolean myDistribution) {
        isMyDistribution = myDistribution;
    }

    public Integer getIsDistributor() {
        return isDistributor;
    }

    public void setIsDistributor(Integer isDistributor) {
        this.isDistributor = isDistributor;
    }

    public String getDistributorName() {
        return distributorName;
    }

    public void setDistributorName(String distributorName) {
        this.distributorName = distributorName;
    }

    public String getDistributorLogo() {
        return distributorLogo;
    }

    public void setDistributorLogo(String distributorLogo) {
        this.distributorLogo = distributorLogo;
    }

    public Date getDistributorTime() {
        return distributorTime;
    }

    public void setDistributorTime(Date distributorTime) {
        this.distributorTime = distributorTime;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getDeclaration() {
        return declaration;
    }

    public void setDeclaration(String declaration) {
        this.declaration = declaration;
    }

    /**
     * 文章转输出视图
     * @param article 活动实体
     * @return 文章输出视图
     */
    public static ArticleOutput transform(Article article){
        ArticleOutput activityOutput = new ArticleOutput();
        BeanUtils.copyProperties(article, activityOutput);
        return activityOutput;
    }

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
}
