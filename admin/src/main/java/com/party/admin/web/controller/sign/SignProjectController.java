package com.party.admin.web.controller.sign;

import com.google.common.base.Strings;
import com.party.admin.biz.sign.SignProjectBizService;
import com.party.admin.utils.RealmUtils;
import com.party.admin.web.dto.AjaxResult;
import com.party.admin.web.dto.input.sign.SignProjectInput;
import com.party.admin.web.dto.output.sign.SignProjectOutput;
import com.party.common.paging.Page;
import com.party.core.exception.BusinessException;
import com.party.core.model.YesNoStatus;
import com.party.core.model.member.Member;
import com.party.core.model.sign.SignProject;
import com.party.core.model.sign.SignProjectAuthor;
import com.party.core.service.sign.ISignProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * 签到项目控制器
 * Created by wei.li
 *
 * @date 2017/6/6 0006
 * @time 10:11
 */

@Controller
@RequestMapping(value = "sign/project")
public class SignProjectController {

    @Autowired
    private ISignProjectService signProjectService;

    @Autowired
    private SignProjectBizService signProjectBizService;

    /**
     * 签到信息列表
     * @param signProjectAuthor 签到信息
     * @param page 分页参数
     * @return 列表信息
     */
    @RequestMapping(value = "list")
    public ModelAndView list(SignProjectAuthor signProjectAuthor, Page page){
        ModelAndView modelAndView = new ModelAndView("sign/signProjectList");
        List<SignProjectOutput> list = signProjectBizService.list(signProjectAuthor, page);
        modelAndView.addObject("list", list);
        modelAndView.addObject("page", page);
        return modelAndView;
    }

    /**
     * 签到信息试图
     * @param id 编号
     * @return 交互信息
     */
    @RequestMapping(value = "view")
    public ModelAndView view(String id){
        ModelAndView modelAndView = new ModelAndView("sign/signProjectView");
        SignProject signProject;
        if (Strings.isNullOrEmpty(id)){
            signProject = new SignProject();
            signProject.setApplyCheck(YesNoStatus.NO.getCode());
            signProject.setRankShow(YesNoStatus.YES.getCode());
            signProject.setGradeCheck(YesNoStatus.NO.getCode());
        }
        else {
            signProject = signProjectService.get(id);
        }
        modelAndView.addObject("signProject", signProject);
        return modelAndView;
    }


    /**
     * 保存签到信息
     * @param signProjectInput 签到项目
     * @return 交互数据
     */
    @ResponseBody
    @RequestMapping(value = "save")
    public AjaxResult save(SignProjectInput signProjectInput){
        AjaxResult ajaxResult = new AjaxResult();
        SignProject signProject = SignProjectInput.transform(signProjectInput);
        Member member = RealmUtils.getCurrentUser();
        if (Strings.isNullOrEmpty(signProjectInput.getId())){
            signProject.setCreateBy(member.getId());
            signProject.setUpdateBy(member.getId());
            signProjectService.insert(signProject);
        }
        else {
            signProject.setUpdateBy(member.getId());
            signProjectService.update(signProject);
        }
        ajaxResult.setSuccess(true);
        return ajaxResult;
    }

    /**
     * 删除签到项目
     * @param id 编号
     * @return 交互数据
     */
    @ResponseBody
    @RequestMapping(value = "delete")
    public AjaxResult delete(String id){
        AjaxResult ajaxResult = new AjaxResult(true);
        try {
            signProjectService.delete(id);
        } catch (BusinessException be) {
            ajaxResult.setSuccess(false);
            ajaxResult.setDescription(be.getMessage());
            return ajaxResult;
        }
        return ajaxResult;
    }
}
