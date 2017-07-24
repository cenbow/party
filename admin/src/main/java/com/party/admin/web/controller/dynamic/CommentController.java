package com.party.admin.web.controller.dynamic;

import java.util.List;
import java.util.Map;

import com.party.core.service.dynamic.biz.DynamicBaseBizService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.party.admin.utils.MyBeanUtils;
import com.party.admin.utils.RealmUtils;
import com.party.admin.web.dto.AjaxResult;
import com.party.admin.web.dto.input.common.CommonInput;
import com.party.admin.web.dto.output.dynamic.CommentOutput;
import com.party.common.paging.Page;
import com.party.common.utils.LangUtils;
import com.party.core.model.dynamic.Comment;
import com.party.core.model.dynamic.Dynamic;
import com.party.core.model.member.Member;
import com.party.core.service.dynamic.ICommentService;
import com.party.core.service.dynamic.IDynamicService;
import com.party.core.service.member.IMemberService;

@Controller
@RequestMapping("")
public class CommentController {
	@Autowired
	private ICommentService commentService;

	@Autowired
	private IMemberService memberService;

	@Autowired
	private IDynamicService dynamicService;

	@Autowired
	private DynamicBaseBizService dynamicBaseBizService;

	/**
	 * 评论
	 */
	@RequestMapping(value = {"/dynamic/comment/commentList","/circle/topicCmt/commentList"})
	public ModelAndView commentList(Comment comment, Page page, CommonInput commonInput) {
		ModelAndView mv = new ModelAndView("dynamic/comment/commentList");
		Map<String, Object> params = CommonInput.appendParams(commonInput);
		mv.addObject("input", commonInput);

		Dynamic dynamic = dynamicService.get(comment.getRefId());
		mv.addObject("dynamic", dynamic);

		List<Comment> comments = commentService.webListPage(comment, params, page);
		List<CommentOutput> commentOutputs = LangUtils.transform(comments, input -> {
			CommentOutput output = CommentOutput.transform(input);
			// 评论者
				Member authorMember = memberService.get(input.getCreateBy());
				output.setAuthor(authorMember);
				return output;
			});
		mv.addObject("comments", commentOutputs);
		mv.addObject("page", page);
		return mv;
	}

	/**
	 * 跳转至发布 评论
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = {"/dynamic/comment/commentForm","/circle/topicCmt/commentForm"})
	public ModelAndView commentForm(String id) {
		ModelAndView mv = new ModelAndView("dynamic/comment/commentForm");
		if (StringUtils.isNotEmpty(id)) {
			Comment comment = commentService.get(id);
			mv.addObject("comment", comment);
		}
		return mv;
	}

	/**
	 * 保存
	 * 
	 * @param result
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = {"/dynamic/comment/save","/circle/topicCmt/save"})
	public AjaxResult save(Comment comment, BindingResult result) throws Exception {
		AjaxResult ajaxResult = new AjaxResult();
		// 数据验证
		if (result.hasErrors()) {
			List<ObjectError> allErros = result.getAllErrors();
			ajaxResult.setDescription(allErros.get(0).getDefaultMessage());
			return ajaxResult;
		}

		if (StringUtils.isNotEmpty(comment.getId())) {
			Comment t = commentService.get(comment.getId());
			MyBeanUtils.copyBeanNotNull2Bean(comment, t);// 将编辑表单中的非NULL值覆盖数据库记录中的值
			commentService.update(t);
		} else {
			comment = new Comment();
			Member currentUser = RealmUtils.getCurrentUser();
			comment.setCreateBy(currentUser.getId());
			comment.setUpdateBy(currentUser.getId());
			commentService.insert(comment);
		}
		ajaxResult.setSuccess(true);
		return ajaxResult;
	}

	/**
	 * 删除点赞
	 */
	@ResponseBody
	@RequestMapping(value = {"/dynamic/comment/delete","/circle/comment/delete"},method = RequestMethod.POST)
	public AjaxResult del(String id) throws Exception {
		AjaxResult ajaxResult = new AjaxResult();
		try {
			dynamicBaseBizService.delCmtBiz(id);
		} catch (Exception e) {
			ajaxResult.setSuccess(false);
		}
		ajaxResult.setSuccess(true);
		return ajaxResult;
	}
}
