package com.party.mobile.web.controller.article;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.party.authorization.annotation.Authorization;
import com.party.common.constant.Constant;
import com.party.core.model.member.Member;
import com.party.core.service.member.IMemberService;
import com.party.mobile.biz.article.ArticleBizService;
import com.party.mobile.biz.currentUser.CurrentUserBizService;
import com.party.mobile.biz.distributor.DistributorBizService;
import com.party.mobile.web.dto.distributor.input.GetDistributorInput;
import com.party.mobile.web.dto.login.output.CurrentUser;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Strings;
import com.party.common.paging.Page;
import com.party.common.utils.LangUtils;
import com.party.common.utils.StringUtils;
import com.party.core.exception.BusinessException;
import com.party.core.model.BaseModel;
import com.party.core.model.article.Article;
import com.party.core.model.channel.Channel;
import com.party.core.service.article.IArticleService;
import com.party.core.service.channel.IChannelService;
import com.party.mobile.biz.article.ArticleGroupBizService;
import com.party.mobile.web.dto.AjaxResult;
import com.party.mobile.web.dto.article.output.ArticleGroupOutput;
import com.party.mobile.web.dto.article.output.ArticleListOutput;
import com.party.mobile.web.dto.article.output.ArticleOutput;
import com.party.common.utils.PartyCode;

/**
 * 文章控制层
 * party
 * Created by Wesley
 * on 2016/11/21
 */

@Controller
@RequestMapping(value = "/article/article")
public class ArticleController {

    @Autowired
    IArticleService articleService;
    @Autowired
    IChannelService channelService;
    @Autowired
    ArticleGroupBizService articleGroupBizService;

    @Autowired
    DistributorBizService distributorBizService;

    @Autowired
    CurrentUserBizService currentUserBizService;

    @Autowired
    IMemberService memberService;

    @Autowired
    ArticleBizService articleBizService;

    /**
     * 分页查询文章列表
     * @param articleType 文章类型
     * @param page 分页参数
     * @return 交互数据
     */
    @ResponseBody
    @RequestMapping(value = "/list")
    public AjaxResult list(String articleType, Page page,String channelid,String order, String articleGroupId){

//        if (Strings.isNullOrEmpty(articleType) || Integer.parseInt(articleType) < 1 || Integer.parseInt(articleType) > 3)
//        {
//            return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, "参数不合法");
//        }
        Article article = new Article();
        article.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
        article.setArticleType(articleType);
        if (StringUtils.isNotEmpty(channelid)) {
			Channel channel = new Channel();
			channel.setId(channelid);
			article.setChannel(channel);
		}
        if (StringUtils.isNotEmpty(articleGroupId)) {
			article.setArticleGroupId(articleGroupId);
		}
        article.setOrderBy(order);

        List<Article> articleList = articleService.listPage(article, page);
        if (!CollectionUtils.isEmpty(articleList)){
            List<ArticleListOutput> outputList = LangUtils.transform(articleList, input -> {
                ArticleListOutput output = ArticleListOutput.transform(input);

                //设置分享链接
                output.setShareLink(getShareLink(input.getId()));

                return output;
            });
            return AjaxResult.success(outputList, page);
        }

        return AjaxResult.success(Collections.EMPTY_LIST, page);
    }

    /**
     * 获取文章详情
     * @param id 文章主键id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getDetails")
    public AjaxResult getDetails(String id, HttpServletRequest request){
        if (Strings.isNullOrEmpty(id))
        {
            return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, "参数不合法");
        }
        Article article = articleService.get(id);
        if (null == article || null == article.getContent())
        {
            return AjaxResult.error(PartyCode.ARTICLE_NOT_EXIT, "文章内容不存在");
        }

        ArticleOutput output = ArticleOutput.transform(article);
        
        //设置文章内容
        output.setContent(StringUtils.stringtohtml(article.getContent()));
        //设置分享链接
        output.setShareLink(getShareLink(id));
        
        article.setReadNum(article.getReadNum() + 1);
        articleService.update(article);
        if(article.getChannel() != null && article.getChannel().getId() != null){
	        Channel channel = channelService.get(article.getChannel().getId());
	        if(channel != null){
	        	output.setChannelId(channel.getId());
	        	output.setChannelName(channel.getName());
	        }
        }
        CurrentUser currentUser = currentUserBizService.getCurrentUser(request);
        Member creator = memberService.get(article.getMember());
        output.setIsPartner(creator.getIsPartner());
        //是否允许分销
        if (null != currentUser){
            Member member = memberService.get(currentUser.getId());
            if (!currentUser.getId().equals(article.getMemberId())){
                output.setIsDistributor(member.getIsDistributor());
            }
            else {
                output.setIsDistributor(Constant.BAN_DISTRIBUTION);
            }
        }
        return AjaxResult.success(output);
    }

    @ResponseBody
	@RequestMapping(value = "/articlegroup/detail")
	public AjaxResult getArticleGroupDetails(String id, HttpServletRequest request) {
		if (Strings.isNullOrEmpty(id)) {
			return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, "参数不合法");
		}

		try {
			ArticleGroupOutput output = articleGroupBizService.getDetails(id, request);
			return AjaxResult.success(output);
		} catch (BusinessException be) {
			return AjaxResult.error(be.getCode(), be.getMessage());
		}
	}


    /**
     * 获取文章分销详情
     * @param input 输入视图
     * @param request 请求参数
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/distribution/detail")
    public AjaxResult getDistributorDetails(HttpServletRequest request, GetDistributorInput input){
        if (Strings.isNullOrEmpty(input.getTargetId()))
        {
            return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, "参数不合法");
        }
        Article article = articleService.get(input.getTargetId());
        if (null == article || null == article.getContent())
        {
            return AjaxResult.error(PartyCode.ARTICLE_NOT_EXIT, "文章内容不存在");
        }

        ArticleOutput output = ArticleOutput.transform(article);

        //设置文章内容
        output.setContent(StringUtils.stringtohtml(article.getContent()));
        //设置分享链接
        output.setShareLink(getShareLink(input.getTargetId()));

        article.setReadNum(article.getReadNum() + 1);
        articleService.update(article);
        if(article.getChannel() != null && article.getChannel().getId() != null){
            Channel channel = channelService.get(article.getChannel().getId());
            if(channel != null){
                output.setChannelId(channel.getId());
                output.setChannelName(channel.getName());
            }
        }

        CurrentUser currentUser = currentUserBizService.getCurrentUser(request);
        output = articleBizService.getDistributionDetails(output, input, currentUser);
        return AjaxResult.success(output);
    }


    /**
     * 获取文章分享链接
     * @param id 文章主键id
     * @return
     */
    private String getShareLink(String id)
    {
        return "http://3g.tongxingzhe.cn/micWeb/html/article/article_detail.html?id="+ id;
    }
}
