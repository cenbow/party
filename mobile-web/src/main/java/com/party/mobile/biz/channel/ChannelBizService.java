package com.party.mobile.biz.channel;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.party.common.paging.Page;
import com.party.common.utils.LangUtils;
import com.party.core.exception.BusinessException;
import com.party.core.model.article.Article;
import com.party.core.model.channel.Channel;
import com.party.core.service.article.IArticleService;
import com.party.core.service.channel.IChannelService;
import com.party.mobile.web.dto.channel.ChannelOutput;
import com.party.common.utils.PartyCode;

@Service
public class ChannelBizService {

	@Autowired
	private IChannelService channelService;

	@Autowired
	private IArticleService articleService;

	/**
	 * 获取频道详情
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	public ChannelOutput getDetails(String id, HttpServletRequest request) {

		Channel channel = channelService.get(id);
		if (channel == null) {
			throw new BusinessException(PartyCode.DYNAMIC_NOT_EXIT, "动态的主键id错误，数据不存在");
		}

		ChannelOutput output = ChannelOutput.transform(channel);
		List<Article> articles = articleService.getByChannelId(channel.getId());
		output.setArticleNum(articles.size());
		return output;
	}

	/**
	 * 列表
	 * 
	 * @param subject
	 * @param page
	 * @return
	 */
	public List<ChannelOutput> getList(Channel channel, Page page) {
		List<Channel> channels = channelService.listPage(channel, page);

		if (!CollectionUtils.isEmpty(channels)) {
			List<ChannelOutput> channelOutputs = LangUtils.transform(channels, channelInput -> {
				ChannelOutput channelOutput = ChannelOutput.transform(channelInput);

				List<Article> articles = articleService.getByChannelId(channelOutput.getId());
				channelOutput.setArticleNum(articles.size());
				return channelOutput;
			});
			return channelOutputs;
		}
		return Collections.EMPTY_LIST;
	}

}
