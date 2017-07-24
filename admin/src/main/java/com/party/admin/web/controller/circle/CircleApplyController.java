package com.party.admin.web.controller.circle;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.party.admin.utils.RealmUtils;
import com.party.admin.web.dto.AjaxResult;
import com.party.admin.web.dto.input.common.CommonInput;
import com.party.common.paging.Page;
import com.party.core.model.circle.Circle;
import com.party.core.model.circle.CircleApply;
import com.party.core.model.member.Member;
import com.party.core.service.circle.ICircleApplyService;
import com.party.core.service.circle.ICircleService;
import com.party.core.service.circle.biz.CircleApplyBizService;

/**
 * 圈子申请
 */
@Controller
@RequestMapping(value = "/circle/apply")
public class CircleApplyController {

    @Autowired
    private ICircleService circleService;
    @Autowired
    private ICircleApplyService circleApplyService;
    @Autowired
    private CircleApplyBizService circleApplyBizService;


    /**
     * 圈子审核列表页面
     */
    @RequestMapping(value = {"list", ""})
    public ModelAndView list(Page page, CircleApply apply, Member member, CommonInput commonInput) {
        page.setLimit(20);
        ModelAndView mv = new ModelAndView("circle/applyList");
        Map<String, Object> params = CommonInput.appendParams(commonInput);
		mv.addObject("input", commonInput);
        apply.setCheckStatus(apply.getCheckStatus() == null ? 0:apply.getCheckStatus());
        params.put("mobile",member.getMobile());
        params.put("realname", member.getRealname());
        List<Map<String, Object>> list = circleApplyService.webListPage(apply, params, page);
        Circle circle = circleService.get(apply.getCircle());
        mv.addObject("circle", circle);
        mv.addObject("list", list);
        mv.addObject("page", page);
        mv.addObject("member", member);
        mv.addObject("apply", apply);
        return mv;
    }


    /**
     * 审核通过
     */
    @ResponseBody
    @RequestMapping(value = "pass")
    public AjaxResult pass(String id) throws Exception {
        AjaxResult ajaxResult = new AjaxResult();
        try {
            circleApplyBizService.pass(id, RealmUtils.getCurrentUser().getId());
        }catch (Exception e) {
            ajaxResult.setSuccess(false);
        }
        ajaxResult.setSuccess(true);
        return ajaxResult;
    }

    /**
     * 审核拒绝
     */
    @ResponseBody
    @RequestMapping(value = "reject")
    public AjaxResult reject(String id) throws Exception {
        AjaxResult ajaxResult = new AjaxResult();
        try {
            circleApplyBizService.reject(id, RealmUtils.getCurrentUser().getId());
        }catch (Exception e) {
            ajaxResult.setSuccess(false);
        }
        ajaxResult.setSuccess(true);
        return ajaxResult;
    }

}
