package com.party.mobile.web.controller.channel;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Strings;
import com.party.common.paging.Page;
import com.party.core.exception.BusinessException;
import com.party.core.model.BaseModel;
import com.party.core.model.channel.Channel;
import com.party.mobile.biz.channel.ChannelBizService;
import com.party.mobile.web.dto.AjaxResult;
import com.party.mobile.web.dto.channel.ChannelOutput;
import com.party.common.utils.PartyCode;

/**
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/channel/channel")
public class ChannelController {

	@Autowired
	private ChannelBizService channelBizService;

	/**
	 * 获取专题详情
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getDetails")
	public AjaxResult getDetails(String id, HttpServletRequest request) {
		if (Strings.isNullOrEmpty(id)) {
			return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, "参数不合法");
		}

		try {
			ChannelOutput output = channelBizService.getDetails(id, request);
			return AjaxResult.success(output);
		} catch (BusinessException be) {
			return AjaxResult.error(be.getCode(), be.getMessage());
		}
	}

	@ResponseBody
	@RequestMapping(value = "/list")
	public AjaxResult getDetails(Channel channel, Page page) {
		channel.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
		channel.setIsShow(1);
		List<ChannelOutput> outputs = channelBizService.getList(channel, page);
		return AjaxResult.success(outputs, page);
	}
}
