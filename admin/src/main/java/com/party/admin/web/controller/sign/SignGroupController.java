package com.party.admin.web.controller.sign;

import com.google.common.base.Strings;
import com.party.admin.biz.sign.SignGroupBizService;
import com.party.admin.utils.RealmUtils;
import com.party.admin.web.dto.AjaxResult;
import com.party.admin.web.dto.input.sign.SignGroupInput;
import com.party.common.paging.Page;
import com.party.core.exception.BusinessException;
import com.party.core.model.member.Member;
import com.party.core.model.sign.GroupAuthor;
import com.party.core.model.sign.SignGroup;
import com.party.core.service.sign.ISignGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * 签到小组控制器
 * Created by wei.li
 *
 * @date 2017/6/6 0006
 * @time 10:45
 */

@Controller
@RequestMapping(value = "sign/group")
public class SignGroupController {

    @Autowired
    private ISignGroupService signGroupService;

    @Autowired
    private SignGroupBizService signGroupBizService;

    /**
     * 签到小组列表
     * @param signGroupInput 签到小组
     * @param page 分页参数
     * @return 交互数据
     */
    @RequestMapping(value = "list")
    public ModelAndView list(SignGroupInput signGroupInput, Page page){
        ModelAndView modelAndView = new ModelAndView("sign/signGroupList");
        List<GroupAuthor> list = signGroupBizService.list(signGroupInput, page);
        modelAndView.addObject("list", list);
        modelAndView.addObject("page", page);
        return modelAndView;
    }


    /**
     * 签到小组查询
     * @param id 编号
     * @return 交互数据
     */
    @RequestMapping(value = "view")
    public ModelAndView view(String id, String projectId){
        ModelAndView modelAndView = new ModelAndView("sign/signGroupView");
        SignGroup signGroup;
        if (Strings.isNullOrEmpty(id)){
            signGroup = new SignGroup();
            signGroup.setProjectId(projectId);
        }
        else {
            signGroup = signGroupService.get(id);
        }
        modelAndView.addObject("signGroup", signGroup);
        return modelAndView;
    }


    /**
     * 保存签到小组
     * @param signGroup 签到小组
     * @return 交互数据
     */
    @ResponseBody
    @RequestMapping(value = "save")
    public AjaxResult save(SignGroup signGroup){
        AjaxResult ajaxResult = new AjaxResult(true);
        Member member = RealmUtils.getCurrentUser();
        if (Strings.isNullOrEmpty(signGroup.getId())){
            signGroup.setCreateBy(member.getId());
            signGroup.setUpdateBy(member.getId());
            signGroupService.insert(signGroup);
        }
        else {
            signGroup.setUpdateBy(member.getId());
            signGroupService.update(signGroup);
        }
        return ajaxResult;
    }

    /**
     * 删除签到小组
     * @param id 编号
     * @return 交互数据
     */
    @ResponseBody
    @RequestMapping(value = "delete")
    public AjaxResult delete(String id){
        AjaxResult ajaxResult = new AjaxResult();
        try {
            signGroupService.delete(id);
        } catch (BusinessException be) {
            ajaxResult.setSuccess(false);
            ajaxResult.setDescription(be.getMessage());
            return ajaxResult;
        }
        ajaxResult.setSuccess(true);
        return ajaxResult;
    }
}
