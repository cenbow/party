package com.party.admin.web.controller.system;

import com.google.common.collect.Maps;
import com.party.admin.biz.system.MainBizService;
import com.party.admin.web.dto.AjaxResult;
import com.party.core.service.member.IMemberActService;
import com.party.core.service.member.IMemberService;
import com.party.core.service.order.IOrderFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by wei.li
 *
 * @date 2017/5/8 0008
 * @time 10:43
 */

@Controller
@RequestMapping(value = "/system/main/")
public class MainController {

    @Autowired
    private IMemberService memberService;

    @Autowired
    private IMemberActService memberActService;

    @Autowired
    private IOrderFormService orderFormService;

    @Autowired
    private MainBizService mainBizService;

    /**
     * 主页视图
     * @return
     */
    @RequestMapping(value = "view")
    public ModelAndView view(){
        ModelAndView modelAndView = new ModelAndView("system/main/main");
        Integer allMember = memberService.allCount();
        Integer unauditedMember = memberService.unauditedCount();
        Integer allOrder = orderFormService.allCount();
        Integer allApply = memberActService.allCount();
        modelAndView.addObject("allMember", allMember);
        modelAndView.addObject("unauditedMember", unauditedMember);
        modelAndView.addObject("allOrder", allOrder);
        modelAndView.addObject("allApply", allApply);
        return modelAndView;
    }

    /**
     * 统计数据
     * @return 交互数据
     */
    @ResponseBody
    @RequestMapping(value = "data")
    public AjaxResult data(){
        AjaxResult ajaxResult = new AjaxResult();
        HashMap<String, List> map = Maps.newHashMap();
        List<String> dayList = mainBizService.listDay();
        List<Integer> memberList = mainBizService.memberList();
        List<Integer> applyList = mainBizService.applyList();
        List<Integer> orderList = mainBizService.orderList();
        map.put("dayList", dayList);
        map.put("memberList", memberList);
        map.put("applyList", applyList);
        map.put("orderList", orderList);
        ajaxResult.setData(map);
        return ajaxResult;
    }

}
