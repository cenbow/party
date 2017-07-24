package com.party.admin.web.controller.sign;

import com.google.common.base.Strings;
import com.party.admin.biz.sign.SignApplyBizService;
import com.party.admin.web.dto.AjaxResult;
import com.party.common.paging.Page;
import com.party.core.model.sign.GroupMember;
import com.party.core.model.sign.SignApply;
import com.party.core.model.sign.SignGroup;
import com.party.core.service.sign.ISignApplyService;
import com.party.core.service.sign.ISignGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * 签到小组成员控制器
 * Created by wei.li
 *
 * @date 2017/6/6 0006
 * @time 14:02
 */
@Controller
@RequestMapping(value = "sign/member")
public class SignGroupMemberController {

    @Autowired
    private ISignApplyService signApplyService;

    @Autowired
    private ISignGroupService signGroupService;

    @Autowired
    private SignApplyBizService signApplyBizService;

    /**
     * 签到小组成员列表
     * @param groupMember 签到小组成员
     * @param page 分页参数
     * @return 成员列表
     */
    @RequestMapping(value = "list")
    public ModelAndView list(GroupMember groupMember, Page page){
        ModelAndView modelAndView = new ModelAndView("sign/signApplyList");
        List<GroupMember> list = signApplyService.groupMemberList(groupMember, page);

        //所属队伍
        SignGroup signGroup = new SignGroup();
        signGroup.setProjectId(groupMember.getProjectId());
        List<SignGroup> signGroupList = signGroupService.list(signGroup);
        modelAndView.addObject("show", true);
        modelAndView.addObject("signGroupList", signGroupList);
        modelAndView.addObject("list", list);
        modelAndView.addObject("page", page);
        return modelAndView;
    }


    /**
     * 签到小组成员列表
     * @param groupMember 签到小组成员
     * @param page 分页参数
     * @return 成员列表
     */
    @RequestMapping(value = "group/list")
    public ModelAndView groupList(GroupMember groupMember, Page page){
        ModelAndView modelAndView = new ModelAndView("sign/signApplyList");
        List<GroupMember> list = signApplyService.groupMemberList(groupMember, page);
        SignGroup myGroup = signGroupService.get(groupMember.getGroupId());
        modelAndView.addObject("myGroup", myGroup);
        modelAndView.addObject("list", list);
        modelAndView.addObject("page", page);
        return modelAndView;
    }


    /**
     * 分配小组
     * @param id 报名编号
     * @return 交互数据
     */
    @RequestMapping(value = "distribute")
    public ModelAndView distributeGroup(String id){
        ModelAndView modelAndView = new ModelAndView("sign/distributeGroup");
        SignApply signApply = signApplyService.get(id);

        SignGroup signGroup = new SignGroup();
        signGroup.setProjectId(signApply.getProjectId());
        List<SignGroup> signGroupList = signGroupService.list(signGroup);
        SignGroup s = new SignGroup();
        s.setName("无队伍");
        s.setId("");
        signGroupList.add(s);

        modelAndView.addObject("signGroupList", signGroupList);
        modelAndView.addObject("signApply", signApply);
        return modelAndView;
    }

    /**
     * 保存分配信息
     * @param id 报名编号
     * @param groupId 分组编号
     * @return 交互数据
     */
    @ResponseBody
    @RequestMapping(value = "distribute/save")
    public AjaxResult distributeSave(String id, String groupId){
        AjaxResult ajaxResult = new AjaxResult();
        signApplyBizService.changeGroup(id, groupId);
        ajaxResult.setSuccess(true);
        return ajaxResult;
    }

    /**
     * 删除签到报名
     * @param id 编号
     * @return 交互数据
     */
    @ResponseBody
    @RequestMapping(value = "delete")
    public AjaxResult delete(String id){
        AjaxResult ajaxResult = new AjaxResult();
        signApplyBizService.delete(id);
        ajaxResult.setSuccess(true);
        return ajaxResult;
    }

    /**
     * 签到报名审核
     * @param status 状态
     * @param id 报名编号
     * @return 交互数据
     */
    @ResponseBody
    @RequestMapping(value = "check")
    public AjaxResult check(Integer status, String id){
        AjaxResult ajaxResult = new AjaxResult();
        signApplyBizService.check(status, id);
        ajaxResult.setSuccess(true);
        return ajaxResult;
    }


    /**
     * 成绩状态审核
     * @param status 状态
     * @param id 报名编号
     * @return 交互数据
     */
    @ResponseBody
    @RequestMapping(value = "checkGrade")
    public AjaxResult checkGrade(Integer status, String id){
        AjaxResult ajaxResult = new AjaxResult();
        signApplyBizService.checkGrade(status, id);
        ajaxResult.setSuccess(true);
        return ajaxResult;
    }
}
