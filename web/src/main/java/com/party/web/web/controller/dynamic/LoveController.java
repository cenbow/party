package com.party.web.web.controller.dynamic;

import com.party.common.paging.Page;
import com.party.common.utils.LangUtils;
import com.party.core.model.dynamic.Dynamic;
import com.party.core.model.love.Love;
import com.party.core.model.member.Member;
import com.party.core.service.dynamic.IDynamicService;
import com.party.core.service.dynamic.biz.DynamicBaseBizService;
import com.party.core.service.love.ILoveService;
import com.party.core.service.member.IMemberService;
import com.party.web.web.dto.AjaxResult;
import com.party.web.web.dto.input.common.CommonInput;
import com.party.web.web.dto.output.dynamic.LoveOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("")
public class LoveController {

	@Autowired
	private ILoveService loveService;

	@Autowired
	private IMemberService memberService;
	
	@Autowired
	private IDynamicService dynamicService;

	@Autowired
	private DynamicBaseBizService dynamicBaseBizService;

	/**
	 * 点赞
	 * @return
	 */
	@RequestMapping(value = {"/dynamic/love/loveList","/circle/topicLove/loveList"})
	public ModelAndView loveList(Love love, Page page, CommonInput commonInput) {
		page.setLimit(20);
		ModelAndView mv = new ModelAndView("dynamic/love/loveList");
		Map<String, Object> params = CommonInput.appendParams(commonInput);
		mv.addObject("input", commonInput);

		Dynamic dynamic = dynamicService.get(love.getRefId());
		mv.addObject("dynamic", dynamic);
		
		List<Love> loves = loveService.webListPage(love, params, page);
		List<LoveOutput> loveOutputs = LangUtils.transform(loves, input -> {
			LoveOutput output = LoveOutput.transform(input);
			// 评论者
				Member authorMember = memberService.get(input.getCreateBy());
				output.setAuthor(authorMember);
				return output;
			});
		mv.addObject("loves", loveOutputs);
		mv.addObject("page", page);
		return mv;
	}

	/**
	 * 删除点赞
	 */
	@ResponseBody
	@RequestMapping(value = {"/dynamic/love/delete","/circle/topicLove/delete"},method = RequestMethod.POST)
	public AjaxResult del(String id) throws Exception {
		AjaxResult ajaxResult = new AjaxResult();
		try {
			dynamicBaseBizService.delLoveBiz(id);
		} catch (Exception e) {
			ajaxResult.setSuccess(false);
		}
		ajaxResult.setSuccess(true);
		return ajaxResult;
	}
}
