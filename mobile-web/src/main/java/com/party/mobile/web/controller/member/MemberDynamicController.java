package com.party.mobile.web.controller.member;

import com.google.common.base.Strings;
import com.party.authorization.annotation.Authorization;
import com.party.common.paging.Page;
import com.party.core.exception.BusinessException;
import com.party.core.model.BaseModel;
import com.party.core.model.dynamic.Dynamic;
import com.party.mobile.biz.currentUser.CurrentUserBizService;
import com.party.mobile.biz.dynamic.DynamicBizService;
import com.party.mobile.web.dto.AjaxResult;
import com.party.mobile.web.dto.dynamic.input.DynamicInput;
import com.party.mobile.web.dto.dynamic.output.DyMemberOutput;
import com.party.mobile.web.dto.dynamic.output.DynamicOutput;
import com.party.mobile.web.dto.dynamic.output.HandleCommentOutput;
import com.party.mobile.web.dto.dynamic.output.HandleLoveOutput;
import com.party.common.utils.PartyCode;
import com.party.mobile.web.dto.login.output.CurrentUser;
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
 * 登录用户对动态的操作接口
 *
 * @Author: Wesley
 * @Description:
 * @Date: Created in 17:18 16/11/15
 * @Modified by:
 */
@Controller
@RequestMapping(value = "member/dynamic")
public class MemberDynamicController {

    @Autowired
    private DynamicBizService dynamicBizService;
    @Autowired
    private  CurrentUserBizService currentUserBizService;
    /**
     * 设置当前登录用户关注某个会员
     * @param id 会员主键id
     * @param request
     * @return
     */
    @Authorization
    @ResponseBody
    @RequestMapping(value = "/setFocus")
    public AjaxResult setFocus(String id, HttpServletRequest request)
    {
        if (Strings.isNullOrEmpty(id))
        {
            return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, "参数不合法");
        }

        try{
            DyMemberOutput output = dynamicBizService.setFocus(id, request);
            return AjaxResult.success(output);
        }catch (BusinessException be)
        {
            return AjaxResult.error(be.getCode(), be.getMessage());
        }
    }

    /**
     * 设置当前登录用户取消关注某个会员
     * @param id 会员主键id
     * @param request
     * @return
     */
    @Authorization
    @ResponseBody
    @RequestMapping(value = "/removeFocus")
    public AjaxResult removeFocus(String id, HttpServletRequest request)
    {
        if (Strings.isNullOrEmpty(id))
        {
            return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, "参数不合法");
        }

        try{
            DyMemberOutput output = dynamicBizService.removeFocus(id, request);
            return AjaxResult.success(output);
        }catch (BusinessException be)
        {
            return AjaxResult.error(be.getCode(), be.getMessage());
        }
    }

    /**
     * 根据会议id，查询该会员的粉丝列表
     * @param page 分页参数
     * @param request
     * @return
     */
    @Authorization
    @ResponseBody
    @RequestMapping(value = "/listFans")
    public AjaxResult listFans(Page page, HttpServletRequest request)
    {
        try {
            List<DyMemberOutput> outputList = dynamicBizService.listFans(page, request);
            return AjaxResult.success(outputList, page);
        }catch (BusinessException be)
        {
            return AjaxResult.error(be.getCode(), be.getMessage());
        }
    }

    /**
     * 根据会员id，查询该会员的关注列表
     * @param page 分页参数
     * @param request
     * @return
     */
    @Authorization
    @ResponseBody
    @RequestMapping(value = "/listFocus")
    public AjaxResult listFocus(Page page, HttpServletRequest request)
    {
        try {
            List<DyMemberOutput> outputList = dynamicBizService.listFocus(page, request);
            return AjaxResult.success(outputList, page);
        }catch (BusinessException be)
        {
            return AjaxResult.error(be.getCode(), be.getMessage());
        }
    }

    /**
     * 查看当前登录用户的动态列表
     * @param page
     * @param request
     * @return
     */
    @Authorization
    @ResponseBody
    @RequestMapping(value = "/listDynamic")
    public AjaxResult listDynamic(Page page, HttpServletRequest request)
    {
        try {
            CurrentUser currentUser = currentUserBizService.getCurrentUser(request);
            Dynamic entity = new Dynamic();
            entity.setCreateBy(currentUser.getId());
            entity.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
            List<DynamicOutput> dynamicOutputList = dynamicBizService.listDynamicNew(entity,null,page,currentUser.getId());
            return AjaxResult.success(dynamicOutputList, page);
        }catch (BusinessException be)
        {
            return AjaxResult.error(be.getCode(), be.getMessage());
        }
    }

    /**
     * 查看当前登录用户的图片列表
     * @param page
     * @param request
     * @return
     */
    @Authorization
    @ResponseBody
    @RequestMapping(value = "/listPic")
    public AjaxResult listPic(Page page, HttpServletRequest request)
    {
        try {
            List<String> dynamicOutputList = dynamicBizService.getPicList(page, request);
            return AjaxResult.success(dynamicOutputList, page);
        }catch (BusinessException be)
        {
            return AjaxResult.error(be.getCode(), be.getMessage());
        }
    }


    /**
     * 设置点赞
     * @param id 动态主键id
     * @param request
     * @return
     */
    @Authorization
    @ResponseBody
    @RequestMapping(value = "/setLove")
    public AjaxResult setLove(String id, HttpServletRequest request)
    {
        if (Strings.isNullOrEmpty(id))
        {
            return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, "参数错误");
        }

        try {
            HandleLoveOutput loveOutput = dynamicBizService.setDynamicLove(id, 1, request);
            return AjaxResult.success(loveOutput);
        }catch (BusinessException be)
        {
            return AjaxResult.error(be.getCode(), be.getMessage());
        }
    }

    /**
     * 删除点赞
     * @param id 动态主键id
     * @param request
     * @return
     */
    @Authorization
    @ResponseBody
    @RequestMapping(value = "/removeLove")
    public AjaxResult removeLove(String id, HttpServletRequest request)
    {
        if (Strings.isNullOrEmpty(id))
        {
            return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, "参数错误");
        }

        try {
            HandleLoveOutput loveOutput = dynamicBizService.setDynamicLove(id, 0, request);
            return AjaxResult.success(loveOutput);
        }catch (BusinessException be)
        {
            return AjaxResult.error(be.getCode(), be.getMessage());
        }
    }

    /**
     * 增加评论
     * @param id 动态主键id
     * @param content 评论内容
     * @param request
     * @return
     */
    @Authorization
    @ResponseBody
    @RequestMapping(value = "/addComment")
    public AjaxResult addComment(String id, String content, HttpServletRequest request)
    {
        if (Strings.isNullOrEmpty(id) || Strings.isNullOrEmpty(content))
        {
            return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, "参数错误");
        }

        try {
            HandleCommentOutput commentOutput = dynamicBizService.setDynamicComment(id, content, request);
            return AjaxResult.success(commentOutput);
        }catch (BusinessException be)
        {
            return AjaxResult.error(be.getCode(), be.getMessage());
        }
    }

    @Authorization
    @ResponseBody
    @RequestMapping(value = "/publishDynamic")
    public AjaxResult publishDynamic(@Validated DynamicInput dynamicInput, BindingResult result, HttpServletRequest request)
    {
        //数据验证
        if (result.hasErrors()){
            List<ObjectError> allErros = result.getAllErrors();
            //TODO 验证结果返回给前端
            allErros.forEach(error -> System.out.println(error.getDefaultMessage()));
        }
        dynamicInput.setPics(dynamicInput.getPics().replaceAll(",","|"));
        try {
            dynamicBizService.saveDynamic(dynamicInput, request);

            return AjaxResult.success();

        }catch (BusinessException be)
        {
            return AjaxResult.error(be.getCode(), be.getMessage());
        } catch (Exception e) {
            return AjaxResult.error();
        }

    }

}
