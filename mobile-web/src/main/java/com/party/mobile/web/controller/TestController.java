package com.party.mobile.web.controller;


import com.party.authorization.manager.impl.RedisTokenManager;
import com.party.common.utils.PinyinUtil;
import com.party.core.model.activity.Activity;
import com.party.core.model.city.Area;
import com.party.core.model.city.City;
import com.party.core.model.crowdfund.Project;
import com.party.core.model.member.Industry;
import com.party.core.model.member.Member;
import com.party.core.model.order.OrderForm;
import com.party.core.service.activity.IActivityService;
import com.party.core.service.city.IAreaService;
import com.party.core.service.city.ICityService;
import com.party.core.service.crowdfund.IProjectService;
import com.party.core.service.member.IIndustryService;
import com.party.core.service.member.IMemberService;
import com.party.core.service.notify.IInstanceService;
import com.party.core.service.order.IOrderFormService;
import com.party.mobile.biz.order.MessageOrderBizService;
import com.party.mobile.biz.order.OrderBizService;
import com.party.mobile.web.dto.AjaxResult;
import com.party.mobile.web.dto.activity.output.ActivityOutput;
import com.party.authorization.annotation.Authorization;
import com.party.notify.notifyPush.servce.INotifySendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


/**
 * party
 * Created by wei.li
 * on 2016/9/20 0020.
 */
@Controller
@RequestMapping("/mobile")
public class TestController {

    @Autowired
    IMemberService memberService;

    @Autowired
    IAreaService areaService;
    @Autowired
    IIndustryService industryService;

    @Autowired
    IActivityService activityService;

    @Autowired
    ICityService cityService;

    @Autowired
    RedisTokenManager redisTokenManager;

    @Autowired
    IProjectService projectService;

    @Autowired
    IOrderFormService orderFormService;

    @Autowired
    OrderBizService orderBizService;

    @Autowired
    INotifySendService notifySendService;

    @Autowired
    MessageOrderBizService messageOrderBizService;

    @Autowired
    IInstanceService instanceService;

    @ResponseBody
    @RequestMapping("/updateAreaPinyin")
    public AjaxResult updateAreaPinyin(){
        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.setSuccess(true);
        List<Area> areas = areaService.listAll();
        for(Area area :areas){
            area.setPinyin(PinyinUtil.hanziToPinyin(area.getName(), ""));
            areaService.update(area);
        }
        return ajaxResult;
    }
    @ResponseBody
    @RequestMapping("/updateIndustryPinyin")
    public AjaxResult updateIndustryPinyin(){
        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.setSuccess(true);
        List<Industry> industries = industryService.listAll();
        for(Industry industry :industries){
            industry.setPinyin(PinyinUtil.hanziToPinyin(industry.getName(), ""));
            industryService.update(industry);
        }
        return ajaxResult;
    }
    @ResponseBody
    @RequestMapping("/mm")
    public AjaxResult test1(){
        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.setSuccess(true);

        redisTokenManager.createRelationship("key", "token");

        String key = redisTokenManager.getKey("token");

        ajaxResult.setData(key);
        return ajaxResult;
    }


    @ResponseBody
    @RequestMapping("/m")
    public AjaxResult test(){
        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.setSuccess(true);
        OrderForm orderForm =orderFormService.get("e0aa453b1f714c22873a0164ae479a27");
        Project project = projectService.get("77cd9c54705e49d7bf696834fa6af61c");
        Member member = memberService.get("71080f2503d44867ad1ec27f7487915a");
        notifySendService.sendProjectStage(project, member, 10);
        return ajaxResult;
    }

    @ResponseBody
    @RequestMapping("/h")
    @Authorization
    public ResponseEntity<String> home() {
        return new ResponseEntity<>("Hello World", HttpStatus.OK);
    }

}
