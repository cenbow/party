package com.party.mobile.web.controller.sign;

import com.party.core.model.sign.SignProject;
import com.party.core.service.crowdfund.IProjectService;
import com.party.core.service.sign.ISignProjectService;
import com.party.mobile.biz.currentUser.CurrentUserBizService;
import com.party.mobile.biz.sign.SignProjectBizService;
import com.party.mobile.web.dto.AjaxResult;
import com.party.mobile.web.dto.login.output.CurrentUser;
import com.party.mobile.web.dto.sign.output.ApplyDetailOutput;
import com.party.mobile.web.dto.sign.output.SignProjectOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 签到项目控制器
 * Created by wei.li
 *
 * @date 2017/6/8 0008
 * @time 17:09
 */

@Controller
@RequestMapping(value = "sign/project")
public class SignProjectController {

    @Autowired
    private SignProjectBizService signProjectBizService;

    @Autowired
    private CurrentUserBizService currentUserBizService;


    /**
     * 获取签到项目详情
     * @param id 项目编号
     * @param request 请求参数
     * @return 交互数据
     */
    @ResponseBody
    @RequestMapping(value = "detail")
    public AjaxResult detail(String id, HttpServletRequest request){
        AjaxResult ajaxResult = new AjaxResult();
        CurrentUser currentUser = currentUserBizService.getCurrentUser(request);
        SignProjectOutput signProjectOutput = signProjectBizService.get(id, currentUser);
        ajaxResult.setSuccess(true);
        ajaxResult.setData(signProjectOutput);
        return ajaxResult;
    }


    /**
     * 报名详情页面
     * @param id 项目编号
     * @return 交互数据
     */
    @ResponseBody
    @RequestMapping(value = "apply/detail")
    public AjaxResult applyDetail(String id){
        AjaxResult ajaxResult = new AjaxResult();
        ApplyDetailOutput applyDetailOutput = signProjectBizService.getApplyDetail(id);
        ajaxResult.setData(applyDetailOutput);
        ajaxResult.setSuccess(true);
        return ajaxResult;
    }
}
