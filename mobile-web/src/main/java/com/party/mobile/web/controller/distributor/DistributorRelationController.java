package com.party.mobile.web.controller.distributor;

import com.google.common.collect.Maps;
import com.party.authorization.annotation.Authorization;
import com.party.common.paging.Page;
import com.party.core.exception.BusinessException;
import com.party.mobile.biz.currentUser.CurrentUserBizService;
import com.party.mobile.biz.distributor.DistributorBizService;
import com.party.mobile.biz.member.MemberBizService;
import com.party.mobile.biz.verifyCode.VerifyCodeBizService;
import com.party.mobile.web.dto.AjaxResult;
import com.party.mobile.web.dto.distributor.input.ApplyDistributorInput;
import com.party.mobile.web.dto.distributor.input.DistributorInput;
import com.party.mobile.web.dto.distributor.input.DistributorListInput;
import com.party.mobile.web.dto.distributor.input.GetDistributorInput;
import com.party.mobile.web.dto.distributor.output.DistributorFillOutput;
import com.party.mobile.web.dto.distributor.output.DistributorListOutput;
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
import java.util.List;
import java.util.Map;

/**
 * 分销关系控制层
 * Created by wei.li
 *
 * @date 2017/3/1 0001
 * @time 17:05
 */

@Controller
@RequestMapping(value = "distributor/relation")
public class DistributorRelationController {

    @Autowired
    private DistributorBizService distributorBizService;

    @Autowired
    private CurrentUserBizService currentUserBizService;

    @Autowired
    private VerifyCodeBizService verifyCodeBizService;

    @Autowired
    private MemberBizService memberBizService;

    /**
     * 分销接口
     * @param input 输入视图
     * @param result 验证结果
     * @param request 请求参数
     * @return 交互数据
     */
    @ResponseBody
    @RequestMapping(value = "bind/distribution")
    public AjaxResult bindAndDistribution(@Validated DistributorInput input,
                                        BindingResult result, HttpServletRequest request){

        AjaxResult ajaxResult = new AjaxResult();
        CurrentUser currentUser = currentUserBizService.getCurrentUser(request);
        //数据验证
        if (result.hasErrors()){
            List<ObjectError> allErrors = result.getAllErrors();
            String description = allErrors.get(0).getDefaultMessage();
            ajaxResult.setErrorCode(PartyCode.PARAMETER_ILLEGAL);
            ajaxResult.setDescription(description);
            return ajaxResult;
        }

        //如果当前登录用户是第三方授权用户，且手机号为空，则需要绑定手机号
        if (CurrentUser.isThirdparty(currentUser)){
            //验证码验证
            boolean verifyResult = verifyCodeBizService.verify(input.getMobile(), input.getVerifyCode());
            if (verifyResult){
                MemberOutput memberOutput = memberBizService.bindPhone(input.getMobile(), input.getRealname(),
                        input.getCompany(), input.getTitle(), input.getIndustry(), request);

                currentUser = currentUserBizService.getCurrentUserByToken(memberOutput.getToken());
            }
            else {
                ajaxResult.setDescription("手机验证码错误");
                return ajaxResult;
            }
        }

        Map map = Maps.newHashMap();
        map.put("id", currentUser.getId());
        try {
            distributorBizService.distributor(DistributorInput.transform(input), currentUser, null);
        } catch (BusinessException be) {
            ajaxResult.setData(map);
            ajaxResult.setErrorCode(be.getCode());
            ajaxResult.setDescription(be.getMessage());
            return ajaxResult;
        }

        ajaxResult.setSuccess(true);
        ajaxResult.setData(map);
        return ajaxResult;
    }


    /**
     * 分销接口
     * @param input 输入参数
     * @param result 验证参数
     * @param request 请求参数
     * @return 交互数据
     */
    @ResponseBody
    @RequestMapping(value = "distribution")
    public AjaxResult distribution(@Validated GetDistributorInput input,
                                   BindingResult result, HttpServletRequest request){

        AjaxResult ajaxResult = new AjaxResult();
        CurrentUser currentUser = currentUserBizService.getCurrentUser(request);
        //数据验证
        if (result.hasErrors()){
            List<ObjectError> allErrors = result.getAllErrors();
            String description = allErrors.get(0).getDefaultMessage();
            ajaxResult.setErrorCode(PartyCode.PARAMETER_ILLEGAL);
            ajaxResult.setDescription(description);
            return ajaxResult;
        }
        Map map = Maps.newHashMap();
        map.put("id", currentUser.getId());
        try {
            distributorBizService.distributor(input, currentUser, null);
        } catch (BusinessException be) {
            ajaxResult.setData(map);
            ajaxResult.setErrorCode(be.getCode());
            ajaxResult.setDescription(be.getMessage());
            return ajaxResult;
        }

        ajaxResult.setSuccess(true);
        ajaxResult.setData(map);
        return ajaxResult;
    }


    /**
     * 设置分销数据
     * @param input 输入视图
     * @param result 验证参数
     * @return 交互数据
     */
    @ResponseBody
    @Authorization
    @RequestMapping(value = "setDistributorData")
    public AjaxResult setDistributorData(@Validated ApplyDistributorInput input, BindingResult result, HttpServletRequest request){
        AjaxResult ajaxResult = new AjaxResult();

        //数据验证
        if (result.hasErrors()){
            List<ObjectError> allErrors = result.getAllErrors();
            String description = allErrors.get(0).getDefaultMessage();
            ajaxResult.setErrorCode(PartyCode.PARAMETER_ILLEGAL);
            ajaxResult.setDescription(description);
            return ajaxResult;
        }
        try {
            distributorBizService.setDistributorData(input);
        }catch (BusinessException be){
            ajaxResult.setDescription(be.getMessage());
            return ajaxResult;
        }
        catch (Exception e) {
            ajaxResult.setDescription("系统异常");
            return ajaxResult;
        }
        ajaxResult.setSuccess(true);
        return ajaxResult;
    }


    /**
     * 分销列表
     * @param input 输入视图
     * @param page 分页参数
     * @param request 请求参数
     * @return
     */
    @ResponseBody
    @RequestMapping("list")
    @Authorization
    public AjaxResult list(DistributorListInput input, Page page, HttpServletRequest request){
        AjaxResult ajaxResult = new AjaxResult();
        CurrentUser currentUser = currentUserBizService.getCurrentUser(request);
        List<DistributorListOutput> listOutputs = distributorBizService.list(input, currentUser, page);
        ajaxResult.setData(listOutputs);
        return ajaxResult;
    }


    /**
     * 获取填写页面详情
     * @param id 编号
     * @param type 类型
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getFill")
    public AjaxResult getFillDetail(String id, Integer type){
        DistributorFillOutput distributorFillOutput = distributorBizService.getFill(id, type);
        return AjaxResult.success(distributorFillOutput);
    }
}