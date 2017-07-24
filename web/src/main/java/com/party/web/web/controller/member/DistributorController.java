package com.party.web.web.controller.member;

import com.google.common.collect.Maps;
import com.party.common.paging.Page;
import com.party.core.model.member.Member;
import com.party.core.service.member.IMemberService;
import com.party.web.web.dto.AjaxResult;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;

/**
 * 分销商管理
 * Created by wei.li
 *
 * @date 2017/3/7 0007
 * @time 9:59
 */

@Controller
@RequestMapping(value = "member/distributor")
public class DistributorController {

    @Autowired
    private IMemberService memberService;


    /**
     * 列表数据
     * @param page 分页参数
     * @return 交互数据
     */
    @ResponseBody
    @RequestMapping(value = "list")
    public HashMap<String, Object> list(Page page){
        HashMap<String, Object> result = Maps.newHashMap();

        //TODO 过滤分销商
        List<Member> memberList = memberService.listPage(new Member(), page);
        result.put("page", page);
        result.put("datas", memberList);
        return result;
    }


    /**
     * 了表页面
     * @return
     */
    @RequestMapping(value = "listView")
    public ModelAndView listView(){
        ModelAndView modelAndView = new ModelAndView("/member/distributor");
        return modelAndView;
    }


    /**
     * 分销开关
     * @param id 分销商编号
     * @param status 开关（0：关闭 1：开启）
     * @return 交互结果
     */
    public AjaxResult distributorSwitch(String id, Integer status){
        AjaxResult ajaxResult = new AjaxResult();

        return ajaxResult;
    }
}
