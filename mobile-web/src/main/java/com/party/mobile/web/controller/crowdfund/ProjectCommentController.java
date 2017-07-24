package com.party.mobile.web.controller.crowdfund;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.party.authorization.annotation.Authorization;
import com.party.core.model.crowdfund.ProjectComment;
import com.party.core.service.crowdfund.impl.ProjectCommentService;
import com.party.core.service.member.impl.MemberService;
import com.party.mobile.biz.currentUser.CurrentUserBizService;
import com.party.mobile.web.dto.AjaxResult;
import com.party.mobile.web.dto.login.output.CurrentUser;

/**
 * 众筹评论控制器
 * Created by Juliana
 *
 * @date 2017/3/1 0001
 * @time 10:17
 */

@Controller
@RequestMapping(value = "/crowdfund/pcomment")
public class ProjectCommentController {

    @Autowired
    private ProjectCommentService projectCommentService;
    @Autowired
    private CurrentUserBizService currentUserBizService;
    @Autowired
    private MemberService memberService;

    /**
     * 获取当前用户的众筹目标
     * @param page 分页参数
     * @param request 请求参数
     * @return 交互数据
     */
    @Authorization
    @ResponseBody
    @RequestMapping(value = "replyComment")
    public AjaxResult replyComment(ProjectComment comment ,HttpServletRequest request){
        AjaxResult ajaxResult = new AjaxResult();
        CurrentUser currentUser = currentUserBizService.getCurrentUser(request);
        comment.setCreateBy(currentUser.getId());
        String id = projectCommentService.insert(comment);
        ajaxResult.setSuccess(true);
        ajaxResult.setData(id);
        return ajaxResult;
    }
}
