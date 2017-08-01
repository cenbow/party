package com.party.admin.web.controller.channel;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.party.admin.biz.file.FileBizService;
import com.party.admin.utils.MyBeanUtils;
import com.party.admin.utils.RealmUtils;
import com.party.admin.web.dto.AjaxResult;
import com.party.admin.web.dto.input.common.CommonInput;
import com.party.admin.web.dto.output.channel.ChannelOutput;
import com.party.common.paging.Page;
import com.party.common.utils.LangUtils;
import com.party.common.utils.StringUtils;
import com.party.core.model.BaseModel;
import com.party.core.model.article.Article;
import com.party.core.model.channel.Channel;
import com.party.core.model.system.Dict;
import com.party.core.model.user.User;
import com.party.core.service.article.IArticleService;
import com.party.core.service.channel.IChannelService;
import com.party.core.service.system.IDictService;
import com.party.core.service.user.IUserService;

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

	@Autowired
	private IDictService dictService;
	
	protected static Logger logger = LoggerFactory.getLogger(ChannelController.class);

	/**
	 * 频道列表
	 * 
	 * @param channel
	 * @param page
	 * @param timeType
	 * @param c_start
	 * @param c_end
	 * @return
	 */
	@RequestMapping(value = "channelList")
	public ModelAndView publishList(Channel channel, Page page, CommonInput commonInput) {
		ModelAndView mv = new ModelAndView("channel/channelList");
		channel.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
		Map<String, Object> params = CommonInput.appendParams(commonInput);
		mv.addObject("input", commonInput);
		List<Channel> channels = channelService.webListPage(channel, params, page);
		List<ChannelOutput> channelOutputs = LangUtils.transform(channels, input -> {
			ChannelOutput channelOutput = ChannelOutput.transform(input);
			List<Article> articles = articleService.getByChannelId(input.getId());
			channelOutput.setArticleNum(articles.size());

			String path = input.getMemberId() + "/channel/";
			String content = "channel/channel_detail.html?channelId=" + input.getId();
			String fileEntity = fileBizService.getFileEntity(input.getId(), path, content);
			channelOutput.setQrCodeUrl(fileEntity);
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
	@RequestMapping(value = "channelForm")
	public ModelAndView publishChannel(String id) {
		ModelAndView mv = new ModelAndView("channel/channelForm");
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
	public ModelAndView channelDetail(String id, Page page) {
		ModelAndView mv = new ModelAndView("channel/channelDetail");
		Channel channel = channelService.get(id);
		mv.addObject("channel", channel);

		Article article = new Article();
		article.setChannel(channel);
		article.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
		List<Article> activities = articleService.webListPage(article, null, page);

		Dict dict = new Dict();
		dict.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
		dict.setType("article_type");

		for (Article entity : activities) {
			String articleType = entity.getArticleType();
			dict.setValue(articleType);
			Dict dictEntity = dictService.getByProperty(dict);
			if (dictEntity != null) {
				entity.setArticleType(dictEntity.getLabel());
			}
		}
		mv.addObject("activities", activities);
		mv.addObject("page", page);
		return mv;
	}

	/**
	 * 发布保存
	 * 
	 * @param channel
	 * @param result
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "save")
	public AjaxResult save(Channel channel, BindingResult result) throws Exception {
		AjaxResult ajaxResult = new AjaxResult();
		// 数据验证
		if (result.hasErrors()) {
			List<ObjectError> allErros = result.getAllErrors();
			ajaxResult.setDescription(allErros.get(0).getDefaultMessage());
			return ajaxResult;
		}

		if (StringUtils.isNotEmpty(channel.getId())) {
			Channel t = channelService.get(channel.getId());
			MyBeanUtils.copyBeanNotNull2Bean(channel, t);// 将编辑表单中的非NULL值覆盖数据库记录中的值
			channelService.update(t);
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

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "delete")
	public AjaxResult delete(String id) {
		try {
			if (StringUtils.isEmpty(id)) {
				return new AjaxResult(false);
			}
			List<Article> articles = articleService.getByChannelId(id);
			if (articles.size() == 0) {
				channelService.delete(id);
				return new AjaxResult(true);
			} else {
				return new AjaxResult(false, "已有文章不能删除");
			}
		} catch (Exception e) {
			logger.info("频道删除异常", e);
		}
		return new AjaxResult(false);
	}
}
