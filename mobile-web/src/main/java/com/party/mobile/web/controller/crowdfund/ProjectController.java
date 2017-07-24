package com.party.mobile.web.controller.crowdfund;

import com.google.common.base.Strings;
import com.party.authorization.annotation.Authorization;
import com.party.common.paging.Page;
import com.party.core.exception.BusinessException;
import com.party.core.service.crowdfund.IProjectService;
import com.party.mobile.biz.crowdfund.ProjectBizService;
import com.party.mobile.biz.currentUser.CurrentUserBizService;
import com.party.mobile.web.dto.AjaxResult;
import com.party.mobile.web.dto.crowdfund.input.SupportInput;
import com.party.mobile.web.dto.crowdfund.output.*;
import com.party.mobile.web.dto.login.output.CurrentUser;
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

/**
 * 众筹控制器
 * Created by wei.li
 *
 * @date 2017/2/17 0017
 * @time 9:43
 */

@Controller
@RequestMapping(value = "crowdfund/project")
public class ProjectController {

    @Autowired
    private ProjectBizService projectBizService;

    @Autowired
    private CurrentUserBizService currentUserBizService;

    @Autowired
    private IProjectService projectService;

    /**
     * 项目列表
     * @param type 列表类型（0:我发布的 1:我支持的）
     * @param page 分页参数
     * @param isSuccess 众筹状态
     * @return
     */
    @Authorization
    @ResponseBody
    @RequestMapping(value = "list")
    public AjaxResult list(Integer type,Integer isSuccess, Page page, HttpServletRequest request){
        AjaxResult ajaxResult = new AjaxResult();
        CurrentUser currentUser = currentUserBizService.getCurrentUser(request);
        List<ProjectListOutput> projectListOutputList = null;
        try {
            projectListOutputList = projectBizService.list(type, isSuccess, page, currentUser);
        } catch (Exception e) {
            ajaxResult.setDescription("系统异常");
            return ajaxResult;
        }
        ajaxResult.setSuccess(true);
        ajaxResult.setData(projectListOutputList);
        return ajaxResult;
    }


    /**
     * 获取众筹项目详情
     * @param id 项目编号
     * @return 项目详情
     */
    @Authorization
    @ResponseBody
    @RequestMapping(value = "getDetails")
    public AjaxResult getDetails(String id, HttpServletRequest request){
        AjaxResult ajaxResult = new AjaxResult();
        //验参
        if (Strings.isNullOrEmpty(id)){
            ajaxResult.setErrorCode(PartyCode.PARAMETER_ILLEGAL);
            ajaxResult.setDescription("众筹项目编号不能为空");
            return ajaxResult;
        }

        CurrentUser currentUser = currentUserBizService.getCurrentUser(request);
        ProjectOutput projectOutput = null;
        try {
            projectOutput = projectBizService.getDetails(id, currentUser);
        } catch (BusinessException be) {
            ajaxResult.setErrorCode(be.getCode());
            ajaxResult.setDescription(be.getMessage());
            return ajaxResult;
        }
        return AjaxResult.success(projectOutput);
    }

    /**
     * 众筹支持
     * @param supportInput 输入视图
     * @param result 参数验证
     * @return 支持结果
     */
    @Authorization
    @ResponseBody
    @RequestMapping(value = "support")
    public AjaxResult support(@Validated SupportInput supportInput, BindingResult result, HttpServletRequest request){
        //数据验证
        AjaxResult ajaxResult = new AjaxResult();
        if (result.hasErrors()){
            List<ObjectError> allErrors = result.getAllErrors();
            String description = allErrors.get(0).getDefaultMessage();
            ajaxResult.setErrorCode(PartyCode.PARAMETER_ILLEGAL);
            ajaxResult.setDescription(description);
            return ajaxResult;
        }
        CurrentUser currentUser = currentUserBizService.getCurrentUser(request);
        String orderId = null;
        try {
            orderId = projectBizService.support(supportInput, currentUser);
        }catch (BusinessException be){
            ajaxResult.setErrorCode(be.getCode());
            ajaxResult.setDescription(be.getMessage());
            return ajaxResult;
        }
        catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setDescription("系统异常");
            return ajaxResult;
        }
        ajaxResult.setSuccess(true);
        ajaxResult.setData(new SupportOrderOutput(orderId));
        return ajaxResult;
    }


    /**
     * 获取剩余资金
     * @param id 众筹编号
     * @return 剩余资金
     */
    @ResponseBody
    @RequestMapping(value = "getMaxAmount")
    public AjaxResult getMaxAmount(String id){
        AjaxResult ajaxResult = new AjaxResult();
        Float amount = projectService.getMaxAmount(id);
        ajaxResult.setSuccess(true);
        ajaxResult.setData(amount);
        return ajaxResult;
    }

    /**
     * 众筹中的众筹
     * @param id 项目编号
     * @param page 分页参数
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "crowdfunding")
    public AjaxResult crowdfunding(String id, Page page){
        AjaxResult ajaxResult = new AjaxResult();
        if (Strings.isNullOrEmpty(id)){
            ajaxResult.setErrorCode(PartyCode.PARAMETER_ILLEGAL);
            ajaxResult.setDescription("众筹项目编号不能为空");
            return ajaxResult;
        }
        List<ProjectForActivityOutput> projectForActivityOutputList = null;
        try {
            projectForActivityOutputList = projectBizService.projectForActivityList(false, id, page);
        } catch (Exception e) {
            ajaxResult.setDescription("系统异常");
            return ajaxResult;
        }
        ajaxResult.setSuccess(true);
        ajaxResult.setData(projectForActivityOutputList);
        return ajaxResult;
    }

    /**
     * 众筹成功的众筹
     * @param id 项目编号
     * @param page 分页参数
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "crowdfunded")
    public AjaxResult crowdfunded(String id, Page page){
        AjaxResult ajaxResult = new AjaxResult();
        if (Strings.isNullOrEmpty(id)){
            ajaxResult.setErrorCode(PartyCode.PARAMETER_ILLEGAL);
            ajaxResult.setDescription("众筹项目编号不能为空");
            return ajaxResult;
        }
        List<ProjectForActivityOutput> projectForActivityOutputList = null;
        try {
            projectForActivityOutputList = projectBizService.projectForActivityList(true, id, page);
        } catch (Exception e) {
            ajaxResult.setDescription("系统异常");
            return ajaxResult;
        }
        ajaxResult.setSuccess(true);
        ajaxResult.setData(projectForActivityOutputList);
        return ajaxResult;
    }

    /**
     * 众筹中的众筹
     * @param id 项目编号
     * @param page 分页参数
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "crowdfundingForBrother")
    public AjaxResult crowdfundingForBrother(String id, Page page){
        AjaxResult ajaxResult = new AjaxResult();
        if (Strings.isNullOrEmpty(id)){
            ajaxResult.setErrorCode(PartyCode.PARAMETER_ILLEGAL);
            ajaxResult.setDescription("众筹项目编号不能为空");
            return ajaxResult;
        }
        List<ProjectForActivityOutput> projectForActivityOutputList = null;
        try {
            projectForActivityOutputList = projectBizService.projectForBrotherList(false, id, page);
        } catch (Exception e) {
            ajaxResult.setDescription("系统异常");
            return ajaxResult;
        }
        ajaxResult.setSuccess(true);
        ajaxResult.setData(projectForActivityOutputList);
        return ajaxResult;
    }

    /**
     * 众筹成功的众筹
     * @param id 项目编号
     * @param page 分页参数
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "crowdfundedForBrother")
    public AjaxResult crowdfundedForBrother(String id, Page page){
        AjaxResult ajaxResult = new AjaxResult();
        if (Strings.isNullOrEmpty(id)){
            ajaxResult.setErrorCode(PartyCode.PARAMETER_ILLEGAL);
            ajaxResult.setDescription("众筹项目编号不能为空");
            return ajaxResult;
        }
        List<ProjectForActivityOutput> projectForActivityOutputList = null;
        try {
            projectForActivityOutputList = projectBizService.projectForBrotherList(true, id, page);
        } catch (Exception e) {
            ajaxResult.setDescription("系统异常");
            return ajaxResult;
        }
        ajaxResult.setSuccess(true);
        ajaxResult.setData(projectForActivityOutputList);
        return ajaxResult;
    }


    /**
     * 所属活动事件所有众筹（众筹中）
     * @param id 活动编号
     * @param page 分页参数
     * @return 交互数据
     */
    @ResponseBody
    @RequestMapping(value = "crowdfundingForAll")
    public AjaxResult crowdfundingForAll(String id, Page page){
        AjaxResult ajaxResult = new AjaxResult();
        if (Strings.isNullOrEmpty(id)){
            ajaxResult.setErrorCode(PartyCode.PARAMETER_ILLEGAL);
            ajaxResult.setDescription("众筹项目编号不能为空");
            return ajaxResult;
        }
        List<ProjectForActivityOutput> projectForActivityOutputList = null;
        try {
            projectForActivityOutputList = projectBizService.projectForAllList(false, id, page);
        } catch (Exception e) {
            ajaxResult.setDescription("系统异常");
            return ajaxResult;
        }
        ajaxResult.setSuccess(true);
        ajaxResult.setData(projectForActivityOutputList);
        return ajaxResult;
    }

    /**
     * 所属活动事件所有众筹(已经筹满)
     * @param id 活动编号
     * @param page 分页参数
     * @return 交互数据
     */
    @ResponseBody
    @RequestMapping(value = "crowdfundedForAll")
    public AjaxResult crowdfundedForAll(String id, Page page){
        AjaxResult ajaxResult = new AjaxResult();
        if (Strings.isNullOrEmpty(id)){
            ajaxResult.setErrorCode(PartyCode.PARAMETER_ILLEGAL);
            ajaxResult.setDescription("众筹项目编号不能为空");
            return ajaxResult;
        }
        List<ProjectForActivityOutput> projectForActivityOutputList = null;
        try {
            projectForActivityOutputList = projectBizService.projectForAllList(true, id, page);
        } catch (Exception e) {
            ajaxResult.setDescription("系统异常");
            return ajaxResult;
        }
        ajaxResult.setSuccess(true);
        ajaxResult.setData(projectForActivityOutputList);
        return ajaxResult;
    }

    /**
     * 众筹中的众筹
     * @param id 项目编号
     * @param page 分页参数
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "representCrowdfunding")
    public AjaxResult representCrowdfunding(String id, Page page){
        AjaxResult ajaxResult = new AjaxResult();
        if (Strings.isNullOrEmpty(id)){
            ajaxResult.setErrorCode(PartyCode.PARAMETER_ILLEGAL);
            ajaxResult.setDescription("众筹项目编号不能为空");
            return ajaxResult;
        }
        List<ProjectForActivityOutput> projectForActivityOutputList = null;
        try {
            projectForActivityOutputList = projectBizService.projectForRepresentList(false, id, page);
        } catch (Exception e) {
            ajaxResult.setDescription("系统异常");
            return ajaxResult;
        }
        ajaxResult.setSuccess(true);
        ajaxResult.setData(projectForActivityOutputList);
        return ajaxResult;
    }

    /**
     * 众筹成功的众筹
     * @param id 项目编号
     * @param page 分页参数
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "represencrowdfunded")
    public AjaxResult represencrowdfunded(String id, Page page){
        AjaxResult ajaxResult = new AjaxResult();
        if (Strings.isNullOrEmpty(id)){
            ajaxResult.setErrorCode(PartyCode.PARAMETER_ILLEGAL);
            ajaxResult.setDescription("众筹项目编号不能为空");
            return ajaxResult;
        }
        List<ProjectForActivityOutput> projectForActivityOutputList = null;
        try {
            projectForActivityOutputList = projectBizService.projectForRepresentList(true, id, page);
        } catch (Exception e) {
            ajaxResult.setDescription("系统异常");
            return ajaxResult;
        }
        ajaxResult.setSuccess(true);
        ajaxResult.setData(projectForActivityOutputList);
        return ajaxResult;
    }


    /**
     * 支持我的众筹中
     * @param id 众筹编号
     * @param page 分页参数
     * @return 众筹列表
     */
    @ResponseBody
    @RequestMapping(value = "supportCrowdfunding")
    public AjaxResult supportCrowdfunding(String id, Page page){
        AjaxResult ajaxResult = new AjaxResult();
        if (Strings.isNullOrEmpty(id)){
            ajaxResult.setErrorCode(PartyCode.PARAMETER_ILLEGAL);
            ajaxResult.setDescription("众筹编号不能为空");
            return ajaxResult;
        }

        List<ProjectForActivityOutput> projectForActivityOutputList;
        try {
            projectForActivityOutputList = projectBizService.supportProjectList(false, id, page);
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setDescription("系统异常");
            return ajaxResult;
        }
        ajaxResult.setSuccess(true);
        ajaxResult.setData(projectForActivityOutputList);
        return ajaxResult;
    }

    /**
     * 支持我的已经筹满
     * @param id 众筹编号
     * @param page 分页参数
     * @return 众筹列表
     */
    @ResponseBody
    @RequestMapping(value = "supportCrowdfunded")
    public AjaxResult supportCrowdfunded(String id, Page page){
        AjaxResult ajaxResult = new AjaxResult();
        if (Strings.isNullOrEmpty(id)){
            ajaxResult.setErrorCode(PartyCode.PARAMETER_ILLEGAL);
            ajaxResult.setDescription("众筹编号不能为空");
            return ajaxResult;
        }

        List<ProjectForActivityOutput> projectForActivityOutputList;
        try {
            projectForActivityOutputList = projectBizService.supportProjectList(true, id, page);
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setDescription("系统异常");
            return ajaxResult;
        }
        ajaxResult.setSuccess(true);
        ajaxResult.setData(projectForActivityOutputList);
        return ajaxResult;
    }

    /**
     * 支持者的所有众筹(众筹中和已筹满)
     * @param id 众筹编号
     * @param page 分页参数
     * @return 交互数据
     */
    @ResponseBody
    @RequestMapping(value = "supportCrowdfundAll")
    public AjaxResult supportCrowdfundAll(String id, Page page){
        AjaxResult ajaxResult = new AjaxResult();
        if (Strings.isNullOrEmpty(id)){
            ajaxResult.setErrorCode(PartyCode.PARAMETER_ILLEGAL);
            ajaxResult.setDescription("众筹编号不能为空");
            return ajaxResult;
        }

        List<ProjectForActivityOutput> projectForActivityOutputList;
        try {
            projectForActivityOutputList = projectBizService.supportProjectList(id, page);
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setDescription("系统异常");
            return ajaxResult;
        }
        ajaxResult.setSuccess(true);
        ajaxResult.setData(projectForActivityOutputList);
        return ajaxResult;
    }

    /**
     * 获取众筹项目最大支持金额
     * @param id 项目编号
     * @return 支持金额
     */
    @ResponseBody
    @RequestMapping(value = "getSupportInit")
    public AjaxResult getSupportInit(String id){
        AjaxResult ajaxResult = new AjaxResult();
        //验参
        if (Strings.isNullOrEmpty(id)){
            ajaxResult.setErrorCode(PartyCode.PARAMETER_ILLEGAL);
            ajaxResult.setDescription("众筹项目编号不能为空");
            return ajaxResult;
        }

        GetSupportInitOutput getSupportInitOutput;
        try {
            getSupportInitOutput = projectBizService.getSupportInit(id);
        } catch (BusinessException be) {
            ajaxResult.setDescription(be.getMessage());
            return ajaxResult;
        }
        ajaxResult.setSuccess(true);
        ajaxResult.setData(getSupportInitOutput);
        return ajaxResult;
    }


    /**
     * 编辑众筹项目
     * @param id 众筹编号
     * @param declaration 众筹宣言
     * @return 加护数据
     */
    @ResponseBody
    @RequestMapping(value = "edite")
    public AjaxResult edite(String id, String declaration, String style){
        AjaxResult ajaxResult = new AjaxResult();
        try {
            projectBizService.edite(id, declaration, style);
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setDescription("服务器异常");
            return ajaxResult;
        }
        ajaxResult.setSuccess(true);
        return ajaxResult;
    }
}
