package com.party.mobile.web.controller.crowdfund;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.party.authorization.annotation.Authorization;
import com.party.common.paging.Page;
import com.party.common.utils.PartyCode;
import com.party.core.exception.BusinessException;
import com.party.core.model.distributor.WithActivity;
import com.party.core.model.member.Member;
import com.party.core.service.member.IMemberService;
import com.party.mobile.biz.crowdfund.RepresentBizService;
import com.party.mobile.biz.currentUser.CurrentUserBizService;
import com.party.mobile.biz.member.MemberBizService;
import com.party.mobile.biz.verifyCode.VerifyCodeBizService;
import com.party.mobile.web.dto.AjaxResult;
import com.party.mobile.web.dto.crowdfund.output.GetRepresentOutput;
import com.party.mobile.web.dto.crowdfund.output.ListForTargetOutput;
import com.party.mobile.web.dto.distributor.input.GetDistributorInput;
import com.party.mobile.web.dto.login.output.CurrentUser;
import com.party.mobile.web.dto.login.output.MemberOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 代言控制器
 * Created by wei.li
 *
 * @date 2017/3/2 0002
 * @time 14:02
 */

@Controller
@RequestMapping(value = "crowdfund/represent")
public class RepresentController {

    @Autowired
    private RepresentBizService representBizService;

    @Autowired
    private CurrentUserBizService currentUserBizService;

    @Autowired
    private VerifyCodeBizService verifyCodeBizService;

    @Autowired
    private MemberBizService memberBizService;

    @Autowired
    private IMemberService memberService;
    /**
     * 代言
     * @param input 输入参数
     * @param result 验证参数
     * @param request 请求参数
     * @return 交互数据
     */
    @ResponseBody
    @Authorization
    @RequestMapping(value = "represent")
    public AjaxResult represent(@Validated GetDistributorInput input, BindingResult result, HttpServletRequest request){
        AjaxResult ajaxResult = new AjaxResult();

        if (result.hasErrors()){
            List<ObjectError> allErrors = result.getAllErrors();
            String description = allErrors.get(0).getDefaultMessage();
            ajaxResult.setErrorCode(PartyCode.PARAMETER_ILLEGAL);
            ajaxResult.setDescription(description);
            return ajaxResult;
        }
        CurrentUser currentUser = currentUserBizService.getCurrentUser(request);

        try {
            if(!Strings.isNullOrEmpty(input.getMobile()) && !Strings.isNullOrEmpty(input.getVerifyCode())) {
                //验证码验证
                boolean verifyResult = verifyCodeBizService.verify(input.getMobile(), input.getVerifyCode());
                if (verifyResult) {
                    //绑定手机号
                    Map<String, Object> map = memberBizService.bindPhoneNew(input.getMobile(), request);
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
        Member curMember = memberBizService.updateImportantInfo(GetDistributorInput.transformMember(input), memberService.get(currentUser.getId()));
        MemberOutput memberOutput = memberBizService.getLoginMember(curMember);


        Map map = Maps.newHashMap();
        map.put("id", currentUser.getId());
        map.put("member",memberOutput);
        try {
              representBizService.represent(input, currentUser);
        }
        catch (BusinessException be){
            ajaxResult.setErrorCode(be.getCode());
            ajaxResult.setDescription(be.getMessage());
            ajaxResult.setData(map);
            return ajaxResult;
        }
        catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setDescription("系统异常");
            return ajaxResult;
        }
        ajaxResult.setSuccess(true);
        ajaxResult.setData(map);
        return ajaxResult;
    }

    /**
     * 获取代言信息
     * @param input 输入参数
     * @param result 验证参数
     * @param request 请求参数
     * @return 交互数据
     */
    @ResponseBody
    @Authorization
    @RequestMapping(value = "getRepresent")
    public AjaxResult getRepresent(@Validated GetDistributorInput input, BindingResult result, HttpServletRequest request){
        AjaxResult ajaxResult = new AjaxResult();

        if (result.hasErrors()){
            List<ObjectError> allErrors = result.getAllErrors();
            String description = allErrors.get(0).getDefaultMessage();
            ajaxResult.setErrorCode(PartyCode.PARAMETER_ILLEGAL);
            ajaxResult.setDescription(description);
            return ajaxResult;
        }

        CurrentUser currentUser = currentUserBizService.getCurrentUser(request);
        GetRepresentOutput getRepresentOutput;
        try {
            getRepresentOutput = representBizService.getRepresent(input, currentUser);
        }
        catch (BusinessException be){
            ajaxResult.setDescription(be.getMessage());
            return ajaxResult;
        }
        catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setDescription("系统异常");
            return ajaxResult;
        }

        ajaxResult.setSuccess(true);
        ajaxResult.setData(getRepresentOutput);
        return ajaxResult;
    }

    /**
     * 代言列表
     * @param page 分页参数
     * @param status 状态(0:进行中, 1:已经截止)
     * @param request 请求参数
     * @return 代言列表
     */
    @ResponseBody
    @Authorization
    @RequestMapping(value = "list")
    public AjaxResult list(Page page,Integer status, HttpServletRequest request){
        AjaxResult ajaxResult = new AjaxResult();
        CurrentUser currentUser = currentUserBizService.getCurrentUser(request);
        List<WithActivity> withActivityList;
        try {
            withActivityList = representBizService.list(page, status, currentUser);
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setDescription("系统异常");
            return ajaxResult;
        }
        ajaxResult.setSuccess(true);
        ajaxResult.setData(withActivityList);
        return ajaxResult;
    }


    /**
     * 项目下代言
     * @param id 项目编号
     * @param page 分页参数
     * @return 代言列表
     */
    @ResponseBody
    @RequestMapping(value = "listForTarget")
    public AjaxResult listForTarget(String id, Page page){
        AjaxResult ajaxResult = new AjaxResult();

        List<ListForTargetOutput> listForTargetOutputList;
        try {
            listForTargetOutputList = representBizService.listForTarget(page, id);
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setDescription("系统异常");
            return ajaxResult;
        }
        ajaxResult.setSuccess(true);
        ajaxResult.setData(listForTargetOutputList);
        return ajaxResult;
    }


    /**
     * 编辑代言
     * @param id 代言编号
     * @param declaration 代言宣言
     * @return 交互数据
     */
    @ResponseBody
    @RequestMapping(value = "edite")
    public AjaxResult edite(String id, String declaration, String style){
        AjaxResult ajaxResult = new AjaxResult();
        try {
            representBizService.edite(id, declaration, style);
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setDescription("服务器异常");
            return ajaxResult;
        }
        ajaxResult.setSuccess(true);
        return ajaxResult;
    }
}
