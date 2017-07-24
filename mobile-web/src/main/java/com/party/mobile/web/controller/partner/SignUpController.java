package com.party.mobile.web.controller.partner;

import com.party.authorization.annotation.Authorization;
import com.party.common.paging.Page;
import com.party.core.exception.BusinessException;
import com.party.mobile.biz.currentUser.CurrentUserBizService;
import com.party.mobile.biz.partner.SignUpBizService;
import com.party.mobile.web.dto.AjaxResult;
import com.party.mobile.web.dto.login.output.CurrentUser;
import com.party.mobile.web.dto.partner.output.SignUpListOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 合作商报名控制层
 * party
 * Created by wei.li
 * on 2016/10/24 0024.
 */

@Controller
@RequestMapping(value = "/partner/signUp")
public class SignUpController {

    @Autowired
    CurrentUserBizService currentUserBizService;

    @Autowired
    SignUpBizService signUpBizService;


    /**
     * 获取合作商报名列表
     * @param request 请求参数
     * @param page 交互参数
     * @return 交互数据
     */
    @ResponseBody
    @Authorization
    @RequestMapping(value = "/list")
    public AjaxResult list(HttpServletRequest request, Page page){

        CurrentUser currentUser = currentUserBizService.getCurrentUser(request);

        List<SignUpListOutput> signUpListOutputList;
        try {
            signUpListOutputList = signUpBizService.list(currentUser.getId(), page);
        } catch (BusinessException be) {
            return AjaxResult.error(be.getCode(), be.getMessage());
        }
        return AjaxResult.success(signUpListOutputList);
    }
}
