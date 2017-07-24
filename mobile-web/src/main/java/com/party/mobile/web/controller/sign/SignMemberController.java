package com.party.mobile.web.controller.sign;

import com.party.authorization.annotation.Authorization;
import com.party.common.paging.Page;
import com.party.common.utils.PartyCode;
import com.party.core.exception.BusinessException;
import com.party.core.model.sign.GroupMember;
import com.party.core.model.sign.SignApplyStatus;
import com.party.core.model.sign.SignGradeStatus;
import com.party.core.service.sign.ISignApplyService;
import com.party.mobile.biz.currentUser.CurrentUserBizService;
import com.party.mobile.biz.member.MemberBizService;
import com.party.mobile.biz.sign.SignMemberBizService;
import com.party.mobile.biz.verifyCode.VerifyCodeBizService;
import com.party.mobile.web.dto.AjaxResult;
import com.party.mobile.web.dto.login.output.CurrentUser;
import com.party.mobile.web.dto.login.output.MemberOutput;
import com.party.mobile.web.dto.sign.input.SignApplyInput;
import com.party.mobile.web.dto.sign.output.MyRankOutput;
import com.party.mobile.web.dto.sign.output.SignApplyOutput;
import com.party.mobile.web.dto.sign.output.SignMemberOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 个人签到控制器
 * Created by wei.li
 *
 * @date 2017/6/9 0009
 * @time 11:56
 */

@Controller
@RequestMapping(value = "sign/member")
public class SignMemberController {

    @Autowired
    private ISignApplyService signApplyService;

    @Autowired
    private CurrentUserBizService currentUserBizService;

    @Autowired
    private SignMemberBizService signMemberBizService;

    @Autowired
    private VerifyCodeBizService verifyCodeBizService;

    @Autowired
    private MemberBizService memberBizService;

    /**
     * 个人排行榜
     * @param projectId 项目编号
     * @param type （默认：全部， 0：今天，1：本周，2：本月，3：一月前）
     * @param page 分页参数
     * @return 交互数据
     */
    @ResponseBody
    @RequestMapping(value = "project/list")
    public AjaxResult listForProject(String projectId, Integer type, Page page){
        AjaxResult ajaxResult = new AjaxResult();
        List<GroupMember> list = signMemberBizService.list(projectId, type, page);
        ajaxResult.setSuccess(true);
        ajaxResult.setData(list);
        return ajaxResult;
    }


    /**
     * 个人排行榜
     * @param projectId 项目编号
     * @param type （默认：全部， 0：今天，1：本周，2：本月，3：一月前）
     * @param page 分页参数
     * @return 交互数据
     */
    @ResponseBody
    @RequestMapping(value = "rank/list")
    public AjaxResult rankList(String projectId, Integer type, Page page){
        AjaxResult ajaxResult = new AjaxResult();
        List<GroupMember> list = signMemberBizService.rankList(projectId, type, page);
        ajaxResult.setSuccess(true);
        ajaxResult.setData(list);
        return ajaxResult;
    }


    /**
     * 我的排行
     * @param projectId 项目编号
     * @param request 请求参数
     * @return 交互数据
     */
    @ResponseBody
    @RequestMapping(value = "project/myRank")
    public AjaxResult myRankForProject(String projectId, HttpServletRequest request){
        AjaxResult ajaxResult = new AjaxResult();
        CurrentUser currentUser = currentUserBizService.getCurrentUser(request);
        MyRankOutput myRankOutput = signMemberBizService.myProjectRank(projectId, currentUser);
        ajaxResult.setSuccess(true);
        ajaxResult.setData(myRankOutput);
        return ajaxResult;
    }


    /**
     * 我的排行
     * @param projectId 项目编号
     * @param type （默认：全部， 0：今天，1：本周，2：本月，3：一月前）
     * @param request 请求参数
     * @return 交互数据
     */
    @ResponseBody
    @RequestMapping(value = "project/rankRecord")
    public AjaxResult projectRankRecord(String projectId, Integer type, HttpServletRequest request){
        AjaxResult ajaxResult = new AjaxResult();
        CurrentUser currentUser = currentUserBizService.getCurrentUser(request);
        MyRankOutput myRankOutput = signMemberBizService.projectRankRecord(projectId, type, currentUser);
        ajaxResult.setSuccess(true);
        ajaxResult.setData(myRankOutput);
        return ajaxResult;
    }


    /**
     * 个人小组排行榜
     * @param groupId 小组编号
     * @param page 分页参数
     * @return 交互数据
     */
    @ResponseBody
    @RequestMapping(value = "group/list")
    public AjaxResult listForGroup(String groupId, Page page){
        AjaxResult ajaxResult = new AjaxResult();
        GroupMember groupMember = new GroupMember();
        groupMember.setGroupId(groupId);
        groupMember.setStatus(SignApplyStatus.PASS_STATUS.getCode());
        groupMember.setGradeStatus(SignGradeStatus.EFFECTIVE.getCode());
        List<GroupMember> list = signApplyService.groupMemberList(groupMember, page);
        ajaxResult.setSuccess(true);
        ajaxResult.setData(list);
        return ajaxResult;
    }


    /**
     * 我的小组排行
     * @param groupId 小组编号
     * @param request 请求参数
     * @return 交互数据
     */
    @ResponseBody
    @RequestMapping(value = "group/myRank")
    public AjaxResult myRankForGroup(String groupId, HttpServletRequest request){
        AjaxResult ajaxResult = new AjaxResult();
        CurrentUser currentUser = currentUserBizService.getCurrentUser(request);
        MyRankOutput myRankOutput = signMemberBizService.myGroupRank(groupId, currentUser);
        ajaxResult.setData(myRankOutput);
        ajaxResult.setSuccess(true);
        return ajaxResult;
    }


    /**
     * 我的小组排行
     * @param groupId 小组编号
     * @param type （默认：全部， 0：今天，1：本周，2：本月，3：一月前）
     * @param request 请求参数
     * @return 交互数据
     */
    @ResponseBody
    @RequestMapping(value = "group/rankRecord")
    public AjaxResult groupRankRecord(String groupId, Integer type, HttpServletRequest request){
        AjaxResult ajaxResult = new AjaxResult();
        CurrentUser currentUser = currentUserBizService.getCurrentUser(request);
        MyRankOutput myRankOutput = signMemberBizService.groupRankRecord(groupId, type, currentUser);
        ajaxResult.setData(myRankOutput);
        ajaxResult.setSuccess(true);
        return ajaxResult;
    }

    /**
     * 签到报名信息
     * @param id 报名编号
     * @return 交互数据
     */
    @ResponseBody
    @RequestMapping(value = "detail")
    public AjaxResult detail(String id, HttpServletRequest request){
        AjaxResult ajaxResult = new AjaxResult();
        CurrentUser currentUser = currentUserBizService.getCurrentUser(request);
        SignMemberOutput signMemberOutput;
        try {
            signMemberOutput = signMemberBizService.detail(id, currentUser);
        } catch (BusinessException be) {
            ajaxResult.setSuccess(false);
            ajaxResult.setDescription(be.getMessage());
            return ajaxResult;
        }
        ajaxResult.setSuccess(true);
        ajaxResult.setData(signMemberOutput);
        return ajaxResult;
    }


    /**
     * 签到项目报名
     * @param applyInput 输入参数
     * @param result 验证结果
     * @param request 请求参数
     * @return 交互数据
     */
    @ResponseBody
    @Authorization
    @RequestMapping(value = "apply")
    public AjaxResult apply(@Validated SignApplyInput applyInput, BindingResult result, HttpServletRequest request){

        AjaxResult ajaxResult = new AjaxResult();
        //数据验证
        if (result.hasErrors()){
            List<ObjectError> allErrors = result.getAllErrors();
            String description = allErrors.get(0).getDefaultMessage();
            ajaxResult.setErrorCode(PartyCode.PARAMETER_ILLEGAL);
            ajaxResult.setDescription(description);
            return ajaxResult;
        }

        SignApplyOutput signApplyOutput = new SignApplyOutput();
        CurrentUser currentUser = currentUserBizService.getCurrentUser(request);
        if (CurrentUser.isThirdparty(currentUser)){
            boolean verifyResult = verifyCodeBizService.verify(applyInput.getMobile(), applyInput.getVerifyCode());
            if (verifyResult){
                //同步用户
                MemberOutput memberOutput = memberBizService.bindPhone(applyInput.getMobile(), applyInput.getRealname(),
                        applyInput.getCompany(), applyInput.getTitle(), applyInput.getIndustry(), request);

                currentUser = currentUserBizService.getCurrentUserByToken(memberOutput.getToken());
                signApplyOutput.setMemberOutput(memberOutput);
            }
            else {
                ajaxResult.setSuccess(false);
                ajaxResult.setDescription("手机验证码错误");
                return ajaxResult;
            }
        }

        //报名
        try {
            String id = signMemberBizService.apply(applyInput.getId(), applyInput.getGroupId(), currentUser);
            signApplyOutput.setApplyId(id);
            ajaxResult.setData(signApplyOutput);
            ajaxResult.setSuccess(true);
            return ajaxResult;
        } catch (Exception b) {
            ajaxResult.setSuccess(false);
            ajaxResult.setDescription("系统异常");
            return ajaxResult;
        }
    }
}
