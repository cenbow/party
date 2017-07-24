package com.party.mobile.web.controller.crowdfund;

import com.google.common.base.Strings;
import com.party.authorization.annotation.Authorization;
import com.party.core.exception.BusinessException;
import com.party.core.model.activity.Activity;
import com.party.core.model.member.Member;
import com.party.core.service.activity.IActivityService;
import com.party.core.service.member.IMemberService;
import com.party.mobile.biz.crowdfund.CrowdfundActivityBizService;
import com.party.mobile.biz.currentUser.CurrentUserBizService;
import com.party.mobile.biz.member.MemberBizService;
import com.party.mobile.biz.verifyCode.VerifyCodeBizService;
import com.party.mobile.web.dto.AjaxResult;
import com.party.mobile.web.dto.crowdfund.input.ApplyInput;
import com.party.mobile.web.dto.crowdfund.input.StatisticsInput;
import com.party.mobile.web.dto.crowdfund.output.ActivityDetailOutput;
import com.party.mobile.web.dto.crowdfund.output.ActivityGetOutput;
import com.party.mobile.web.dto.login.output.CurrentUser;
import com.party.mobile.web.dto.login.output.MemberOutput;
import com.party.common.utils.PartyCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 众筹活动控制器
 * Created by wei.li
 *
 * @date 2017/2/24 0024
 * @time 17:24
 */

@Controller
@RequestMapping(value = "crowdfund/activity")
public class CrowdfundActivityController {

    @Autowired
    private CrowdfundActivityBizService crowdfundActivityBizService;

    @Autowired
    private CurrentUserBizService currentUserBizService;

    @Autowired
    private VerifyCodeBizService verifyCodeBizService;

    @Autowired
    private MemberBizService memberBizService;

    @Autowired
    private IActivityService activityService;

    @Autowired
    private IMemberService memberService;

    /**
     * 众筹项目详情
     * @param id 编号
     * @return 交互数据
     */
    @ResponseBody
    @RequestMapping(value = "detail")
    public AjaxResult detail(String id, HttpServletRequest request){
        AjaxResult ajaxResult = new AjaxResult();
        if (Strings.isNullOrEmpty(id)){
            ajaxResult.setErrorCode(PartyCode.PARAMETER_ILLEGAL);
            ajaxResult.setDescription("活动编号不能为空");
            return ajaxResult;
        }
        CurrentUser currentUser = currentUserBizService.getCurrentUser(request);
        ActivityDetailOutput activityDetailOutput = null;
        try {
            activityDetailOutput = crowdfundActivityBizService.detail(id, currentUser);
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setDescription("系统异常");
            return ajaxResult;
        }
        ajaxResult.setSuccess(true);
        ajaxResult.setData(activityDetailOutput);
        return ajaxResult;
    }


    /**
     * 获取活动
     * @param id 编号
     * @return 输出视图
     */
    @ResponseBody
    @RequestMapping(value = "get")
    public AjaxResult get(String id){
        AjaxResult ajaxResult = new AjaxResult();
        if (Strings.isNullOrEmpty(id)){
            ajaxResult.setErrorCode(PartyCode.PARAMETER_ILLEGAL);
            ajaxResult.setDescription("活动编号不能为空");
            return ajaxResult;
        }

        Activity activity = activityService.get(id);
        ActivityGetOutput activityGetOutput = ActivityGetOutput.transform(activity);
        ajaxResult.setSuccess(true);
        ajaxResult.setData(activityGetOutput);
        return ajaxResult;
    }

    /**
     *
     * @param applyInput
     * @param result
     * @param request
     * @return
     */
    @Authorization
    @ResponseBody
    @RequestMapping(value = "apply")
    public AjaxResult apply(@Validated ApplyInput applyInput, BindingResult result, HttpServletRequest request){
        AjaxResult ajaxResult = new AjaxResult();
        //数据验证
        if (result.hasErrors()){
            List<ObjectError> allErrors = result.getAllErrors();
            String description = allErrors.get(0).getDefaultMessage();
            return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, description);
        }

        CurrentUser currentUser = currentUserBizService.getCurrentUser(request);

//        //如果当前登录用户是第三方授权用户，且手机号为空，则需要绑定手机号
//        if (CurrentUser.isThirdparty(currentUser)){
//            //验证码验证
//            boolean verifyResult = verifyCodeBizService.verify(applyInput.getMobile(), applyInput.getVerifyCode());
//            if (verifyResult){
//                MemberOutput memberOutput = memberBizService.bindPhone(applyInput.getMobile(), applyInput.getRealname(),
//                        applyInput.getCompany(), applyInput.getTitle(), applyInput.getIndustry(), request);
//
//                currentUser = currentUserBizService.getCurrentUserByToken(memberOutput.getToken());
//            }
//        }
        try {
            if(!Strings.isNullOrEmpty(applyInput.getMobile()) && !Strings.isNullOrEmpty(applyInput.getVerifyCode())) {
                //验证码验证
                boolean verifyResult = verifyCodeBizService.verify(applyInput.getMobile(), applyInput.getVerifyCode());
                if (verifyResult) {
                    //绑定手机号
                    Map<String, Object> map = memberBizService.bindPhoneNew(applyInput.getMobile(), request);
                    //如果当前用户有合并操作需重新赋值
                    if(null != map.get("memberId") && !map.get("memberId").equals(currentUser.getId())){
                        currentUser = currentUserBizService.getCurrentUserByToken((String) map.get("token"));
                    }
                }
            }
        }catch (BusinessException be){
            return  AjaxResult.error(be.getCode(),be.getMessage());
        }
        //如果字段为空 更新必要的用户信息
        Member curMember = memberBizService.updateImportantInfo(ApplyInput.transformMember(applyInput), memberService.get(currentUser.getId()));
        MemberOutput memberOutput = memberBizService.getLoginMember(curMember);

        //报名
        HashMap map;
        try {
            map = crowdfundActivityBizService.apply(applyInput, currentUser.getId());
            map.put("member",memberOutput);
        } catch (BusinessException be) {
            ajaxResult.setDescription(be.getMessage());
            return ajaxResult;
        }
        ajaxResult.setSuccess(true);
        ajaxResult.setData(map);
        return ajaxResult;
    }

}
