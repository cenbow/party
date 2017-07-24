package com.party.web.web.controller.channel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.party.common.paging.Page;
import com.party.common.utils.LangUtils;
import com.party.common.utils.StringUtils;
import com.party.core.model.BaseModel;
import com.party.core.model.article.Article;
import com.party.core.model.channel.Channel;
import com.party.core.model.member.Member;
import com.party.core.model.user.User;
import com.party.core.service.article.IArticleService;
import com.party.core.service.channel.IChannelService;
import com.party.core.service.user.IUserService;
import com.party.web.biz.file.FileBizService;
import com.party.web.utils.RealmUtils;
import com.party.web.web.dto.AjaxResult;
import com.party.web.web.dto.output.channel.ChannelOutput;

/**
 * 频道
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/channel/channel")
public class ChannelController {

	@Autowired
	private IChannelService channelService;

	@Autowired
	private IUserService userService;

	@Autowired
	private IArticleService articleService;

	@Autowired
	private FileBizService fileBizService;

	/**
	 * 跳转至列表 我发布的频道
	 * 
	 * @return
	 */
	@RequestMapping(value = "channelList")
	public ModelAndView channelList(Channel channel, Page page, Integer timeType, String c_start, String c_end) {
		ModelAndView mv = new ModelAndView("channel/channelList");
		Member user = RealmUtils.getCurrentUser();
		channel.setMember(user.getId());
		channel.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
		Map<String, Object> params = new HashMap<String, Object>();
		// 时间块处理
		if (timeType != null && timeType != 0) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar calendar = Calendar.getInstance();
			Date ed = calendar.getTime(); // 结束时间
			if (timeType == 2) { // 本周内
				int day_of_week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
				if (day_of_week == 0) {
					day_of_week = 7;
				}
				calendar.add(Calendar.DATE, -day_of_week + 1);
			} else if (timeType == 3) { // 本月内
				calendar.set(Calendar.DAY_OF_MONTH, 1);
			}
			Date sd = calendar.getTime(); // 开始时间
			String std = sdf.format(sd) + " 00:00:00";
			params.put("startDate", std);
			String end = sdf.format(ed) + " 23:59:59";
			params.put("endDate", end);

			mv.addObject("timeType", timeType);
		}
		// 时间段处理
		if (StringUtils.isNotEmpty(c_start)) {
			params.put("c_start", c_start);
			mv.addObject("c_start", c_start);
		}
		if (StringUtils.isNotEmpty(c_end)) {
			params.put("c_end", c_end);
			mv.addObject("c_end", c_end);
		}
		List<Channel> channels = channelService.webListPage(channel, params, page);
		String path = RealmUtils.getCurrentUser().getId() + "/channel/";
		List<ChannelOutput> channelOutputs = LangUtils.transform(channels, input -> {
			ChannelOutput channelOutput = ChannelOutput.transform(input);
			List<Article> articles = articleService.getByChannelId(input.getId());
			channelOutput.setArticleNum(articles.size());

			String content = "article/article_detail.html?id=" + input.getId();
			String qrCodeUrl = fileBizService.getFileEntity(input.getId(), path, content);
			channelOutput.setQrCodeUrl(qrCodeUrl);

			return channelOutput;
		});
		mv.addObject("page", page);
		mv.addObject("channels", channelOutputs);
		return mv;
	}

	/**
	 * 跳转至发布
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "publishChannel")
	public ModelAndView publishChannel(String id) {
		ModelAndView mv = new ModelAndView("channel/publishChannel");
		if (StringUtils.isNotEmpty(id)) {
			Channel channel = channelService.get(id);
			mv.addObject("channel", channel);
		}
		return mv;
	}

	/**
	 * 跳转至详情
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "channelDetail")
	public ModelAndView channelDetail(String id) {
		ModelAndView mv = new ModelAndView("channel/channelDetail");
		Channel channel = channelService.get(id);
		mv.addObject("channel", channel);
		return mv;
	}

	/**
	 * 发布保存
	 * 
	 * @param channel
	 * @param result
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "save")
	public AjaxResult save(Channel channel, BindingResult result) {
		AjaxResult ajaxResult = new AjaxResult();
		// 数据验证
		if (result.hasErrors()) {
			List<ObjectError> allErros = result.getAllErrors();
			ajaxResult.setDescription(allErros.get(0).getDefaultMessage());
			return ajaxResult;
		}

		if (StringUtils.isNotEmpty(channel.getId())) {
			channelService.update(channel);
		} else {
			User user = userService.findByLoginName("admin");
			channel.setCreateBy(user.getId());
			channel.setUpdateBy(user.getId());
			channel.setIsShow(0);
			channel.setMember(RealmUtils.getCurrentUser().getId());
			channelService.insert(channel);
		}
		ajaxResult.setSuccess(true);
		return ajaxResult;
	}
}
