package com.party.mobile.web.controller.sign;

import com.party.authorization.annotation.Authorization;
import com.party.common.paging.Page;
import com.party.core.exception.BusinessException;
import com.party.core.model.sign.GroupAuthor;
import com.party.core.model.sign.GroupMember;
import com.party.core.model.sign.SignApply;
import com.party.core.service.sign.ISignApplyService;
import com.party.core.service.sign.ISignGroupService;
import com.party.mobile.biz.currentUser.CurrentUserBizService;
import com.party.mobile.biz.sign.SignGroupBizService;
import com.party.mobile.web.dto.AjaxResult;
import com.party.mobile.web.dto.login.output.CurrentUser;
import com.party.mobile.web.dto.sign.output.SignGroupOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 签到小组控制器
 * Created by wei.li
 *
 * @date 2017/6/8 0008
 * @time 19:14
 */

@Controller
@RequestMapping(value = "sign/group")
public class SignGroupController {

    @Autowired
    private ISignGroupService signGroupService;

    @Autowired
    private SignGroupBizService signGroupBizService;

    @Autowired
    private CurrentUserBizService currentUserBizService;

    /**
     * 小组排行列表
     * @param projectId 项目编号
     * @return 交互数据
     */
    @ResponseBody
    @RequestMapping(value = "list/page3")
    public AjaxResult listPage3(String projectId){
        AjaxResult ajaxResult = new AjaxResult();
        GroupAuthor groupAuthor = new GroupAuthor();
        groupAuthor.setProjectId(projectId);

        Page page = new Page();
        page.setLimit(3);
        List<GroupAuthor> list = signGroupService.groupAuthorList(groupAuthor, page);
        ajaxResult.setData(list);
        ajaxResult.setSuccess(true);
        return ajaxResult;
    }

    /**
     * 小组排行
     * @param projectId 项目编号
     * @param type （默认：全部， 0：今天，1：本周，2：本月，3：一月前）
     * @param page 分页参数
     * @return 交互数据
     */
    @ResponseBody
    @RequestMapping(value = "list")
    public AjaxResult list(String projectId, Integer type, Page page){
        AjaxResult ajaxResult = new AjaxResult();
        List<GroupAuthor> list = signGroupBizService.list(projectId, type, page);
        ajaxResult.setData(list);
        ajaxResult.setSuccess(true);
        return ajaxResult;
    }


    /**
     * 小组排行
     * @param projectId 项目编号
     * @param type （默认：全部， 0：今天，1：本周，2：本月，3：一月前）
     * @param page 分页参数
     * @return 交互数据
     */
    @ResponseBody
    @RequestMapping(value = "rank/list")
    public AjaxResult rankList(String projectId, Integer type, Page page){
        AjaxResult ajaxResult = new AjaxResult();
        List<GroupAuthor> list = signGroupBizService.rankList(projectId, type, page);
        ajaxResult.setData(list);
        ajaxResult.setSuccess(true);
        return ajaxResult;
    }


    /**
     * 签到小组详情
     * @param id 编号
     * @param request 请求参数
     * @return 交互数据
     */
    @ResponseBody
    @RequestMapping(value = "detail")
    public AjaxResult detail(String id, HttpServletRequest request){
        AjaxResult ajaxResult = new AjaxResult();
        CurrentUser currentUser = currentUserBizService.getCurrentUser(request);
        SignGroupOutput signGroupOutput = signGroupBizService.get(id, currentUser);
        ajaxResult.setData(signGroupOutput);
        ajaxResult.setSuccess(true);
        return ajaxResult;
    }

    /**
     * 参加小组
     * @param groupId 小组编号
     * @param request 请求参数
     * @return 交互数据
     */
    @ResponseBody
    @Authorization
    @RequestMapping(value = "join")
    public AjaxResult join(String groupId, HttpServletRequest request){
        AjaxResult ajaxResult = new AjaxResult();
        CurrentUser currentUser = currentUserBizService.getCurrentUser(request);
        try {
            signGroupBizService.join(groupId, currentUser);
        } catch (BusinessException be) {
            ajaxResult.setErrorCode(be.getCode());
            ajaxResult.setDescription(be.getMessage());
            ajaxResult.setSuccess(false);
            return ajaxResult;
        }
        ajaxResult.setSuccess(true);
        return ajaxResult;
    }
}
